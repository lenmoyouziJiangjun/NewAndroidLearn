package com.lll.common.http.old;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * Version 1.0
 * Created by lll on 17/4/20.
 * Description httpUrlConnection 网络请求工具类
 * 确定，需要自己创建thread，
 * copyright generalray4239@gmail.com
 */
public class HttpUrlConnectionUtils {
    /**
     * @param url
     * @param queryParams
     * @return
     */
    private static String getUrl(String url, Map<String, String> queryParams) {
        StringBuilder sb = new StringBuilder(url + "/?");
        for (String key : queryParams.keySet()) {
            sb.append(key + "=" + queryParams.get(key) + "&");
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * 获取cookies
     *
     * @param cookies
     * @return
     */
    private static String getCookies(Map<String, String> cookies) {
        Iterator<Map.Entry<String, String>> it = cookies.entrySet().iterator();
        int count = 0;
        String contentStr = "";
        while (it.hasNext()) {
            @SuppressWarnings("rawtypes")
            java.util.Map.Entry entry = it.next();
            count++;
            if (count == 1) {
                contentStr = entry.getKey() + "=" + entry.getValue();
            } else {
                contentStr += ";" + entry.getKey() + "=" + entry.getValue();
            }
        }

        return contentStr;
    }

    /**
     * get请求
     *
     * @param url
     * @param cookies
     * @param queryParams
     * @return
     */
    public static String doGet(String url, Map<String, String> cookies, Map<String, String> queryParams) {
        if (queryParams != null) {
            //拼接参数
            url = getUrl(url, queryParams);
        }
        return null;
    }

    public static String doPost(String url, Map<String, String> cookies, Map<String, String> queryParams, Map<String, String> formParams) {
        if (queryParams != null) {
            //拼接参数
            url = getUrl(url, queryParams);
        }
        return null;
    }

    /**
     * 文件上传
     *
     * @param urlStr
     * @param cookies
     * @param formParams
     * @param fileMap
     * @param contentType
     * @return
     */
    public static String postFile(String urlStr, Map<String, String> cookies, Map<String, String> formParams, Map<String, File>
            fileMap, String contentType) {
        int nRet = 0;
        String res = "";
        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------------jumei";
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            //添加cookie 方便服务器读取，否则上传失败
            if (cookies != null) {
                conn.setRequestProperty("Cookie", getCookies(cookies));
            }
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // 参数
            if (formParams != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator<Map.Entry<String, String>> iter = formParams.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = entry.getKey();
                    String inputValue = entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }

            // 文件
            if (fileMap != null) {
                Iterator<Map.Entry<String, File>> iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, File> entry = iter.next();
                    String inputName = entry.getKey();
                    File file = entry.getValue();
                    if (file == null) {
                        continue;
                    }
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    String filename = file.getName();
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes());
                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (MalformedURLException e) {
            nRet = 50000 + 1500;
            e.printStackTrace();
        } catch (ProtocolException e) {
            nRet = 50000 + 1499;
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            nRet = 50000 + 900;
            e.printStackTrace();
        } catch (IOException e) {
            nRet = 50000 + 3000;
            e.printStackTrace();
        } catch (Exception e) {
            nRet = 40000 + 3;
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }


}
