package com.pk.project_io.comment.dto;

import javax.validation.constraints.NotBlank;

public class CommentActionResponseDto {

    @NotBlank
    private String status;

    public CommentActionResponseDto() {
    }

    public CommentActionResponseDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
