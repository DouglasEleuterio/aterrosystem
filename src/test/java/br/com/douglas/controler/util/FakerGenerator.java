package br.com.douglas.controler.util;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;

import org.springframework.util.ReflectionUtils;

import com.github.javafaker.Faker;

public class FakerGenerator {

	  private final Faker faker;
	    private final Random random;
	    private final Map<String, Object> params;

	    private FakerGenerator() {
	        this.faker = new Faker(Locale.getDefault());
	        this.random = new Random();
	        this.params = new HashMap<>();
	    }

	    public static FakerGenerator builder() {
	        return new FakerGenerator();
	    }

	    public FakerGenerator param(String name, Object value) {
	        params.put(name, value);
	        return this;
	    }
	    
	    public FakerGenerator param(Map<String, Object> params) {
	        this.params.putAll(params);
	        return this;
	    }
	    
	    public <T> T generate(Class<T> type, boolean withId) throws Exception {
	    	return createAndFill(type, withId);
	    }

	    public <T> List<T> generate(Class<T> type, boolean withId, int size) throws Exception {
	        final var values = new ArrayList<T>();
	        for (int i = 0; i < size; i++) {
	            values.add(createAndFill(type, withId));
	        }
	        return values;
	    }

	    private <T> T createAndFill(Class<T> type, boolean withId) throws Exception {
	        T instance = type.getDeclaredConstructor().newInstance();
	        for (Field field : type.getDeclaredFields()) {
	        	ReflectionUtils.makeAccessible(field);
	        	Object value = getRandomValueForField(field, withId);
	        	ReflectionUtils.setField(field, instance, value);
	        }
	        return instance;
	    }

	    private Object getRandomValueForField(Field field, boolean withId) throws Exception {
	        final var type = field.getType();

	        if (this.params.containsKey(field.getName())) {
	            return this.params.get(field.getName());
	        }

	        if (field.isAnnotationPresent(Email.class)) {
	            return faker.internet().emailAddress();
	        }

	        if (field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(ManyToMany.class)) {
	            return null;
	        }
	        
	        if (Collection.class.isAssignableFrom(field.getType())){
	            return null;
	        }

	        if (withId && field.isAnnotationPresent(Id.class)) {
	            return UUID.randomUUID().toString();
	        }

	        if (field.getName().equalsIgnoreCase("name")
	                || field.getName().equalsIgnoreCase("nome")
	                || field.getName().equalsIgnoreCase("fullname")) {
	            return faker.name().fullName();
	        }

	        if (field.getName().equalsIgnoreCase("firstname")) {
	            return faker.name().firstName();
	        }

	        return createRandomField(type, withId);
	    }

		private Object createRandomField(final Class<?> type, boolean withId) throws Exception {
			if (type.isEnum()) {
				final var enumValues = type.getEnumConstants();
				return enumValues[random.nextInt(enumValues.length)];
			} else if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
				return random.nextInt();
			} else if (type.equals(Long.TYPE) || type.equals(Long.class)) {
				return random.nextLong();
			} else if (type.equals(Double.TYPE) || type.equals(Double.class)) {
				return random.nextDouble();
			} else if (type.equals(Float.TYPE) || type.equals(Float.class)) {
				return random.nextFloat();
			} else {
				return createRandomFieldComplemento(type, withId);
			}
		}

		private Object createRandomFieldComplemento(final Class<?> type, boolean withId) throws Exception {
			if (type.equals(String.class)) {
				return UUID.randomUUID().toString();
			} else if (type.equals(BigInteger.class)) {
				return BigInteger.valueOf(random.nextInt());
			} else if (type.equals(Boolean.TYPE) || type.equals(Boolean.class)) {
				return random.nextBoolean();
			} else if (type.equals(LocalDate.class)) {
				return LocalDate.now();
			} else if (type.equals(LocalDateTime.class)) {
				return LocalDateTime.now();
			} else {
				return createAndFill(type, withId);
			}
		}
}
