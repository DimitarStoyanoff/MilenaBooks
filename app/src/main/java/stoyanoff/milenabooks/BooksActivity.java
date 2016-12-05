package stoyanoff.milenabooks;

import android.support.v4.app.Fragment;

public class BooksActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new BooksFragment().newInstance();
    }
}
