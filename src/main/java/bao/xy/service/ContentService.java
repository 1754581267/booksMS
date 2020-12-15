package bao.xy.service;

import bao.xy.model.Content;
import bao.xy.model.PageData;
import bao.xy.model.TableData;

public interface ContentService {

    TableData<Content> content(String bookId, PageData pd);

    /**
     * 添加章节内容
     * @param content 内容
     * @return String: addSuc addErr
     */
    String add(Content content);

    /**
     * 修改章节
     * @param content 章节对象
     * @return String updtSuc updtErr
     */
    String updt(Content content);
    /**
     * 删除章节
     * @param id
     * @return
     */
    String delChap(String id, String bookId);
}
