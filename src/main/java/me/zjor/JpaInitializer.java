package me.zjor;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

/**
 * @author: Sergey Royz
 * @since: 18.09.2013
 */
public class JpaInitializer {

    @Inject
    public JpaInitializer(final PersistService persistService) {
        persistService.start();
    }
}
