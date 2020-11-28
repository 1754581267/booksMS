package bao.xy.utils;

import bao.xy.model.Staff;
import bao.xy.service.impl.LoginServiceImpl;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 将当前时间转换为字符串格式
     * @return date
     */
    public static String strDate() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return date;
    }

    /**
     * 查询职位
     * @param work 职位
     * @param suffix 特定的字符
     * @return  is+suffix  not+suffix
     */
    public static String isOrNot(String work, String suffix, HttpServletRequest request) {
        String code = "";
        Staff user = (Staff) request.getSession().getAttribute("user");
        String ework = user.getWork();
        if (ework.equals(work)) {
            code = "is" + suffix;
        } else {
            code = "not" + suffix;
        }
        return code;
    }

    public static JSONObject strJson(String str) {
        if (StringUtils.isNotEmpty(str)) {
            try {
                JSONObject strJson = (JSONObject) JSONObject.parse(str);
                return strJson;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将id数组组成的字符串分割添加进list
     * @param ids
     * @return
     */
    public static List<Integer> idList(String ids) {
        String[] idListStr = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String isStr : idListStr) {
            int id1 = Integer.parseInt(isStr);
            idList.add(id1);
        }
        return idList;
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return false or true
     */
    public static boolean isEmpty(String str) {
        return str == null || str.equals("");
    }

    /**
     * 判断字符串是否不为空
     * @param str
     * @return false or true
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否全都不为空
     * @param arr
     * @return false or true
     */
    public static boolean isAllNotEmpty(String...arr) {
        boolean flag = false;
        for (String str : arr) {
            if (!isEmpty(str)) {
                flag =  true;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 删除最后一个符号
     * @param data 字符串
     * @return 字符串子串
     */
    public static String delEnd(String data) {
        if (!"".equals(data)) {
            data = data.substring(0,data.length()-1);
        }
        return data;
    }

    /**
     * 将字符串进行加密操作
     * @param str 需要加密的字符串
     * @return 加密后的字符串
     */
    public static String encode(String str) {
        byte[] s = str.getBytes();
        for (int i = 0; i < s.length; i++) {
            s[i] += 5;
        }
        return new String(s);
    }

    /**
     * 将加密后的字符串解密
     * @param str 已经加密后的字符串
     * @return 解密后的字符串
     */
    public static String docode(String str) {
        byte[] s = str.getBytes();
        for (int i = 0; i < s.length; i++) {
            s[i] -= 5;
        }
        return new String(s);
    }
}
