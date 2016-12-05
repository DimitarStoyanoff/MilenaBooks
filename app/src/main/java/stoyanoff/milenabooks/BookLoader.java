package stoyanoff.milenabooks;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Stoyanoff on 29/11/2016.
 */

public class BookLoader {
    Gson gson;
    Retrofit retrofit;
    BooksService service;
    Retrofit retrofit2;
    BooksService service2;

    private OnReceivedBookListListener aBookListener;
    private OnBitmapSentListener aBitmapSentListener;
    private OnBookUploadedListener aBookUploadedListener;

    private final String API_URL = "http://milenabooks.azurewebsites.net/api/";
    private final String IMGUR_URL = "https://api.imgur.com/3/";


    public BookLoader(){

        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();


        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        service = retrofit.create(BooksService.class);

        retrofit2 = new Retrofit.Builder()
                .baseUrl(IMGUR_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        service2 = retrofit2.create(BooksService.class);
    }

    public interface BooksService {
        @GET("books")
        Call<List<Book>> listBooks();

        @Multipart
        @Headers("Authorization: Client-ID 8a768c0c829f44b")
        @POST("upload")
        Call<CustomImage> uploadImage(@Part("image") RequestBody fileRequestBody );

        @POST("Books")
        Call<Book> uploadBook(@Body Book book);

    }

    public interface OnReceivedBookListListener {
        void receivedBooks(List<Book> books);
    }
    public interface OnBitmapSentListener {
        void receivedImage(CustomImage imgurModel);
    }
    public interface OnBookUploadedListener{
        void bookUploaded(String message);

    }



    public void loadBooks(OnReceivedBookListListener mBookListener){
        aBookListener = mBookListener;
        Call<List<Book>> call = service.listBooks();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                Log.d("BooksResponse", "Success!");

                aBookListener.receivedBooks(response.body());
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.d("BooksResponse", "Some Error");
            }
        });
    }

//    public void sendBitmap(File imageFile, OnBitmapSentListener mBitmapListener){
//        aBitmapSentListener = mBitmapListener;
//
//        RequestBody file = RequestBody.create(MediaType.parse("image/*"),imageFile);
//
//        Call<ImgurModel> call = service2.uploadImage(file);
//
//
//        call.enqueue(new Callback<ImgurModel>() {
//            @Override
//            public void onResponse(Call<ImgurModel> call, Response<ImgurModel> response) {
//                Log.d("BooksResponse", "Success!");
//                aBitmapSentListener.receivedImage(response.body());
//
//            }
//
//            @Override
//            public void onFailure(Call<ImgurModel> call, Throwable t) {
//                Log.d("BooksResponse", "Some Error");
//            }
//        });
//    }

    public void uploadOneBook(Book uploadedBook, OnBookUploadedListener mBookUploadedListener){

        aBookUploadedListener = mBookUploadedListener;

        Call<Book> call = service.uploadBook(uploadedBook);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                Log.d("BookLoader", "Book uploaded");
                aBookUploadedListener.bookUploaded("Book uploaded");
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.d("BookLoader", "Book upload failed!");
                aBookUploadedListener.bookUploaded("Upload failed");
            }
        });

    }

    public void sendBitmap(File imageFile, OnBitmapSentListener mBitmapListener){
        aBitmapSentListener = mBitmapListener;

        RequestBody fileRequestBody = RequestBody.create(MediaType.parse("image/*"),imageFile);

        Call<CustomImage> call = service.uploadImage(fileRequestBody);


        call.enqueue(new Callback<CustomImage>() {
            @Override
            public void onResponse(Call<CustomImage> call, Response<CustomImage> response) {
                Log.d("BooksResponse", "Success!");

                aBitmapSentListener.receivedImage(response.body());
            }

            @Override
            public void onFailure(Call<CustomImage> call, Throwable t) {
                Log.d("BooksResponse", "Some Error");
            }
        });
    }

}
