package br.com.douglas.rsql.jpa.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class JpaExpressionUtils {

    public static Expression<String> expIgnoreAccents(final CriteriaBuilder builder, Path<?> path) {
        return builder.upper(builder.function("translate", String.class, path,
                builder.literal("âàãáÁÂÀÃéêÉÊíÍóôõÓÔÕúÚÇç"),
                builder.literal("AAAAAAAAEEEEIIOOOOOOUUCC")));
    }

    public static <Y> Expression<Y> getExpression(Y argument, CriteriaBuilder builder, Path<?> path) {
        Optional<Expression<Y>> localDateTimeExpression = createExpression(argument, builder, path, LocalDateTime.class);

        if(localDateTimeExpression.isPresent())
            return localDateTimeExpression.get();

        Optional<Expression<Y>> localDateExpression = createExpression(argument, builder, path, LocalDate.class);
        if(localDateExpression.isPresent())
            return localDateExpression.get();

        Optional<Expression<Y>> dateExpression = createExpression(argument, builder, path, Date.class);
        if(dateExpression.isPresent())
            return dateExpression.get();

        return builder.literal(argument);
    }

    public static <Y> Optional<Expression<Y>> createExpression(final Y argument,
                                                               final CriteriaBuilder builder,
                                                               final Path<?> path,
                                                               final Class<?> clazz) {
        if(Objects.nonNull(argument) && path.getJavaType().isAssignableFrom(clazz)){
            return Optional.of(builder.literal(argument));
        }

        return Optional.empty();
    }
}
