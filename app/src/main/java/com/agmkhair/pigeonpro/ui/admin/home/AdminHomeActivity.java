package com.agmkhair.pigeonpro.ui.admin.home;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.agmkhair.pigeonpro.databinding.ActivityAdminHomeBinding;
import com.agmkhair.pigeonpro.R;
import com.agmkhair.pigeonpro.ui.admin.add.addmember.AddMemberActivity;
import com.agmkhair.pigeonpro.ui.admin.bird.AdminBirdActivity;
import com.agmkhair.pigeonpro.ui.admin.bird.AdminBirdPresenter;
import com.agmkhair.pigeonpro.ui.admin.bird.AdminBirdViewModel;
import com.agmkhair.pigeonpro.ui.admin.memberlist.MemberListActivity;
import com.agmkhair.pigeonpro.ui.admin.memberlist.MemberListPresenter;
import com.agmkhair.pigeonpro.ui.admin.memberlist.MemberListViewModel;
import com.agmkhair.pigeonpro.ui.model.Birds;
import com.agmkhair.pigeonpro.ui.model.Club;
import com.agmkhair.pigeonpro.ui.model.Member;
import java.util.List;
import static com.agmkhair.pigeonpro.ui.Variable.adminDatabase;
import static com.agmkhair.pigeonpro.ui.Variable.clubBirds;
import static com.agmkhair.pigeonpro.ui.Variable.clubMemberNumber;
import static com.agmkhair.pigeonpro.ui.Variable.adminId;


public class AdminHomeActivity extends AppCompatActivity {

    private Boolean isFabOpen = false;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    public static Club adminPro;
    private ActivityAdminHomeBinding binding;
    private NumberPicker picker1, picker2, picker3;
    private String[] pickerVals;

    // private DatabaseReference databaseReference;
    private AdminBirdViewModel viewModel;
    private MemberListViewModel memberListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_home);
        init();
        initViewPage();
        pickerVals = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        initListining();

    }

    int pStatus = 0;
    Handler handler = new Handler();
    private void initViewPage()
    {

        viewModel.getClubBirds(new AdminBirdPresenter()
        {
            @Override
            public void showSuccess(String msg)
            {

            }
            @Override
            public void showError(String msg)
            {

            }

            @Override
            public void showLoading() {

            }
        }).observe(this, new Observer<List<Birds>>() {
            @Override
            public void onChanged(List<Birds> birds)
            {

                int size = birds.size();


                if(size < 10)
                {
                    binding.progressBarCircleBirdsId.setProgress(size);
                    binding.progressBarCircleBirdsId.setMax(10);
                }
                else if(size <100 )
                {
                    binding.progressBarCircleBirdsId.setMax(100);


                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            while (pStatus < 100) {
                                pStatus += 1;

                                handler.post(new Runnable() {

                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub
                                        binding.tvTotalBirdsListShowId.setText(String.valueOf(pStatus));
                                        binding.progressBarCircleBirdsId.setProgress(pStatus);

                                    }
                                });
                                try {
                                    // Sleep for 200 milliseconds.
                                    // Just to display the progress slowly
                                    Thread.sleep(16); //thread will take approx 3 seconds to finish
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();



                }
                else
                {
                    binding.progressBarCircleBirdsId.setProgress(size);
                    binding.progressBarCircleBirdsId.setMax((size+30));
                }

            }
        });
        memberListViewModel.getMembers(new MemberListPresenter() {
            @Override
            public void showSuccess(String msg) {

            }

            @Override
            public void showError(String msg)
            {

            }


        }).observe(this, new Observer<List<Member>>() {
            @Override
            public void onChanged(List<Member> members) {

                final int size = members.size();
              //  final Handler handler = new Handler();

                if(size < 10)
                {
                    binding.progressBarCircleMemberId.setMax(10);


                    for(int i = 1; i <= size;i++) {


                        final int finalI = i;

                        new Handler().postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void run() {




                                binding.tvTotalMemberListShowId.setText(String.valueOf(finalI));
                                binding.progressBarCircleMemberId.setProgress(finalI);




                        }

                    },1500);
                    }



                }
                else if(size <100 )
                {
                    binding.progressBarCircleMemberId.setProgress(size);
                    binding.progressBarCircleMemberId.setMax(100);
                }
                else
                {
                    binding.progressBarCircleMemberId.setProgress(size);
                    binding.progressBarCircleMemberId.setMax((size+30));
                }


            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();


      //  loadDataFromfirebase();

    }

    private void initListining() {

        binding.fabMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });

        binding.fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this, AddMemberActivity.class));
            }
        });

        binding.fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminHomeActivity.this);
                View view = LayoutInflater.from(AdminHomeActivity.this).inflate(R.layout.dialog_bird_add, null);

                EditText ownerID = view.findViewById(R.id.dialog_et_admin_add_bird_owner_id);
                final TextView birdTeg = view.findViewById(R.id.dialog_tv_admin_bird_teg_add_id);
                final CheckBox checkBox = view.findViewById(R.id.dialog_cb_check_id);
                final LinearLayout linearLayout = view.findViewById(R.id.dialog_ll_numberPicker_id);

                Button btn = view.findViewById(R.id.btn_admin_bird_add_id);

                builder.create();
                builder.setView(view);
                final AlertDialog dialog = builder.create();

                //linearLayout.setVisibility(View.GONE);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            linearLayout.setVisibility(View.VISIBLE);

                        } else {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                });

                picker1 = view.findViewById(R.id.numberpicker_count_1);
                picker2 = view.findViewById(R.id.numberpicker_count_2);
                picker3 = view.findViewById(R.id.numberpicker_count_3);

                picker1.setMaxValue(9);
                picker1.setMinValue(0);
                picker1.setDisplayedValues(pickerVals);

                picker2.setMaxValue(9);
                picker2.setMinValue(0);
                picker2.setDisplayedValues(pickerVals);

                picker3.setMaxValue(9);
                picker3.setMinValue(0);
                picker3.setDisplayedValues(pickerVals);


                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tag = birdTeg.getText().toString();
                        String id;
                        if (checkBox.isChecked()) {

                            int total = picker1.getValue() * 100 + picker2.getValue() * 10 + picker3.getValue();

                            Toast.makeText(AdminHomeActivity.this, String.valueOf(tag), Toast.LENGTH_SHORT).show();
                            Toast.makeText(AdminHomeActivity.this, String.valueOf(total), Toast.LENGTH_SHORT).show();
                           int tegNumber = Integer.parseInt(tag);
                            if (total > tegNumber) {
                                int i;
                                for ( i = tegNumber+1 ; total > i; i++) {

                                     id = clubBirds.push().getKey();
                                     Birds birds = null;// = new Birds(id, userId, "", "", "PAB" + i, "null", "", "", "", "", adminId[0],"","200k");
                                     clubBirds.child(String.valueOf(adminId[0])).child("PAB11").child(id).setValue(birds);

                                }
                            } else {
                                Toast.makeText(AdminHomeActivity.this, "Teg Number reverse", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            id = clubBirds.push().getKey();
                            Birds birds = null;//= new Birds(id, userId, "", "", "PAB" + tag, "null", "", "", "", "", adminId[0],"","200k");
                            clubBirds.child(String.valueOf(adminId[0])).child("PAB12").child(id).setValue(birds);

                        }



                        dialog.dismiss();
                    }


                });


                dialog.show();
            }
        });


        binding.btnAdminBirdListShowId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AdminHomeActivity.this, AdminBirdActivity.class));
                //finish();
            }
        });


        binding.btnAdminMemberListShowId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this, MemberListActivity.class));
                //finish();
            }
        });

    }

    private void clubBirdAdd()
    {
        //clubBirds.child(String.valueOf(adminId)).child("PAB11").setValue();
    }

    private void init() {
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        viewModel = ViewModelProviders.of(this).get(AdminBirdViewModel.class);
        memberListViewModel = ViewModelProviders.of(this).get(MemberListViewModel.class);
    }

    public void animateFAB() {

        if (isFabOpen) {

            binding.fabMember.startAnimation(rotate_backward);
            binding.fab1.startAnimation(fab_close);
            binding.fab2.startAnimation(fab_close);
            binding.fab1.setClickable(false);
            binding.fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            binding.fabMember.startAnimation(rotate_forward);
            binding.fab1.startAnimation(fab_open);
            binding.fab2.startAnimation(fab_open);
            binding.fab1.setClickable(true);
            binding.fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");

        }
    }

    private void loadDataFromfirebase() {

    //    AdminBirdRepository birdRepository = null;

        //  adminDatabase = FirebaseDatabase.getInstance().getReference("Clubs");

        adminDatabase.child(adminId[0]).child("Member").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                clubMemberNumber = dataSnapshot.getChildrenCount();

                Toast.makeText(AdminHomeActivity.this, String.valueOf(clubMemberNumber), Toast.LENGTH_SHORT).show();

                //  adminPro = dataSnapshot.getValue(Club.class);
                //dataSnapshot.getValue();
                //Log.i(TAG, "onDataChange: Successful ");

                try {

                    // Picasso.get().load(adminPro.getPro_image()).into(circularImageView);

                }
                catch (Exception e)
                {
                    // dialog.dismiss();
                    Toast.makeText(AdminHomeActivity.this, "You Have no data !! ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //   Log.i(TAG, "onCancelled: Fail");
            }
        });


        adminDatabase.child(adminId[0]).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                // clubMemberNumber =  dataSnapshot.getChildrenCount();

                // Toast.makeText(AdminHomeActivity.this, String.valueOf(clubMemberNumber), Toast.LENGTH_SHORT).show();

                //  adminPro = dataSnapshot.getValue(Club.class);
                Club club = dataSnapshot.getValue(Club.class);
                //dataSnapshot.getValue();

                //Log.i(TAG, "onDataChange: Successful ");

                try {

                    // Picasso.get().load(adminPro.getPro_image()).into(circularImageView);

                    //   Log.d("Admin Information", " "+dataSnapshot.getValue(Club.class));
//                    Log.d("Admin Information", " "+club.getClubTeg());
                    Toast.makeText(AdminHomeActivity.this, "admin " + club.getClubId() + " " + club.getPhoneNumber(), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(AdminHomeActivity.this, "admin ", Toast.LENGTH_SHORT).show();
                    Toast.makeText(AdminHomeActivity.this, "name " + club.getName(), Toast.LENGTH_SHORT).show();
                    // Log.d("Admin Information", " "+club.getName());
                    //Log.d("Admin Information", " "+club.getClubId());
                    //Log.d("Admin Information", " "+club.getPhoneNumber());

                 /*    email.setText(adminPro.getGmail());
                    location.setText(adminPro.getLocation());
                    admin_name.setText(adminPro.getName());
                    number.setText(adminPro.getNumber());
                    dialog.dismiss();*/

                }
                catch (Exception e) {
                    // dialog.dismiss();
                    Toast.makeText(AdminHomeActivity.this, "You Have no data !! ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //   Log.i(TAG, "onCancelled: Fail");
            }
        });
    }
}
