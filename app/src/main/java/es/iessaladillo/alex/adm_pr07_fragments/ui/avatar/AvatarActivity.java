package es.iessaladillo.alex.adm_pr07_fragments.ui.avatar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.alex.adm_pr07_fragments.R;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.Avatar;
import es.iessaladillo.alex.adm_pr07_fragments.utils.ResourcesUtils;

public class AvatarActivity extends AppCompatActivity {

    @VisibleForTesting
    public static final String EXTRA_AVATAR = "EXTRA_AVATAR";
    private ImageView imgAvatars[] = new ImageView[6];
    private TextView lblAvatars[] = new TextView[6];
    private final byte positionAvatar1 = 0;
    private final byte positionAvatar2 = 1;
    private final byte positionAvatar3 = 2;
    private final byte positionAvatar4 = 3;
    private final byte positionAvatar5 = 4;
    private final byte positionAvatar6 = 5;
    private AvatarActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        viewModel = ViewModelProviders.of(this).get(AvatarActivityViewModel.class);
        getIntentData();
        initViews();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_AVATAR) && viewModel.getAvatar() == null) {
                viewModel.setAvatar(intent.getParcelableExtra(EXTRA_AVATAR));
            }
        }
    }

    private void initViews() {
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

    private void setupAvatar() {
        imgAvatars[positionAvatar1] = ActivityCompat.requireViewById(this, R.id.imgAvatar1);
        imgAvatars[positionAvatar2] = ActivityCompat.requireViewById(this, R.id.imgAvatar2);
        imgAvatars[positionAvatar3] = ActivityCompat.requireViewById(this, R.id.imgAvatar3);
        imgAvatars[positionAvatar4] = ActivityCompat.requireViewById(this, R.id.imgAvatar4);
        imgAvatars[positionAvatar5] = ActivityCompat.requireViewById(this, R.id.imgAvatar5);
        imgAvatars[positionAvatar6] = ActivityCompat.requireViewById(this, R.id.imgAvatar6);

        lblAvatars[positionAvatar1] = ActivityCompat.requireViewById(this, R.id.lblAvatar1);
        lblAvatars[positionAvatar2] = ActivityCompat.requireViewById(this, R.id.lblAvatar2);
        lblAvatars[positionAvatar3] = ActivityCompat.requireViewById(this, R.id.lblAvatar3);
        lblAvatars[positionAvatar4] = ActivityCompat.requireViewById(this, R.id.lblAvatar4);
        lblAvatars[positionAvatar5] = ActivityCompat.requireViewById(this, R.id.lblAvatar5);
        lblAvatars[positionAvatar6] = ActivityCompat.requireViewById(this, R.id.lblAvatar6);

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
        imageView.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
    }

    private void deselectImage(ImageView imageView) {
        imageView.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_avatar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSelect) {
            submitAvatar();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void startForResult(Activity activity, int requestCode, Avatar avatar) {
        Intent intent = new Intent(activity, AvatarActivity.class);
        intent.putExtra(EXTRA_AVATAR, avatar);
        activity.startActivityForResult(intent, requestCode);
    }

    private void submitAvatar() {
        Intent result = new Intent();
        Avatar avatar = viewModel.getAvatar();

        result.putExtra(EXTRA_AVATAR, avatar);
        setResult(RESULT_OK, result);
        finish();
    }
}
