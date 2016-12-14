package stoyanoff.milenabooks.activities;

import android.support.v4.app.Fragment;

import stoyanoff.milenabooks.fragments.BooksFragment;

public class BooksActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new BooksFragment().newInstance();
    }
}
