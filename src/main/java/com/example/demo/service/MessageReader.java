package com.example.demo.service;

import com.example.demo.dto.stats.Message;

import java.io.InputStream;
import java.util.List;

public interface MessageReader {

    List<Message> readMessage(InputStream in) throws Exception;
}
