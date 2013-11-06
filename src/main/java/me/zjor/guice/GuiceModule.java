package me.zjor.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import me.zjor.JpaInitializer;
import me.zjor.controller.AjaxController;
import me.zjor.controller.SampleController;
import me.zjor.manager.TaskManager;
import me.zjor.session.Session;
import me.zjor.session.SessionManager;

/**
 * @author: Sergey Royz
 * @since: 31.10.2013
 */
public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(JpaInitializer.class).asEagerSingleton();

        bind(SessionManager.class).in(Singleton.class);
        bind(Session.class).in(Singleton.class);

        bind(TaskManager.class).in(Singleton.class);

        bind(AjaxController.class).in(Singleton.class);
        bind(SampleController.class).in(Singleton.class);
    }
}
