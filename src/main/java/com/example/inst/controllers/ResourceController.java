package com.example.inst.controllers;


import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Controller
public class ResourceController {

    @GetMapping("/images/{code}.jpg")
    @ResponseBody
    public byte[] styles(@PathVariable("code") String code) throws IOException {
        // получаем содержимое файла из папки ресурсов в виде потока
        InputStream in = getClass().getClassLoader().getResourceAsStream("static/images/"+code+".jpg");
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
        final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.add("Content-Type", "image/jpeg");
        return response;
    }
    @GetMapping("/pickchers/{pick}")
    @ResponseBody
    public byte[] pickchers(@PathVariable String pick) {
        try {
            pick=pick.replaceAll("dvoetochie",":")
                    .replaceAll("ravno","=")
                    .replaceAll("vopros","?").replaceAll("procent","%")
                    .replaceAll("ampersant", "&").replaceAll("sslesh","/");

            URL url = new URL(pick);
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
            final HttpHeaders httpHeaders= new HttpHeaders();
            httpHeaders.add("Content-Type", "image/jpeg");
            return response;
        }catch (Exception e){
            System.out.println(e.fillInStackTrace());
        }
       return null;
    }

    public String getCleanURl(String url){
        String chistUrl= url.replace(":","dvoetochie")
                .replaceAll("=","ravno")
                .replaceAll("\\?","vopros").replaceAll("%","procent")
                .replaceAll("&", "ampersant").replaceAll("/","sslesh");
        return chistUrl;
    }
}