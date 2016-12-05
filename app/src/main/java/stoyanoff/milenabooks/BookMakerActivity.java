package stoyanoff.milenabooks;

import android.support.v4.app.Fragment;

/**
 * Created by Stoyanoff on 1/12/2016.
 */

public class BookMakerActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return BookMakerFragment.newInstance();
    }
}
