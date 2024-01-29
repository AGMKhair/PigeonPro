package com.agmkhair.pigeonpro.ui.home;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.agmkhair.pigeonpro.ui.model.Birds;
import java.util.List;

public class HomeViewModel extends AndroidViewModel{

   // private MutableLiveData<String> mText;
   HomeRepository homeRepository;


    public HomeViewModel(@NonNull  Application application) {
        super(application);
        homeRepository = new HomeRepository(application);
    }

    public LiveData<List<Birds>> getBirds()
    {
        return homeRepository.getBirdsDetails();
    }
}