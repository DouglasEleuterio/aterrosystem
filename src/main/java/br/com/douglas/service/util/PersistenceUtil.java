package br.com.douglas.service.util;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.collection.internal.PersistentSet;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

@Slf4j
public final class PersistenceUtil {
    private PersistenceUtil() {
    }
    private static void cleanFromProxies(Object value, List<Object> handledObjects) {
        if ((value != null) && (!isProxy(value)) && !containsTotallyEqual(handledObjects, value)) {
            handledObjects.add(value);
            if (value instanceof Iterable) {
                for (Object item : (Iterable<?>) value) {
                    cleanFromProxies(item, handledObjects);
                }
            } else if (value.getClass().isArray()) {
                for (Object item : (Object[]) value) {
                    cleanFromProxies(item, handledObjects);
                }
            }
            BeanInfo beanInfo = null;
            try {
                beanInfo = Introspector.getBeanInfo(value.getClass());
            } catch (IntrospectionException e) {
                //log.debug("Erro ao realizar a remoção do proxy do hibernate", e)
            }
            cleanProxyFromObject(value, beanInfo, value.getClass(), handledObjects);
        }
    }
    private static void cleanProxyFromObject(Object value, BeanInfo beanInfo, Class<?> valueClass, List<Object> handledObjects) {
        if (beanInfo != null && !PrimitivesUtil.isPrimitive(valueClass)
                && !String.class.isAssignableFrom(valueClass)
                && !LocalDate.class.isAssignableFrom(valueClass)
                && !LinkedHashSet.class.isAssignableFrom(valueClass)
                && !Long.class.isAssignableFrom(valueClass)) {
            Field[] fields = value.getClass().getDeclaredFields();
            for (Field property : fields) {
                try {
                    ReflectionUtils.makeAccessible(property);
                    Object fieldValue = ReflectionUtils.getField(property, value);
                    if (isProxy(fieldValue)) {
                        fieldValue = unproxyObject(fieldValue);
                        BeanUtils.setProperty(value, property.getName(), fieldValue);
                    }
                    cleanFromProxies(fieldValue, handledObjects);
                } catch (Exception e) {
                    //log.debug("Erro ao realizar a remoção do proxy do hibernate", e)
                }
            }
        }
    }
    public static <T> T cleanFromProxies(T value) {
        T result = unproxyObject(value);
        cleanFromProxies(result, new ArrayList<>());
        return result;
    }
    private static boolean containsTotallyEqual(Collection<?> collection, Object value) {
        if (CollectionUtils.isEmpty(collection)) {
            return false;
        }
        for (Object object : collection) {
            if (object == value) {
                return true;
            }
        }
        return false;
    }
    public static boolean isProxy(Object value) {
        if (value == null) return false;
        return (value instanceof HibernateProxy) || (value instanceof PersistentCollection);
    }
    @SuppressWarnings("unchecked")
    private static <T> T unproxyObject(T object) {
        if (isProxy(object)) {
            if (object instanceof PersistentCollection persistentCollection) {
                return (T) unproxyPersistentCollection(persistentCollection);
            } else if (object instanceof HibernateProxy) {
                return (T) Hibernate.unproxy(object);
            } else {
                return null;
            }
        }
        return object;
    }
    private static Object unproxyPersistentCollection(PersistentCollection persistentCollection) {
        if (persistentCollection instanceof PersistentSet) {
            if (Objects.nonNull(persistentCollection.getStoredSnapshot())) {
                return unproxyPersistentSet((Map<?, ?>) persistentCollection.getStoredSnapshot());
            } else {
                return new LinkedHashSet<>();
            }
        }
        return persistentCollection.getStoredSnapshot();
    }
    private static <T> Set<T> unproxyPersistentSet(Map<T, ?> persistenceSet) {
        return new LinkedHashSet<>(persistenceSet.keySet());
    }
}
