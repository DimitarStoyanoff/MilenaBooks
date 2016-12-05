package stoyanoff.milenabooks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Stoyanoff on 28/11/2016.
 */

public class SingleBookFragment extends Fragment {

    private static final String EXTRA_ONE_BOOK = "giffMe1Book";

    private Book cBook;
    private TextView nameTextView;
    private TextView priceTextView;
    private TextView authorTextView;
    private RatingBar ratingBar;
    private ImageView bookImageView;


    public static SingleBookFragment newInstance(Book currentBook){

        Bundle bookBundle = new Bundle();
        bookBundle.putSerializable(EXTRA_ONE_BOOK, currentBook);
        SingleBookFragment fragment = new SingleBookFragment();
        fragment.setArguments(bookBundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_single_book, container, false);

        cBook = (Book) getArguments().getSerializable(EXTRA_ONE_BOOK);

        bookImageView = (ImageView) v.findViewById(R.id.upload_book_image);
        nameTextView = (TextView)  v.findViewById(R.id.single_book_name);
        priceTextView = (TextView) v.findViewById(R.id.single_book_price);
        authorTextView = (TextView) v.findViewById(R.id.single_book_author);
        ratingBar = (RatingBar) v.findViewById(R.id.single_book_rating_bar);



        nameTextView.setText(cBook.getBookName());
        priceTextView.setText("Price: " + Float.toString(cBook.getBookPrice()));
        authorTextView.setText(cBook.getBookAuthor());
        ratingBar.setRating(cBook.getBookRating());
        ratingBar.setIsIndicator(true);
        ratingBar.setScaleX(0.5f);
        ratingBar.setScaleY(0.5f);

        Picasso.with(getActivity()).load(cBook.getPictureUrl())
                .error(R.drawable.book_error_icon)
                .placeholder(R.drawable.book_placeholder_icon)
                .into(bookImageView);


        return v;
    }

}
