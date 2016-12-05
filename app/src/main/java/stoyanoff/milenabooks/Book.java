package stoyanoff.milenabooks;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Stoyanoff on 28/11/2016.
 */

public class Book implements Serializable{
    @SerializedName("Id")
    private String bookId;

    @SerializedName("Name")
    private String bookName;

    @SerializedName("Price")
    private float bookPrice;

    @SerializedName("Author")
    private String bookAuthor;

    @SerializedName("PictureURL")
    private String pictureUrl;

    @SerializedName("Rating")
    private int bookRating;

    @SerializedName("Description")
    private String bookDescription;

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }



    public float getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(float bookPrice) {
        this.bookPrice = bookPrice;
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

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getBookRating() {
        return bookRating;
    }

    public void setBookRating(int bookRating) {
        this.bookRating = bookRating;
    }







}
