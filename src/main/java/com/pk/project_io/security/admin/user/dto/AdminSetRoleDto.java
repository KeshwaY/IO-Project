package com.pk.project_io.security.admin.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AdminSetRoleDto {

    @NotBlank
    private String role;

    @JsonProperty("action")
    @Pattern(regexp = "^GRANT$|^REMOVE$", message = "You can only GRANT role or REMOVE!")
    private String action;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
