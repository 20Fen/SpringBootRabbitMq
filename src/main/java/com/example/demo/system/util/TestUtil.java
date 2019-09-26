package com.example.demo.system.util;

import lombok.extern.log4j.Log4j2;

import java.io.File;

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
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
    }

    /**
     * @description 获取当前操作系统的可用空间
     * @Param [ dirName ]
     * @return void
     */
    public static Long getFreeDiskSpace(String dirName){
        File dir = new File(dirName);
        long usableSpace = dir.getUsableSpace();
        log.info(usableSpace);
        return usableSpace;
    }
}
