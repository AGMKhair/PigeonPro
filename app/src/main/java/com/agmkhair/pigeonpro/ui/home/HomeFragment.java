package com.agmkhair.pigeonpro.ui.home;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.agmkhair.pigeonpro.R;
import com.agmkhair.pigeonpro.ui.model.Birds;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;
    private HomeAdapter homeAdapter;
    private RecyclerView recyclerView;
    private TextView search;
    DatabaseReference showDatabase;


    public HomeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        // final TextView textView = root.findViewById(R.id.text_home);
        recyclerView = root.findViewById(R.id.rv_birds_show_id);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        // recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), GridLayoutManager.VERTICAL,false));
        search = root.findViewById(R.id.et_search_id);

        showDatabase = FirebaseDatabase.getInstance().getReference("Birds");

        homeAdapter = new HomeAdapter(new ArrayList<Birds>(),getActivity());
        recyclerView.setAdapter(homeAdapter);

       initViewPage();
        methodSearch();
        return root;
    }

    private void initViewPage()
    {
        homeViewModel.getBirds().observe(this, new Observer<List<Birds>>() {
            @Override
            public void onChanged(@Nullable List<Birds> birds)
            {
                homeAdapter.setList(birds);
            }
        });
    }


    private void methodSearch()
    {
         search.addTextChangedListener(new TextWatcher()
         {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()) {
                    setAdapter(s.toString());
                } else {
                  //  HomeFragment.this.notifyAll();
                     //initViewPage();
                }
            }
        });
    }

    private void setAdapter(final String searchedString)
    {

        final List<Birds> list= new ArrayList<>();

        showDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list.clear();


/*
                String s =  dataSnapshot.child(user).child(userphone).getValue(String.class);
                textView.setText(s);*/

                for (DataSnapshot sabbirSnapshot : dataSnapshot.getChildren())
                {

                    String id = sabbirSnapshot.child("ring").getValue(String.class);

                    String user_name = sabbirSnapshot.child("userId").getValue(String.class);
                    if(id.toLowerCase().contains(searchedString.toLowerCase()))
                    {
                        Birds information = sabbirSnapshot.getValue(Birds.class);
                        list.add(information);

                    }else if(user_name.toLowerCase().contains(searchedString.toLowerCase()))
                    {
                        Birds information = sabbirSnapshot.getValue(Birds.class);
                        list.add(information);
                    }

                }

                homeAdapter = new HomeAdapter(list,getActivity());
                //listView.setAdapter(homeAdapter);
                // homeAdapter.setList(birds);
                recyclerView.setAdapter(homeAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}