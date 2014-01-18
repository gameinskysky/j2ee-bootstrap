package me.zjor.session.expiration;

import lombok.extern.slf4j.Slf4j;
import me.zjor.session.Session;
import me.zjor.session.SessionManager;

import java.util.Date;

/**
 * @author: Sergey Royz
 * Date: 18.01.2014
 */
@Slf4j
public abstract class SessionExpirationPolicyChain {

	protected final SessionManager sessionManager;

	private final SessionExpirationPolicyChain chain;

	protected SessionExpirationPolicyChain(SessionManager sessionManager, SessionExpirationPolicyChain chain) {
		this.sessionManager = sessionManager;
		this.chain = chain;
	}

	protected abstract Session doProcess(Session session);

	public Session process(Session session) {
		session = doProcess(session);
		if (chain != null && session != null) {
			return chain.process(session);
		}

		return session;
	}

	public static class ExpireSessionPolicy extends SessionExpirationPolicyChain {

		public ExpireSessionPolicy(SessionManager sessionManager, SessionExpirationPolicyChain chain) {
			super(sessionManager, chain);
		}

		@Override
		protected Session doProcess(Session session) {
			if (session.getExpirationDate().before(new Date())) {
				sessionManager.remove(session.getSessionId());
				return null;
			}
			return session;
		}
	}

	public static class ExtendSessionPolicy extends SessionExpirationPolicyChain {

		private final long timeDelta;

		public ExtendSessionPolicy(long timeDelta, SessionManager sessionManager, SessionExpirationPolicyChain chain) {
			super(sessionManager, chain);
			this.timeDelta = timeDelta;
		}

		@Override
		protected Session doProcess(Session session) {
			long currentExpTime = session.getExpirationDate().getTime();
			long newExpTime = new Date().getTime() + timeDelta;
			if (newExpTime - currentExpTime > Session.DEFAULT_MIN_CHANGE_INTERVAL_MILLIS) {
				session.setExpirationDate(new Date(new Date().getTime() + timeDelta));
				log.info("Extending session lifetime: {}", session.getExpirationDate());
				sessionManager.persist(session);
			}
			return session;
		}
	}

}
