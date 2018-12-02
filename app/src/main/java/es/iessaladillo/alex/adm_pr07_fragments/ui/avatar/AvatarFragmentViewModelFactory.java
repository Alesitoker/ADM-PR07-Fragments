package es.iessaladillo.alex.adm_pr07_fragments.ui.avatar;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaladillo.alex.adm_pr07_fragments.local.Database;

public class AvatarFragmentViewModelFactory implements ViewModelProvider.Factory {

    private Database database;

    public AvatarFragmentViewModelFactory(Database database) {
        this.database = database;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AvatarFragmentViewModel(database);
    }
}
