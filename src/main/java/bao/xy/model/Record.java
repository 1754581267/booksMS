package bao.xy.model;

public class Record {

    private String id;
    private String userId;
    private String bookId;
    private String bookName;
    private String author;
    private String bookCont;
    private String date;
    private String count;

    public Record() {
    }

    public Record(String id, String userId, String bookId, String bookName, String author, String bookCont, String date, String count) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.bookCont = bookCont;
        this.date = date;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookCont() {
        return bookCont;
    }

    public void setBookCont(String bookCont) {
        this.bookCont = bookCont;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "record{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", bookCont='" + bookCont + '\'' +
                ", date='" + date + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
