package br.com.douglas.rsql.jpa;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import java.util.Optional;

public class Property {

    private String name;
    private Property joinProperty;

    public Property(String property) {
        this.verifyJoin(property);
    }

    private void verifyJoin(String property) {
        if(property.contains(".")) {
            String[] properties = property.split("\\.", 2);
            this.name = properties[0];
            this.joinProperty = new Property(properties[1]);
        } else {
            this.name = property;
        }
    }

    public <T, Y> Path<T> getPath(From<T,Y> root) {
        Path<?> pathProperty = root.get(name);

        if(joinProperty != null) {
            return (Path<T>) joinProperty.getPath((From<?, ?>) getJoin(root));
        } else {
            return (Path<T>) pathProperty;
        }
    }

public <T, Y> Join<T, Y> getJoin(From<T,Y> root) {
        Optional<?> join = root.getJoins().stream().filter(j -> j.getAttribute().getName().equals(name)).findFirst();
        if(join.isPresent()) {
            return (Join<T,Y>) join.get();
        } else {
            return root.join(name, JoinType.LEFT);
        }
    }
}
