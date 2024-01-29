package com.agmkhair.pigeonpro.ui.admin.add.addmember;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.agmkhair.pigeonpro.R;
import com.agmkhair.pigeonpro.databinding.ActivityAddMemberBinding;


public class AddMemberActivity extends AppCompatActivity
{

    ActivityAddMemberBinding binding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_member);

        initListener();

    }

    private void initListener()
    {
           binding.btnAddMemberId.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v)
               {

                  String name = binding.etAdminAddMemberNameId.getText().toString();
                  String number = binding.etAdminAddMemberNumberId.getText().toString();

                   if(name.isEmpty() )
                   {
                       binding.etAdminAddMemberNameId.setError("Wrong!!");
                   }
                   else if(number.length()<11) {
                       binding.etAdminAddMemberNumberId.setError("Wrong!!");
                   }else {
                       if(isValidPhoneNumber(number)){
                           boolean status = validateUsing_libphonenumber("+880", number);

                           if(status)
                           {

                               // Club club = new Club(mAuth.getUid(),name,"",number,)

                               //Member member = new Member("PAB-"+(clubMemberNumber+1),name,number,"","",adminId[0],"","","");
                              // adminDatabase.child(adminId[0]).child("Member").child(member.getId()).setValue(member);

                               binding.etAdminAddMemberNameId.setText("");
                               binding.etAdminAddMemberNumberId.setText("");

                               //  Intent intent = new Intent(AddMemberActivity.this, OTPVerificationActivity.class);
                               // intent.putExtra("number",number );
                               //startActivity(intent);

                           }
                           else
                               {
                               Toast.makeText(AddMemberActivity.this, "Invalid Phone Number (libphonenumber)", Toast.LENGTH_SHORT).show();
                               //  tvIsValidPhone.setText("Invalid Phone Number (libphonenumber)");
                           }
                       }
                       else {
                           Toast.makeText(AddMemberActivity.this, "Invalid Phone Number (isValidPhoneNumber)", Toast.LENGTH_SHORT).show();
                           //tvIsValidPhone.setText("Invalid Phone Number (isValidPhoneNumber)");
                       }
                   }
               }
           });
    }


    private boolean isValidPhoneNumber(CharSequence phoneNumber)
    {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    private boolean validateUsing_libphonenumber(String countryCode, String phNumber)
    {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            //phoneNumber = phoneNumberUtil.parse(phNumber, "IN");  //if you want to pass region code
            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode);
        } catch (NumberParseException e)
        {
            System.err.println(e);
        }

        boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
        if (isValid) {
            String internationalFormat = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            Toast.makeText(AddMemberActivity.this, "Phone Number is Valid " + internationalFormat, Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(this, "Phone Number is Invalid " + phoneNumber, Toast.LENGTH_LONG).show();
            return false;
        }
    }


}
