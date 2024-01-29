package com.agmkhair.pigeonpro.ui.addpigeon;

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
import android.text.InputFilter;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.agmkhair.pigeonpro.R;
import com.agmkhair.pigeonpro.databinding.ActivityAddPersonalPigeonBinding;
import com.agmkhair.pigeonpro.ui.model.Birds;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import io.ghyeok.stickyswitch.widget.StickySwitch;

import static com.agmkhair.pigeonpro.ui.Variable.listPersinalBirds;
import static com.agmkhair.pigeonpro.ui.Variable.memberInfo;
import static com.agmkhair.pigeonpro.ui.mypigeon.viewpigeon.MyPigeonFragment.CAMERA_REQUEAT_CODE;
import static com.agmkhair.pigeonpro.ui.mypigeon.viewpigeon.MyPigeonFragment.GALLERY_REQUEST_CODE;

public class AddPersonalPigeonActivity extends AppCompatActivity {

    DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Birds");
    private Bitmap pigeonImage = null;
    private String pigeonImageEye = null, pigeonImageBody = null;
    // private CircleImageView pigeonIV;
    private ActivityAddPersonalPigeonBinding binding;
    private RadioButton radioCatagoryButton;//,radioTypeButton;

    private ListView lv;
    private EditText editText;
    private ArrayAdapter<String> adapter;
    private StickySwitch stickySwitchType, stickySwitchGender;
    private String products[] = {"Apple", "Banana", "Pinapple", "Orange", "Papaya", "Melon",
            "Grapes", "Water Melon", "Lychee", "Guava", "Mango", "Kivi"};
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_personal_pigeon);
        //  binding.rbMaleId.setChecked(true);
        // binding.rbMaleId.setSelected(false);
        binding.rbFavoriteId.setChecked(true);
        onClick();
        spinner();


        stickySwitchType = findViewById(R.id.sticky_Switch_type);

        stickySwitchGender = findViewById(R.id.sticky_Switch_gender);

        stickySwitchType.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(StickySwitch.Direction direction, String text) {

                Toast.makeText(AddPersonalPigeonActivity.this, text, Toast.LENGTH_SHORT).show();
                if (text.equalsIgnoreCase("Giribaz") || text.equalsIgnoreCase("গিরিবাজ") )
                    binding.autoRandomNumberId.setVisibility(View.VISIBLE);
                else
                    binding.autoRandomNumberId.setVisibility(View.GONE);

                //  Log.d("TAG", "Now Selected : " + direction.name() + ", Current Text : " + text);
                //  Toast.makeText(AddPersonalPigeonActivity.this, "This is Current Text", Toast.LENGTH_SHORT).show();
            }
        });

        //Toast.makeText(this, stickySwitch.getText(), Toast.LENGTH_SHORT).show();

    }

    private void spinner()
    {

        spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    int maxLengthofEditText = 2;
                    binding.etAddPigeonRecentResultId.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthofEditText)});
                    binding.etAddPigeonRecentResultId.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});

                }else
                {
                    int maxLengthofEditText = 4;
                    binding.etAddPigeonRecentResultId.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthofEditText)});
//                    binding.etAddPigeonRecentResultId.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    int checkImage = 0;

    private void onClick() {

        binding.ivAddPigeonImage1Id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkImage = 1;
                CameraGalleryImageCapture();
            }
        });

        binding.ivAddPigeonImage2Id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkImage = 2;
                CameraGalleryImageCapture();
            }
        });


        binding.swichBreadedId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.swichBreadedId.isOn()) {

                    binding.etAddPigeonFatherRingNumberId.setVisibility(View.GONE);
                    binding.etAddPigeonMotherRingNumberId.setVisibility(View.GONE);

                } else {
                    binding.etAddPigeonFatherRingNumberId.setVisibility(View.VISIBLE);
                    binding.etAddPigeonMotherRingNumberId.setVisibility(View.VISIBLE);

                }

            }
        });


        binding.autoRandomNumberId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] id = memberInfo.getId().split("1");
                binding.etAddPigeonRingNumberId.setText(id[1] + "-" + String.valueOf(checkId()));
                //checkId()
            }
        });

        binding.btnSavePigeonAddedDataId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pigeonImageEye == null || pigeonImageBody == null) {
                    defaultImage();
                }

                String name, ring, f_ring, m_ring, gender = null, details, type = null;
                String race_result, breded, category;

                /* int selectedId = binding.radioGroup1.getCheckedRadioButtonId();
                int selectedTypeId = binding.radioGroup2.getCheckedRadioButtonId();*/

                int selectedCatagoryId = binding.radioGroup2.getCheckedRadioButtonId();


                // find the radiobutton by returned id
//              /*  radioSexButton = findViewById(selectedId);
//                radioTypeButton= findViewById(selectedTypeId);
//*/

                radioCatagoryButton = findViewById(selectedCatagoryId);
                //binding.rbFemaleId
                name = binding.etAddPigeonNameId.getText().toString();
                ring = binding.etAddPigeonRingNumberId.getText().toString();
                f_ring = binding.etAddPigeonFatherRingNumberId.getText().toString();
                m_ring = binding.etAddPigeonMotherRingNumberId.getText().toString();

                category = radioCatagoryButton.getText().toString();
                gender = binding.stickySwitchGender.getText();
                type = binding.stickySwitchType.getText().toString();

                race_result = binding.etAddPigeonRecentResultId.getText().toString();

                if (binding.swichBreadedId.isOn()) {
                    breded = binding.swichBreadedId.getLabelOn();
                } else {
                    breded = binding.swichBreadedId.getLabelOff();
                }

                details = binding.etAddPigeonDetailsId.getText().toString();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                String id = firebaseDatabase.push().getKey();
                String sp = spinner.getSelectedItem().toString();

                // Birds birds = new Birds(id,name,ring,f_ring,m_ring,gender,details);
                Birds birds = new Birds(id, userId, "", pigeonImageEye, pigeonImageBody, type, name, ring, gender, race_result + " " + sp, details, breded, f_ring, m_ring, category, "");

                if (name.isEmpty() || ring.isEmpty() || f_ring.isEmpty() || m_ring.isEmpty() || gender.isEmpty() || type.isEmpty() || details.isEmpty()) {
                    Toast.makeText(AddPersonalPigeonActivity.this, "Filup the all filed", Toast.LENGTH_SHORT).show();
                } else {

                    firebaseDatabase.child(id).setValue(birds);
                    ClearData();
                    Toast.makeText(AddPersonalPigeonActivity.this, "Save Pigeon Information", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private int checkId() {
        int old = 0, cur = 0;
        for (int i = 0; listPersinalBirds.size() > i; i++) {
            cur = 0;

            //New = ;
            String[] value = listPersinalBirds.get(i).getRing().split("-");
            if (!value[1].isEmpty()) {


                String ringId = value[1].toString();
                //    if (!ringId.isEmpty())
                cur = Integer.parseInt(ringId);

                //cur =

            }

            if (cur > old) {
                old = cur;
            }
        }
        if (old != 0)
            return old + 1;
        else
            return 1;

    }

    private void imageChoice() {

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

    private void defaultImage() {
        Drawable eye = null, body = null;
        switch (checkImage) {
            case 0:
                eye = getResources().getDrawable(R.drawable.eye_png);
                body = getResources().getDrawable(R.drawable.body_png);

                pigeonImage = ((BitmapDrawable) eye).getBitmap();
                pigeonImageEye = getEncoded64ImageStringFromBitmap(pigeonImage);
                pigeonImage = ((BitmapDrawable) body).getBitmap();
                pigeonImageBody = getEncoded64ImageStringFromBitmap(pigeonImage);
                break;
            case 1:
                body = getResources().getDrawable(R.drawable.body_png);
                pigeonImage = ((BitmapDrawable) body).getBitmap();
                pigeonImageBody = getEncoded64ImageStringFromBitmap(pigeonImage);
                break;
            case 2:
                eye = getResources().getDrawable(R.drawable.eye_png);
                pigeonImage = ((BitmapDrawable) eye).getBitmap();
                pigeonImageEye = getEncoded64ImageStringFromBitmap(pigeonImage);
                break;

        }
        //Drawable drawable = getResources().getDrawable(R.drawable.ic_pigeon);
        pigeonImage = ((BitmapDrawable) eye).getBitmap();
        pigeonImageEye = getEncoded64ImageStringFromBitmap(pigeonImage);
        pigeonImage = ((BitmapDrawable) body).getBitmap();
        pigeonImageBody = getEncoded64ImageStringFromBitmap(pigeonImage);

        //        pigeonIV.setImageBitmap(pigeonImage);
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }

    private void ClearData() {
        binding.etAddPigeonNameId.setText("");
        binding.etAddPigeonRingNumberId.setText("");
        binding.etAddPigeonFatherRingNumberId.setText("");
        binding.etAddPigeonMotherRingNumberId.setText("");
        //  binding.radioGroup1.clearCheck();
        binding.etAddPigeonDetailsId.setText("");
        checkImage = 0;
        defaultImage();
        //binding.ivAddPigeonImage1Id.setImageBitmap(defaultImage());
//      Toast.makeText(this, "Save Pigeon Information", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == CAMERA_REQUEAT_CODE && resultCode == Activity.RESULT_OK) {

                Toast.makeText(this, "Camera Request", Toast.LENGTH_SHORT).show();
                pigeonImage = (Bitmap) data.getExtras().get("data");


            } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

                Toast.makeText(this, "Galary Request", Toast.LENGTH_SHORT).show();
                Uri imgaeUri = data.getData();

                try {
                    InputStream imageStream = getContentResolver().openInputStream(imgaeUri);
                    pigeonImage = BitmapFactory.decodeStream(imageStream);
                  /*  binding.ivAddPigeonImage1Id.setImageBitmap(pigeonImage);
                    pigeonImageEye = getEncoded64ImageStringFromBitmap(pigeonImage);
                   */ //setImageVar.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (pigeonImage == null) {
                defaultImage();
                checkImage = 0;
            }


            if (checkImage == 1) {
                binding.ivAddPigeonImage1Id.setImageBitmap(pigeonImage);
                pigeonImageEye = getEncoded64ImageStringFromBitmap(pigeonImage);

            } else if (checkImage == 2) {
                binding.ivAddPigeonImage2Id.setImageBitmap(pigeonImage);
                pigeonImageBody = getEncoded64ImageStringFromBitmap(pigeonImage);

            }

        } catch (Exception e) {

        }

    }

}