package es.iessaladillo.alex.adm_pr07_fragments.ui.avatar;

import java.util.List;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.alex.adm_pr07_fragments.local.Database;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.Avatar;

public class AvatarFragmentViewModel extends ViewModel {

    private final Database database;
    private Avatar avatar;

    public AvatarFragmentViewModel(Database database) {
        this.database = database;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public void changeAvatar(int position) {
        this.avatar = database.queryAvatar(database.queryAvatars().get(position).getId());
    }

    public List<Avatar> queryAvatars() {
        return database.queryAvatars();
    }
}
