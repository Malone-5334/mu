package com.baselibrary.ftp;

import android.util.Log;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 作者：Malone
 * 时间： 2018/9/28.
 * 邮箱：178917169@qq.com
 * 功能描述：
 */
public class FTPClientManager {
    private static final String TAG = FTPClientManager.class.getName();
    private FTPClient client;

    public FTPClientManager() {
        init();
    }

    private void init() {
        client = new FTPClient();
    }

    /**
     * 是否连接成功
     *
     * @return
     */
    public boolean isConnected() {
        return client.isConnected();
    }

    /**
     * 建立连接
     *
     * @param address 地址
     * @return
     */
    public boolean connect(String address) {
        String host = FTPUtil.parseHost(address);
        int port = FTPUtil.parsePort(address);
        String user = FTPUtil.parseUser(address);
        String pass = FTPUtil.parsePass(address);
        return connect(host, port, user, pass);
    }

    /**
     * 建立连接
     *
     * @param host  主机
     * @param port  端口
     * @param user  用户
     * @param press 密码
     * @return
     */
    public boolean connect(String host, int port, String user, String press) {
        try {
            client.connect(host, port);
            client.login(user, press);
            int reply = client.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                return true;
            }
            disconnect();
            Log.d(TAG, "无法连接到FTP服务器，错误码为：" + reply);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        try {
            client.logout();
            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载
     *
     * @param fileName   文件名称
     * @param remotePath FTP服务器文件路径
     * @param locaPath   本地存储路径
     * @param lisenter   回调接口监听
     */
    public void download(String fileName, String remotePath, String locaPath, DownloadLisenter lisenter) {
        if (fileName == null || remotePath == null || locaPath == null) {
            if (lisenter != null) {
                lisenter.onError(new IllegalArgumentException("File name and FTP server file path and local storage path cannot be empty"));
            }
            return;
        }
        if (client == null || !client.isConnected()) {
            if (lisenter != null) {
                lisenter.onError(new IllegalArgumentException("The FTP server was not connected"));
            }
            return;
        }
        String charset = "UTF-8";
        try {
            int reply = client.sendCommand("OPTS UTF8", "ON");
            if (!FTPReply.isPositiveCompletion(reply)) {
                charset = "GBK";
            }
            client.enterLocalPassiveMode();
            String fn = new String(fileName.getBytes(), Charset.forName(charset));
            String rp = new String(remotePath.getBytes(charset), "ISO-8859-1");
            FTPFile[] fs = client.listFiles(rp + fn);
            for (FTPFile f : fs) {
                InputStream is = client.retrieveFileStream(f.getName());
                reply = client.getReplyCode();
                if (!FTPReply.isPositivePreliminary(reply)) {
                    client.disconnect();
                    if (lisenter != null) {
                        lisenter.onError(new IOException("Error getting file information. Error code " + reply));
                    }
                    return;
                }
                DownloadThread thread = new DownloadThread(is, f.getSize(), locaPath + fileName, lisenter);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传
     *
     * @param file     文件
     * @param lisenter 监听接口
     */
    public void upload(FTPFile file, UploadLisenter lisenter) {
        if (!client.isConnected()) {
            return;
        }
        if (file.isFile()) {

        }

    }
}
