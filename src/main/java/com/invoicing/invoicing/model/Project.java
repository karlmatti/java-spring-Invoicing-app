package com.invoicing.invoicing.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Project extends AuditModel{
    @Id
    @GeneratedValue(generator = "projectGenerator")
    @SequenceGenerator(
            name = "projectGenerator",
            sequenceName = "projectSequence",
            initialValue = 1000
    )
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String label;

    private String description;

    public Project() {
    }
    public Project(String label, String description){

        this.label = label;
        this.description = description;
    }

    public Project(Long id, String label, String description) {
        this.id = id;
        this.label = label;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
