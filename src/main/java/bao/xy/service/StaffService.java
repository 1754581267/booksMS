package bao.xy.service;

import bao.xy.model.PageData;
import bao.xy.model.Staff;
import bao.xy.model.TableData;

import javax.servlet.http.HttpServletRequest;

public interface StaffService {

    /**
     * 获取staff表中的数据
     * @param pageData 页面的相关数据
     * @return TableData<Staff>
     */
    TableData<Staff> paging(PageData pageData);

    /**
     * 查询用户名是否存在
     * @param staff 员工信息
     * @return "unameOK" "unameNot"
     */
    String exist(String str, Staff staff);

    /**
     * 添加员工
     * @param staff 员工信息
     * @return "addSuc" "addErr"
     */
    String add(Staff staff);

    /**
     * 修改员工信息
     * @param staff 员工信息
     * @return
     */
    String updt(Staff staff);

    /**
     * 重置密码
     * @param staff 员工信息
     * @return "resetSuc" "resetErr"
     */
    String reset(Staff staff, HttpServletRequest request);
}

