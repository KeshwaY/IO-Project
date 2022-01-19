package com.pk.project_io.group.dto;

import javax.validation.constraints.NotBlank;

public class GroupGetDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String ownerUsername;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }
}
