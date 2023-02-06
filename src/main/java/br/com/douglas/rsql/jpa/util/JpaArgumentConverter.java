package br.com.douglas.rsql.jpa.util;

import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.criteria.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@NoArgsConstructor
public class JpaArgumentConverter {

    public static Object convert(final Path<?> path, final List<?> args) {
        if(args.size() > 1) {
            Object[] objects = args.stream().map(x -> convert(path, x)).toArray();
            return new ArrayList<>(Arrays.asList(objects));
        }
        return convert(path, args.get(0));
    }

    public static Object convert(final Path<?> path, final Object argument) {
        if(Objects.isNull(argument) || argument.equals("null") || argument.equals("undefined"))
            return argument;

        if(path.getJavaType().isEnum())
            return Enum.valueOf( (Class) path.getJavaType(), (String) argument);
        if(path.getJavaType().isAssignableFrom(Boolean.class) || path.getJavaType().isAssignableFrom(boolean.class))
            return Boolean.valueOf((String) argument);
        if(path.getJavaType().isAssignableFrom(Date.class))
            return toDate((String) argument);
        if(path.getJavaType().isAssignableFrom(LocalDate.class))
            return toLocalDate((String) argument);
        if(path.getJavaType().isAssignableFrom(LocalDateTime.class))
            return toLocalDateTime((String) argument);

        return argument;
    }

    public static Date toDate(String argument) {
        return DateTime.parse(argument).toDate();
    }

    public static LocalDate toLocalDate(String argument) {
        DateTime dateTime = DateTime.parse(argument);
        return LocalDate.of(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth());
    }

    public static LocalDateTime toLocalDateTime(String argument) {
        DateTime dateTime = DateTime.parse(argument);
        return Instant.ofEpochMilli(dateTime.getMillis()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
