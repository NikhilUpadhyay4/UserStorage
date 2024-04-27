package com.userstorage.repository;

import com.userstorage.modal.FileModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileUploaderRepository extends MongoRepository<FileModel, String> {
    FileModel findByFileName(String fileName);
    List<FileModel> getByUsername(String username);
    FileModel findByFileNameAndUsername(String fileName,String username);

}
