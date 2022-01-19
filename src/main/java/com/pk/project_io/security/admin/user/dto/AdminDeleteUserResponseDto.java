package com.pk.project_io.security.admin.user.dto;

public class AdminDeleteUserResponseDto {

    private String status;

    public AdminDeleteUserResponseDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
