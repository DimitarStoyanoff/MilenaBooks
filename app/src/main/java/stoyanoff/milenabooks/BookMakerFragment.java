package stoyanoff.milenabooks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Stoyanoff on 1/12/2016.
 */

public class BookMakerFragment extends Fragment {


    private final int PICK_IMAGE = 1;

    private EditText bookNameEditText;

    private EditText bookAuthorEditText;
    private EditText bookPriceEditText;
    private EditText bookDescriptionEditText;
    private ImageView uploadImageView;
    private Button bookSubmitButton;
    private ProgressBar progressBar;
    private BookLoader bookLoader;
    private OnAddNewBookItemListener aOnAddNewBookItemListener;

    private Book newBook;
    private File imageFile;



    public static BookMakerFragment newInstance() {
        return new BookMakerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_maker, container, false);


        bookNameEditText = (EditText) view.findViewById(R.id.book_name_edit_text);
        bookAuthorEditText = (EditText) view.findViewById(R.id.book_author_edit_text);
        bookPriceEditText = (EditText) view.findViewById(R.id.book_price_edit_text);
        bookDescriptionEditText = (EditText) view.findViewById(R.id.book_description_edit_text);
        uploadImageView = (ImageView) view.findViewById(R.id.upload_book_image);
        bookSubmitButton = (Button) view.findViewById(R.id.submit_button);
        progressBar = (ProgressBar) view.findViewById(R.id.book_maker_progress_bar);

        newBook = new Book();

        uploadImageView.setImageResource(R.drawable.upload_image_icon);

        uploadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
                startActivityForResult(chooserIntent, PICK_IMAGE);

            }
        });



        bookSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookSubmitButton.setClickable(false);
                progressBar.setIndeterminate(true);

                newBook.setBookName(bookNameEditText.getText().toString());
                if(!bookPriceEditText.getText().toString().equals("")){
                    newBook.setBookPrice(Float.valueOf(bookPriceEditText.getText().toString()));
                }
                newBook.setBookAuthor(bookAuthorEditText.getText().toString());
                newBook.setBookDescription(bookDescriptionEditText.getText().toString());

                if(imageFile != null){
                    bookLoader = new BookLoader();
                    bookLoader.sendBitmap(imageFile, new BookLoader.OnBitmapSentListener() {
                        @Override
                        public void receivedImage(CustomImage imgurModel) {
                            if(imgurModel != null) {
                           // newBook.setPictureUrl(imgurModel.getCustomImage().getFileURL());
                                newBook.setPictureUrl(imgurModel.getFileURL());

                                uploadBookCallback(newBook,aOnAddNewBookItemListener );
                            } else {
                                Toast.makeText(getContext(), "No image uploaded", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        return view;
    }


    private void uploadBookCallback(Book book,OnAddNewBookItemListener addNewBookItemListener ){


        aOnAddNewBookItemListener = addNewBookItemListener;
        bookLoader.uploadOneBook(newBook, new BookLoader.OnBookUploadedListener() {
            @Override
            public void bookUploaded(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                bookSubmitButton.setClickable(true);
                progressBar.setIndeterminate(false);
                //// TODO: 5/12/2016
                if(message.equals("Book uploaded")){


                    aOnAddNewBookItemListener.newBookItemCreated(newBook);


                }

            }
        });

    }

    public interface OnAddNewBookItemListener{
        void newBookItemCreated(Book book);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri  imageUri = data.getData();
            uploadImageView.setImageURI(imageUri);
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

                imageFile = new File(getContext().getCacheDir(),"image1");
                imageFile.createNewFile();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,0,bos);
                byte[] bitmapdata = bos.toByteArray();

                FileOutputStream fos = new FileOutputStream(imageFile);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}