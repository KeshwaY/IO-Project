package com.pk.project_io.security.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AdminPutDto {

    @NotBlank
    @Email
    @JsonProperty("user_email")
    private String userEmail;

    @NotBlank
    @JsonProperty("new_value")
    private String newValue;

    @NotBlank
    private String reason;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
