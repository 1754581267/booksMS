package bao.xy.dao;

import bao.xy.model.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据库登录业务
 */
@Mapper
public interface LoginMapping {

    List<Staff> selet(@Param("userName") String userName);

}
