package es.iessaladillo.alex.adm_pr07_fragments.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.alex.adm_pr07_fragments.R;
import es.iessaladillo.alex.adm_pr07_fragments.databinding.FragmentProfileFullBinding;
import es.iessaladillo.alex.adm_pr07_fragments.local.Database;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.User;
import es.iessaladillo.alex.adm_pr07_fragments.ui.list.OnChangeAvatarListener;
import es.iessaladillo.alex.adm_pr07_fragments.ui.main.MainActivityViewModel;
import es.iessaladillo.alex.adm_pr07_fragments.utils.IntentsUtils;
import es.iessaladillo.alex.adm_pr07_fragments.utils.KeyboardUtils;
import es.iessaladillo.alex.adm_pr07_fragments.utils.SnackbarUtils;
import es.iessaladillo.alex.adm_pr07_fragments.utils.TextViewUtils;
import es.iessaladillo.alex.adm_pr07_fragments.utils.ValidationUtils;

public class ProfileFragment extends Fragment {

    private static final String ARG_USER = "ARG_USER";
    private User user;
    private FragmentProfileFullBinding b;
    private ProfileFragmentViewModel viewModel;
    private MainActivityViewModel activityViewModel;
    private OnChangeAvatarListener listener;

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_USER, user);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getTargetFragment() != null) {
                listener = (OnChangeAvatarListener) getTargetFragment();
            } else {
                listener = (OnChangeAvatarListener) context;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException("Listener must implement YesNoDialogFragment.Listener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startOnchange();
    }

    @Override
    public void onPause() {
        super.onPause();
        finishOnChange();
    }

    private void startOnchange() {
        TextViewUtils.afterTextChanged(b.acProfile.txtName, b.acProfile.lblName, getContext(), viewModel);
        TextViewUtils.onTextChanged(b.acProfile.txtEmail, b.acProfile.lblEmail, b.acProfile.imgEmail, getContext(), viewModel);
        TextViewUtils.onTextChanged(b.acProfile.txtPhonenumber, b.acProfile.lblPhonenumber, b.acProfile.imgPhonenumber, getContext(), viewModel);
        TextViewUtils.afterTextChanged(b.acProfile.txtAddress, b.acProfile.lblAddress, b.acProfile.imgAddress, getContext(), viewModel);
        TextViewUtils.onTextChanged(b.acProfile.txtWeb, b.acProfile.lblWeb, b.acProfile.imgWeb, getContext(), viewModel);
    }

    private void finishOnChange() {
        TextViewUtils.removeOnTextChanged(b.acProfile.txtName);
        TextViewUtils.removeOnTextChanged(b.acProfile.txtEmail);
        TextViewUtils.removeOnTextChanged(b.acProfile.txtPhonenumber);
        TextViewUtils.removeOnTextChanged(b.acProfile.txtAddress);
        TextViewUtils.removeOnTextChanged(b.acProfile.txtWeb);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_full, container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(getArguments());
        Objects.requireNonNull(getArguments().getParcelable(ARG_USER));

        viewModel = ViewModelProviders.of(this, new ProfileFragmentViewModelFactory(
                Database.getInstance())).get(ProfileFragmentViewModel.class);
        activityViewModel = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        user = getArguments().getParcelable(ARG_USER);
        setupActionBar();
        initViews();
        if (savedInstanceState != null) {
            setupSaveData();
        }

        if (savedInstanceState == null) {
            if (user.getAvatar() != null) {
                startProfile();
            } else {
                viewModel.setNewUser(true);
            }
        }
    }

    private void setupActionBar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setIcon(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setTitle(R.string.avatar_label);
        }
    }

    private void setupSaveData() {
        if (!viewModel.isValid_name()) {
            validateName();
        }
        if (!viewModel.isValid_email()) {
            validateEmail();
        }
        if (!viewModel.isValid_phonenumber()) {
            validatePhonenumber();
        }
        if (!viewModel.isValid_address()) {
            validateAddress();
        }
        if (!viewModel.isValid_web()) {
            validateWeb();
        }
    }

    private void initViews() {
        defaultAvatar();
        showAvatar();
        changeFocus();
        editorAction();
        b.imgAvatar.setOnClickListener(v -> changeImg());
        b.lblAvatar.setOnClickListener(v -> changeImg());
        b.acProfile.imgEmail.setOnClickListener(v -> sendEmail());
        b.acProfile.imgPhonenumber.setOnClickListener(v -> dialPhoneNumber());
        b.acProfile.imgAddress.setOnClickListener(v -> searchInMap());
        b.acProfile.imgWeb.setOnClickListener(v -> webSearch());
    }

    private void startProfile() {
        viewModel.setAvatar(user.getAvatar());
        showAvatar();
        b.acProfile.txtName.setText(user.getName());
        b.acProfile.txtEmail.setText(user.getEmail());
        b.acProfile.txtPhonenumber.setText(String.valueOf(user.getPhoneNumber()));
        b.acProfile.txtAddress.setText(user.getAddress());
        b.acProfile.txtWeb.setText(user.getWeb());
    }

    private void defaultAvatar() {
        if (viewModel.getAvatar() == null) {
            viewModel.setAvatar(viewModel.getDefaultAvatar());
        }
    }

    private void webSearch() {
        Intent intent;
        if (validateWeb()) {
            intent = IntentsUtils.newWebSearch(b.acProfile.txtWeb.getText().toString());
            sendIntent(intent);
        }
    }

    private void searchInMap() {
        Intent intent;
        if (validateAddress()) {
            intent = IntentsUtils.newSearchInMap(b.acProfile.txtAddress.getText().toString());
            sendIntent(intent);
        }
    }

    private void dialPhoneNumber() {
        Intent intent;
        if (validatePhonenumber()) {
            intent = IntentsUtils.newDial(b.acProfile.txtPhonenumber.getText().toString());
            sendIntent(intent);
        }
    }

    private void sendEmail() {
        Intent intent;
        if (validateEmail()) {
            intent = IntentsUtils.newEmail(b.acProfile.txtEmail.getText().toString());
            sendIntent(intent);
        }
    }

    private void sendIntent(Intent intent) {
        if (IntentsUtils.isAvailable(requireContext(), intent)) {
            startActivity(intent);
        } else {
            KeyboardUtils.hideSoftKeyboard(requireActivity());
            SnackbarUtils.snackbar(b.acProfile.imgWeb, "Can not find an application to perform this action");
        }
    }

    private void editorAction() {
        b.acProfile.txtWeb.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                save();
                return true;
            }
            return false;
        });
    }

    private void changeFocus() {
        TextViewUtils.changeFocus(b.acProfile.txtName, b.acProfile.lblName);
        TextViewUtils.changeFocus(b.acProfile.txtEmail, b.acProfile.lblEmail);
        TextViewUtils.changeFocus(b.acProfile.txtPhonenumber, b.acProfile.lblPhonenumber);
        TextViewUtils.changeFocus(b.acProfile.txtAddress, b.acProfile.lblAddress);
        TextViewUtils.changeFocus(b.acProfile.txtWeb, b.acProfile.lblWeb);
    }

    private void showAvatar() {
        if (activityViewModel.getAvatar() != null) {
            viewModel.setAvatar(activityViewModel.getAvatar());
        }
        b.imgAvatar.setImageResource(viewModel.getAvatar().getImageResId());
        b.imgAvatar.setTag(viewModel.getAvatar().getImageResId());
        b.lblAvatar.setText(viewModel.getAvatar().getName());
    }

    private void changeImg() {
        listener.onChangeAvatar(viewModel.getAvatar());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateName() {
        if (b.acProfile.txtName.getText().toString().isEmpty()) {
            invalidData(b.acProfile.txtName, b.acProfile.lblName);
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        if (!ValidationUtils.isValidEmail(b.acProfile.txtEmail.getText().toString())) {
            invalidData(b.acProfile.txtEmail, b.acProfile.lblEmail, b.acProfile.imgEmail);
            return false;
        }
        return true;
    }

    private boolean validatePhonenumber() {
        if (!ValidationUtils.isValidPhone(b.acProfile.txtPhonenumber.getText().toString())) {
            invalidData(b.acProfile.txtPhonenumber, b.acProfile.lblPhonenumber, b.acProfile.imgPhonenumber);
            return false;
        }
        return true;
    }

    private boolean validateAddress() {
        if (b.acProfile.txtAddress.getText().toString().isEmpty()) {
            invalidData(b.acProfile.txtAddress, b.acProfile.lblAddress, b.acProfile.imgAddress);
            return false;
        }
        return true;
    }

    private boolean validateWeb() {
        if (!ValidationUtils.isValidUrl(b.acProfile.txtWeb.getText().toString())) {
            invalidData(b.acProfile.txtWeb, b.acProfile.lblWeb, b.acProfile.imgWeb);
            return false;
        }
        return true;
    }

    private void invalidData(EditText txt, TextView lbl) {
        txt.setError(getString(R.string.main_invalid_data));
        lbl.setEnabled(false);
    }

    private void invalidData(EditText txt, TextView lbl, ImageView imageView) {
        txt.setError(getString(R.string.main_invalid_data));
        lbl.setEnabled(false);
        imageView.setEnabled(false);
    }

    private boolean validate() {
        boolean validName, validEmail, validPhonenumber, validAddress, validWeb;
        validName = validateName();
        validEmail = validateEmail();
        validPhonenumber = validatePhonenumber();
        validAddress = validateAddress();
        validWeb = validateWeb();

        viewModel.setValid_name(validName);
        viewModel.setValid_email(validEmail);
        viewModel.setValid_phonenumber(validPhonenumber);
        viewModel.setValid_address(validAddress);
        viewModel.setValid_web(validWeb);

        return validName && validEmail && validPhonenumber && validAddress && validWeb;
    }

    private void savedUser() {
        user.setAvatar(viewModel.getAvatar());
        user.setName(String.valueOf(b.acProfile.txtName.getText()));
        user.setEmail(String.valueOf(b.acProfile.txtEmail.getText()));
        user.setPhoneNumber(Integer.valueOf(String.valueOf(b.acProfile.txtPhonenumber.getText())));
        user.setAddress(String.valueOf(b.acProfile.txtAddress.getText()));
        user.setWeb(String.valueOf(b.acProfile.txtWeb.getText()));

        if (viewModel.isNewUser()) {
            viewModel.addUser(user);
        } else {
            viewModel.saveEditedUser(user);
        }
    }

    private void save() {
        KeyboardUtils.hideSoftKeyboard(requireActivity());

        if (!validate()) {
            SnackbarUtils.snackbar(b.acProfile.lblName, getString(R.string.main_error_saving));
        } else {
            SnackbarUtils.snackbar(b.acProfile.lblName, getString(R.string.main_saved_succesfully));
            savedUser();
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activityViewModel.setAvatar(null);
    }
}
