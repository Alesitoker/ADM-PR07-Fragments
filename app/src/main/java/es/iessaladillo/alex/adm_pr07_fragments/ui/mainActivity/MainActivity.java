package es.iessaladillo.alex.adm_pr07_fragments.ui.mainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.alex.adm_pr07_fragments.R;
import es.iessaladillo.alex.adm_pr07_fragments.ui.list.ListUsersFragment;
import es.iessaladillo.alex.adm_pr07_fragments.ui.profile.ProfileFragment;
import es.iessaladillo.alex.adm_pr07_fragments.utils.FragmentUtils;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        if (getSupportFragmentManager().findFragmentByTag(
                ListUsersFragment.class.getSimpleName()) == null) {
            loadInitialFragment();
        }
        viewModel.getUser().observe(this, user -> open());
    }

    private void loadInitialFragment() {
        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                ListUsersFragment.newInstance(), ListUsersFragment.class.getSimpleName());
    }

    public void open() {
        if (getSupportFragmentManager().findFragmentByTag(ProfileFragment.class.getSimpleName()) == null) {
            FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flContent,
                    ProfileFragment.newInstance(viewModel.getUser().getValue()),
                    ProfileFragment.class.getSimpleName(),
                    ProfileFragment.class.getSimpleName(), FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        } else {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                    ListUsersFragment.newInstance(), ListUsersFragment.class.getSimpleName());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
