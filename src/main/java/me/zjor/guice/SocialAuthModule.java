package me.zjor.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.zjor.auth.AuthProvider;
import me.zjor.auth.social.FacebookAppProperties;
import me.zjor.auth.social.FacebookAuthController;
import me.zjor.auth.social.FacebookAuthProvider;

/**
 * @author: Sergey Royz
 * Date: 24.03.2014
 */
public class SocialAuthModule extends AbstractModule {


	@Override
	protected void configure() {
		bind(FacebookAppProperties.class).toProvider(FacebookAppPropertiesProvider.class).in(Singleton.class);
		bind(AuthProvider.class).to(FacebookAuthProvider.class).in(Singleton.class);
		bind(FacebookAuthController.class).in(Singleton.class);
	}

	protected static class FacebookAppPropertiesProvider implements Provider<FacebookAppProperties> {

		@Override
		public FacebookAppProperties get() {
			return new FacebookAppProperties() {
				@Override
				public String getClientId() {
					return "735092636525455";
				}

				@Override
				public String getClientSecret() {
					return "6c0c5f7a19a4cb506ca1de5d1e3622e1";
				}
			};
		}
	}
}
