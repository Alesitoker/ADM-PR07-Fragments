package es.iessaladillo.alex.adm_pr07_fragments.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import es.iessaladillo.alex.adm_pr07_fragments.R;
import es.iessaladillo.alex.adm_pr07_fragments.databinding.FragmentListBinding;
import es.iessaladillo.alex.adm_pr07_fragments.local.Database;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.User;
import es.iessaladillo.alex.adm_pr07_fragments.ui.main.MainActivityViewModel;

public class ListUsersFragment extends Fragment {

    private ListUsersFragmentViewModel viewModel;
    private FragmentListBinding b;
    private ListUsersFragmentAdapter listAdapter;
    private MainActivityViewModel activityViewModel;

    public static ListUsersFragment newInstance() {
        return new ListUsersFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, new ListUsersFragmentViewModelFactory(
                Database.getInstance())).get(ListUsersFragmentViewModel.class);
        activityViewModel = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        setupActionBar();
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
        listAdapter = new ListUsersFragmentAdapter();
        listAdapter.setOnEditUserClickListener(position -> openProfile(listAdapter.getItem(position)));
        listAdapter.setOnDeleteUserClickListener(position -> viewModel.deleteUser(listAdapter.getItem(position)));

        b.lstUsers.setHasFixedSize(true);
        b.lstUsers.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.lstUsers_columns)));
        b.lstUsers.setItemAnimator(new DefaultItemAnimator());
        b.lstUsers.setAdapter(listAdapter);
    }

    private void openProfile(User user) {
        activityViewModel.setOpen(true);
        activityViewModel.setUser(user);
    }
    private void addUser() {
        openProfile(new User());
    }

    private void setupActionBar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(R.string.app_name);
        }
    }
}
