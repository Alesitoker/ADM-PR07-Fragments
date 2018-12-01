package es.iessaladillo.alex.adm_pr07_fragments.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.alex.adm_pr07_fragments.R;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.Avatar;
import es.iessaladillo.alex.adm_pr07_fragments.local.model.User;

// DO NOT TOUCH

public class Database {

    private static Database instance;

    private final ArrayList<Avatar> avatars = new ArrayList<>();
    private final Random random = new Random(1);
    private ArrayList<User> users;
    private MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();
    private long count;

    private Database() {
        insertAvatar(new Avatar(R.drawable.cat1, "Tom"));
        insertAvatar(new Avatar(R.drawable.cat2, "Luna"));
        insertAvatar(new Avatar(R.drawable.cat3, "Simba"));
        insertAvatar(new Avatar(R.drawable.cat4, "Kitty"));
        insertAvatar(new Avatar(R.drawable.cat5, "Felix"));
        insertAvatar(new Avatar(R.drawable.cat6, "Nina"));

        users = new ArrayList<>(Arrays.asList(
                new User(queryAvatar(1), "Baldo", "baldo@mero.com", 666666666, "La casa de baldo", "http://www.marca.com"),
                new User(queryAvatar(2), "Pollo", "pollo@frito.es", 962746501, "El hogar", "https://www.youtube.com"),
                new User(queryAvatar(3), "Aguacate", "Aguacatecito@sinmas.com", 972906203, "No tengo", "https://asoftmurmur.com/")
        ));
        updateUsersLiveData();
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    @VisibleForTesting()
    public void insertAvatar(Avatar avatar) {
        long id = ++count;
        avatar.setId(id);
        avatars.add(avatar);
    }

    public Avatar getRandomAvatar() {
        if (avatars.size() == 0) return null;
        return avatars.get(random.nextInt(avatars.size()));
    }

    public Avatar getDefaultAvatar() {
        if (avatars.size() == 0) return null;
        return avatars.get(0);
    }

    public List<Avatar> queryAvatars() {
        return new ArrayList<>(avatars);
    }

    public Avatar queryAvatar(long id) {
        for (Avatar avatar: avatars) {
            if (avatar.getId() == id) {
                return avatar;
            }
        }
        return null;
    }

    @VisibleForTesting
    public void setAvatars(List<Avatar> list) {
        count = 0;
        avatars.clear();
        avatars.addAll(list);
    }

    public LiveData<List<User>> getUsers() {
        return usersLiveData;
    }

    private void updateUsersLiveData() {
        usersLiveData.setValue(new ArrayList<>(users));
    }

    public void addUser(User user) {
        users.add(user);
        updateUsersLiveData();
    }

    public void deleteUser(User user) {
        users.remove(user);
        updateUsersLiveData();
    }

    public void saveEditedUser(User user) {
        int position = -1;

        for(int i = 0; i < users.size() && position == -1; i++)  {
            if (users.get(i).getId() == user.getId()) {
                position = i;
            }
        }

        users.set(position, user);
        updateUsersLiveData();
    }
}
