package com.pk.project_io.group.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Could not find the group!")
public class GroupNotFoundException extends Exception {
}
