package com.pk.project_io.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class UpdatePropertyPostDto {

    @NotBlank
    @JsonProperty("new_value")
    private String newValue;

    public UpdatePropertyPostDto() {
    }

    public UpdatePropertyPostDto(String newValue) {
        this.newValue = newValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
