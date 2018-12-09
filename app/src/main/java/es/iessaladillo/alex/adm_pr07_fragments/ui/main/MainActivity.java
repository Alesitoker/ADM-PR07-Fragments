package es.iessaladillo.alex.adm_pr07_fragments.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.alex.adm_pr07_fragments.R;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.Avatar;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.User;
import es.iessaladillo.alex.adm_pr07_fragments.ui.avatar.AvatarFragment;
import es.iessaladillo.alex.adm_pr07_fragments.ui.list.ListUsersFragment;
import es.iessaladillo.alex.adm_pr07_fragments.ui.list.OnChangeAvatarListener;
import es.iessaladillo.alex.adm_pr07_fragments.ui.profile.ProfileFragment;
import es.iessaladillo.alex.adm_pr07_fragments.utils.FragmentUtils;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements OnChangeAvatarListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityViewModel viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        if (getSupportFragmentManager().findFragmentByTag(
                ListUsersFragment.class.getSimpleName()) == null) {
            loadInitialFragment();
        }
        viewModel.getUser().observe(this, userEvent -> {
            User user = userEvent.getContentIfNotHandled();
            if (user != null) {
                openProfile(user);
            }
        });
    }

    private void loadInitialFragment() {
        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                ListUsersFragment.newInstance(), ListUsersFragment.class.getSimpleName());
    }

    public void openProfile(User user) {
            FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flContent,
                    ProfileFragment.newInstance(user),
                    ProfileFragment.class.getSimpleName(),
                    ProfileFragment.class.getSimpleName(), FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }

    @Override
    public void onChangeAvatar(Avatar avatar) {
        FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flContent,
                AvatarFragment.newInstance(avatar),
                AvatarFragment.class.getSimpleName(),
                AvatarFragment.class.getSimpleName(), FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
