package br.com.douglas.rsql.jpa.util;

import lombok.NoArgsConstructor;

import javax.persistence.criteria.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
public class JpaPathUtils {
    
    public static List<Object> castArguments(
            final Path<?> path, 
            final List<String> arguments) {
        final Class<?> type = path.getJavaType();

        Object[] argumentsAr = arguments.stream().map(a -> {
            if (Objects.isNull(a) || a.equals("null") || a.equals("undefined"))
                return null;
            else if (isLike(a))
                return a;
            else if (type.equals(Integer.class))
                return Integer.parseInt(a);
            else if (type.equals(Long.class))
                return Long.parseLong(a);
            else if (type.equals(Double.class))
                return Double.parseDouble(a);
            else
                return a;
        }).toArray();
        return new ArrayList<>(Arrays.asList(argumentsAr));
    }
    
    public static boolean isLike(final Object argument) {
        if(argument instanceof String) {
            final String argumentString = (String) argument;
            return argumentString.startsWith("*") || argumentString.endsWith("*");
        }
        return false;
    }
}
