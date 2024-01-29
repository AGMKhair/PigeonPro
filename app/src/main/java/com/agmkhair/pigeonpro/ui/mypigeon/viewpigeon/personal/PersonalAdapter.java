package com.agmkhair.pigeonpro.ui.mypigeon.viewpigeon.personal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.agmkhair.pigeonpro.R;
import com.agmkhair.pigeonpro.ui.model.Birds;
import java.util.List;


public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.MyViewHolder> {


    private ItemClickLitener itemClickLitener;
    private List<Birds> birdsList;
    private Context context;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Birds");

    // DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Birds");


    public PersonalAdapter(List<Birds> birdsList, Context context,ItemClickLitener itemClickLitener) {
        this.birdsList = birdsList;
        this.context = context;
        this.itemClickLitener = itemClickLitener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.model_pegion,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position)
    {

        final Birds birds = birdsList.get(position);
        holder.imageView.setImageBitmap(StringToBitMap(birds.getImageEye()));
      //  holder.btn.
        holder.deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupMenu(holder, birds);
            }
        });


    }



    void popupMenu(MyViewHolder holder, final Birds birds){

        PopupMenu popup = new PopupMenu(context, holder.deleteBTN);
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            public boolean onMenuItemClick(MenuItem item) {
                //Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

               switch (item.getItemId())
               {
                   case R.id.deleteMenuId:

                       AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                       dialog.setTitle("Delete Message !!");
                       dialog.setMessage("Are you sure to delete it ?");

                       dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                               ref.child(birds.getId()).removeValue();

                               dialog.dismiss();
                           }
                       });

                       dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                               dialog.dismiss();
                           }
                       });

                       dialog.show();

                       break;

                   case R.id.saleMenuId:
                       ref.child(birds.getId()).child("sale").setValue("done");

                       break;
               }


                return true;
            }
        });

        popup.show();

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
    public int getItemCount() {
        return birdsList.size();
    }

    public void setList(List<Birds> birds) {
        this.birdsList = birds;
        notifyDataSetChanged();
    }





















    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        Button btn;
        ImageView imageView;
        ImageButton deleteBTN;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            btn = itemView.findViewById(R.id.btn_model_personal_pigeon_view_id);
            deleteBTN = itemView.findViewById(R.id.btnI_delete_id);
            imageView = itemView.findViewById(R.id.iv_model_personal_pigeon_image_id);
            btn.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(itemClickLitener != null)
            {
                itemClickLitener.onClick(v,getAdapterPosition(),birdsList);
            }

        }
    }

}
