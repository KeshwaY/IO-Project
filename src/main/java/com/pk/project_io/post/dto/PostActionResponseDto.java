package com.pk.project_io.post.dto;

import javax.validation.constraints.NotBlank;

public class PostActionResponseDto {

    @NotBlank
    private String status;

    public PostActionResponseDto() {
    }

    public PostActionResponseDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
