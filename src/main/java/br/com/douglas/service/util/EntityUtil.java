package br.com.douglas.service.util;

import br.com.douglas.entity.base.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ResolvableType;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
public final class EntityUtil {
    private EntityUtil() {
    }

    public static boolean isValid(BaseEntity entity) {
        return entity != null && entity.getId() != null;
    }

    public static <T> Collection<T> mergeCollection(Collection<T> source, Collection<T> target) {
        if (source == null) {
            return target;
        } else {
            source.clear();
            source.addAll(target);
            return source;
        }
    }

    public static <T extends BaseEntity> Optional<T> findCollectionId(Collection<T> source, Predicate<? super T> predicate) {
        if (source == null || source.isEmpty()) {
            return Optional.empty();
        }
        return source.stream().filter(predicate).findFirst();
    }

    public static <T extends BaseEntity> T copy(T entity) {
        try {
            ResolvableType resolvableType = ResolvableType.forInstance(entity);
            return (T) resolvableType.toClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.debug("Erro ao copiar entidade: ", e);
            return entity;
        }
    }

    public static <T extends BaseEntity> T unproxy(T entity) {
        return PersistenceUtil.cleanFromProxies(entity);
    }
}
