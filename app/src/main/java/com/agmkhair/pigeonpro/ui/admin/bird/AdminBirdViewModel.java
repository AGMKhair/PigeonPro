package com.agmkhair.pigeonpro.ui.admin.bird;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.agmkhair.pigeonpro.ui.model.Birds;

import java.util.List;

public class AdminBirdViewModel extends AndroidViewModel {

    AdminBirdRepository repository;

    public AdminBirdViewModel(@NonNull Application application) {
        super(application);
        repository = new AdminBirdRepository(application);
    }

    public LiveData<List<Birds>> getClubBirds(AdminBirdPresenter presenter){

        return repository.getBirdsList(presenter);
    }

}
