package es.iessaladillo.alex.adm_pr07_fragments.ui.list;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.alex.adm_pr07_fragments.local.Database;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.User;

public class ListUsersFragmentViewModel extends ViewModel {

    private final Database database;
    private LiveData<List<User>> users;

    public ListUsersFragmentViewModel(Database database) {
        this.database = database;
    }

    public LiveData<List<User>> getUsers() {
        if (users == null) {
            users = database.getUsers();
        }
        return users;
    }

    public void deleteUser(User user) {
        database.deleteUser(user);
    }

}
