package bao.xy.service.impl;

import bao.xy.dao.LoginMapping;
import bao.xy.model.Staff;
import bao.xy.service.LoginService;
import bao.xy.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 登录业务
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginMapping loginMapping;

    @Transactional( rollbackFor = Exception.class)
    public Integer login(Staff staff, HttpServletRequest request){
        // 查询用户信息
        List<Staff> selet = loginMapping.selet(staff.getUserName());
        Integer code = 0;
        if (selet.size() != 0 && selet != null) {
            Staff selStaff = selet.get(0);
            String uname = selStaff.getUserName();
            String pwd = StringUtils.docode(selStaff.getPassword());
            if ("已解锁".equals(selStaff.getState())) {
                if (uname.equals(staff.getUserName())) {
                    code += 1;
                    if (pwd.equals(staff.getPassword())) {
                        request.getSession().setAttribute("user", selStaff);
                        code += 1;
                    }
                }
            } else {
                code = -2;
            }

        }
        return code;
    }

}
