package com.agmkhair.pigeonpro.ui.admin.memberlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.agmkhair.pigeonpro.R;
import com.agmkhair.pigeonpro.databinding.ActivityMemberListBinding;
import com.agmkhair.pigeonpro.ui.model.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberListActivity extends AppCompatActivity {

    ActivityMemberListBinding binding;
    private MemberListAdapter adapter;
    private MemberListViewModel viewModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this,R.layout.activity_member_list);

         initFunctionality();
         initViewPage();
         initListener();
    }

    private void initListener() {

        viewModels.getMembers(new MemberListPresenter() {
            @Override
            public void showSuccess(String msg) {

            }

            @Override
            public void showError(String msg) {

            }


        }).observe(this, new Observer<List<Member>>() {
            @Override
            public void onChanged(List<Member> members) {
                adapter.setList(members);
            }
        });
    }


    private void initViewPage() {
        binding.rvMemberListShowId.setLayoutManager(new LinearLayoutManager(MemberListActivity.this, LinearLayoutManager.VERTICAL,false));
        binding.rvMemberListShowId.setAdapter(adapter);
    }

    private void initFunctionality() {
        adapter = new MemberListAdapter(new ArrayList<Member>(),this);
        viewModels = ViewModelProviders.of(this).get(MemberListViewModel.class);
        //viewModel = ViewModelProviders.of(this).get(MemberListViewModel.class);

//        viewModel = ViewModelProviders.of(this).get(MemberListViewModel.class);
    }
}
