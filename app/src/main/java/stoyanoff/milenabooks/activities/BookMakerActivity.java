package stoyanoff.milenabooks.activities;

import android.support.v4.app.Fragment;

import stoyanoff.milenabooks.fragments.BookMakerFragment;

/**
 * Created by Stoyanoff on 1/12/2016.
 */

public class BookMakerActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return BookMakerFragment.newInstance();
    }
}
