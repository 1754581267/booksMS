package bao.xy.dao;

import bao.xy.model.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface StaffMapping {

    /**
     * 获取staff表中的数据
     * @param rb limit
     * @param work 查询职位
     * @param name 查询姓名
     * @param state 查询状态
     * @return List<Staff>
     */
    List<Staff> select(RowBounds rb, @Param("work") String work, @Param("name") String name, @Param("state") String state);

    /**
     * 获取员工表中的数据
     * @param work 查询职位
     * @param name 查询姓名
     * @param state 查询状态
     * @return 数据条数
     */
    Integer listCount(@Param("work") String work, @Param("name") String name, @Param("state") String state);

    /**
     * 添加员工
     * @param staff 员工信息
     * @return Integer
     */
    Integer add(@Param("staff") Staff staff);

    /**
     * 更新员工信息
     * @param staff 需要修改的员工信息
     * @return Integer
     */
    Integer updt(@Param("staff") Staff staff);
}
