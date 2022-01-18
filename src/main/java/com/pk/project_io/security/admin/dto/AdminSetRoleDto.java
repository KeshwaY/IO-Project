package com.pk.project_io.security.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AdminSetRoleDto {

    @NotBlank
    @JsonProperty("user")
    private String email;

    @NotBlank
    private String role;

    @JsonProperty("action")
    @Pattern(regexp = "^GRANT$|^REMOVE$", message = "You can only GRANT role or REMOVE!")
    private String action;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
