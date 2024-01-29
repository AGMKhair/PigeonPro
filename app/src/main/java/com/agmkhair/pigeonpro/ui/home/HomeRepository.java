package com.agmkhair.pigeonpro.ui.home;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.agmkhair.pigeonpro.ui.model.Birds;

import java.util.List;

import static com.agmkhair.pigeonpro.ui.Variable.allBirds;

public class HomeRepository
{
    private Application application;
    private MutableLiveData<List<Birds>> mutableLiveData;
    //private List<Birds> birdsList = new ArrayList<>();

    public HomeRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Birds>> getBirdsDetails()
    {

        if(mutableLiveData == null)
        { mutableLiveData = new MutableLiveData<>(); }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Birds");

        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                allBirds.clear();

                //   Log.d("This is Test : ", "have data check name : " + donorOnline.getName());
                //dataSnapshot.getChildrenCount();


                if (dataSnapshot != null)
                {

                    for (DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        Birds birds = ds.getValue(Birds.class);
                        allBirds.add(birds);
                    }

                    mutableLiveData.setValue(allBirds);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {


            }
        });

        return mutableLiveData;
    }

}
