package com.jkuhail.android.myapplication.model;

public class Book {
    private String bookTitle;
    private String authorName;
    private String publishedDate;
    private String description;
    private int pageCount;
    private String categories;
    private String image;
    private String url;

    public Book(String bookTitle, String authorName, String publishedDate, String categories) {
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.publishedDate = publishedDate;
        this.categories = categories;
    }

    public Book(String bookTitle, String authorName, String publishedDate, String description, int pageCount, String categories, String image, String url) {
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.publishedDate = publishedDate;
        this.description = description;
        this.pageCount = pageCount;
        this.categories = categories;
        this.image = image;
        this.url = url;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
