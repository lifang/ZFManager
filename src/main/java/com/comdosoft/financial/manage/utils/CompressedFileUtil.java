package com.comdosoft.financial.manage.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @Description: 文件压缩工具类 将指定文件/文件夹压缩成zip、rar压缩文件
 */
public class CompressedFileUtil {

    private static final Logger log = LoggerFactory.getLogger(CompressedFileUtil.class);

    /**
     * @param resourcesPath 源文件/文件夹
     * @param zipFile       目的压缩文件保存路径
     * @return void
     * @throws Exception
     * @desc 将源文件/文件夹生成指定格式的压缩文件,格式zip
     */
    public static void compressedFile(String resourcesPath, String zipFile)
            throws Exception {
        File resourcesFile = new File(resourcesPath); // 源文件
        File targetZipFile = new File(zipFile);
        // 如果目的路径不存在，则新建
        if (!targetZipFile.getParentFile().exists()) {
            targetZipFile.getParentFile().mkdirs();
        }
        log.info("zip:" + targetZipFile.getAbsolutePath());

        FileOutputStream outputStream = new FileOutputStream(targetZipFile);
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                outputStream));

        createCompressedFile(out, resourcesFile, "");

        out.close();
    }

    /**
     * @param out  输出流
     * @param file 目标文件
     * @return void
     * @throws Exception
     * @desc 生成压缩文件。 如果是文件夹，则使用递归，进行文件遍历、压缩 如果是文件，直接压缩
     */
    private static void createCompressedFile(ZipOutputStream out, File file, String dir)
            throws Exception {
        // 如果当前的是文件夹，则进行进一步处理
        if (file.isDirectory()) {
            // 得到文件列表信息
            File[] files = file.listFiles();
            // 将文件夹添加到下一级打包目录
            out.putNextEntry(new ZipEntry(dir + "/"));
            dir = dir + "/" + file.getName() + "/";
            // 循环将文件夹中的文件打包
            for (int i = 0; i < files.length; i++) {
                createCompressedFile(out, files[i], dir + files[i].getName()); // 递归处理
            }
        } else { // 当前的是文件，打包处理
            // 文件输入流
            FileInputStream fis = new FileInputStream(file);

            out.putNextEntry(new ZipEntry(dir));
            // 进行写操作
            int j = 0;
            byte[] buffer = new byte[1024];
            while ((j = fis.read(buffer)) > 0) {
                out.write(buffer, 0, j);
            }
            // 关闭输入流
            fis.close();
        }
    }

    public static void unCompressedFile(String zipfile, String unzipPath) throws Exception {

        BufferedOutputStream bos = null;
        //创建输入字节流
        FileInputStream fis = new FileInputStream(zipfile);
        //根据输入字节流创建输入字符流
        BufferedInputStream bis = new BufferedInputStream(fis);
        //根据字符流，创建ZIP文件输入流
        ZipInputStream zis = new ZipInputStream(bis);
        //zip文件条目，表示zip文件
        ZipEntry entry;
        //循环读取文件条目，只要不为空，就进行处理
        while ((entry = zis.getNextEntry()) != null) {
            int count;
            byte date[] = new byte[2048];
            //如果条目是文件目录，则继续执行
            if (entry.isDirectory()) {
                continue;
            } else {
                bos = new BufferedOutputStream(new FileOutputStream(getRealFileName(unzipPath, entry.getName())));
                while ((count = zis.read(date)) != -1) {
                    bos.write(date, 0, count);
                }
                bos.flush();
                bos.close();
            }
        }
        zis.close();
    }

    private static File getRealFileName(String zippath, String absFileName) {
        String[] dirs = absFileName.split("/", absFileName.length());
        //创建文件对象
        File file = new File(zippath);
        if (dirs.length > 2) {
            for (int i = 1; i < dirs.length - 1; i++) {
                //根据file抽象路径和dir路径字符串创建一个新的file对象，路径为文件的上一个目录
                file = new File(file, dirs[i]);
            }
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(file, dirs[dirs.length - 1]);
        return file;
    }
}
