package bao.xy.service.impl;

import bao.xy.dao.BookMapping;
import bao.xy.model.*;
import bao.xy.service.BookService;
import bao.xy.service.JdbcService;
import bao.xy.utils.PageUtils;
import bao.xy.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class BookServiceImpl  implements BookService {

    public static String imgPath = "D:\\Javawork\\booksMS\\src\\main\\resources\\static\\img\\";

    @Resource
    private JdbcService jdbcService;

    @Resource
    private BookMapping bookMapping;

    @Override
    @Transactional( rollbackFor = Exception.class)
    public TableData<Book> paging(PageData pageData) {
        TableData<Book> td = new TableData(pageData);
        String genre = null;
        String name = null;
        String author = null;
        JSONObject searchData = pageData.getSearchData();
        if (searchData != null) {
            genre = searchData.getString("genre");
            name = searchData.getString("name");
            author = searchData.getString("author");
        }
        Integer listCount = bookMapping.listCount(genre, name, author);
        td.setDataCount(listCount);
        if (listCount == 0) {
            return td;
        }

        List<Book> bookList = bookMapping.select(PageUtils.getRowBounds(pageData.getPageIndex(), pageData.getPageSize()), genre, name, author);
        td.setDataList(bookList);
        return td;
    }

    /**
     * 查询书名是否存在
     * @param book 书籍信息
     * @return "nameOK" "nameNot"
     */
    public String exist(String str, Book book) {

        if ("upname".equals(str)) {
            Integer select = jdbcService.select("book", "name", book.getName(), "id", book.getId());
            if (select == 1) {
                return "nameOK";
            }
        }
        Integer sel = jdbcService.sel("book", "name", book.getName());
        if (sel == 0) {
            return "nameOK";
        } else {
            return "nameNot";
        }
    }

    @Transactional( rollbackFor = Exception.class)
    public String add(Book book, MultipartFile myFile) {
        if (myFile != null && !myFile.isEmpty()) {
            if (myFile.getContentType().startsWith("image/")) {
                String name = myFile.getOriginalFilename();
                String suffix = name.substring(name.lastIndexOf(".") + 1);
                // 获取文件后缀名 并将书名加后缀 命名为封面名
                String newName = book.getName() + "." + suffix;
                File file = new File(imgPath + newName);
                try {
                    myFile.transferTo(file);
                    book.setImg(newName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        book.setDate(StringUtils.strDate());
        Integer add = bookMapping.add(book);
//        jdbcService.add("content", "book_id", book.getId());
        if (add > 0) {
            return "addSuc";
        }
        return "addErr";
    }

    @Transactional( rollbackFor = Exception.class)
    public String updt(Book book, MultipartFile myFile) {
        if (myFile != null && !myFile.isEmpty()) {
            if (myFile.getContentType().startsWith("image/")) {
                String name = myFile.getOriginalFilename();
                // 获取文件后缀名 并将书名加后缀 命名为封面名
                String suffix = name.substring(name.lastIndexOf(".") + 1);
                String newName = book.getName() + "." + suffix;
                File file = new File(imgPath + newName);
                try {
                    myFile.transferTo(file);
                    jdbcService.updt(1, "book", "img", newName, "id", book.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Integer updt = bookMapping.updt(book);
        if (updt > 0) {
            return "updtSuc";
        }
        return "updtErr";
    }


}
