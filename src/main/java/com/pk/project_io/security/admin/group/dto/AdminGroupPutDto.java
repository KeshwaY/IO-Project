package com.pk.project_io.security.admin.group.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class AdminGroupPutDto {

    @NotBlank
    private String mode;

    @NotBlank
    @JsonProperty("new_value")
    private String newValue;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
