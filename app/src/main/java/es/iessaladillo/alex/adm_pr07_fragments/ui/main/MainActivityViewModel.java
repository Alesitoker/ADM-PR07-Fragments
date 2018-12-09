package es.iessaladillo.alex.adm_pr07_fragments.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.Avatar;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.Event;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.User;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<Event<User>> user = new MutableLiveData<>();
    private Avatar avatar;

    public LiveData<Event<User>> getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user.setValue(new Event<>(user));
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
