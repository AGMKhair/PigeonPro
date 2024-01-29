package com.agmkhair.pigeonpro.ui.admin.bird;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.agmkhair.pigeonpro.R;
import com.agmkhair.pigeonpro.databinding.ActivityAdminBirdBinding;
import com.agmkhair.pigeonpro.ui.model.Birds;

import java.util.ArrayList;
import java.util.List;

public class AdminBirdActivity extends AppCompatActivity
{
    private ActivityAdminBirdBinding birdBinding;
    private RecyclerView recyclerView;
    private AdminBirdViewModel viewModel;
    private AdminBirdAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

          birdBinding= DataBindingUtil.setContentView(this, R.layout.activity_admin_bird);

          initFunctionality();
          initViewPager();
          initLisining();

    }

    private void initViewPager()
    {
        birdBinding.rvBirdsListShowId.setLayoutManager(new LinearLayoutManager(AdminBirdActivity.this, LinearLayoutManager.VERTICAL,false));
        birdBinding.rvBirdsListShowId.setAdapter(adapter);
    }

    private void initFunctionality()
    {
        adapter = new AdminBirdAdapter(new ArrayList<Birds>(),getApplicationContext());
        viewModel = ViewModelProviders.of(this).get(AdminBirdViewModel.class);
    }

    private void initLisining()
    {
        //birdBinding.etSearchBirdsListId

        viewModel.getClubBirds(new AdminBirdPresenter() {
            @Override
            public void showSuccess(String msg) {

            }

            @Override
            public void showError(String msg) {

            }

            @Override
            public void showLoading() {

            }
        }).observe(this, new Observer<List<Birds>>() {
            @Override
            public void onChanged(List<Birds> birds) {
                    adapter.setList(birds);
            }
        });
    }

}
