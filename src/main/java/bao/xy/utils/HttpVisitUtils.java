package bao.xy.utils;


import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;


public class HttpVisitUtils {

    /**
     * 初始化URL链接
     * 
     * @param url URL
     * @return URL链接
     */
    private static URLConnection initConnection(String url) {
        try {
            URLConnection connection = (new URL(url)).openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将InputStream中数据转换为byte[]
     * 
     * @param inputStream InputStream
     * @return byte byte[]数据
     * @throws IOException IO异常
     */
    private static byte[] getBytesByInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 1024];
        int n = 0;
        while ((n = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, n);
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 参数转换
     * 
     * @param param 参数
     * @return 转换后的结果
     */
    private static String transformationParam(Map<String, Object> param) {
        StringBuffer stringBuffer = new StringBuffer();
        param.forEach((key, value) -> {
            if (stringBuffer.length() != 0) {
                stringBuffer.append("&");
            }
            stringBuffer.append(key + "=" + value);
        });
        return stringBuffer.toString();
    }

    /**
     * GET方式访问链接
     * 
     * @param url 网址
     * @return 访问结果
     */
    public static byte[] getToBytes(String url) {
        return getToBytes(url, null);
    }

    /**
     * GET方式访问链接
     * 
     * @param url   网址
     * @param param 参数
     * @return 访问结果
     */
    public static byte[] getToBytes(String url, Map<String, Object> param) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        if (param != null) {
            url += "?" + transformationParam(param);
        }
        URLConnection connection = initConnection(url);
        if (connection == null) {
            return null;
        }
        try {
            connection.connect();
            return getBytesByInputStream(connection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * GET方式访问链接
     * 
     * @param url 网址
     * @return 访问结果
     */
    public static String get(String url) {
        return get(url, null, "UTF-8");
    }

    /**
     * GET方式访问链接
     * 
     * @param url         网址
     * @param charsetName 字符集编码
     * @return 访问结果
     */
    public static String get(String url, String charsetName) {
        return get(url, null, charsetName);
    }

    /**
     * GET方式访问链接
     * 
     * @param url         网址
     * @param param       参数
     * @param charsetName 字符集编码
     * @return 访问结果
     */
    public static String get(String url, Map<String, Object> param, String charsetName) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        if (param != null) {
            url += "?" + transformationParam(param);
        }
        URLConnection connection = initConnection(url);
        if (connection == null) {
            return null;
        }
        try {
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charsetName));
            String line = null;
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line);
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * POST方式访问链接
     * 
     * @param url 网址
     * @return 访问结果
     */
    public static byte[] postToBytes(String url) {
        return postToBytes(url, null);
    }

    /**
     * POST方式访问链接
     * 
     * @param url   网址
     * @param param 参数
     * @return 访问结果
     */
    public static byte[] postToBytes(String url, Map<String, Object> param) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        URLConnection connection = initConnection(url);
        if (connection == null) {
            return null;
        }
        connection.setDoOutput(true);
        connection.setDoInput(true);
        try {
            PrintWriter out = new PrintWriter(connection.getOutputStream());
            String paramStr = "";
            if (param != null) {
                paramStr = transformationParam(param);
            }
            out.print(paramStr);
            out.flush();
            return getBytesByInputStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * POST方式访问链接
     * 
     * @param url 网址
     * @return 访问结果
     */
    public static String post(String url) {
        return post(url, null, "UTF-8");
    }

    /**
     * POST方式访问链接
     * 
     * @param url         网址
     * @param charsetName 字符集编码
     * @return 访问结果
     */
    public static String post(String url, String charsetName) {
        return post(url, null, charsetName);
    }

    /**
     * POST方式访问链接
     * 
     * @param url   网址
     * @param param 参数
     * @return 访问结果
     */
    public static String post(String url, Map<String, Object> param) {
        return post(url, param, "UTF-8");
    }

    /**
     * POST方式访问链接
     * 
     * @param url         网址
     * @param param       参数
     * @param charsetName 字符集编码
     * @return 访问结果
     */
    public static String post(String url, Map<String, Object> param, String charsetName) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        URLConnection connection = initConnection(url);
        if (connection == null) {
            return null;
        }
        connection.setDoOutput(true);
        connection.setDoInput(true);
        try {
            PrintWriter out = new PrintWriter(connection.getOutputStream());
            String paramStr = "";
            if (param != null) {
                paramStr = transformationParam(param);
            }
            out.print(paramStr);
            out.flush();
            String line = null;
            StringBuffer stringBuffer = new StringBuffer();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charsetName));
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line);
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}