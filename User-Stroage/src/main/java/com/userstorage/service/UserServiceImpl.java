package com.userstorage.service;

import com.userstorage.config.BucketName;
import com.userstorage.exception.custome.FileExistsExceptions;
import com.userstorage.exception.custome.FileNotFoundException;
import com.userstorage.exception.custome.FileUploadException;
import com.userstorage.exception.custome.InvalidDataException;
import com.userstorage.modal.FileModel;
import com.userstorage.repository.FileUploaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private  FileUploadService fileStore;

    @Autowired
    private  FileUploaderRepository repository;


    private final Logger log  = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public FileModel saveFile(String title, String description, MultipartFile file, String folderName) {
        if (file.isEmpty()){
            throw new InvalidDataException("File is Empty, Please upload one file");
        }

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-length", String.valueOf(file.getSize()));
        String path = String.format("%s/%s", BucketName.BUCKET_NAME.getBucketName(), folderName);
        String fileName = String.format("%s", file.getOriginalFilename());
        

        try {
            fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload file");
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        FileModel fileModel = FileModel.builder()
                .fileName(fileName)
                .title(title)
                .filePath(path)
                .contentType(file.getContentType())
                .username(folderName)
                .uploadDate(dateFormat.format(date))
                .build();
        repository.save(fileModel);
        return repository.findByFileName(file.getName());
    }

    @Override
    public List<FileModel> getAllFiles(String username) {
        return repository.getByUsername(username);
    }

    @Override
    public FileModel search(String fileName, String username) {
        return repository.findByFileNameAndUsername(fileName, username);
    }

    @Override
    public byte[] downloadFile(String id, String username) {
        FileModel fileModel = repository.findById(id).orElse(null);
        if (Objects.nonNull(fileModel)) {
            return fileStore.download(fileModel.getFilePath(), fileModel.getFileName(), username);
        }else {
            throw new FileNotFoundException("File doesn't exists");
        }
    }

    @Override
    public void deleteFile(String fileName, String username) {
        FileModel fileModel = repository.findByFileNameAndUsername(fileName,username);
        fileStore.delete(fileModel.getFilePath(), fileName, username);
        repository.deleteById(fileModel.getId());
    }
}
