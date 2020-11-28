package bao.xy.dao;

import bao.xy.model.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface RecordMapping {

    List<Record> list(RowBounds rb, @Param("name") String name, @Param("author") String author, @Param("date") String date);

    Integer listCount(@Param("name") String name, @Param("author") String author, @Param("date") String date);

    Integer add(@Param("record") Record record);
}
