package bao.xy.service.impl;

import bao.xy.dao.JdbcUtilsMapper;
import bao.xy.dao.RecordMapping;
import bao.xy.model.PageData;
import bao.xy.model.Record;
import bao.xy.model.Staff;
import bao.xy.model.TableData;
import bao.xy.service.RecordService;
import bao.xy.utils.PageUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Resource
    RecordMapping recordMapping;

    @Override
    @Transactional( rollbackFor = Exception.class)
    public TableData<Record> paging(PageData pageData) {
        TableData<Record> td = new TableData(pageData);
        String name = null;
        String author = null;
        String date = null;
        JSONObject searchData = pageData.getSearchData();
        if (searchData != null) {
            name = searchData.getString("name");
            author = searchData.getString("author");
            date = searchData.getString("date");
        }
        Integer listCount = recordMapping.listCount(name, author, date);
        td.setDataCount(listCount);
        if (listCount == 0) {
            return td;
        }

        List<Record> list = recordMapping.list(PageUtils.getRowBounds(pageData.getPageIndex(), pageData.getPageSize()), name, author, date);
        td.setDataList(list);
        return td;
    }

    @Resource
    JdbcUtilsMapper jdbcUtilsMapper;

    public String delList(String table, String k, List<Integer> idList) {
        Integer integer = jdbcUtilsMapper.delIds("records", "book_id", idList);
//        System.out.println(integer + "-" + idList.size());
        if (integer >= idList.size()) {
            return "delSuc";
        } else {
            return "delErr";
        }
    }
}
