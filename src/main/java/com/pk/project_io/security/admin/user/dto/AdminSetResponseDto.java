package com.pk.project_io.security.admin.user.dto;

import javax.validation.constraints.NotBlank;

public class AdminSetResponseDto {

    @NotBlank
    private String user;

    @NotBlank
    private String status;

    public AdminSetResponseDto() {
    }

    public AdminSetResponseDto(String user, String status) {
        this.user = user;
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
