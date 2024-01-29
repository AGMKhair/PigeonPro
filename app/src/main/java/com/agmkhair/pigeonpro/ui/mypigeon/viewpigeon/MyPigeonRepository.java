package com.agmkhair.pigeonpro.ui.mypigeon.viewpigeon;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.agmkhair.pigeonpro.ui.model.Birds;
import java.util.ArrayList;
import java.util.List;

import static com.agmkhair.pigeonpro.ui.Variable.listPersinalBirds;
import static com.agmkhair.pigeonpro.ui.Variable.memberInfo;


public class MyPigeonRepository {

    private Application application;
    private MutableLiveData<List<Birds>> mutableLiveData;
    private MutableLiveData<List<Birds>> mutableLiveDataClub;

    private List<Birds> listClub= new ArrayList<>();

    public MyPigeonRepository(Application application)
    {
        this.application = application;
    }


    public MutableLiveData<List<Birds>> getPersi(MyPigeonPresenter presentation)
    {

        if (mutableLiveData == null)
        {
            mutableLiveData = new MutableLiveData<>();
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Birds");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //   Log.d("This is Test : ", "have data check name : " + donorOnline.getName());
                if (dataSnapshot != null) {

                    listPersinalBirds.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Birds birds = ds.getValue(Birds.class);
                        if (birds.getClubId().equals("") && birds.getUserId().equals(memberInfo.getId())  && birds.getSale().equals("")) {

                            listPersinalBirds.add(birds);
                        }

                    }

                    mutableLiveData.setValue(listPersinalBirds);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return mutableLiveData;
    }


    public MutableLiveData<List<Birds>> getClub(MyPigeonPresenter presentation) {


        if (mutableLiveDataClub == null) {
            mutableLiveDataClub = new MutableLiveData<>();
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Birds");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //   Log.d("This is Test : ", "have data check name : " + donorOnline.getName());
                if (dataSnapshot != null) {

                    listClub.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Birds birds = ds.getValue(Birds.class);
                        if (!birds.getClubId().equals("")) {

                            listClub.add(birds);
                        }

                    }

                    mutableLiveDataClub.setValue(listClub);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        return mutableLiveDataClub;
    }
}
