package com.redhat.example.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@SequenceGenerator(name="customEntityIdSeq", sequenceName="CUSTOM_ENTITY_ID_SEQ", allocationSize=1)
public class MyCustomEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="customEntityIdSeq")
    @Column(name = "id")
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyCustomEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyCustomEntity that = (MyCustomEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
