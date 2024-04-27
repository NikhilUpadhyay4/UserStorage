package com.userstorage.exception.custome;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @PROJECT_NAME : User-Stroage
 * @PACKAGE_NAME : com.userstroage.exception.custome
 * @CREATED_BY : nikhilupadhyay
 * @CREATED_DATE : 27/04/24
 **/

@AllArgsConstructor
@Getter
public class FileExistsExceptions extends RuntimeException {
    private final String message;
}
