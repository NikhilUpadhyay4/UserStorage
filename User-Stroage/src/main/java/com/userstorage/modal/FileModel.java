package com.userstorage.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class FileModel {
    @Id
    private String id;
    private String title;
    private String fileName;
    private String contentType;
    private String uploadDate;
    private String filePath;
    private String username;
}
