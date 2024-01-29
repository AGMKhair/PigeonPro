package com.agmkhair.pigeonpro.ui.admin.memberlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.agmkhair.pigeonpro.ui.model.Member;

import java.util.ArrayList;
import java.util.List;

import static com.agmkhair.pigeonpro.ui.Variable.adminId;

public class MemberListRepository  {

    private Application application;
    private MutableLiveData<List<Member>> mutableLiveData;
    private List<Member> list = new ArrayList<>();

    public MemberListRepository(Application application) {
        this.application = application;
    }


    public MutableLiveData<List<Member>> getMemberList(MemberListPresenter presentation){


        if(mutableLiveData == null)
        {
            mutableLiveData = new MutableLiveData<>();
        }
        /// setData();
        //FirebaseAuth.getInstance().ge

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Clubs").child(adminId[0]).child("Member");


        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                //   Log.d("This is Test : ", "have data check name : " + donorOnline.getName());
                //dataSnapshot.getChildrenCount();


                if (dataSnapshot != null)
                {

                    for (DataSnapshot ds : dataSnapshot.getChildren())
                    {

                            Member member = ds.getValue(Member.class);
                            list.add(member);

                    }

                    mutableLiveData.setValue(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        if(list.isEmpty()){
            presentation.showError("Data show error!! ");
        }
        else
        {
            presentation.showSuccess("Data show success!! ");

        }
        //  mutableLiveData.setValue(list);

        return mutableLiveData;


    }



}
