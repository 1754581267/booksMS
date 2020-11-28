package bao.xy.model;

public class Book {
    // ID
    private String id;
    // 类别
    private String genre;
    // 书名
    private String name;
    // 作者
    private String author;
    // 简介
    private String note;
    // 大小
    private String size;
    // 封面信息
    private String img;
    // 录入时间
    private String date;

    public Book() {
    }

    public Book(String id, String genre, String name, String author, String note, String size, String img, String date) {
        this.id = id;
        this.genre = genre;
        this.name = name;
        this.author = author;
        this.note = note;
        this.size = size;
        this.img = img;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", genre='" + genre + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", note='" + note + '\'' +
                ", size='" + size + '\'' +
                ", img='" + img + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
