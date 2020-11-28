package bao.xy.controller;

import bao.xy.model.*;
import bao.xy.service.*;
import bao.xy.service.impl.BookServiceImpl;
import bao.xy.service.impl.LoginServiceImpl;
import bao.xy.utils.StringUtils;
import bao.xy.utils.WebUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BooksController {

    @Resource
    JdbcService jdbcService;

    @Resource
    LoginService loginService;

    @RequestMapping("/login.ajax")
    public String login(Staff user, HttpServletRequest request) {
        // 状态码
        Integer code = 0;
        // 2. 校验参数
        if (StringUtils.isAllNotEmpty(user.getUserName(), user.getPassword())) {
            // 3.调用业务处理(service)
            code = loginService.login(user, request);
        } else {
            code = -1;
        }
        JSONObject json = new JSONObject();
        json.put("code", code);
        return json.toJSONString();
    }

    @Resource
    MainService mainService;

    @RequestMapping("/main.ajax")
    public String main(String str, String password, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        Staff user = (Staff) request.getSession().getAttribute("user");
        // 1. 校验参数
        if (StringUtils.isEmpty(str)) {
            return null;
        } else {
            // 获取姓名和职位
            if ("work".equals(str)) {

                json.put("name", user.getName());
                json.put("work", user.getWork());
            }
            // 查询原始密码
            if ("pwd".equals(str)) {
                String code = mainService.oldPwd(password, request);
                json.put("code", code);
            }
            // 修改密码
            if ("change".equals(str)) {
                String code = mainService.change(password, request);
                json.put("code", code);
            }
            // 退出登录
            if ("out".equals(str)) {
                // 将session值 清空
                request.getSession().setAttribute("user", null);
                json.put("code", "ok");
            }
        }

        return json.toJSONString();
    }

    @Resource
    private StaffService staffService;

    @RequestMapping("/staff.ajax")
    public String Staff(PageData pageData, String str, Staff staff, HttpServletRequest request) {
        String code = null;
        TableData<Staff> td = new TableData(pageData);
        if ("paging".equals(str)) {
            td = staffService.paging(pageData);
            code = StringUtils.isOrNot("管理员", "adm", request);
        }
        if ("uname".equals(str) || "upuname".equals(str)) {
            code = staffService.exist(str, staff);
        }
        if ("add".equals(str)) {
            System.out.println(staff);
            if (staffService.exist("uname", staff).equals("unameOK")) {
                code = staffService.add(staff);
            } else {
                code = "exist";
            }
        }
        if ("updt".equals(str)) {
            System.out.println(staff);
            if (staffService.exist("upuname", staff).equals("unameOK")) {
                code = staffService.updt(staff);
            } else {
                code = "exist";
            }
        }

        List<Integer> idList = new ArrayList<>();
        if (StringUtils.isNotEmpty(staff.getId())) {
            idList = StringUtils.idList(staff.getId());
            // 不允许操作id为1的用户信息
            for (Integer id : idList) {
                if (id == 1) {
                    idList.remove(id);
                }
            }

        }
        // 调用的删除业务
        if ("del".equals(str)) {
            code = jdbcService.delListIds("staff", idList);
        }
        // 解锁业务
        if ("empower".equals(str)) {
            code = jdbcService.updtIds("staff", "state", "已解锁", idList);
        }
        // 锁定业务
        if ("revoke".equals(str)) {
            code = jdbcService.updtIds("staff", "state", "已锁定", idList);
        }
        // 充值密码
        if ("reset".equals(str)) {
            code = staffService.reset(staff, request);
        }

        td.setCode(code);
        return WebUtils.returnData(td);
    }


//   书籍管理
    @Resource
    private BookService bookService;

    @RequestMapping("/book.ajax")
    public String book(PageData pd, String str, Book book, MultipartFile myFile, HttpServletRequest request) {
        TableData<Book> td = new TableData<>(pd);
        String code = null;

        if ("paging".equals(str)) {
            td = bookService.paging(pd);
            code = StringUtils.isOrNot("管理员", "adm", request);
        }

        if ("add".equals(str)) {
            code = bookService.add(book, myFile);
        }

        if ("updt".equals(str)) {
            code = bookService.updt(book, myFile);
        }

        if ("name".equals(str) || "upname".equals(str)) {
            code = bookService.exist(str, book);
        }

        List<Integer> idList = new ArrayList<>();
        if (StringUtils.isNotEmpty(book.getId())) {
            idList = StringUtils.idList(book.getId());
        }

        // 调用的删除业务
        if ("del".equals(str)) {
            code = jdbcService.delList("content", "book_id", idList);
            code = jdbcService.delListIds("book", idList);
        }

        td.setCode(code);
        return WebUtils.returnData(td);
    }

    @Resource
    ContentService contentService;

    @RequestMapping("/content.ajax")
    public String content(String bookId, PageData pd) {
        JSONObject json = new JSONObject();

        TableData<Content> td = contentService.content(bookId, pd);
        json.put("list", td);
        return json.toJSONString();

    }

    @RequestMapping("/content.add.ajax")
    public String contentAdd(String chap, String id, String content) {
        JSONObject json = new JSONObject();
        String add = contentService.add(chap, id, content);

        json.put("code", add);
        return json.toJSONString();
    }

    /**
     * 显示图片
     * @param response
     * @param img 图片信息
     * @throws Exception
     */
    @RequestMapping("/img")
    public void imgShow(HttpServletResponse response, String img) throws Exception {

        String imgPath = BookServiceImpl.imgPath;

        // 输出流
        OutputStream ops = response.getOutputStream();
        FileInputStream fis = null;
        try {
            // 读取图片
            fis = new FileInputStream(new File( imgPath + img));

            byte[] b = new byte[1024];
            int line = 0;
            while ((line = fis.read(b)) != -1) {
                ops.write(b, 0, line);
            }
        } catch (FileNotFoundException e) {
            // 当文件没找到时用默认的封面
            img = "default.jpg";
            fis = new FileInputStream(new File(imgPath + img));
            byte[] b = new byte[1024];
            int line = 0;
            while ((line = fis.read(b)) != -1) {
                ops.write(b, 0, line);
            }
        } finally {
            fis.close();
            ops.close();
        }
    }

    @Resource
    RecordService recordService;

    @RequestMapping("/record.ajax")
    public String record(PageData pd, String str, Record record, HttpServletRequest request) {
        TableData<Record> td = new TableData<>(pd);
        String code = null;
        if ("paging".equals(str)) {
            td = recordService.paging(pd);
            code = StringUtils.isOrNot("管理员", "adm", request);
        }
        List<Integer> idList = new ArrayList<>();
        if (StringUtils.isNotEmpty(record.getId())) {
            idList = StringUtils.idList(record.getId());
        }
        if ("del".equals(str)) {
            code = recordService.delList("records", "book_id", idList);
        }


        td.setCode(code);
        return WebUtils.returnData(td);
    }

}
