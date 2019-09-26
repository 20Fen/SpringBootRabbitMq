package com.example.demo.system.util;

import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * Description:
 *
 * @author yangfl
 * @date 2019年09月25日 9:33
 * Version 1.0
 */
@Log4j2
public class TestUtil {


    /**
     * @return void
     * @description 判断路径是否存在
     * @Param [ path ]
     */
    public static void checkFilePath(String path) {
        File targetFile = new File(path);
//        如果不存在则创建
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
    }

    /**
     * @return void
     * @description 获取当前操作系统的可用空间
     * @Param [ dirName ]
     */
    public static Long getFreeDiskSpace(String dirName) {
        File dir = new File(dirName);
        long usableSpace = dir.getUsableSpace();
        log.info(usableSpace);
        return usableSpace;
    }

    /**
     * Description:  遍历文件用的递归
     * @param file:
     * @param map:
     * @return void
     * @date  2019/9/26 15:35
     */
    public static void listFile(File file, Map<String, String> map) {
        String property = System.getProperty("user.dir");
        System.out.println(property);
        if (!file.isFile()) {
            //如果不是一个文件，而是一个目录
            File[] listFiles = file.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                listFile(listFiles[i], map);
            }
        } else {
            String substring = file.getName().substring(file.getName().indexOf("_") + 1);
            map.put(file.getName(), substring);
        }
    }

/**
 * Description:  下载文件
 * @param fileUrl:
 * @param response:
 * @return void
 * @date  2019/9/26 15:34
 */
    public static void down(String fileUrl, HttpServletResponse response) throws IOException {
        //读取下载的文件
        FileInputStream in = new FileInputStream(fileUrl);
        //创建输出流
        OutputStream out = response.getOutputStream();
        // 创建缓冲区
        byte buffer[] = new byte[1024];
        int len = 0;
        // 循环将输入流中的内容读取到缓冲区当中
        while ((len = in.read(buffer)) > 0) {
            // 输出缓冲区的内容到浏览器，实现文件下载
            out.write(buffer, 0, len);
        }
        // 关闭文件输入流
        in.close();
        // 关闭输出流
        out.close();
    }
}