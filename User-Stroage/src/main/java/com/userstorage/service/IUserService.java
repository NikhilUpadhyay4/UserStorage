package com.userstorage.service;

import com.userstorage.modal.FileModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IUserService {
    FileModel saveFile(String title, String description, MultipartFile file , String username);
    List<FileModel> getAllFiles(String username);
    FileModel search(String fileName ,String username);
    byte[] downloadFile(String id, String username);
    public void deleteFile(String fileName, String username);
}
