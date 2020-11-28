package bao.xy.dao;

import bao.xy.model.Book;
import bao.xy.model.Content;
import bao.xy.model.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface BookMapping {

    /**
     * 添加书籍信息
     * @param book
     * @return Integer
     */
    Integer add(@Param("book") Book book);

    /**
     * 修改书籍信息
     * @param book 书籍信息
     * @return Integer
     */
    Integer updt(@Param("book") Book book);

    /**
     * 获取staff表中的数据
     * @param rb limit
     * @param genre 查询职位
     * @param name 查询姓名
     * @param author 查询状态
     * @return List<Staff>
     */
    List<Book> select(RowBounds rb, @Param("genre") String genre, @Param("name") String name, @Param("author") String author);

    /**
     * 获取员工表中的数据
     * @param genre 查询职位
     * @param name 查询姓名
     * @param author 查询状态
     * @return 数据条数
     */
    Integer listCount(@Param("genre") String genre, @Param("name") String name, @Param("author") String author);


}
