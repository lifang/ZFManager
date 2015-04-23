package com.comdosoft.financial.manage.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class HttpFile {

    // @Value("${localpath}")
    // private static String localpath = "C:/test/local/";
     private static String localpath="/opt/data/";
    // private static String urlpath="http://127.0.0.1:8080/file/index/upload";
    // @Value("${urlpath}")
    private static String urlpath="http://121.40.84.2:8888/File/index/upload" ;

    // private static String urlpath = "http://file.ebank007.com/File/index/upload";

    public static String upload(MultipartFile file, String path) {
        String upload_path = localpath + path;
        String name = file.getOriginalFilename();
        int a = -1;
        try {
            String extName = "";
            if (name.lastIndexOf(".") >= 0) {
                extName = name.substring(name.lastIndexOf("."));
            }
            name = new Date().getTime() + SysUtils.getRandNum(6).toString() + extName;
            File f = new File(upload_path, name);
            FileUtils.copyInputStreamToFile(file.getInputStream(), f);
            a = postHttp(urlpath, path, f);
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
        if (a == -1) {
            return "同步上传失败";
        } else {
            return path + name;
        }
    }

    public static String uploadPos(MultipartFile file, String path) {
        String upload_path = localpath + path;
        String name = file.getOriginalFilename();
        String extName = "";
        if (name.lastIndexOf(".") >= 0) {
            extName = name.substring(name.lastIndexOf("."));
        }
        String ppname = new Date().getTime() + SysUtils.getRandNum(6).toString();
        String oo = upload_path + ppname + "_o" + extName;
        File osFile = new File(oo);
        if (!osFile.getParentFile().exists()) {
            osFile.getParentFile().mkdirs();
        }
        int a = -1;
        try {
            file.transferTo(osFile);
            // 大图
            String bb = upload_path + ppname + "_b" + extName;
            Thumbnails.of(oo).size(660, 660).toFile(bb);
            String mm = upload_path + ppname + "_m" + extName;
            Thumbnails.of(oo).size(340, 340).toFile(mm);
            String ss = upload_path + ppname + "_s" + extName;
            Thumbnails.of(oo).size(55, 55).toFile(ss);
            File qf = new File(bb);
            a = postHttp(urlpath, path, qf);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        File qf2 = new File(mm);
                        postHttp(urlpath, path, qf2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        File qf2 = new File(ss);
                        postHttp(urlpath, path, qf2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (a == -1) {
            return "同步上传失败";
        } else {
            return path + ppname + "_b" + extName;
        }
    }

    public static int postHttp(String url, String path, File file) throws IOException {

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
        mEntityBuilder.addBinaryBody("file", file);
        mEntityBuilder.addTextBody("path", path);
        httppost.setEntity(mEntityBuilder.build());
        HttpResponse resp = httpClient.execute(httppost);
        int code = resp.getStatusLine().getStatusCode();
        if (200 == code) {
            return 0;
        } else {
            return -1;
        }
    }

}
