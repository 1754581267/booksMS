package bao.xy.service.impl;

import bao.xy.dao.ContentMapping;
import bao.xy.model.Content;
import bao.xy.model.PageData;
import bao.xy.model.TableData;
import bao.xy.service.ContentService;
import bao.xy.service.JdbcService;
import bao.xy.utils.PageUtils;
import bao.xy.utils.StringUtils;
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
        List<Content> list = contentMapping.content(PageUtils.getRowBounds(pd.getPageIndex(), pd.getPageSize()), bookId);
        td.setDataList(list);
        return td;
    }

    /**
     * 添加章节内容
     * @param content 内容
     * @return String: addSuc addErr
     */
    @Override
    @Transactional( rollbackFor = Exception.class)
    public String add(Content content) {
        System.out.println(content.toString());
        Integer add = contentMapping.add(content);
        System.out.println(add);
        if (add > 0) {
//            将添加的章节字数更新到书籍信息
            String size = jdbcService.selStr("book", "size", "id", content.getBookId());
            int  len = content.getChapter().length() + content.getContent().length();
            Integer i = 0;
            if (size != null && !size.equals("")) {
                i = Integer.parseInt(size);
            }
            size = i + len + "";
            String date = StringUtils.strDate();
            jdbcService.updt(1, "book", "size", size, "id", content.getBookId());
            jdbcService.updt(1,"book", "date", date, "id", content.getBookId());
            return "addSuc";
        }
        return "addErr";
    }

    /**
     * 修改章节
     * @param content 章节对象
     * @return String updtSuc updtErr
     */
    @Transactional( rollbackFor = Exception.class)
    public String updt(Content content) {
        content.setDate(StringUtils.strDate());
        Integer updt = contentMapping.updt(content);
        if (updt > 0) {
            return "updtSuc";
        }
        return "updtErr";
    }

    /**
     * 删除章节
     * @param id
     * @return
     */
    @Override
    @Transactional( rollbackFor = Exception.class)
    public String delChap(String id, String bookId) {
        System.out.println(id +"=" + bookId);
        String size = jdbcService.selStr("book", "size", "id", bookId);
        Integer s = Integer.parseInt(size);
        Integer i = jdbcService.selStr("content", "chapter", "id", id).length();
        Integer j = jdbcService.selStr("content", "content", "id", id).length();
        size = s - i - j + "";
        System.out.println(size);
        jdbcService.updt(1, "book", "size", size, "id", bookId);
        Integer del = jdbcService.delect("content", "id", id);
        if (del > 0) {
            return "delSuc";
        }
        return "delErr";
    }

}
