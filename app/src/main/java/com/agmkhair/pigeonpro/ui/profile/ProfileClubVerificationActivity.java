package com.agmkhair.pigeonpro.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.agmkhair.pigeonpro.MainActivity;
import com.agmkhair.pigeonpro.R;
import com.agmkhair.pigeonpro.databinding.ActivityProfileClubVerificationBinding;

public class ProfileClubVerificationActivity extends AppCompatActivity {

    private ActivityProfileClubVerificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_club_verification);

        binding.btnConfirmClubVerificationId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileClubVerificationActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
