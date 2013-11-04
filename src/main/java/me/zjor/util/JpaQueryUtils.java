package me.zjor.util;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author: Sergey Royz
 * @since: 03.11.2013
 */
public class JpaQueryUtils {

    /**
     * Retrieves first element from JPA query result set or null
     * @param query
     * @param <T>
     * @return
     */
    public static <T> T getFirstOrNull(TypedQuery<T> query) {
        List<T> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

}
