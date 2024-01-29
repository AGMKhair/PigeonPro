package com.agmkhair.pigeonpro.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.agmkhair.pigeonpro.R;
import com.agmkhair.pigeonpro.ui.model.Birds;
import com.agmkhair.pigeonpro.ui.model.Member;
import com.agmkhair.pigeonpro.ui.paydegree.PayDegreeActivity;

import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.agmkhair.pigeonpro.ui.Variable.birdOwner;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
{

    private List<Birds> birdsList;
    private Context context;

    public HomeAdapter(List<Birds> birdsList, Context context)
    {
        this.birdsList = birdsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.model_pigeon_show,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {

        final Birds birds = birdsList.get(position);
       // String name = ;

        holder.type.setText("");
        holder.name.setText(birds.getName());
//      holder.rating.setText("");
        holder.race.setText(birds.getRecentRace());

        holder.icon.setImageBitmap(StringToBitMap(birds.getImageBody()));


        // holder.profile.setImageResource(R.drawable.pegion_profile);
        //holder.fav.setImageResource(R.drawable.button_right);

        holder.details.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View convertview = LayoutInflater.from(context).inflate(R.layout.dialog_pigeon_details_show, null);

                builder.setView(convertview);
                builder.setCancelable(false);
                builder.create();

              /*
              Button no, yes;
              no = convertview.findViewById(R.id.dialog_btn_club_member_on_id);
              yes = convertview.findViewById(R.id.dialog_btn_club_member_yes_id);
               */

              TextView name,ring,f_ring,m_ring, gender,type,details,payDegree;
              Button cancel,owner;
              CircleImageView icon;


              name = convertview.findViewById(R.id.tv_pigeon_name_show_id);
              payDegree = convertview.findViewById(R.id.tv_payDegree_id);
              ring = convertview.findViewById(R.id.tv_pigeon_ring_number_show_id);
              f_ring = convertview.findViewById(R.id.tv_pigeon_father_ring_number_show_id);
              m_ring = convertview.findViewById(R.id.tv_pigeon_mother_ring_number_show_id);
              gender = convertview.findViewById(R.id.tv_pigeon_gender_show_id);
              type = convertview.findViewById(R.id.tv_pigeon_types_name_show_id);
              details = convertview.findViewById(R.id.tv_pigeon_details_show_id);
              cancel = convertview.findViewById(R.id.btn_cancel_pigeon_show_dialog_id);
              icon = convertview.findViewById(R.id.iv_pigeon_show_and_update_image_id);
              owner = convertview.findViewById(R.id.btn_edit_pigeon_id);
              owner.setVisibility(View.VISIBLE);


                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                name.setText(birds.getName());
                ring.setText(birds.getRing());
                f_ring.setText(birds.getFather_ring());
                m_ring.setText(birds.getMother_ring());
                type.setText(context.getResources().getString(R.string.type_show)+" "+birds.getType());
                gender.setText(context.getResources().getString(R.string.gender)+" "+birds.getGender());
                details.setText(context.getResources().getString(R.string.details)+" "+birds.getDetails());
                owner.setText(R.string.owner);
                Toast.makeText(context, birds.getUserId(), Toast.LENGTH_SHORT).show();

                birdOwner(birds.getUserId());
                icon.setImageBitmap(StringToBitMap(birds.getImageEye()));
                cancel.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v)
                  {
                      alertDialog.dismiss();
                  }
              });


                owner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        final View view = LayoutInflater.from(context).inflate(R.layout.layout_owner_details, null);

                        builder.setView(view);
                        builder.setCancelable(false);
                        builder.create();

                        final AlertDialog dialog = builder.create();
                        dialog .show();


                        final TextView name,email,phone,city,address;
                        final CircleImageView profile;
                        final Button close;


                        name = view.findViewById(R.id.pigeon_owner_name_show_id);
                        email = view.findViewById(R.id.pigeon_owner_email_show_id);
                        phone = view.findViewById(R.id.pigeon_owner_phone_show_id);
                        city = view.findViewById(R.id.pigeon_owner_city_show_id);
                        address = view.findViewById(R.id.pigeon_owner_address_show_id);
                        profile = view.findViewById(R.id.pigeon_owner_profile_show_id);
                        close = view.findViewById(R.id.btn_owner_close_id);



                        name.setText(birdOwner.getName().toString());
                        email.setText(birdOwner.getEmail());
                        phone.setText(birdOwner.getNumber()); // = view.findViewById(R.id.pigeon_owner_phone_show_id);
                        city.setText(birdOwner.getCity()); // = view.findViewById(R.id.pigeon_owner_city_show_id);
                        address.setText(birdOwner.getAddress()); // = view.findViewById(R.id.pigeon_owner_address_show_id);
                        profile.setImageBitmap(StringToBitMap(birdOwner.getImage())); // = view.findViewById(R.id.pigeon_owner_profile_show_id);

                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });


              payDegree.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      context.startActivity(new Intent(context, PayDegreeActivity.class).putExtra("sale",birds.getSale()).putExtra("ring", birds.getRing()).putExtra("f_ring", birds.getFather_ring()).putExtra("m_ring", birds.getMother_ring()).putExtra("race",birds.getRecentRace()));

                      //context.startActivity(new Intent(context, PayDegreeActivity.class);
                      //context.startActivity(new Intent(context, PayDegreeActivity.class).putExtra("m_ring", birds.getMother_ring()));

                  }
              });


            }
        });

    }

    public void birdOwner(String userId)
    {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        databaseReference.child(userId).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //birdsList.clear();

                //   Log.d("This is Test : ", "have data check name : " + donorOnline.getName());
                //dataSnapshot.getChildrenCount();

                birdOwner = dataSnapshot.getValue(Member.class);




                // = view.findViewById(R.id.btn_owner_close_id);

//                                if (dataSnapshot != null)
//                                {
//
//                                    for (DataSnapshot ds : dataSnapshot.getChildren())
//                                    {
//                                        Birds birds = ds.getValue(Birds.class);
//                                        allBirds.add(birds);
//                                    }
//
//                                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {


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




    @Override
    public int getItemCount()
    {
        return birdsList.size();
    }

    public void setList(List<Birds> birds) {
        this.birdsList = birds;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        //private CircleImageView profile;
        private TextView name,type,rating,race;
        private Button details;
        private CircleImageView icon;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            icon = itemView.findViewById(R.id.ivc_model_profile_id);
            type = itemView.findViewById(R.id.tv_model_birds_type_id);
            name = itemView.findViewById(R.id.tv_model_birds_name_id);
            //rating = itemView.findViewById(R.id.tv_model_rating_id);
            //profile = itemView.findViewById(R.id.ivc_menu_profile_id);
            details = itemView.findViewById(R.id.btn_model_details_id);
            race = itemView.findViewById(R.id.pigeon_recent_result);
            //fav = itemView.findViewById(R.id.iv_model_favourite_id);
        }
    }
}
