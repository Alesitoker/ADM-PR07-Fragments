package es.iessaladillo.alex.adm_pr07_fragments.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.alex.adm_pr07_fragments.R;
import es.iessaladillo.alex.adm_pr07_fragments.local.Database;
import es.iessaladillo.alex.adm_pr07_fragments.utils.TextViewUtils;

public class ProfileFragment extends Fragment {

    private ViewDataBinding b;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        TextViewUtils.afterTextChanged(txtName, lblName, this, viewModel);
        TextViewUtils.onTextChanged(txtEmail, lblEmail, imgEmail, this, viewModel);
        TextViewUtils.onTextChanged(txtPhonenumber, lblPhonenumber, imgPhonenumber, this, viewModel);
        TextViewUtils.afterTextChanged(txtAddress, lblAddress, imgAddress, this, viewModel);
        TextViewUtils.onTextChanged(txtWeb, lblWeb, imgWeb, this, viewModel);
    }

    private void finishOnChange() {
        TextViewUtils.removeOnTextChanged(txtName);
        TextViewUtils.removeOnTextChanged(txtEmail);
        TextViewUtils.removeOnTextChanged(txtPhonenumber);
        TextViewUtils.removeOnTextChanged(txtAddress);
        TextViewUtils.removeOnTextChanged(txtWeb);
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
        initViews();
        viewModel = ViewModelProviders.of(this, new ProfileFragmentViewModelFactory(Database.getInstance())).get(ProfileFragmentViewModel.class);
        if (savedInstanceState != null) {
            setupSaveData();
        }
        if (user.getAvatar() != null) {
            startProfile();
        } else {
            isNewUser = true;
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
        lblAvatar = ActivityCompat.requireViewById(this, R.id.lblAvatar);
        imgAvatar = ActivityCompat.requireViewById(this, R.id.imgAvatar);
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);
        txtEmail = ActivityCompat.requireViewById(this, R.id.txtEmail);
        txtPhonenumber = ActivityCompat.requireViewById(this, R.id.txtPhonenumber);
        txtAddress = ActivityCompat.requireViewById(this, R.id.txtAddress);
        txtWeb = ActivityCompat.requireViewById(this, R.id.txtWeb);
        lblName = ActivityCompat.requireViewById(this, R.id.lblName);
        lblEmail = ActivityCompat.requireViewById(this, R.id.lblEmail);
        lblPhonenumber = ActivityCompat.requireViewById(this, R.id.lblPhonenumber);
        lblAddress = ActivityCompat.requireViewById(this, R.id.lblAddress);
        lblWeb = ActivityCompat.requireViewById(this, R.id.lblWeb);
        imgEmail = ActivityCompat.requireViewById(this, R.id.imgEmail);
        imgPhonenumber = ActivityCompat.requireViewById(this, R.id.imgPhonenumber);
        imgAddress = ActivityCompat.requireViewById(this, R.id.imgAddress);
        imgWeb = ActivityCompat.requireViewById(this, R.id.imgWeb);

        defaultAvatar();
        showAvatar();
        changeFocus();
        editorAction();
        imgAvatar.setOnClickListener(v -> changeImg());
        lblAvatar.setOnClickListener(v -> changeImg());
        imgEmail.setOnClickListener(v -> sendEmail());
        imgPhonenumber.setOnClickListener(v -> dialPhoneNumber());
        imgAddress.setOnClickListener(v -> searchInMap());
        imgWeb.setOnClickListener(v -> webSearch());
    }
}
