package stoyanoff.milenabooks.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import stoyanoff.milenabooks.services.BookLoader;
import stoyanoff.milenabooks.R;
import stoyanoff.milenabooks.activities.BookMakerActivity;
import stoyanoff.milenabooks.activities.BookPagerActivity;
import stoyanoff.milenabooks.model.Book;

/**
 * Created by Stoyanoff on 28/11/2016.
 */

public class BooksFragment extends Fragment {

    private RecyclerView mBooksRecyclerView;
    private List<Book> aBooks  = new ArrayList<>();
    private FloatingActionButton fab;
    private BookLoader bookLoader;
    private SwipeRefreshLayout swipeRefreshLayout;


    public static BooksFragment newInstance() {
        return new BooksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_books, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) v .findViewById(R.id.fragment_books_swipe_refresh_layout);

        fab = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent makerIntent = new Intent(getActivity(), BookMakerActivity.class);
                startActivity(makerIntent);


            }
        });


        mBooksRecyclerView = (RecyclerView) v
                .findViewById(R.id.fragment_books_recycler_view);
        mBooksRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        mBooksRecyclerView.setAdapter(new BookAdapter(aBooks));

        updateAdapter();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateAdapter();

            }
        });


        return v;
    }

        private void updateAdapter(){


            bookLoader = new BookLoader();
            bookLoader.loadBooks(new BookLoader.OnReceivedBookListListener() {
                @Override
                public void receivedBooks(List<Book> books) {
                    if (isAdded()) {
                        aBooks = books;
                        mBooksRecyclerView.setAdapter(new BookAdapter(aBooks));
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }
            });
        }

    @Override
    public void onResume() {
        super.onResume();

        BookMakerFragment bookMaker = new BookMakerFragment();
        bookMaker.notifyForNewBook(new BookMakerFragment.OnAddNewBookItemListener() {
            @Override
            public void newBookItemCreated(Book book) {

                aBooks.add(aBooks.size()-1,book);
                mBooksRecyclerView.getAdapter().notifyItemInserted(aBooks.size()-1);

            }
        });

        updateAdapter();

    }

    private class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameTextView;
        private ImageView bookImage;
        private Book hBook;


        public BookHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.book_name);
            bookImage = (ImageView) itemView.findViewById(R.id.book_image);
            itemView.setOnClickListener(this);

        }
        public void bindBookItem(Book item) {
            hBook = item;

            nameTextView.setText(item.getBookName());

            Picasso.with(getActivity()).load(item.getPictureUrl())
                    .error(R.drawable.book_error_icon)
                    .placeholder(R.drawable.book_placeholder_icon)
                    .into(bookImage);
        }

        @Override
        public void onClick(View view) {

             Intent intent = BookPagerActivity.newIntent(getActivity(), aBooks,aBooks.indexOf(hBook));
             startActivity(intent);
        }
    }

    private class BookAdapter extends RecyclerView.Adapter<BookHolder> {


        private List<Book> mBooks;

        public BookAdapter(List<Book> books){

            mBooks = books;

        }

        @Override
        public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_book, parent, false);
            return new BookHolder(view);
        }

        @Override
        public void onBindViewHolder(BookHolder holder, int position) {
            Book book = mBooks.get(position);
            holder.bindBookItem(book);


        }

        @Override
        public int getItemCount() {
            return mBooks.size();
        }
    }


}
