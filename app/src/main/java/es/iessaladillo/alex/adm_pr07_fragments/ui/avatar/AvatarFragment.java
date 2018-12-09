package es.iessaladillo.alex.adm_pr07_fragments.ui.avatar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.alex.adm_pr07_fragments.R;
import es.iessaladillo.alex.adm_pr07_fragments.databinding.FragmentAvatarBinding;
import es.iessaladillo.alex.adm_pr07_fragments.local.Database;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.Avatar;
import es.iessaladillo.alex.adm_pr07_fragments.ui.main.MainActivityViewModel;
import es.iessaladillo.alex.adm_pr07_fragments.utils.ResourcesUtils;

public class AvatarFragment extends Fragment {

    private static String ARG_AVATAR = "ARG_AVATAR";
    private ImageView imgAvatars[] = new ImageView[6];
    private TextView lblAvatars[] = new TextView[6];
    private final byte positionAvatar1 = 0;
    private final byte positionAvatar2 = 1;
    private final byte positionAvatar3 = 2;
    private final byte positionAvatar4 = 3;
    private final byte positionAvatar5 = 4;
    private final byte positionAvatar6 = 5;
    private FragmentAvatarBinding b;
    private AvatarFragmentViewModel viewModel;
    private MainActivityViewModel activityViewModel;

    public static AvatarFragment newInstance(Avatar avatar) {
        AvatarFragment fragment = new AvatarFragment();
        Bundle arguments = new Bundle();

        arguments.putParcelable(ARG_AVATAR, avatar);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_avatar, container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(getArguments());
        Objects.requireNonNull(getArguments().getParcelable(ARG_AVATAR));

        viewModel = ViewModelProviders.of(this, new AvatarFragmentViewModelFactory(
                Database.getInstance())).get(AvatarFragmentViewModel.class);
        activityViewModel = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        if (viewModel.getAvatar() == null) {
            viewModel.setAvatar(getArguments().getParcelable(ARG_AVATAR));
        }
        setupActionBar();
        setupAvatar();
        startAvatar();
        selectedImage();
        imgAvatars[positionAvatar1].setOnClickListener(v -> changeSelectedAvatar(positionAvatar1));
        imgAvatars[positionAvatar2].setOnClickListener(v -> changeSelectedAvatar(positionAvatar2));
        imgAvatars[positionAvatar3].setOnClickListener(v -> changeSelectedAvatar(positionAvatar3));
        imgAvatars[positionAvatar4].setOnClickListener(v -> changeSelectedAvatar(positionAvatar4));
        imgAvatars[positionAvatar5].setOnClickListener(v -> changeSelectedAvatar(positionAvatar5));
        imgAvatars[positionAvatar6].setOnClickListener(v -> changeSelectedAvatar(positionAvatar6));
        lblAvatars[positionAvatar1].setOnClickListener(v -> changeSelectedAvatar(positionAvatar1));
        lblAvatars[positionAvatar2].setOnClickListener(v -> changeSelectedAvatar(positionAvatar2));
        lblAvatars[positionAvatar3].setOnClickListener(v -> changeSelectedAvatar(positionAvatar3));
        lblAvatars[positionAvatar4].setOnClickListener(v -> changeSelectedAvatar(positionAvatar4));
        lblAvatars[positionAvatar5].setOnClickListener(v -> changeSelectedAvatar(positionAvatar5));
        lblAvatars[positionAvatar6].setOnClickListener(v -> changeSelectedAvatar(positionAvatar6));
    }

    private void setupActionBar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setIcon(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setTitle(R.string.avatar_label);
        }
    }

    private void setupAvatar() {
        imgAvatars[positionAvatar1] = b.imgAvatar1;
        imgAvatars[positionAvatar2] = b.imgAvatar2;
        imgAvatars[positionAvatar3] = b.imgAvatar3;
        imgAvatars[positionAvatar4] = b.imgAvatar4;
        imgAvatars[positionAvatar5] = b.imgAvatar5;
        imgAvatars[positionAvatar6] = b.imgAvatar6;

        lblAvatars[positionAvatar1] = b.lblAvatar1;
        lblAvatars[positionAvatar2] = b.lblAvatar2;
        lblAvatars[positionAvatar3] = b.lblAvatar3;
        lblAvatars[positionAvatar4] = b.lblAvatar4;
        lblAvatars[positionAvatar5] = b.lblAvatar5;
        lblAvatars[positionAvatar6] = b.lblAvatar6;
    }

    private void startAvatar() {
        List<Avatar> avatars = viewModel.queryAvatars();

        for (int i = 0; i < imgAvatars.length; i++) {
            imgAvatars[i].setImageResource(avatars.get(i).getImageResId());
            imgAvatars[i].setTag(avatars.get(i).getImageResId());
            lblAvatars[i].setText(avatars.get(i).getName());
        }
    }

    private void selectedImage() {
        int position;

        position = obtainPositionAvatar();
        selectAvatar(imgAvatars[position], lblAvatars[position]);
    }

    private void deselectedImage() {
        int position;

        position = obtainPositionAvatar();
        deselectAvatar(imgAvatars[position], lblAvatars[position]);
    }

    private int obtainPositionAvatar() {
        List<Avatar> avatarToSelect = viewModel.queryAvatars();
        Long avatarId = viewModel.getAvatar().getId();
        int position = 0;

        if (avatarId == avatarToSelect.get(positionAvatar1).getId()) {
            position = positionAvatar1;
        } else if (avatarId == avatarToSelect.get(positionAvatar2).getId()) {
            position = positionAvatar2;
        } else if (avatarId == avatarToSelect.get(positionAvatar3).getId()) {
            position = positionAvatar3;
        } else if (avatarId == avatarToSelect.get(positionAvatar4).getId()) {
            position = positionAvatar4;
        } else if (avatarId == avatarToSelect.get(positionAvatar5).getId()) {
            position = positionAvatar5;
        } else if (avatarId == avatarToSelect.get(positionAvatar6).getId()) {
            position = positionAvatar6;
        }
        return position;
    }

    private void changeSelectedAvatar(int position) {
        deselectedImage();
        viewModel.changeAvatar(position);
        selectedImage();
    }

    private void selectAvatar(ImageView imgAvatar, TextView lblAvatar) {
        selectImage(imgAvatar);
        imgAvatar.setEnabled(false);
        lblAvatar.setEnabled(false);
    }

    private void deselectAvatar(ImageView imgAvatar, TextView lblAvatar) {
        deselectImage(imgAvatar);
        imgAvatar.setEnabled(true);
        lblAvatar.setEnabled(true);
    }

    private void selectImage(ImageView imageView) {
        imageView.setAlpha(ResourcesUtils.getFloat(requireContext(), R.dimen.avatar_selected_image_alpha));
    }

    private void deselectImage(ImageView imageView) {
        imageView.setAlpha(ResourcesUtils.getFloat(requireContext(), R.dimen.avatar_not_selected_image_alpha));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_avatar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSelect) {
            submitAvatar();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void submitAvatar() {
        activityViewModel.setAvatar(viewModel.getAvatar());
        activityViewModel.setSubmit(true);
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}
