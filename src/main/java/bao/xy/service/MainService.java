package bao.xy.service;

import javax.servlet.http.HttpServletRequest;

public interface MainService {

    /**
     * 查询原密码
     * @param password 输入的密码
     * @return "same"  "not"
     */
    String oldPwd(String password, HttpServletRequest request);

    /**
     * 修改密码
     * @param password 新密码
     * @return "change" "not"
     */
    String change(String password, HttpServletRequest request);

}
