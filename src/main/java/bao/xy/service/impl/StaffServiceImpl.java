package bao.xy.service.impl;

import bao.xy.dao.StaffMapping;
import bao.xy.model.PageData;
import bao.xy.model.Staff;
import bao.xy.model.TableData;
import bao.xy.service.JdbcService;
import bao.xy.service.StaffService;
import bao.xy.utils.PageUtils;
import bao.xy.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {

    @Resource
    StaffMapping staffMapping;

    @Resource
    JdbcService jdbcService;

    @Override
    @Transactional( rollbackFor = Exception.class)
    public TableData<Staff> paging(PageData pageData) {
        TableData<Staff> td = new TableData(pageData);
        String work = null;
        String name = null;
        String state = null;
        JSONObject searchData = pageData.getSearchData();
        if (searchData != null) {
            work = searchData.getString("work");
            name = searchData.getString("name");
            state = searchData.getString("state");
        }
        Integer listCount = staffMapping.listCount(work, name, state);
        td.setDataCount(listCount);
        if (listCount == 0) {
            return td;
        }

        List<Staff> staffList = staffMapping.select(PageUtils.getRowBounds(pageData.getPageIndex(), pageData.getPageSize()), work, name, state);
        td.setDataList(staffList);
        return td;
    }

    /**
     * 查询用户名是否存在
     * @param staff 员工信息
     * @return "unameOK" "unameNot"
     */
    public String exist(String str, Staff staff) {

        if ("upuname".equals(str)) {
            Integer select = jdbcService.select("staff", "user_name", staff.getUserName(), "id", staff.getId());
            if (select == 1) {
                return "unameOK";
            }
        }
        Integer sel = jdbcService.sel("staff", "user_name", staff.getUserName());
        if (sel == 0) {
            return "unameOK";
        } else {
            return "unameNot";
        }
    }

    /**
     * 添加员工
     * @param staff 员工信息
     * @return "addSuc" "addErr"
     */
    @Transactional( rollbackFor = Exception.class)
    public String add(Staff staff) {
        String encode = StringUtils.encode(staff.getPassword());
        staff.setPassword(encode);
        Integer add = staffMapping.add(staff);
        if (add != 0) {
            return "addSuc";
        }
        return "addErr";
    }

    /**
     * 修改员工信息
     * @param staff 员工信息
     * @return "updtSuc" "updtErr"
     */
    @Transactional( rollbackFor = Exception.class)
    public String updt(Staff staff) {
        String encode = StringUtils.encode(staff.getPassword());
        staff.setPassword(encode);
        Integer updt = staffMapping.updt(staff);
        if (updt > 0) {
            return "updtSuc";
        }
        return "updtErr";
    }

    /**
     * 重置密码
     * @param staff 员工信息
     * @return "resetSuc" "resetErr"
     */
    public String reset(Staff staff, HttpServletRequest request) {
        // 密码加密
        staff.setPassword(StringUtils.encode(staff.getPassword()));
        Staff user = (Staff) request.getSession().getAttribute("user");
        if (user.getName().equals("admin")) {
            boolean updt = jdbcService.updt(1, "staff", "password", staff.getPassword(), "id", staff.getId());
            if (updt) {
                return "resetSuc";
            }
        }
        return "resetErr";
    }
}
