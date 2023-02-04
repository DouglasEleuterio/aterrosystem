package br.com.douglas.rsql.jpa.rsql;

import br.com.douglas.rsql.jpa.util.JpaArgumentConverter;
import br.com.douglas.rsql.jpa.util.JpaExpressionUtils;
import br.com.douglas.rsql.jpa.util.JpaPathUtils;
import br.com.douglas.rsql.util.StringUtil;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Objects;

public class GenericRsqlSpecification<T> implements Specification<T> {

     private RsqlSpecificationProperty specificationProperty;

    public GenericRsqlSpecification(RsqlSpecificationProperty specificationProperty) {
        this.specificationProperty = specificationProperty;
    }

    @Override
    public Specification<T> and(Specification<T> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<T> or(Specification<T> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<T> root,
                                 @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder builder) {

        final Path<T> path = specificationProperty.getProperty().getPath(root);
        ComparisonOperator operator = specificationProperty.getOperator();
        List<String> arguments = specificationProperty.getArguments();

        return toPredicate(arguments, operator, path, builder);
    }

     private Predicate toPredicate(
             final List<String> arguments,
             final ComparisonOperator operator,
             final Path<?> path,
             final CriteriaBuilder builder) {

        final List<Object> args = JpaPathUtils.castArguments(path, arguments);
         Object argument = JpaArgumentConverter.convert(path, args);

        switch (Objects.requireNonNull(RsqlSearchOperation.getSimpleOperator(operator))) {

            case EQUAL:
                return getEqualPredicate(argument, path, builder);

            case NOT_EQUAL:
                return getNotEqualPredicate(argument, path, builder);

            case GREATER_THAN:
                return builder.greaterThan((Expression) path, (Expression) JpaExpressionUtils.getExpression(argument, builder, path) );

            case GREATER_THAN_OR_EQUAL:
                return builder.greaterThanOrEqualTo((Expression) path, (Expression) JpaExpressionUtils.getExpression(argument, builder, path));

            case LESS_THAN:
                return builder.lessThan((Expression) path, (Expression) JpaExpressionUtils.getExpression(argument, builder, path));

            case LESS_THAN_OR_EQUAL:
                return builder.lessThanOrEqualTo((Expression) path, (Expression) JpaExpressionUtils.getExpression(argument, builder, path));

            case IN:
                return path.in(argument);
            case NOT_IN:
                return builder.not(path.in(argument));
        }
        return null;
    }

    private Predicate getNotEqualPredicate(final Object argument,
                                           final Path<?> path,
                                           final CriteriaBuilder builder) {
        if(JpaPathUtils.isLike(argument))
            return builder.notLike(JpaExpressionUtils.expIgnoreAccents(builder, path), getArgumentLike(argument));
        else if (argument == null)
                return builder.isNotNull(path);
        else
            return builder.notEqual(path, argument);
    }

    private Predicate getEqualPredicate(final Object argument, final Path<?> path, final CriteriaBuilder builder) {
        if (JpaPathUtils.isLike(argument)) {
            if(!path.getJavaType().isAssignableFrom(String.class))
                return builder.like(path.as(String.class), getArgumentLike(argument));
            return builder.like(JpaExpressionUtils.expIgnoreAccents(builder, path), getArgumentLike(argument));
        } else if (argument == null)
            return builder.isNull(path);
        else
            return builder.equal(path, argument);
    }

    private String getArgumentLike(@NonNull Object argument) {
        return StringUtil.ignoreAccents(argument.toString().replace("*", "%").toUpperCase());
    }
}
