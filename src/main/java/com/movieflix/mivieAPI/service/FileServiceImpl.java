package com.movieflix.mivieAPI.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements  FileService{
//    @Override
//    public String uploadeFile(String path, MultipartFile file) throws IOException {
//        // get the file name
//        String fileName = file.getOriginalFilename();
//
//        //to get file path
//        String filePath = path + File.separator + fileName;
//
//        // create file object
//
//        File  f = new File(filePath);
//        if(!f.exists()){
//            f.mkdir();
//        }
//        // copy the file or upload  file to path
//        Files.copy(file.getInputStream() , Paths.get(filePath) , StandardCopyOption.REPLACE_EXISTING);
//        return fileName;
//    }
    @Override
    public String uploadeFile(String path, MultipartFile file) throws IOException {
        // Get the file name
        String fileName = file.getOriginalFilename();

        // Construct the file path
        String filePath = path + File.separator + fileName;

        // Ensure the parent directory exists
        File f = new File(filePath);
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();  // Creates the directory structure if it doesn't exist
        }
        // Copy the file to the destination path (overwriting if exists)
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;  // Optionally, you can return filePath if needed
    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;
        return new FileInputStream(filePath);
    }
}
