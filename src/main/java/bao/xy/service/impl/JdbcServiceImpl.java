package bao.xy.service.impl;

import bao.xy.dao.JdbcUtilsMapper;
import bao.xy.service.JdbcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @CreateTime: 2020-09-20-15-39
 */
@Service
public class JdbcServiceImpl implements JdbcService {

    @Resource
    private JdbcUtilsMapper mapper;

    @Override
    @Transactional( rollbackFor = Exception.class) // 出现异常指令回滚(rollback) 不带注解直接提交(commit)
    public boolean updt(Integer num, String table, String k, String v, String wherek, String wherev) {
        Integer updt = mapper.updt(table, k, v, wherek, wherev);
        if (updt >= num) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询 一个约束
     * @param table 表名
     * @param k 键
     * @param k1 约束键
     * @param v1 约束值
     * @return string
     */
    @Override
    public String selStr(String table, String k, String k1, String v1) {
        return selectStr(table, k, k1, v1, null, null);
    }

    /**
     * 查询 两个约束
     * @param table 表名
     * @param k 键
     * @param k1 约束键1
     * @param v1 约束值1
     * @param k2 约束键2
     * @param v2 约束值2
     * @return string
     */
    @Override
    @Transactional( rollbackFor = Exception.class)
    public String selectStr(String table, String k, String k1, String v1, String k2, String v2) {
        return mapper.selectStr(table, k, k1, v1, k2, v2);
    }

    /**
     * 查询 一个约束
     * @param table 表名
     * @param k1 约束键
     * @param v1 约束值
     * @return integer
     */
    @Override
    public Integer sel(String table, String k1, String v1) {
        return select(table, k1, v1, null, null);
    }

    /**
     * 查询 两个约束
     * @param table 表名
     * @param k1 约束键1
     * @param v1 约束值1
     * @param k2 约束键2
     * @param v2 约束值2
     * @return integer
     */
    @Override
    @Transactional( rollbackFor = Exception.class)
    public Integer select(String table, String k1, String v1, String k2, String v2) {
        return mapper.select(table, k1, v1, k2, v2);
    }

    /**
     * 删除数据库对象
     * @param table 表名
     * @param k Key
     * @param v value
     * @return Integer
     */
    @Override
    @Transactional( rollbackFor = Exception.class)
    public Integer delect(String table, String k, String v) {
        return mapper.delect(table, k, v);
    }

    /**
     * 根据id删除数据
     * @param table 表名
     * @param idList List<Integer> ids 值
     * @return "delSuc" "delErr"
     */
    @Override
    public String delListIds(String table, List<Integer> idList) {
        return delList(table, "id", idList);
    }

    /**
     * 根据id删除数据
     * @param table 表名
     * @param k 键
     * @param idList List<Integer> ids 值
     * @return "delSuc" "delErr"
     */
    @Override
    @Transactional( rollbackFor = Exception.class)
    public String delList(String table, String k, List<Integer> idList) {
        Integer integer = mapper.delIds(table, k, idList);
        if (integer == idList.size()) {
            return "delSuc";
        } else {
            return "delErr";
        }
    }

    /**
     * 按id修改表中信息
     * @param table 表名
     * @param k key
     * @param v value
     * @param idList List<Integer> idList
     * @return "updtSuc" "updtErr"
     */
    public String updtIds(String table, String k, String v, List<Integer> idList) {
        Integer integer = mapper.updtIds(table, k, v, idList);
        if (integer == idList.size()) {
            return "updtSuc";
        } else {
            return "updtErr";
        }
    }

    /**
     * 添加数据
     * @param table 表名
     * @param k 对应的key
     * @param v 对应的value
     * @return Integer
     */
    @Override
    @Transactional( rollbackFor = Exception.class)
    public Integer add(String table, String k, String v) {
        Integer add = mapper.add(table, k, v);
        return add;
    }

    @Override
    @Transactional( rollbackFor = Exception.class)
    public boolean truncateTable(String tableName) {
        Integer i = mapper.truncateTable(tableName);
        if (i == 0) {
            return true;
        }
        return false;
    }
}
