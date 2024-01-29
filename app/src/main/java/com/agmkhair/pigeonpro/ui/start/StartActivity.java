package com.agmkhair.pigeonpro.ui.start;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.agmkhair.pigeonpro.MainActivity;
import com.agmkhair.pigeonpro.R;
import com.agmkhair.pigeonpro.databinding.ActivityStartBinding;
import com.agmkhair.pigeonpro.ui.admin.home.AdminHomeActivity;
import com.agmkhair.pigeonpro.ui.common.LanguageHelper;
import com.agmkhair.pigeonpro.ui.common.SharedPref;
import com.agmkhair.pigeonpro.ui.home.HomeFragment;
import com.agmkhair.pigeonpro.ui.login.AdminLoginActivity;
import com.agmkhair.pigeonpro.ui.otp.OTPActivity;

import static com.agmkhair.pigeonpro.ui.Variable.userId;

public class StartActivity extends AppCompatActivity {

   // SharedPreferences.Editor editor = pref.edit();

    //private ActivityHomeBinding binding;

    ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = DataBindingUtil.setContentView(this,R.layout.activity_start);

      binding.btnStartAMemberId.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
                goToUser();

          }
      });

      //    Admin button comment

    /*  binding.btnStartOwnerId.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              goToAdmin();

          }
      });*/

    }

    private void goToAdmin() {
        startActivity(new Intent(StartActivity.this, AdminLoginActivity.class).putExtra("person","club"));
        finish();
    }



    private void goToUser() {

        startActivity(new Intent(StartActivity.this, OTPActivity.class).putExtra("person","personal"));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
/*

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Login", 0); // 0 - for private mode
        String user = pref.getString("Login", null); // getting String
        Toast.makeText(this, user, Toast.LENGTH_SHORT).show();
*/

        SharedPref pref = new SharedPref(StartActivity.this);

        String language = pref.getString(LanguageHelper.SELECTED_LANGUAGE);

        if (language.equalsIgnoreCase("bn")) {
            LanguageHelper.setLanguage(this, "bn");

        } else {
            LanguageHelper.setLanguage(this, "en");
        }


        userId = null;

        try {
            userId = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        }catch (Exception e)
        {

        }
        if(userId != null)
        {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }
   /*
        if(user!=null)
        {
            if(user.equals("user"))
            {
                goToUserHome();

            }else if(user.equals("admin"))
            {
                goToAdminHome();
            }
        }*/

    }

    private void goToUserHome() {
        startActivity(new Intent(StartActivity.this, HomeFragment.class).putExtra("person","personal"));
        finish();
    }

    private void goToAdminHome() {

        startActivity(new Intent(StartActivity.this, AdminHomeActivity.class).putExtra("person","personal"));
        finish();
    }




}
