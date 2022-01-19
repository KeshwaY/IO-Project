package com.pk.project_io.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class PostPutDto {

    @NotBlank
    @JsonProperty("new_description")
    private String newDescription;

    public String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }
}
