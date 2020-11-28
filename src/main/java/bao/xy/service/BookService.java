package bao.xy.service;

import bao.xy.model.Book;
import bao.xy.model.Content;
import bao.xy.model.PageData;
import bao.xy.model.TableData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

    /**
     * 查询书名是否存在
     * @param book 书籍信息
     * @return "nameOK" "nameNot"
     */
    String exist(String str, Book book);

    TableData<Book> paging(PageData pageData);

    String add(Book book, MultipartFile myFile);

    String updt(Book book, MultipartFile myFile);

}
