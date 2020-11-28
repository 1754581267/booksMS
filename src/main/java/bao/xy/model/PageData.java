package bao.xy.model;

import bao.xy.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;

public class PageData {

    private final static Integer DEFAULT_PAGE_SIZE = 5;

    // 分页索引
    private Integer pageIndex;

    // 分数数据条数
    private Integer pageSize;

    // 搜索数据
    private String searchData;

    public PageData() {
    }

    public PageData(Integer pageIndex, Integer pageSize, String searchData) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.searchData = searchData;
    }

    public Integer getPageIndex() {
        if (this.pageIndex == null || this.pageIndex < 1) {
            this.pageIndex = 1;
        }
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        if (this.pageSize == null || this.pageSize < 1) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public JSONObject getSearchData() {
        if (StringUtils.isNotEmpty(this.searchData)) {
            try {
                JSONObject parse = (JSONObject) JSONObject.parse(this.searchData);
                return parse;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setSearchData(String searchData) {
        this.searchData = searchData;
    }

    @Override
    public String toString() {
        return "PageData{" +
                "pageIndex='" + pageIndex + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", searchData='" + searchData + '\'' +
                '}';
    }
}
