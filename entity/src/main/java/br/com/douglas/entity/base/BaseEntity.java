package br.com.douglas.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.PrePersist;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseEntity implements Serializable {

    @PrePersist
    protected void prePersist() {
        if(Objects.isNull(this.getId()) || Strings.isBlank(this.getId()))
            this.setId(java.util.UUID.randomUUID().toString());
    }

    public abstract String getId();

    public abstract void setId(String id);
}
