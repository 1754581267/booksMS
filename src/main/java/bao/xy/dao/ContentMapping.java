package bao.xy.dao;

import bao.xy.model.Content;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface ContentMapping {

    /**
     * 获取电子书的章节信息
     * @param bookId
     * @return List<Content>
     */
    List<Content> content(RowBounds rb, @Param("bookId") String bookId);

    /**
     * 添加章节内容
     * @param content 章节对象
     * @return Integer
     */
    Integer add(@Param("content") Content content);

    /**
     * 修改章节内容
     * @param content 章节对象
     * @return Integer
     */
    Integer updt(@Param("content") Content content);

    /**
     * 获取电子书的章节信息的数量
     * @param bookId
     * @return Integer
     */
    Integer listCount(@Param("bookId") String bookId);
}
