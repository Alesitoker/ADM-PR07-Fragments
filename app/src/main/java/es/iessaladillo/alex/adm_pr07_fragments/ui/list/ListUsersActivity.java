package es.iessaladillo.alex.adm_pr07_fragments.ui.list;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import es.iessaladillo.alex.adm_pr07_fragments.R;
import es.iessaladillo.alex.adm_pr07_fragments.databinding.ActivityListBinding;
import es.iessaladillo.alex.adm_pr07_fragments.local.Database;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.User;
import es.iessaladillo.alex.adm_pr07_fragments.ui.profile.ProfileActivity;

public class ListUsersActivity extends AppCompatActivity {

    private ActivityListBinding b;
    private ListUsersActivityViewModel viewModel;
    private ListUsersActivityAdapter listAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_list);
        viewModel = ViewModelProviders.of(this, new ListUsersActivityViewModelFactory(Database.getInstance())).get(ListUsersActivityViewModel.class);
        setupViews();
        observerUsers();
    }

    private void observerUsers() {
        viewModel.getUsers().observe(this, this::refresherListAdapter);
    }

    private void refresherListAdapter(List<User> users) {
        listAdapter.submitList(users);
        b.lblEmptyView.setVisibility(users.size() == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    private void setupViews() {
        setupRecyclerView();

        b.fabtnAdd.setOnClickListener(v -> addUser());
        b.lblEmptyView.setOnClickListener(v -> addUser());
    }

    private void setupRecyclerView() {
        listAdapter = new ListUsersActivityAdapter();
        listAdapter.setOnEditUserClickListener(position -> openProfile(listAdapter.getItem(position)));
        listAdapter.setOnDeleteUserClickListener(position -> viewModel.deleteUser(listAdapter.getItem(position)));

        b.lstUsers.setHasFixedSize(true);
        b.lstUsers.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.lstUsers_columns)));
        b.lstUsers.setItemAnimator(new DefaultItemAnimator());
        b.lstUsers.setAdapter(listAdapter);

    }

    private void openProfile(User user) {
        ProfileActivity.startActivity(this, user);
    }
    private void addUser() {
        openProfile(new User());
    }
}
