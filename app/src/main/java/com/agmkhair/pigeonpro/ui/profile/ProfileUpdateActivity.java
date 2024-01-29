package com.agmkhair.pigeonpro.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.agmkhair.pigeonpro.MainActivity;
import com.agmkhair.pigeonpro.R;
import com.agmkhair.pigeonpro.databinding.ActivityProfileClubVerificationBinding;
import com.agmkhair.pigeonpro.databinding.ActivityProfileUpdateBinding;
import com.agmkhair.pigeonpro.ui.model.Member;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.agmkhair.pigeonpro.ui.Variable.userDB;

public class ProfileUpdateActivity extends AppCompatActivity {

    ActivityProfileUpdateBinding binding;
    public static int CAMERA_REQUEAT_CODE = 10;
    public static int GALLERY_REQUEST_CODE = 20;

    //Adapter Variable
    private Bitmap pigeonImage = null;
    private String pigeonImageString = null;
    private CircleImageView pigeonIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile_update);



        ImageView imageView = findViewById(R.id.setImageCameraAndGallary);
        pigeonIV = findViewById(R.id.profile_image);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CameraGalleryImageCapture();
            }
        });



        binding.btnUserProfileUpdateId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String userName ,phone,address,loft,city,email;


                userName  = binding.editProfileUsarnameId.getText().toString();
                phone = binding.editProfilePhoneId.getText().toString();
                address = binding.editProfileAddressId.getText().toString();
                city  = binding.editProfileCityId.getText().toString();
                loft = binding.editLoftNameId.getText().toString();
                email = binding.etProfileEmailId.getText().toString();


                if(pigeonImageString != null)
                {

                if(userName.isEmpty() || phone.isEmpty() || address.isEmpty())
                {     
                    Toast.makeText(ProfileUpdateActivity.this, "Fil Up The All Filed", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    String id = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

                    Member member = new Member(id,"",pigeonImageString,userName,loft, phone,address,city,email,"","");
                    userDB.child(id).setValue(member);

                    //  userDB.child().setValue(new Member(userName,phone,address,zip));
                    startActivity(new Intent(ProfileUpdateActivity.this, MainActivity.class));
                    finish();
                }

                }
                else
                {
                    Toast.makeText(ProfileUpdateActivity.this, "Please Choice Your Profile Pic", Toast.LENGTH_SHORT).show();
                    //defaultImage();
                }

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

    private void CameraGalleryImageCapture() {

        View imageCaptureDialogView = LayoutInflater.from(this).inflate(R.layout.view_alertdialog_camera_gallary_design, null, false);

        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(imageCaptureDialogView);
        dialog.show();

        ImageView camera, gallery;

        camera = imageCaptureDialogView.findViewById(R.id.getCameraImageId);
        gallery = imageCaptureDialogView.findViewById(R.id.getGalleryImageId);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEAT_CODE);

            }
        });


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
            }
        });
    }

    private void defaultImage()
    {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_pigeon);
        pigeonImage = ((BitmapDrawable) drawable).getBitmap();
        pigeonImageString = getEncoded64ImageStringFromBitmap(pigeonImage);
        // pigeonIV.setImageBitmap(pigeonImage);
    }


    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == CAMERA_REQUEAT_CODE && resultCode == Activity.RESULT_OK) {

                Toast.makeText(this, "Camera Request", Toast.LENGTH_SHORT).show();
                pigeonImage = (Bitmap) data.getExtras().get("data");
                pigeonIV.setImageBitmap(pigeonImage);
                pigeonImageString = getEncoded64ImageStringFromBitmap(pigeonImage);
            } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

                Toast.makeText(this, "Galary Request", Toast.LENGTH_SHORT).show();


                Uri imgaeUri = data.getData();

                try {
                    InputStream imageStream = this.getContentResolver().openInputStream(imgaeUri);
                    pigeonImage = BitmapFactory.decodeStream(imageStream);
                    pigeonIV.setImageBitmap(pigeonImage);
                    pigeonImageString = getEncoded64ImageStringFromBitmap(pigeonImage);
                    //setImageVar.setImageBitmap(bitmap);
                } catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
            } else if (pigeonImage == null) {
                defaultImage();
            }

        } catch (Exception e) {

        }

    }

}
