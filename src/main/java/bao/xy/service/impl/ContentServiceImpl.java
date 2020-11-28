package bao.xy.service.impl;

import bao.xy.dao.ContentMapping;
import bao.xy.model.Content;
import bao.xy.model.PageData;
import bao.xy.model.TableData;
import bao.xy.service.ContentService;
import bao.xy.service.JdbcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Resource
    ContentMapping contentMapping;

    @Resource
    JdbcService jdbcService;


    @Override
    @Transactional( rollbackFor = Exception.class)
    public TableData<Content> content(String bookId, PageData pd) {
        TableData<Content> td = new TableData<>(pd);

        Integer listCount = contentMapping.listCount(bookId);
        td.setDataCount(listCount);
        if (listCount == null || listCount == 0) {
            return td;
        }
        List<Content> list = contentMapping.content(bookId);
        td.setDataList(list);
        return td;
    }

    /**
     * 添加章节内容
     * @param chap 章节
     * @param id 书籍ID
     * @param content 内容
     * @return String: addSuc addErr
     */
    @Override
    public String add(String chap, String id, String content) {
        boolean updt = jdbcService.updt(1, "content", chap, content, "book_id", id);
        if (updt) {
//            String str = String.valueOf(content(id).get(0));
//            int  len = (str.getBytes().length - 153) / 3;
//            String size = len + "字";
//            String date = StringUtils.strDate();
//            jdbcService.updt(1, "book", "size", size, "id", id);
//            jdbcService.updt(1,"book", "date", date, "id", id);
            return "addSuc";
        }
        return "addErr";
    }

}
