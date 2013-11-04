package me.zjor.manager;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

/**
 * @author: Sergey Royz
 * @since: 03.11.2013
 */
public abstract class AbstractManager {

    @Inject
    private Provider<EntityManager> emProvider;

    protected EntityManager jpa() {
        return emProvider.get();
    }

}
