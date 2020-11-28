package bao.xy.dao;

import bao.xy.dto.RecordBook;
import bao.xy.model.Book;
import bao.xy.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WechatMapping {

    Integer add(@Param("user") User user);

    List<User> selUser(@Param("openid") String openid);

    /**
     * 根据点击量读取书籍信息
     * @param genre 类别
     * @param name 书名
     * @param author 作者
     * @return List<book>
     */
    List<Book> selBook(@Param("id") String id,@Param("genre") String genre, @Param("name") String name, @Param("author") String author, @Param("date") String date);

    List<RecordBook> selRBook(@Param("id") String id);

    /**
     * 获取章节内容
     * @param bookId 书ID
     * @param str 章节信息
     * @return string
     */
    String bContent(@Param("bookId") String bookId,@Param("str") String str);
}
