package com.pk.project_io.security.admin.dto;

import javax.validation.constraints.NotBlank;

public class AdminActionResponseDto {

    @NotBlank
    private String status;

    public AdminActionResponseDto() {
    }

    public AdminActionResponseDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
