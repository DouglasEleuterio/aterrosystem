package br.com.douglas.service.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
public class BeanUtils {

    private BeanUtils() {
    }

    public static <T, A extends Annotation> Optional<T> getBean(ApplicationContext context, Class<A> annotation, Predicate<A> predicate) {
        try {
            Map<String, Object> beans = context.getBeansWithAnnotation(annotation);
            for (Map.Entry<String, Object> value : beans.entrySet()) {
                Object bean = value.getValue();
                A annotationValue = bean.getClass().getAnnotation(annotation);
                if (predicate.test(annotationValue)) {
                    return Optional.of((T) bean);
                }
            }
        } catch (Exception e) {
            log.error("Erro ao buscar bean", e);
        }

        return Optional.empty();
    }
}
