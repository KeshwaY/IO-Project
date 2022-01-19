package com.pk.project_io.post.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Could not find the post!")
public class PostNotFoundException extends Exception {
}
