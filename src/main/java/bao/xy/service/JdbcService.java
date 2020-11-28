package bao.xy.service;

import java.util.List;

/**
 * @Description:
 * @CreateTime: 2020-09-20-15-38
 */
public interface JdbcService {


    boolean updt(Integer num, String table, String k, String v, String wherek, String wherev);


    /**
     * 查询 一个约束
     * @param table 表
     * @param k 键
     * @param k1 约束键
     * @param v1 约束值
     * @return String
     */
    String selStr(String table, String k, String k1, String v1);

    /**
     * 查询 两个约束
     * @param table 表名
     * @param k 键
     * @param k1 约束键1
     * @param v1 约束值1
     * @param k2 约束键2
     * @param v2 约束值2
     * @return String
     */
    String selectStr(String table, String k, String k1, String v1, String k2, String v2);

    /**
     * 查询 一个约束
     * @param table 表
     * @param k1 约束键
     * @param v1 约束值
     * @return integer
     */
    Integer sel(String table, String k1, String v1);

    /**
     * 查询 两个约束
     * @param table 表名
     * @param k1 约束键1
     * @param v1 约束值1
     * @param k2 约束键2
     * @param v2 约束值2
     * @return integer
     */
    Integer select(String table, String k1, String v1, String k2, String v2);

    /**
     * 根据id删除数据
     * @param table 表名
     * @param idList id[]
     * @return integer
     */
    String delListIds(String table, List<Integer> idList);

    /**
     * 根据id删除数据
     * @param table 表名
     * @param k 键
     * @param idList List<Integer> ids 值
     * @return "delSuc" "delErr"
     */
    String delList(String table, String k, List<Integer> idList);

    /**
     * 按id修改表中信息
     * @param table 表名
     * @param k key
     * @param v value
     * @param idList List<Integer> ids
     * @return "updtSuc" "updtErr"
     */
    String updtIds(String table, String k, String v, List<Integer> idList);

    /**
     * 添加数据
     * @param table 表名
     * @param k 对应的key
     * @param v 对应的value
     * @return Integer
     */
    Integer add(String table, String k, String v);

    boolean truncateTable(String tableName);
}
