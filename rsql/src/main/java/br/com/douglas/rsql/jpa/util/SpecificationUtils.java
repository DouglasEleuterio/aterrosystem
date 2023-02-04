package br.com.douglas.rsql.jpa.util;

import br.com.douglas.rsql.jpa.rsql.CustomRsqlVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class SpecificationUtils {

    private SpecificationUtils() {

    }

    public static <T>Specification<T> rsqlToSpecification(String search) {
        if(Objects.nonNull(search)) {
            Node rootNode = new RSQLParser().parse(search);
            return rootNode.accept(new CustomRsqlVisitor<>());
        }
        return null;
    }
}
