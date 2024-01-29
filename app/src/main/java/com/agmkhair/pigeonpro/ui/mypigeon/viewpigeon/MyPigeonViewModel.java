package com.agmkhair.pigeonpro.ui.mypigeon.viewpigeon;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.agmkhair.pigeonpro.ui.model.Birds;

import java.util.List;

public class MyPigeonViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private MyPigeonRepository myPigeonRepository;

    public MyPigeonViewModel(@NonNull Application application) {
        super(application);
            myPigeonRepository = new MyPigeonRepository(application);
//        mText = new MutableLiveData<>();
//        mText.setValue("This is dashboard fragment");
    }

    public LiveData<List<Birds>> getBirds(MyPigeonPresenter pigeonPresenter)
    {

        return myPigeonRepository.getPersi(pigeonPresenter);
    }

    public LiveData<List<Birds>> getBirdsClub(MyPigeonPresenter pigeonPresenter)
    {

        return myPigeonRepository.getClub(pigeonPresenter);
    }
}