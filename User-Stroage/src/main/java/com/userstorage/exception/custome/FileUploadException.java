package com.userstorage.exception.custome;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @PROJECT_NAME : User-Stroage
 * @PACKAGE_NAME : com.userstorage.exception.custome
 * @CREATED_BY : nikhilupadhyay
 * @CREATED_DATE : 27/04/24
 **/

@AllArgsConstructor
@Getter
public class FileUploadException extends RuntimeException{
    private String message;
}
