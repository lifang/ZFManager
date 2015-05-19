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

    private static String localpath = RootUrl.localpath;

    private static String urlpath = RootUrl.filepath + "File/index/upload";

    private static String delpath = RootUrl.filepath + "File/index/delete";

    private static String zippath = RootUrl.filepath + "File/index/zip";

    /**
     * 上传文件
     * 
     * @param file
     * @param path
     *            path后有/ 如 "test/a/b/"
     * @return
     */
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

    /**
     * 文件上传大小判断
     * 
     * @param file
     *            path后有/ 如 "test/a/b/"
     * @return
     */
    public static boolean fileSize(MultipartFile file) {
        long fileSize = file.getSize();
        if ((fileSize / 1024) > (1024 * 2)) {
            return false;
        }
        return true;
    }

    /**
     * 上传pos图片
     * 
     * @param file
     * @param path
     *            path后有/ 如 "test/a/b/"
     * @return
     */
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

    /**
     * 更新删除原文件
     * 
     * @param path
     *            文件路径 如 "test/a/s.jpg"
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public static int postDel(String path) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(delpath);
        MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
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

    /**
     * 下载打包
     * 
     * @param path
     *            目录path前后都没/ 如"test/a/b"
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public static int postWar(String path) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(zippath);
        MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
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

    /**
     * 下载打包
     * 
     * @param path
     *            打包图片地址 如"test/a/s.jpg"
     * @param id
     *            打包的终端号id
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public static int postWar(String[] path, String id) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(zippath);
        MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
        if (path.length == 0) {
            return -2;
        }
        StringBuilder sb = new StringBuilder();
        for (String a : path) {
            sb.append(a + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        mEntityBuilder.addTextBody("path", sb.toString());
        mEntityBuilder.addTextBody("id", id);
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
