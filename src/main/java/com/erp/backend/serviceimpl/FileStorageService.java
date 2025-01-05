package com.erp.backend.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
public class FileStorageService {

    private final String uploadDir = System.getProperty("user.dir") + "/uploads";


    @PostConstruct
    public void init(){
     File directory=new File(uploadDir);
     if(!directory.exists()){
        directory.mkdirs();
     }
    }

    public String saveFile(MultipartFile file,String subDirectory) throws IOException{
        String filePath=uploadDir+subDirectory+"/"+file.getOriginalFilename();
        File targetfile=new File(filePath);
        targetfile.getParentFile().mkdirs();
        file.transferTo(targetfile);
        return filePath;
    }

    public Resource loadFile(String filePath) throws MalformedURLException {
        return new UrlResource(Paths.get(filePath).toUri());
    }
}
