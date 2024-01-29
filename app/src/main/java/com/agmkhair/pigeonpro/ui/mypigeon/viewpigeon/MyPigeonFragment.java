package com.agmkhair.pigeonpro.ui.mypigeon.viewpigeon;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.agmkhair.pigeonpro.R;
import com.agmkhair.pigeonpro.ui.model.Birds;
import com.agmkhair.pigeonpro.ui.mypigeon.viewpigeon.personal.ItemClickLitener;
import com.agmkhair.pigeonpro.ui.mypigeon.viewpigeon.personal.PersonalAdapter;
import com.agmkhair.pigeonpro.ui.paydegree.PayDegreeActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.agmkhair.pigeonpro.ui.Variable.listPersinalBirds;

public class MyPigeonFragment extends Fragment implements ItemClickLitener {

    private MyPigeonViewModel dashboardViewModel;
    private RecyclerView myPigeon;      //,clubPigeon;
    private PersonalAdapter adapter;    //;, //clubAdapter;


    //Camera & Gallery
    public static int CAMERA_REQUEAT_CODE = 10;
    public static int GALLERY_REQUEST_CODE = 20;


    //Adapter Variable
    private Bitmap pigeonImage = null;
    private String pigeonImageString = null;
    private CircleImageView pigeonIV;
    private int editCheck = 0;
    private TextView searchPigeon;

    private DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Birds");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(MyPigeonViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mypigeon, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);

        myPigeon = root.findViewById(R.id.rv_personal_pigeon_show_id);
        searchPigeon = root.findViewById(R.id.et_search_member_personal_pigeon_id);
        //clubPigeon = root.findViewById(R.id.rv_club_pigeon_show_id);

        myPigeon.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        //clubPigeon.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));

        adapter = new PersonalAdapter(new ArrayList<Birds>(), getContext(), this);
        //clubAdapter = new PersonalAdapter(new ArrayList<Birds>(), getContext());

        myPigeon.setAdapter(adapter);
        //clubPigeon.setAdapter(clubAdapter);

        /*RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        myPigeon.setLayoutManager(mLayoutManager);*/

        dashboardViewModel.getBirds(new MyPigeonPresenter() {
            @Override
            public void showSuccess(String msg) {

            }

            @Override
            public void showError(String msg) {

            }
        }).observe(this, new Observer<List<Birds>>() {
            @Override
            public void onChanged(@Nullable List<Birds> s) {
                //  textView.setText(s);
                adapter.setList(s);

            }
        });



    initLisener();


        return root;
    }

    private void initLisener() {
        searchPigeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodSearch();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        try {
            if (requestCode == CAMERA_REQUEAT_CODE && resultCode == Activity.RESULT_OK) {

                Toast.makeText(getActivity(), "Camera Request", Toast.LENGTH_SHORT).show();
                pigeonImage = (Bitmap) data.getExtras().get("data");
                pigeonIV.setImageBitmap(pigeonImage);
                pigeonImageString = getEncoded64ImageStringFromBitmap(pigeonImage);
            }
            else if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

                Toast.makeText(getActivity(), "Galary Request", Toast.LENGTH_SHORT).show();


                Uri imgaeUri = data.getData();

                try {
                    InputStream imageStream = getActivity().getContentResolver().openInputStream(imgaeUri);
                    pigeonImage = BitmapFactory.decodeStream(imageStream);
                    pigeonIV.setImageBitmap(pigeonImage);
                    pigeonImageString = getEncoded64ImageStringFromBitmap(pigeonImage);
                    //setImageVar.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (pigeonImage == null) {
               // defaultImage();
                pigeonImageString = null;
            }

        } catch (Exception e) {

        }

    }


    @Override
    public void onClick(View view, int position, List<Birds> birds) {
        Birds bird = birds.get(position);
        dialog(bird);
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


    private void dialog(final Birds birds) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View convertview = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_pigeon_details_show, null);


        builder.setView(convertview);
        builder.setCancelable(false);
        builder.create();

              /* Button no, yes;
              no = convertview.findViewById(R.id.dialog_btn_club_member_on_id);
              yes = convertview.findViewById(R.id.dialog_btn_club_member_yes_id); */

        final TextView name, ring, f_ring, m_ring, gender, type, details, payDegree;
        final Button cancel, edit, updateBTN;
        final RadioGroup radioGroup;
        final RadioButton[] radioTypeButton = new RadioButton[1];
        final RadioButton rb_1;
        final RadioButton rb_2;
        final EditText detailsET, nameET;

        radioGroup = convertview.findViewById(R.id.radioGroupType);

        detailsET = convertview.findViewById(R.id.et_edit_pigeon_details_id);
        nameET = convertview.findViewById(R.id.et_edit_pigeon_name_id);
        pigeonIV = convertview.findViewById(R.id.iv_pigeon_show_and_update_image_id);
        payDegree = convertview.findViewById(R.id.tv_payDegree_id);

        pigeonIV.setImageBitmap(StringToBitMap(birds.getImageBody()));
        name = convertview.findViewById(R.id.tv_pigeon_name_show_id);
        ring = convertview.findViewById(R.id.tv_pigeon_ring_number_show_id);
        f_ring = convertview.findViewById(R.id.tv_pigeon_father_ring_number_show_id);
        m_ring = convertview.findViewById(R.id.tv_pigeon_mother_ring_number_show_id);
        gender = convertview.findViewById(R.id.tv_pigeon_gender_show_id);
        type = convertview.findViewById(R.id.tv_pigeon_types_name_show_id);
        details = convertview.findViewById(R.id.tv_pigeon_details_show_id);
        cancel = convertview.findViewById(R.id.btn_cancel_pigeon_show_dialog_id);
        edit = convertview.findViewById(R.id.btn_edit_pigeon_id);
        updateBTN = convertview.findViewById(R.id.btn_update_pigeon_show_dialog_id);
        rb_1 = convertview.findViewById(R.id.rb_favorite_ty_id);
        rb_2 = convertview.findViewById(R.id.rb_normal_ty_id);


        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        name.setText(birds.getName());
        ring.setText(birds.getRing());
        f_ring.setText(birds.getFather_ring());
        m_ring.setText(birds.getMother_ring());
        type.setText("Type : " + birds.getType());
        gender.setText("Gender : " + birds.getGender());
        details.setText("Details : " + birds.getDetails());

        edit.setVisibility(View.VISIBLE);



        payDegree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                startActivity(new Intent(getActivity(), PayDegreeActivity.class).putExtra("ring", birds.getRing()).putExtra("f_ring", birds.getFather_ring()).putExtra("m_ring", birds.getMother_ring()).putExtra("race",birds.getRecentRace()));


            }
        });

        pigeonIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editCheck == 1)
                {
                    try {
                        CameraGalleryImageCapture();
                    }catch (Exception e) {}
                    //editCheck = 0;
                }

            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editCheck = 1;
                type.setText("Type : ");

                //details.setVisibility(View.GONE);
                details.setText("Details :- ");
                detailsET.setVisibility(View.VISIBLE);
                detailsET.setText(birds.getDetails());

                name.setVisibility(View.GONE);
                nameET.setVisibility(View.VISIBLE);
                nameET.setText(birds.getName());


                radioGroup.setVisibility(View.VISIBLE);

                if (birds.getType().equals("Favorite")) {
                    rb_1.setChecked(true);
                } else {
                    rb_2.setChecked(true);
                }

                updateBTN.setVisibility(View.VISIBLE);
                edit.setVisibility(View.GONE);
                payDegree.setVisibility(View.GONE);

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


        updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedTypeId = radioGroup.getCheckedRadioButtonId();
                radioTypeButton[0] = convertview.findViewById(selectedTypeId);

                String type = radioTypeButton[0].getText().toString();


                String name = nameET.getText().toString();
                String details = detailsET.getText().toString();
                //String details = detailsET.getText().toString() ;

                firebaseDatabase.child(birds.getId()).child("name").setValue(name);
                firebaseDatabase.child(birds.getId()).child("type").setValue(type);
                firebaseDatabase.child(birds.getId()).child("details").setValue(details);

                if (pigeonImageString != null)
                {
                    firebaseDatabase.child(birds.getId()).child("image").setValue(pigeonImageString);
                }

                alertDialog.dismiss();

            }
        });





    }


    private void CameraGalleryImageCapture() {

        View imageCaptureDialogView = LayoutInflater.from(getActivity()).inflate(R.layout.view_alertdialog_camera_gallary_design, null, false);

        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
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

 /*   private void defaultImage() {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_pigeon);
        pigeonImage = ((BitmapDrawable) drawable).getBitmap();
        pigeonImageString = getEncoded64ImageStringFromBitmap(pigeonImage);
        // pigeonIV.setImageBitmap(pigeonImage);
    }

*/
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }



    private void methodSearch()
    {
        searchPigeon.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()) {
                    setAdapter(s.toString());
                } else {
                    //  HomeFragment.this.notifyAll();
                    //initViewPage();
                    setAdapter();

                }
            }
        });
    }

    private void setAdapter() {
        adapter = new PersonalAdapter(listPersinalBirds,getActivity(),this);
        myPigeon.setAdapter(adapter);
    }

    private void setAdapter(final String searchedString)
    {


        int textlength = searchedString.length();
        List<Birds> tempArrayList  = new ArrayList<>();
        //ArrayList<ContactStock> tempArrayList = new ArrayList<ContactStock>();
        for(Birds c: listPersinalBirds){
            if (textlength <= c.getName().length())
            {
                if (c.getName().toLowerCase().contains(searchedString.toString().toLowerCase())) {
                    tempArrayList.add(c);
                }
            }
        }
        adapter = new PersonalAdapter( tempArrayList, getActivity(),this);
        myPigeon.setAdapter(adapter);
    }

}
