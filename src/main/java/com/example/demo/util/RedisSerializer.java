package com.example.demo.util;

import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Utility for creating and converting object to byte and vice versa
 */
@Component
public class RedisSerializer {


    /**
     * Convert the bytes to Object. deSerialize Object
     * @param bytes
     * @return
     * @throws Exception
     */
    public Object convertToObject(byte[] bytes) throws Exception{
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        Object o;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return o;
    }

    /**
     * Convert Object into Byte. Serialize the Object
     * @param obj
     * @return
     * @throws Exception
     */
    public byte[] convertToByte(Object obj) throws Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] yourBytes = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            yourBytes = bos.toByteArray();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return yourBytes;
    }
}
