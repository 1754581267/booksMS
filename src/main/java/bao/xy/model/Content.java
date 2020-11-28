package bao.xy.model;

public class Content {

    private String id;
    private String bookId;
    // 章节数
    private String serial;
    // 章节名
    private String chapter;
    // 章节内容
    private String content;

    private String date;

    public Content() {
    }

    public Content(String id, String bookId, String serial, String chapter, String content, String date) {
        this.id = id;
        this.bookId = bookId;
        this.serial = serial;
        this.chapter = chapter;
        this.content = content;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id='" + id + '\'' +
                ", bookId='" + bookId + '\'' +
                ", serial='" + serial + '\'' +
                ", chapter='" + chapter + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
