
package com.omoscorplation.webtree.common;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Objects;

/**
 *
 * @author Takuto Esumi
 */
public class Util {

    public Util() {
    }
    
    public static boolean nb(Object value){
    
        if (Objects.isNull(value)) {
            return true;
        }
        if (value instanceof String) {
            String strValue = (String) value;
            return strValue.trim().isEmpty();
        }
        if (value instanceof Number) {
            Number numberValue = (Number) value;
            return numberValue.doubleValue() == 0.0;
        }
        if (value instanceof Collection) {
            Collection<?> collectionValue = (Collection<?>) value;
            return collectionValue.isEmpty();
        }
        if (value.getClass().isArray()) {
            if (Array.getLength(value) == 0) {
                return true;
            }
        }
        if (value instanceof Boolean) {
            return !((Boolean) value);
        }

        return false; // その他の型は空ではないとみなす
    }
}
