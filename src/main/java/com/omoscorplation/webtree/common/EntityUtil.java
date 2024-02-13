/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.omoscorplation.webtree.common;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.lang.reflect.Field;

/**
 *
 * @author omos
 */
public class EntityUtil {
    public EntityUtil() {
    }
    
    public static String getPrimaryKeyFieldName(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                // @Idアノテーションがついているフィールドが主キー
                return field.getName();
            } else if (field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).unique()) {
                // @Column(unique=true)アノテーションがついているフィールドが主キー（一意制約がある場合）
                return field.getName();
            }
        }

        // 主キーが見つからなかった場合はnullまたはエラー処理を行う
        return null;
    } 
}
