package stoyanoff.milenabooks.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import stoyanoff.milenabooks.R;
import stoyanoff.milenabooks.fragments.SingleBookFragment;
import stoyanoff.milenabooks.model.Book;

/**
 * Created by Stoyanoff on 28/11/2016.
 */

public class BookPagerActivity  extends AppCompatActivity{
    private ViewPager mViewPager;
    List<Book> books = new ArrayList<>();
    private int bookPosition;


    private static final String EXTRA_BOOKS = "giffMeBook";
    private static final String EXTRA_POSITION = "giffMePosition";

    public static Intent newIntent(Context packageContext, List<Book> book, int position) {
        Intent newPagerIntent = new Intent(packageContext, BookPagerActivity.class);
        Bundle sentBundle = new Bundle();
        sentBundle.putSerializable(EXTRA_BOOKS, (Serializable) book);
        sentBundle.putInt(EXTRA_POSITION, position);
        newPagerIntent.putExtras(sentBundle);

        return newPagerIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_pager);


        Intent thisIntent = this.getIntent();
        Bundle receivedBundle = thisIntent.getExtras();
        books = (List<Book>) receivedBundle.getSerializable(EXTRA_BOOKS);
        bookPosition = receivedBundle.getInt(EXTRA_POSITION);



        mViewPager = (ViewPager) findViewById(R.id.activity_book_view_pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return SingleBookFragment.newInstance(books.get(position));
            }
            @Override
            public int getCount() {
                return books.size();
            }
        });

        mViewPager.setCurrentItem(bookPosition);
    }
}
