package com.pk.project_io.post.dto;

import javax.validation.constraints.NotBlank;

public class PostPostDto {

    @NotBlank
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
