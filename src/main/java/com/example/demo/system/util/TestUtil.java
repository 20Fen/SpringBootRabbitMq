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
     * @return boolean
     * @description 删除文件下所有文件
     * @Param [ path ]
     * @date 2019/8/29 16:29
     */
    private static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
//        文件是否存在
        if (!file.exists()) {
            return flag;
        }
//        是不是目录
        if (!file.isDirectory()) {
            return flag;
        }
//        得到文件夹中的文件
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
//                对目录进行删除
                boolean delete = temp.delete();
                if (!delete) {
                    log.error("删除失败");
                }
            }
            if (temp.isDirectory()) {
                //先删除文件夹里面的文件
                delAllFile(path + File.separator + tempList[i]);
                // 再删除空文件夹
                delFolder(path + File.separator + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }

    /**
     * @return void
     * @description 删除空文件夹
     * @Param [ folderPath ]
     * @date 2019/8/29 16:30
     */
    public static void delFolder(String folderPath) {
        try {
            //删除完里面所有内容
            delAllFile(folderPath);
            String filePath = folderPath;
            File myFilePath = new File(filePath);
            //删除空文件夹
            boolean myFile = myFilePath.delete();
            if (!myFile) {
                log.error("删除失败");
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

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
     *
     * @param file:
     * @param map:
     * @return void
     * @date 2019/9/26 15:35
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
     *
     * @param fileUrl:
     * @param response:
     * @return void
     * @date 2019/9/26 15:34
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