package me.zjor.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * @author: Sergey Royz
 * @since: 31.10.2013
 */
public class AppGuiceServletContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        //TODO: add JPA Unit
        return Guice.createInjector(new GuiceModule(), new GuiceServletModule());
    }
}
