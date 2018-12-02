package es.iessaladillo.alex.adm_pr07_fragments.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import es.iessaladillo.alex.adm_pr07_fragments.R;
import es.iessaladillo.alex.adm_pr07_fragments.ui.list.ListUsersFragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportFragmentManager().findFragmentByTag(
                ListUsersFragment.class.getSimpleName()) == null) {
            loadInitialFragment();
        }
    }

    private void loadInitialFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,
                ListUsersFragment.newInstance(), ListUsersFragment.class.getSimpleName()).commit();
    }
}
