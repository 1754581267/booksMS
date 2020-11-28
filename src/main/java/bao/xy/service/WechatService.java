package bao.xy.service;

import bao.xy.dto.RecordBook;
import bao.xy.model.Book;
import bao.xy.model.Content;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface WechatService {

    JSONObject login(JSONObject data);

    /**
     * 根据点击量读取书籍信息
     * @param sd 搜索信息
     * @return json
     */
    List<Book> recBook(String sd);

    /**
     * 读取书籍章节内容
     * @param sd
     * @return
     */
    List<Content> content(String sd);

    /**
     * 获取用户阅读记录
     * @param id 用户id
     * @return JSONObject
     */
    public List<RecordBook> recordBook(String id);

    /**
     * 读取章节信息
     * @param json json格式的数据
     * @return string 内容
     */
    String bookContnet(JSONObject json);
}
