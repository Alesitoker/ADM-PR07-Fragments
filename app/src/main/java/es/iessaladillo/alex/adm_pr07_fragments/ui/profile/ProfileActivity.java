package es.iessaladillo.alex.adm_pr07_fragments.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.alex.adm_pr07_fragments.R;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.User;
import es.iessaladillo.alex.adm_pr07_fragments.ui.avatar.AvatarActivity;
import es.iessaladillo.alex.adm_pr07_fragments.utils.IntentsUtils;
import es.iessaladillo.alex.adm_pr07_fragments.utils.KeyboardUtils;
import es.iessaladillo.alex.adm_pr07_fragments.utils.SnackbarUtils;
import es.iessaladillo.alex.adm_pr07_fragments.utils.TextViewUtils;
import es.iessaladillo.alex.adm_pr07_fragments.utils.ValidationUtils;

public class ProfileActivity extends AppCompatActivity {

    private TextView lblAvatar;
    private ImageView imgAvatar;
    private EditText txtName;
    private EditText txtEmail;
    private EditText txtPhonenumber;
    private EditText txtAddress;
    private EditText txtWeb;
    private TextView lblName;
    private TextView lblEmail;
    private TextView lblPhonenumber;
    private TextView lblAddress;
    private TextView lblWeb;
    private ImageView imgEmail;
    private ImageView imgPhonenumber;
    private ImageView imgAddress;
    private ImageView imgWeb;
    private final int RC_AVATAR = 12;
    private static final String EXTRA_USER = "EXTRA_USER";
    private User user;
    private boolean isNewUser = false;
    private ProfileActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_full);
        viewModel = ViewModelProviders.of(this).get(ProfileActivityViewModel.class);
        getIntentData();
        initViews();
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

    @Override
    protected void onResume() {
        super.onResume();
        startOnchange();
    }

    @Override
    protected void onPause() {
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

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
           if (intent.hasExtra(EXTRA_USER)) {
               user = intent.getParcelableExtra(EXTRA_USER);
           }
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

    private void startProfile() {
        viewModel.setAvatar(user.getAvatar());
        showAvatar();
        txtName.setText(user.getName());
        txtEmail.setText(user.getEmail());
        txtPhonenumber.setText(String.valueOf(user.getPhoneNumber()));
        txtAddress.setText(user.getAddress());
        txtWeb.setText(user.getWeb());
    }

    private void defaultAvatar() {
        if (viewModel.getAvatar() == null) {
            viewModel.setAvatar(viewModel.getDefaultAvatar());
        }
    }

    private void webSearch() {
        Intent intent;
        if (validateWeb()) {
            intent = IntentsUtils.newWebSearch(txtWeb.getText().toString());
            sendIntent(intent);
        }
    }

    private void searchInMap() {
        Intent intent;
        if (validateAddress()) {
            intent = IntentsUtils.newSearchInMap(txtAddress.getText().toString());
            sendIntent(intent);
        }
    }

    private void dialPhoneNumber() {
        Intent intent;
        if (validatePhonenumber()) {
            intent = IntentsUtils.newDial(txtPhonenumber.getText().toString());
            sendIntent(intent);
        }
    }

    private void sendEmail() {
        Intent intent;
        if (validateEmail()) {
            intent = IntentsUtils.newEmail(txtEmail.getText().toString());
            sendIntent(intent);
        }
    }

    private void sendIntent(Intent intent) {
        if (IntentsUtils.isAvailable(this, intent)) {
            startActivity(intent);
        } else {
            KeyboardUtils.hideSoftKeyboard(this);
            SnackbarUtils.snackbar(imgWeb, "Can not find an application to perform this action");
        }
    }

    private void editorAction() {
        txtWeb.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                save();
                return true;
            }
            return false;
        });
    }

    private void changeFocus() {
        TextViewUtils.changeFocus(txtName, lblName);
        TextViewUtils.changeFocus(txtEmail, lblEmail);
        TextViewUtils.changeFocus(txtPhonenumber, lblPhonenumber);
        TextViewUtils.changeFocus(txtAddress, lblAddress);
        TextViewUtils.changeFocus(txtWeb, lblWeb);
    }

    private void showAvatar() {
        imgAvatar.setImageResource(viewModel.getAvatar().getImageResId());
        imgAvatar.setTag(viewModel.getAvatar().getImageResId());
        lblAvatar.setText(viewModel.getAvatar().getName());
    }

    private void changeImg() {
        AvatarActivity.startForResult(this, RC_AVATAR, viewModel.getAvatar());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == RC_AVATAR) {
            if (data != null && data.hasExtra(AvatarActivity.EXTRA_AVATAR)) {
                viewModel.setAvatar(data.getParcelableExtra(AvatarActivity.EXTRA_AVATAR));
                showAvatar();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fragment_profile, menu);
        return super.onCreateOptionsMenu(menu);
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
        if (txtName.getText().toString().isEmpty()) {
            invalidData(txtName, lblName);
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        if (!ValidationUtils.isValidEmail(txtEmail.getText().toString())) {
            invalidData(txtEmail, lblEmail, imgEmail);
            return false;
        }
        return true;
    }

    private boolean validatePhonenumber() {
        if (!ValidationUtils.isValidPhone(txtPhonenumber.getText().toString())) {
            invalidData(txtPhonenumber, lblPhonenumber, imgPhonenumber);
            return false;
        }
        return true;
    }

    private boolean validateAddress() {
        if (txtAddress.getText().toString().isEmpty()) {
            invalidData(txtAddress, lblAddress, imgAddress);
            return false;
        }
        return true;
    }

    private boolean validateWeb() {
        if (!ValidationUtils.isValidUrl(txtWeb.getText().toString())) {
            invalidData(txtWeb, lblWeb, imgWeb);
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

    public static void startActivity(Activity activity, User user) {
        Intent intent = new Intent(activity, ProfileActivity.class);
        intent.putExtra(EXTRA_USER, user);
        activity.startActivity(intent);
    }

    private void submitUser() {
        savedUser();

        finish();
    }

    private void savedUser() {
        user.setAvatar(viewModel.getAvatar());
        user.setName(String.valueOf(txtName.getText()));
        user.setEmail(String.valueOf(txtEmail.getText()));
        user.setPhoneNumber(Integer.valueOf(String.valueOf(txtPhonenumber.getText())));
        user.setAddress(String.valueOf(txtAddress.getText()));
        user.setWeb(String.valueOf(txtWeb.getText()));

        if (isNewUser) {
            viewModel.addUser(user);
        } else {
            viewModel.saveEditedUser(user);
        }
    }

    private void save() {
        KeyboardUtils.hideSoftKeyboard(this);

        if (!validate()) {
            SnackbarUtils.snackbar(lblName, getString(R.string.main_error_saving));
        } else {
            SnackbarUtils.snackbar(lblName, getString(R.string.main_saved_succesfully));
            submitUser();
        }
    }
}
