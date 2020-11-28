package bao.xy.utils;

import org.apache.ibatis.session.RowBounds;

/**
 * @Description: 页面工具类
 * @CreateTime: 2020-09-21-19-49
 */
public class PageUtils {

    /**
     * 获取最大页面数
     * @param dataCount 数据条数
     * @param pageSize 每页数据数量
     * @return
     */
    public static Integer getLastPage(Integer dataCount, Integer pageSize) {
        if (dataCount % pageSize == 0) {
            return dataCount / pageSize;
        } else {
            return (dataCount / pageSize) + 1;
        }
    }

    /**
     * 分页数据计算
     * @param pageIndex 当前页
     * @param pageSize  每页数据条数
     * @return RowBounds
     */
    public static RowBounds getRowBounds(Integer pageIndex, Integer pageSize) {
        int offset = (pageIndex - 1) * pageSize;
        int limit = pageSize;
        RowBounds rb = new RowBounds(offset, limit);
        return rb;
    }

}
