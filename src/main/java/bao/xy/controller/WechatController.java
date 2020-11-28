package bao.xy.controller;

import bao.xy.dto.RecordBook;
import bao.xy.model.Book;
import bao.xy.model.Content;
import bao.xy.service.WechatService;
import bao.xy.utils.HttpVisitUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class WechatController {

    @Resource
    WechatService wechatService;

    @RequestMapping("/wechat-login.ajax")
    public JSONObject login(@RequestBody JSONObject data) {
        JSONObject login = wechatService.login(data);
        return login;
    }

    @RequestMapping("/wechat-book.ajax")
    public JSONObject book(@RequestBody JSONObject data) {
        String sd = data.getString("sd");
        String str = data.getString("str");
        JSONObject json = new JSONObject();
        List<Book> bookList = wechatService.recBook(sd);
        if ("all".equals(str)) {
            List<Content> content = wechatService.content(sd);
            json.put("content", content);
        }
        json.put("list", bookList);
        return json;
    }

    @RequestMapping("/wechat-rb.ajax")
    public JSONObject reorcdBook(@RequestBody JSONObject data) {
        String id = data.getString("id");
        JSONObject json = new JSONObject();
        List<RecordBook> recordBooks = wechatService.recordBook(id);
        json.put("rb", recordBooks);
        return json;
    }

    @RequestMapping("/wechat-content.ajax")
    public JSONObject content(@RequestBody JSONObject data) {
        String contnet = wechatService.bookContnet(data);
        JSONObject json = new JSONObject();
        json.put("content", contnet);
        return json;
    }

}
