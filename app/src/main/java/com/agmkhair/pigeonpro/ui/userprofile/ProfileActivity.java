package com.agmkhair.pigeonpro.ui.userprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import com.agmkhair.pigeonpro.R;
import com.agmkhair.pigeonpro.databinding.ActivityProfileBinding;
import com.agmkhair.pigeonpro.ui.mypigeon.viewpigeon.MyPigeonViewModel;

import static com.agmkhair.pigeonpro.ui.Variable.listPersinalBirds;
import static com.agmkhair.pigeonpro.ui.Variable.memberInfo;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    private MyPigeonViewModel dashboardViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);
          initFunction();
          initViewPage();
          initLisiner();

    }

    private void initViewPage() {

    }

    private void initFunction()
    {


        dashboardViewModel = ViewModelProviders.of(this).get(MyPigeonViewModel.class);
        binding.userImageShow.setImageBitmap(StringToBitMap(memberInfo.getImage()));
        binding.userNameShow.setText(memberInfo.getName());
        binding.userAddressShow.setText(memberInfo.getAddress());
        binding.userPhoneShow.setText(memberInfo.getNumber());
        binding.userCityShowId.setText(memberInfo.getCity());
        binding.totalCoinShow.setText(memberInfo.getCoin());

        if(listPersinalBirds.size() > 0)
        {
            binding.totalPigeonShow.setText(String.valueOf(listPersinalBirds.size()));

        }else {

            binding.totalPigeonShow.setText("Loading...");
        }

    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e)
        {
            e.getMessage();
            return null;
        }
    }


    private void initLisiner()
    {
    }
}
