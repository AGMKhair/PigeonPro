package com.agmkhair.pigeonpro.ui.otp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.agmkhair.pigeonpro.MainActivity;
import com.agmkhair.pigeonpro.R;
import com.agmkhair.pigeonpro.databinding.ActivityOtpBinding;
import static com.agmkhair.pigeonpro.ui.Variable.userId;


public class OTPActivity extends AppCompatActivity {

    ActivityOtpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = DataBindingUtil.setContentView(this,R.layout.activity_otp);



        binding.btnOtpSendCodeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String number = binding.etOtpNumberId.getText().toString();

                 if(number.isEmpty() || number.length()<11)
                 {

                    binding.etOtpNumberId.setError("Wrong!!");
                 }else {

                     if(isValidPhoneNumber(number)){
                         boolean status = validateUsing_libphonenumber("+880", number);
                         if(status){

                             Intent intent = new Intent(OTPActivity.this, OTPVerificationActivity.class);
                             intent.putExtra("number",number );
                             startActivity(intent);

                         } else {
                             Toast.makeText(OTPActivity.this, "Invalid Phone Number (libphonenumber)", Toast.LENGTH_SHORT).show();
                           //  tvIsValidPhone.setText("Invalid Phone Number (libphonenumber)");
                         }
                     }
                     else {
                         Toast.makeText(OTPActivity.this, "Invalid Phone Number (isValidPhoneNumber)", Toast.LENGTH_SHORT).show();
                         //tvIsValidPhone.setText("Invalid Phone Number (isValidPhoneNumber)");
                     }

                 }
            }
        });

    }







    @Override
    protected void onStart() {
        super.onStart();
        //userId = null;
        try {
            userId = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        }catch (Exception e)
        {

        }
        if(userId != null)
        {
            startActivity(new Intent(OTPActivity.this,MainActivity.class));
            finish();
        }
    }


    public boolean isPhoneNumberValid(String phoneNumber, String countryCode)
    {
        //NOTE: This should probably be a member variable.
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try
        {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber, countryCode);
            return phoneUtil.isValidNumber(numberProto);
        }
        catch (NumberParseException e)
        {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

        return false;
    }



    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    private boolean validateUsing_libphonenumber(String countryCode, String phNumber) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            //phoneNumber = phoneNumberUtil.parse(phNumber, "IN");  //if you want to pass region code
            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode);
        } catch (NumberParseException e) {
            System.err.println(e);
        }

        boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
        if (isValid) {
            String internationalFormat = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            Toast.makeText(OTPActivity.this, "Phone Number is Valid " + internationalFormat, Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(this, "Phone Number is Invalid " + phoneNumber, Toast.LENGTH_LONG).show();
            return false;
        }
    }


}
