package com.agmkhair.pigeonpro.ui.otp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.agmkhair.pigeonpro.MainActivity;
import com.agmkhair.pigeonpro.R;
import com.agmkhair.pigeonpro.databinding.ActivityOtpverificationBinding;
import com.agmkhair.pigeonpro.ui.profile.ProfileClubVerificationActivity;
import com.agmkhair.pigeonpro.ui.profile.ProfileUpdateActivity;

import java.util.concurrent.TimeUnit;

import static com.agmkhair.pigeonpro.ui.Variable.code;
import static com.agmkhair.pigeonpro.ui.Variable.firebaseAuth;
import static com.agmkhair.pigeonpro.ui.Variable.mVerificationId;
import static com.agmkhair.pigeonpro.ui.Variable.userDB;

public class OTPVerificationActivity extends AppCompatActivity {

    ActivityOtpverificationBinding binding;

    SharedPreferences pref;// = getApplicationContext().getSharedPreferences("Login", 0); // 0 - for private mode
    SharedPreferences.Editor editor;// = pref.edit();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = getApplicationContext().getSharedPreferences("Login", 0); // 0 - for private mode
        editor = pref.edit();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_otpverification);

        binding.btnOtpVerifyId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AlertDialog dialog = new AlertDialog.Builder();
                String c1 = binding.otpVerifybox1.getText().toString();
                String c2 = binding.otpVerifybox2.getText().toString();
                String c3 = binding.otpVerifybox3.getText().toString();
                String c4 = binding.otpVerifybox4.getText().toString();
                String c5 = binding.otpVerifybox5.getText().toString();
                String c6 = binding.otpVerifybox6.getText().toString();

              //  code = new StringBuilder().append(c1).append(c2).toString();

                if(c1.isEmpty() || c2.isEmpty() || c3.isEmpty() || c4.isEmpty() || c5.isEmpty() || c6.isEmpty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OTPVerificationActivity.this);
                    builder.setMessage("Fill up all Verification number ");
                    builder.show();

                }
                else
                {
                    verifyVerificationCode(c1+c2+c3+c4+c5+c6);
                }

            }
        });


    }

    private void ConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(OTPVerificationActivity.this);
        View convertview = LayoutInflater.from(OTPVerificationActivity.this).inflate(R.layout.dialog_login_profile_confirmation, null);

        builder.setView(convertview);
        builder.setCancelable(false);
        builder.create();

        Button no, yes;

        no = convertview.findViewById(R.id.dialog_btn_club_member_on_id);
        yes = convertview.findViewById(R.id.dialog_btn_club_member_yes_id);

        final AlertDialog alertDialog = builder.create();

        alertDialog.show();

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
                startActivity(new Intent(OTPVerificationActivity.this, ProfileUpdateActivity.class));
                finish();

            }
        });

        yes.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                        startActivity(new Intent(OTPVerificationActivity.this, ProfileClubVerificationActivity.class));
                        finish();
                    }
                }
        );
    }

    private final void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(OTPVerificationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            FirebaseDatabase.getInstance().getReference("Member");

                            editor.putString("login", "user"); // Storing long
                            editor.commit(); // commit changes

                            //ConfirmationDialog();
                            goToUserHome();

                        } else {
                            //verification unsuccessful.. display an error message
                            String message = "Somthing is wrong, we will fix it soon...";
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            {
                                message = "Invalid code entered...";
                            }
                            Toast.makeText(OTPVerificationActivity.this, "" + message, Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    private void goToUserHome()
    {
        final String id = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        userDB.child(id).child("id").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String status = dataSnapshot.getValue(String.class);

                if(status == null)
                {

                    startActivity(new Intent(OTPVerificationActivity.this, ProfileUpdateActivity.class));
                    finish();

                }
                else
                {
                    startActivity(new Intent(OTPVerificationActivity.this, MainActivity.class));
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        String number = getIntent().getStringExtra("number");

        if(number!= null)
       {
           sendVerificationCode(number);
       }

    }

    private void setVerification() {

        binding.otpVerifybox1.setText(code.charAt(0));
        binding.otpVerifybox2.setText(code.charAt(1));
        binding.otpVerifybox3.setText(code.charAt(2));
        binding.otpVerifybox4.setText(code.charAt(3));
        binding.otpVerifybox5.setText(code.charAt(4));
        binding.otpVerifybox6.setText(code.charAt(5));

    }

    public  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS

            code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null)
            {
                 //number.setText(code);
                //verifying the code

               verifyVerificationCode(code);

            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
           // Toast.makeText(OTPVerificationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("TAG", "onVerificationFailed: Error" );
        }
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    private void sendVerificationCode(String mobile) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+88"+ mobile,
                60,
                TimeUnit.SECONDS,
                (Activity) TaskExecutors.MAIN_THREAD,
                mCallbacks);

        // mobileNumber = mobile;
        // confirmClear();

    }

}
