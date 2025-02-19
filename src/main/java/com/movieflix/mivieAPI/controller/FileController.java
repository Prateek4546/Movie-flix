package com.movieflix.mivieAPI.controller;

import com.movieflix.mivieAPI.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file")
public class FileController {

    private  final FileService fileService;

    public FileController(FileService fileService){
        this.fileService = fileService;
    }
    @Value("${project.poster}")
    private  String path ;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile file) throws IOException {
         String uploadedFileName = fileService.uploadeFile(path , file);
         return ResponseEntity.ok("file uploaded : " + uploadedFileName);
    }

    @GetMapping("/{fileName}")
    public void servFileHandler(@PathVariable String fileName , HttpServletResponse response) throws IOException {
        InputStream resourseFile = fileService.getResourceFile(path , fileName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resourseFile , response.getOutputStream());
//        This line copies the contents of the file (represented by the InputStream resourseFile) to the response's output stream (response.getOutputStream()).
//        The StreamUtils.copy() method handles the actual transfer of the file data.
//        The file's data will be streamed from the server to the client, allowing the client to download or view the file.
//        The response.getOutputStream() is where the file will be written to, and this stream will be sent back to the client as part of the HTTP response.
    }
}
