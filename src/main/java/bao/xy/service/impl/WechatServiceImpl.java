package bao.xy.service.impl;

import bao.xy.dao.ContentMapping;
import bao.xy.dao.RecordMapping;
import bao.xy.dao.WechatMapping;
import bao.xy.dto.RecordBook;
import bao.xy.model.Book;
import bao.xy.model.Content;
import bao.xy.model.Record;
import bao.xy.model.User;
import bao.xy.service.JdbcService;
import bao.xy.service.WechatService;
import bao.xy.utils.HttpVisitUtils;
import bao.xy.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WechatServiceImpl implements WechatService {

    private String WechatId = null;

    private static final String APP_ID = "wx58251c38ef1a99e6";
    private static final String APP_PASSWD = "171d7702ee1567ef3174b66ce919fa77";

    @Resource
    WechatMapping wechatMapping;

    @Resource
    JdbcService jdbcService;

    @Resource
    ContentMapping contentMapping;

    @Resource
    RecordMapping recordMapping;

    @Transactional( rollbackFor = Exception.class)
    public JSONObject login(JSONObject data) {
        String code = data.getJSONObject("login").getString("code");
        JSONObject info = data.getJSONObject("info");
        String name = info.getString("nickName");
        String img = info.getString("avatarUrl");

        String url = "https://api.weixin.qq.com/sns/jscode2session" +
                "?appid=" + APP_ID + "" +
                "&secret=" + APP_PASSWD + "" +
                "&js_code=" + code +
                "&grant_type=authorization_code";
        String s = HttpVisitUtils.get(url);
        JSONObject parse = (JSONObject) JSONObject.parse(s);
        String openid = parse.getString("openid");

        String gender = info.getString("gender");
        String country = info.getString("country");
        String city = info.getString("city");
        String province = info.getString("province");
        String language = info.getString("language");
        if ("1".equals(gender)) {
            gender = "男";
        } else {
            gender = "女";
        }
        String id = null;
        List<User> users = wechatMapping.selUser(openid);

        // 为空 用户不存在 添加用户信息
        if (users.size() == 0) {
            User user = new User("null", openid, name, gender, country, province, city, language);
            id = user.getId();
            Integer add = wechatMapping.add(user);
        } else {
            User user1 = users.get(0);
            id = user1.getId();
            // 如果用户名修改了 进行更新
            if (!user1.getNickName().equals(name)) {
                boolean updt = jdbcService.updt(1, "user", "nickName", name, "openid", openid);
            }
        }
        WechatId = id;
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("img", img);
        json.put("id", id);
        return json;
    }

    /**
     * 根据点击量读取书籍信息
     * @param sd 搜索信息
     * @return json
     */
    @Override
    @Transactional( rollbackFor = Exception.class)
    public List<Book> recBook(String sd) {
        String id = null;
        String genre = null;
        String name = null;
        String author = null;
        String date = null;
        JSONObject sdJson = StringUtils.strJson(sd);
        if (sdJson != null) {
            id = sdJson.getString("id");
            genre = sdJson.getString("genre");
            name = sdJson.getString("name");
            author = sdJson.getString("author");
            date = sdJson.getString("date");
        }
        List<Book> bookList = wechatMapping.selBook(id, genre, name, author,date);
        return bookList;
    }

    /**
     * 读取书籍章节内容
     * @param sd
     * @return
     */
    @Override
    @Transactional( rollbackFor = Exception.class)
    public List<Content> content(String sd) {
        JSONObject sdJson = StringUtils.strJson(sd);
        String id = null;
        if (sdJson != null) {
            id = sdJson.getString("id");
        }
        List<Content> content = contentMapping.content(id);
        return content;
    }

    /**
     * 获取用户阅读记录
     * @param id 用户id
     * @return JSONObject
     */
    @Override
    @Transactional( rollbackFor = Exception.class)
    public List<RecordBook> recordBook(String id) {
        List<RecordBook> recordBooks = wechatMapping.selRBook(id);
        return recordBooks;
    }

    /**
     * 读取章节信息
     * @param json json格式的数据
     * @return string 内容
     */
    @Override
    @Transactional( rollbackFor = Exception.class)
    public String bookContnet(JSONObject json) {
        String bookId = json.getString("bookId");
        String str = json.getString("str");
        String content = wechatMapping.bContent(bookId, str);
        // 向记录表添加记录
        if (WechatId != null ) {
            String date =  StringUtils.strDate();
            System.out.println(date);
            Record record = new Record(null, WechatId, bookId, null, null, str, date, null);
            recordMapping.add(record);
        }
        return content;
    }


}
