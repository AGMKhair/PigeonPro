package com.agmkhair.pigeonpro.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.agmkhair.pigeonpro.R;
import com.agmkhair.pigeonpro.databinding.ActivityAdminLoginBinding;
import com.agmkhair.pigeonpro.ui.admin.home.AdminHomeActivity;

import static com.agmkhair.pigeonpro.ui.Variable.adminAuth;

public class AdminLoginActivity extends AppCompatActivity {

    private ActivityAdminLoginBinding binding;
    private String emailValue;
    private String passwordValue;
    //pr ivate FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_admin_login);
        // mAuth = FirebaseAuth.getInstance();

        binding.btnClubOwnerLoginId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(validate())
                {
                    adminAuth.signInWithEmailAndPassword(emailValue, passwordValue)
                            .addOnCompleteListener(AdminLoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        //Log.d("", "signInWithEmail:success");
                                        // FirebaseUser user = mAuth.getCurrentUser();
                                        // updateUI(user);
                                        goToMainClass();


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        // Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(AdminLoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        //   updateUI(null);
                                    }

                                    // ...
                                }
                            });
                }



            }
        });
    }

    private void goToMainClass()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Login", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Login", "admin"); // Storing long
        editor.commit();


        startActivity(new Intent(AdminLoginActivity.this, AdminHomeActivity.class));
        finish();
    }




    public boolean validate() {
     //   boolean valid = true;

        emailValue = binding.etLoginClubOwnerUsarnameId.getText().toString();
        passwordValue = binding.etLoginClubOwnerPasswordId.getText().toString();

        if (emailValue.isEmpty()) {
            binding.etLoginClubOwnerUsarnameId.setError("enter a valid email address");
           return false;
        } else {
            binding.etLoginClubOwnerUsarnameId.setError(null);
        }

        if (passwordValue.isEmpty() || passwordValue.length() < 6)
        {

            binding.etLoginClubOwnerPasswordId.setError("enter a valid password");
            return false;

        } else {
            binding.etLoginClubOwnerPasswordId.setError(null);
        }

        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();



        if(adminAuth.getCurrentUser() != null)
        {
            goToMainClass();
        }

    }

}
