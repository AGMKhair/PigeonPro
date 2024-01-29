package com.agmkhair.pigeonpro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.agmkhair.pigeonpro.ui.aboutus.AboutUsActivity;
import com.agmkhair.pigeonpro.ui.addpigeon.AddPersonalPigeonActivity;
import com.agmkhair.pigeonpro.ui.common.LanguageHelper;
import com.agmkhair.pigeonpro.ui.common.SharedPref;
import com.agmkhair.pigeonpro.ui.home.HomeFragment;
import com.agmkhair.pigeonpro.ui.model.Member;
import com.agmkhair.pigeonpro.ui.mypigeon.viewpigeon.MyPigeonFragment;
import com.agmkhair.pigeonpro.ui.start.StartActivity;
import com.agmkhair.pigeonpro.ui.userprofile.ProfileActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.agmkhair.pigeonpro.ui.Variable.mAuth;
import static com.agmkhair.pigeonpro.ui.Variable.memberInfo;
import static com.agmkhair.pigeonpro.ui.Variable.userDB;

public class MainActivity extends AppCompatActivity /*implements RewardedVideoAdListener*/ {

//    private RewardedAd mRewardedVideoAd;

     DrawerLayout drawer;
     TextView title,userName;
     FloatingActionButton floatingActionButton;
     LinearLayout logout,profile,contact,donate,about;

     CircleImageView imageProfile;


    // ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //LanguageHelper.setLanguage(this,"bn");

        setContentView(R.layout.activity_main);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


         drawer = findViewById(R.id.drawer);
         title = findViewById(R.id.tv_title_id);
         floatingActionButton = findViewById(R.id.fab_add_pigeon_id);


        initFuncation();
        initViewPager();
        initListener();

//         MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
//
//         mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
//         mRewardedVideoAd.setRewardedVideoAdListener(this);
//         loadRewardedVideoAd();

         floatingActionButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddPersonalPigeonActivity.class));

             }
         });




    }

    private void initViewPager()
    {

        Toast.makeText(this, "User Phone"+mAuth.getCurrentUser().getPhoneNumber(), Toast.LENGTH_SHORT).show();
        userDB.child(mAuth.getCurrentUser().getPhoneNumber()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                memberInfo = dataSnapshot.getValue(Member.class);
                Toast.makeText(MainActivity.this,"number "+ memberInfo.getId(), Toast.LENGTH_SHORT).show();

                //userInfo.add()

                if( memberInfo.getImage() != null)
                {
                    try {

                        imageProfile.setImageBitmap(StringToBitMap(memberInfo.getImage()));

                    }
                    catch (Exception e)
                    {

                    }
                }
                    userName.setText(memberInfo.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void initListener()
    {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FirebaseAuth.getInstance().signOut();
                drawer.closeDrawer(Gravity.RIGHT);
                startActivity(new Intent(MainActivity.this, StartActivity.class));
                finish();
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                drawer.closeDrawer(Gravity.RIGHT);

                // finish();
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /*     startActivity(new Intent(MainActivity.this, ContactActivity.class));
                drawer.closeDrawer(Gravity.RIGHT);*/

            }
        });

        contWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onlineLink("http://www.spontbd.com");
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onlineLink("https://www.facebook.com/spontbd/");
            }
        });


        You1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onlineLink("https://www.youtube.com/pigeonpettips/");
            }
        });


        You2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onlineLink("https://www.youtube.com/channel/UCDnOcn_4bDur32fDbqBs2Hw");
            }
        });


        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                drawer.closeDrawer(Gravity.RIGHT);
            }
        });


    /*    donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                drawer.closeDrawer(Gravity.RIGHT);
            }
        });*/

        contPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                callIntent.setData(Uri.parse("tel://+8801990014631"));

//                if (ActivityCompat.checkSelfPermission(MainActivity.this,
//                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
//                {
//                    return;
//                }
                startActivity(callIntent);
            }
        });




    }

    private void onlineLink(String link)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(link));
        startActivity(intent);
    }

    ImageView contPhone, contEmail,contWeb,You1,You2,facebook;
    SwitchCompat aSwitch;
    TextView aboutUs;
    private void initFuncation()
    {

        //donate = findViewById(R.id.menu_donate_ll_id);
        aboutUs = findViewById(R.id.about_us_id);
        aboutUs.setText(R.string.about);
        about = findViewById(R.id.menu_about_us_ll_id);
        logout = findViewById(R.id.menu_log_out_ll_id);
        profile = findViewById(R.id.menu_my_account_ll_id);
        contact = findViewById(R.id.menu_contact_us_ll_id);
        userName = findViewById(R.id.tv_menu_profile_name_id);
        imageProfile = findViewById(R.id.ivc_menu_profile_id);
        contPhone = findViewById(R.id.company_call);
        contEmail = findViewById(R.id.company_email);
        contWeb = findViewById(R.id.company_web);

        facebook = findViewById(R.id.company_fb);
        You1= findViewById(R.id.youtube_ahsan);
        You2 = findViewById(R.id.youtube_ssbd);

        aSwitch = findViewById(R.id.languageBtn);

        String language = new SharedPref(this).getString(LanguageHelper.SELECTED_LANGUAGE);

        if (language.equalsIgnoreCase("bn")) {
            aSwitch.setChecked(false);
        } else {
            aSwitch.setChecked(true);
        }

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    // language is en
                    //setLang("en");
                    LanguageHelper.setLanguage(MainActivity.this, "en");
                  ///  Toast.makeText(MainActivity.this, R.string.title_home, Toast.LENGTH_SHORT).show();
                    startActivity(getIntent());
                    // recreate();
                    /*     LanguageHelper.setLanguage(MainActivity.this, "en");
                    MainActivity.this.recreate();*/
                } else {
                    // language is bn

                    //setLang("bn");
                    Toast.makeText(MainActivity.this, R.string.title_home, Toast.LENGTH_SHORT).show();
                    LanguageHelper.setLanguage(MainActivity.this, "bn");
                    startActivity(getIntent());
             /*       LanguageHelper.setLanguage(MainActivity.this, "bn");
                    MainActivity.this.recreate();*/
                }

            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        goToFragment(new HomeFragment());
       title.setText(R.string.title_home);
    }

    private void goToFragment(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            // Fragment fragment = new MyPigeonFragment();
            Log.d("navigation", String.valueOf(item.getItemId()));
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //toolbar.setTitle("Shop");
                        goToFragment(new HomeFragment());
                        title.setText(R.string.title_home);

                    // replace the FrameLayout with new Fragment

                     return true;
                case R.id.navigation_dashboard:

                    // replace the FrameLayout with new Fragment
                    goToFragment(new MyPigeonFragment());
                    title.setText(R.string.title_my_pigeon);
                    return true;

                case R.id.navigation_notifications:
                    //toolbar.setTitle("Cart");
                    if(drawer.isDrawerOpen(Gravity.RIGHT))
                    {
                        drawer.closeDrawer(Gravity.RIGHT);
                    }
                    else
                    {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                    return true;
            }

            return false;
        }
    };





    private void loadRewardedVideoAd() {
//        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
//                new AdRequest.Builder().build());
      /*  mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544~3347511713",
                new AdRequest.Builder().build());*/
    }





//    @Override
//    public void onRewardedVideoAdLoaded() {
//        Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
        //video_button.setVisibility(View.VISIBLE);
//    }

//    @Override
//    public void onRewardedVideoAdOpened()
//    {
//        Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void onRewardedVideoStarted()
//    {
//        Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void onRewardedVideoAdClosed() {
//        loadRewardedVideoAd();
        // Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
//    }



//    @Override
//    public void onRewarded(RewardItem rewardItem) {
     /*   points = points + 10;
        showpoints.setText("Total points :" + points);
*/



//    }

//    @Override
//    public void onRewardedVideoAdLeftApplication() {
//        Toast.makeText(this, "onRewardedVideoAdLeftApplication",
//                Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void onRewardedVideoAdFailedToLoad(int i) {
//        Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void onRewardedVideoCompleted() {
//        Toast.makeText(this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
//    }


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();

//        if (mRewardedVideoAd.isLoaded())
//        {
           // userDB.child(memberInfo.getId()).child("coin").setValue(Integer.parseInt(memberInfo.getCoin())+10);
//            mRewardedVideoAd.show();
//            Toast.makeText(MainActivity.this, "Video load", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(MainActivity.this, "Video not load", Toast.LENGTH_SHORT).show();
//        }
//    }
}