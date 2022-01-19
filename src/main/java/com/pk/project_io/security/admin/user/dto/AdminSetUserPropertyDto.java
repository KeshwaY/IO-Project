package com.pk.project_io.security.admin.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class AdminSetUserPropertyDto {

    @NotBlank
    @JsonProperty("new_value")
    private String newValue;

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
