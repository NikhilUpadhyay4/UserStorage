package com.userstorage.controller;

import com.userstorage.modal.FileModel;
import com.userstorage.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;




@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {


    @Autowired
    private IUserService service;

    @GetMapping("/{username}/files")
    public ResponseEntity<List<FileModel>> getFiles(@PathVariable("username") String username){
        return  new ResponseEntity<>(service.getAllFiles(username), HttpStatus.OK);
    }

    @PostMapping(path = "/{username}/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> upload(@RequestParam("title") String title,
                                                  @RequestParam("description") String description,
                                                  @RequestBody MultipartFile file,
                                                  @PathVariable("username") String username){
        service.saveFile(title,description,file,username);
        return new ResponseEntity<>("File Uploaded Successful!",HttpStatus.CREATED);
    }

    @GetMapping("/{username}/search/{fileName}")
    public ResponseEntity<FileModel> search(@PathVariable("username")String username, @PathVariable("fileName") String fileName){
        return ResponseEntity.ok().body(service.search(fileName,username));
    }

    @GetMapping("/{username}/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("fileId") String fileId, @PathVariable("username") String username){
        byte[] rep = service.downloadFile(fileId,username);
        if(Objects.nonNull(rep) && rep.length == 0)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok().body(rep);
    }


}
