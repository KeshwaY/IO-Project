package com.pk.project_io.security.admin.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pk.project_io.post.dto.PostGetDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AdminPostGetDto extends PostGetDto {

    @NotNull
    private Long id;

    @NotBlank
    @JsonProperty("user_email")
    private String userEmail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
