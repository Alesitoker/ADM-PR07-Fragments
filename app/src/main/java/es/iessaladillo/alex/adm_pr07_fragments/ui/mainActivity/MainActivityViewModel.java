package es.iessaladillo.alex.adm_pr07_fragments.ui.mainActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.Avatar;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.User;

public class MainActivityViewModel extends ViewModel {

    private User user;
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private Avatar avatar;
    private MutableLiveData<Avatar> avatarLiveData = new MutableLiveData<>();

    public LiveData<User> getUser() {
        return userLiveData;
    }

    public void setUser(User user) {
        this.user = user;
        updateUserLiveData();
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    private void updateUserLiveData() {
        userLiveData.setValue(user);
    }
}
