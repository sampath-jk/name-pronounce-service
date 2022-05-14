package com.wf.hackathon.service.ai;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Test {

    
    public byte[] readStream() throws IOException
    {
        File file = new File("D:\\Hackathon\\workspace\\name-pronounce-service\\src\\main\\java\\com\\wf\\hackathon\\service\\ai\\time.wav");
    FileInputStream fl = new FileInputStream(file);
  
    // Now creating byte array of same length as file
    byte[] arr = new byte[(int)file.length()];
    fl.read(arr);
    fl.close();
    return arr;
    }
    }
