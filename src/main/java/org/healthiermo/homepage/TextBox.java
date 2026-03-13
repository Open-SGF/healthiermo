package org.healthiermo.homepage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Objects;

/**
 We are creating an composite primary key in order so the .save(S entity) method
 Knows what to update on an update to the entity.

 We call "box" and "pie" as the primary key as this combination is technically the primary key to store text.
 */
class CompositeKey implements Serializable {
    private String box;
    private String pie;

    public CompositeKey() {}

    public CompositeKey(String box, String pie) {
        this.box = box;
        this.pie = pie;
    }

    public String getBox() {
        return box;
    }

    public String getPie() {
        return pie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeKey that = (CompositeKey) o;
        return Objects.equals(box, that.box) && Objects.equals(pie, that.pie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(box, pie);
    }
}

@Entity
@IdClass(CompositeKey.class)
public class TextBox {

    @Id
    @NotBlank
    @Column(columnDefinition="TEXT")
    private String box;

    @Id
    @NotBlank
    @Column(columnDefinition="TEXT")
    private String pie;

    @NotBlank
    private String title;

    private String text;

    public TextBox() {}

    public TextBox(String box, String pie, String title, String text) {
        this.box = box;
        this.pie = pie;
        this.title = title;
        this.text = text;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getPie() {
        return pie;
    }

    public void setPie(String pie) {
        this.pie = pie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
