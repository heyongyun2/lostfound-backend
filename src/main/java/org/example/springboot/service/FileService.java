package org.example.springboot.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class FileService {
    public void previewFile(String fileName, HttpServletResponse response)throws IOException {
        File file=new File("D:/print_upload/"+fileName);
        if(!file.exists()){
            response.setStatus(404);
            return;
        }
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String contentType;
        if(".pdf".equalsIgnoreCase(suffix)){
            contentType = "application/pdf";
            response.setHeader("Content‑Disposition","inline;filename="+fileName);
        }else if(".docx".equalsIgnoreCase(suffix)){
            contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            response.setHeader("Content‑Disposition","attachment;filename="+fileName);
        }else if(".png".equalsIgnoreCase(suffix)){
            contentType = "image/png";
            response.setHeader("Content‑Disposition","inline;filename="+fileName);
        }else if(".jpg".equalsIgnoreCase(suffix) || ".jpeg".equalsIgnoreCase(suffix)){
            contentType = "image/jpeg";
            response.setHeader("Content‑Disposition","inline;filename="+fileName);
        }else{
            contentType = "application/octet‑stream";
            response.setHeader("Content‑Disposition","attachment;filename="+fileName);
        }

        response.setContentType(contentType);
        Files.copy(file.toPath(),response.getOutputStream());
    }


    }

