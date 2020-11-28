package bao.xy.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: 输出到B端的相关工具类
 * @CreateTime: 2020-08-11-15-20
 */
public class WebUtils {

    /**
     * 将对象转换为json数据格式
     * @param object
     * @return
     */
    public static String returnData(Object object) {
        JSONObject json = (JSONObject) JSONObject.toJSON(object);
        return json.toJSONString();
    }

    /**
     *
     * @param response 响应
     * @param str  需要输出的字符串
     * @throws IOException
     */
    public static void ResponsPrint(HttpServletResponse response, String str){
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write("<b style=\"display: block; font-size : 22px; text-align: center\">" + str + "</b>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param response 响应
     * @param href 跳转的地址
     * @param str   提示信息
     * @param scend 跳转的定时时间
     * @throws IOException
     */
    public static void jump(HttpServletResponse response, String href, String str, int scend) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        writer.write("<a href=\"" + href + "\" style=\"display: block; font-size : 150%; text-align: center\">" + str + "</a>");
        response.setHeader("refresh",  scend + ";"+ href );
        writer.flush();
    }
}
