package com.userstorage.exception.custome;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @PROJECT_NAME : User-Stroage
 * @PACKAGE_NAME : com.userstroage.exception.custome
 * @CREATED_BY : nikhilupadhyay
 * @CREATED_DATE : 27/04/24
 **/

@Getter
@AllArgsConstructor
public class InvalidDataException extends RuntimeException{
    private String message;
}
