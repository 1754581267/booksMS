package bao.xy.service.impl;

import bao.xy.model.Staff;
import bao.xy.service.JdbcService;
import bao.xy.service.MainService;
import bao.xy.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class MainServiceImpl implements MainService {

    @Resource
    JdbcService jdbcService;

    /**
     * 查询原密码
     * @param password 输入的原密码
     * @return "same"  "not"
     */
    public String oldPwd(String password, HttpServletRequest request) {
        Staff user = (Staff) request.getSession().getAttribute("user");
        if (user.getPassword().equals(password)) {
            return "same";
        }
        return "not";
    }

    /**
     * 修改密码
     * @param password 新密码
     * @return "change" "not"
     */
    public String change(String password, HttpServletRequest request) {
        // 将密码加密
        String encode = StringUtils.encode(password);
        // 修改密码业务
        Staff user = (Staff) request.getSession().getAttribute("user");
        boolean updt = jdbcService.updt(1, "staff", "password", encode, "id", user.getId());
        // 返回结果
        if (updt) {
            request.getSession().setAttribute("user", null);
            return "change";
        }
        return "not";
    }

}
