package es.iessaladillo.alex.adm_pr07_fragments.ui.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaladillo.alex.adm_pr07_fragments.local.Database;

public class ProfileFragmentViewModelFactory implements ViewModelProvider.Factory {

    private Database database;

    public ProfileFragmentViewModelFactory(Database database) {
        this.database = database;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProfileFragmentViewModel(database);
    }
}
