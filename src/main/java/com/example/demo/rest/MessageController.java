package com.example.demo.rest;

import com.example.demo.dto.stats.Message;
import com.example.demo.elastic.service.WhatsAppData;
import com.example.demo.service.WhatsappMessageReader;
import io.searchbox.core.search.aggregation.TermsAggregation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private WhatsAppData whatsAppData;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadMessage(@RequestParam("file")MultipartFile uploadfile) {
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {
            InputStream is = uploadfile.getInputStream();
            whatsAppData.loadWhatsAppData(is);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - " +
                uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/addtopic/{topicName}")
    public ResponseEntity<?> addTopic(@PathParam("topicName") String topicName) {
        return new ResponseEntity("", new HttpHeaders() , HttpStatus.OK);
    }

    /**
     *
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<?> list() {
        try {
            List<Message> messagelist = whatsAppData.list();
            return new ResponseEntity(messagelist, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("userStats")
    public ResponseEntity<?> userStats() {
        try {
            Map<String , Long> messagelist = whatsAppData.aggregationByUser();
            return new ResponseEntity(messagelist, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
