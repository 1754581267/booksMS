package bao.xy.service;

import bao.xy.model.PageData;
import bao.xy.model.Record;
import bao.xy.model.TableData;

import java.util.List;

public interface RecordService {

    TableData<Record> paging(PageData pageData);

    String delList(String table, String k, List<Integer> idList);
}
