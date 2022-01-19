package com.pk.project_io.comment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Could not find the comment!")
public class CommentNotFoundException extends Exception {
}
