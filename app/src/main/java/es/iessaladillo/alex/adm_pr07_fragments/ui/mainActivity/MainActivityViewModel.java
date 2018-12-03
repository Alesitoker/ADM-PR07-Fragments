package es.iessaladillo.alex.adm_pr07_fragments.ui.mainActivity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.Avatar;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.User;

public class MainActivityViewModel extends ViewModel {

    User user;
    MutableLiveData<User> userLiveData;
    Avatar avatar;
    MutableLiveData<Avatar> avatarLiveData;


}
