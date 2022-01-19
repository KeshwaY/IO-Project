package com.pk.project_io.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User property is not valid, may be occupied")
public class UserPropertyUpdateException extends Exception {
}
