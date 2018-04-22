package com.example.demo.service;

import com.example.demo.dto.stats.Message;
import com.example.demo.dto.stats.User;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class WhatsappMessageReader implements MessageReader {

    @Override
    public List<Message> readMessage(InputStream in) throws Exception {
        List<Message> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        Message message = null;
        while ((line = reader.readLine()) != null) {
            if (isNewMessage(line)) {
                if (message != null) {
                    list.add(message);
                }
                message = createMessage(line);
            } else {
                if (message != null) {
                    message.appendMessage(line);
                }
            }
        }
        System.out.println(list);   //Prints the string content read from input stream
        reader.close();
        return list;
    }

    public boolean isNewMessage(String message) {
        return message != null && message.length() > 15 && message.substring(0, 15).split("/").length > 2;
    }

    public Message createMessage(String line) throws Exception {
        Message message = new Message();
        String ops[] = line.split("-");

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy, HH:mm");
        Date date = dateFormat.parse(ops[0]);
        message.setCreated(date.getTime());
        User user = new User();
        user.setName(ops[1].split(":")[0]);
        message.setCreated(date.getTime());
        message.setUser(user);
        if (ops[1].split(":").length > 1) {
            message.appendMessage(ops[1].split(":")[1]);
        }
        return message;
    }
}
