package com.example.inst.controllers;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class dounloadpick {

    dounloadpick(String urlpick){
        try {
            URL url = new URL(urlpick);
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1!=(n=in.read(buf)))
            {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();
            FileOutputStream fos = new FileOutputStream("C:\\Users\\Gri\\Desktop\\SpringCourse-master\\folowback\\src\\main\\resources\\static\\images\\profpick.jpg");
            fos.write(response);
            fos.close();
        }catch (Exception e){
            e.fillInStackTrace();
        }

    }

}

