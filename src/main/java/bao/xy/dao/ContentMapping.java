package bao.xy.dao;

import bao.xy.model.Content;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContentMapping {

    /**
     * 获取电子书的章节信息
     * @param bookId
     * @return List<Content>
     */
    List<Content> content(@Param("bookId") String bookId);

    /**
     * 获取电子书的章节信息的数量
     * @param bookId
     * @return Integer
     */
    Integer listCount(@Param("bookId") String bookId);
}
