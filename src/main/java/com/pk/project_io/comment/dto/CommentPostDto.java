package com.pk.project_io.comment.dto;

import javax.validation.constraints.NotBlank;

public class CommentPostDto {

    @NotBlank
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
