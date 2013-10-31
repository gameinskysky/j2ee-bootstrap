package me.zjor.guice;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import me.zjor.controller.AjaxController;

/**
 * @author: Sergey Royz
 * @since: 31.10.2013
 */
public class GuiceServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(AjaxController.class).in(Singleton.class);

        filter("/*").through(GuiceContainer.class, ImmutableMap.of(
                "com.sun.jersey.config.property.WebPageContentRegex", "(/static/.*)|(.*\\.jsp)",
                "com.sun.jersey.api.json.POJOMappingFeature", "true"
        ));

    }
}
