package de.hska.iwi.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int id;
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
