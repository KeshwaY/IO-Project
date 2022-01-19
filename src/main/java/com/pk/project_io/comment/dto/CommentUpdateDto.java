package com.pk.project_io.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class CommentUpdateDto {

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
