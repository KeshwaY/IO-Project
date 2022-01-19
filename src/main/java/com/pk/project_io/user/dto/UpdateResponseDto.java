package com.pk.project_io.user.dto;

import javax.validation.constraints.NotBlank;

public class UpdateResponseDto {

    @NotBlank
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
