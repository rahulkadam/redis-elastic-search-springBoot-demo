package com.example.demo.rest;

import com.example.demo.service.IplScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

@RestController
@RequestMapping("/schedule")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IplScheduleController {

    @Autowired
    private IplScheduleService iplScheduleService;

    @PostMapping("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity uploadSchedule(@RequestParam("file")MultipartFile uploadfile) {
        if (uploadfile.isEmpty()) {
            return new ResponseEntity<>("please select a file!", HttpStatus.OK);
        }

        try {
            InputStream is = uploadfile.getInputStream();
            iplScheduleService.uploadSchedule(is);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - " +
                uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }
}
