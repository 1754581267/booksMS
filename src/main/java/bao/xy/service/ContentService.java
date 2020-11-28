package bao.xy.service;

import bao.xy.model.Content;
import bao.xy.model.PageData;
import bao.xy.model.TableData;

public interface ContentService {

    TableData<Content> content(String bookId, PageData pd);

    /**
     * 添加章节内容
     * @param chap 章节
     * @param id 书籍ID
     * @param content 内容
     * @return String: addSuc addErr
     */
    String add(String chap, String id, String content);
}
