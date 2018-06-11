package com.xuan.mysingle.common.support;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/22
 */
public interface Column {
    String getName();

    Class<?> getType();

    String getTableName();

    default boolean isAssignable(Class<?> clazz) {
        if(clazz == null) {
            return false;
        } else {
            Class columnType = this.getType();
            if(clazz.isPrimitive()) {
                if(clazz.equals(Integer.TYPE)) {
                    clazz = Integer.class;
                }

                if(clazz.equals(Boolean.TYPE)) {
                    clazz = Boolean.class;
                }

                if(clazz.equals(Character.TYPE)) {
                    clazz = Character.class;
                }

                if(clazz.equals(Byte.TYPE)) {
                    clazz = Byte.class;
                }

                if(clazz.equals(Short.TYPE)) {
                    clazz = Short.class;
                }

                if(clazz.equals(Long.TYPE)) {
                    clazz = Long.class;
                }

                if(clazz.equals(Float.TYPE)) {
                    clazz = Float.class;
                }

                if(clazz.equals(Double.TYPE)) {
                    clazz = Double.class;
                }

                if(clazz.equals(Void.TYPE)) {
                    clazz = Void.class;
                }
            }

            return columnType.isAssignableFrom(clazz);
        }
    }
}
