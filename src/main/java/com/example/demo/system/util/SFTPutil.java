package com.example.demo.system.util;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.*;

/**
 * Description:
 *
 * @author yangfl
 * @date 2019年11月25日 13:40
 * Version 1.0
 */
public class SFTPutil {
    private ChannelSftp sftp;
    private Session session;
    /**
     * SFTP 登录用户名
     */
    private String username;
    /**
     * SFTP 登录密码
     */
    private String password;
    /**
     * 私钥
     */
    private String privateKey;
    /**
     * SFTP 服务器地址IP地址
     */
    private String host;
    /**
     * SFTP 端口
     */
    private Integer port;

    /**
     * 构造基于密码认证的sftp对象
     */

    private String sftpPath;

    private static final Properties prop = new Properties();

    public SFTPutil(String username, String password, String host, int port) {

        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    /**
     * 构造基于秘钥认证的sftp对象
     */
    public SFTPutil(String username, String host, int port, String privateKey) {

        this.username = username;
        this.host = host;
        this.port = port;
        this.privateKey = privateKey;
    }

    public SFTPutil() {

    }


    /**
     * 连接sftp服务器
     */
    public  void login() {

        Integer timeout = Integer.parseInt(prop.getProperty("sftp.timeout"));

        try {
            JSch jsch = new JSch();

            if (privateKey != null) {
                jsch.addIdentity(privateKey);// 设置私钥
            }
            session = jsch.getSession(username, host, port);


            if (password != null) {
                session.setPassword(password);
            }

            //防止远程主机公钥改变导致连接失败
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            //设置上传时间
            session.setTimeout(timeout);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;

            System.out.println("连接成功");
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接 server
     */
    public void logout() {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }

    /**
     * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
     *
     * @param basePath     服务器的基础路径
     * @param directory    上传到该目录
     * @param sftpFileName sftp端文件名
     * @param in           输入流
     */
    public void upload(String basePath, String directory, String sftpFileName, InputStream input) throws SftpException {
        try {
            //System.out.println(sftp.pwd());
            System.out.println("上传开始");

//           File file = new File(sftpFileName);
//            Vector<?> objects = listFiles(basePath +"/"+directory);
//            for (Object object : objects) {
//                if (object.equals(file.getName())) {
//                    System.out.println("出现重名");
//                    sftp.cd(basePath);
//                    sftp.cd(directory);
//                    sftp.put(input,file.getName()+"1");
//                }}

            sftp.cd(basePath);
            //sftp.cd(directory);
            sftp.put(input, sftpFileName);  //上传文件
            System.out.println("上传结束");

        } catch (
                SftpException e) {
            System.out.println("上传失败");
        }

    }


    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     * @param
     */
    public Vector<?> listFiles(String directory) throws SftpException {
        //System.out.println(sftp.pwd());
        // sftp.cd(directory);
        return sftp.ls(directory);
    }

    /**
     * 下载文件。
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     */
    public void download1(String directory, String downloadFile, String saveFile) throws SftpException, FileNotFoundException {
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }

        // File file = new File(saveFile);
        sftp.get(downloadFile, saveFile);
    }

    /**
     * 下载文件
     *
     * @param
     * @param remoteFile 远程文件
     * @param localFile  本地文件
     */
    public void download11( String remoteFile, String localFile) {
        try {
            remoteFile = "sftpPath" + remoteFile;
            sftp.get(remoteFile, localFile);
            sftp.quit();
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }
}
