package me.zjor.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * @author: Sergey Royz
 * @since: 31.10.2013
 */
public class AppGuiceServletContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {

        return Guice.createInjector(
				new JpaPersistModule("AppJpaUnit"),
				new GuiceModule(),
				new SocialAuthModule(),
				new GuiceServletModule());
    }
}
