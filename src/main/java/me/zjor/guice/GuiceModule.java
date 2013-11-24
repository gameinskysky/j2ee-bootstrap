package me.zjor.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletScopes;
import me.zjor.JpaInitializer;
import me.zjor.app.service.TaskService;
import me.zjor.auth.*;
import me.zjor.app.controller.AjaxController;
import me.zjor.controller.Application;
import me.zjor.controller.SampleController;
import me.zjor.app.manager.TaskManager;
import me.zjor.session.SessionManager;
import me.zjor.session.SessionService;

/**
 * @author: Sergey Royz
 * @since: 31.10.2013
 */
public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(JpaInitializer.class).asEagerSingleton();
        bind(SessionService.class).asEagerSingleton();

        bind(SessionManager.class).in(Singleton.class);
        bind(AuthUserManager.class).in(Singleton.class);
        bind(AuthUserService.class).in(Singleton.class);

        bind(TaskManager.class).in(Singleton.class);
        bind(TaskService.class).in(Singleton.class);

        bind(AuthController.class).in(Singleton.class);
        bind(AjaxController.class).in(Singleton.class);
        bind(SampleController.class).in(Singleton.class);
        bind(Application.class).in(Singleton.class);

        bind(String.class).annotatedWith(UserId.class).toProvider(UserIdProvider.class).in(ServletScopes.REQUEST);

    }

}
