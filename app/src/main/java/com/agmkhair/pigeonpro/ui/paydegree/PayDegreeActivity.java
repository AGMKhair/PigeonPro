package com.agmkhair.pigeonpro.ui.paydegree;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.agmkhair.pigeonpro.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.agmkhair.pigeonpro.ui.model.Birds;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.agmkhair.pigeonpro.ui.Variable.allBirds;
import static com.agmkhair.pigeonpro.ui.Variable.birdOwner;

public class PayDegreeActivity extends AppCompatActivity {

    String sale, ring, raceing, f_ring, m_ring;
    Birds ring_2, ring_3, ring_4 = null, ring_5 = null, ring_6 = null, ring_7 = null, ring_8 = null, ring_9 = null, ring_10 = null, ring_11 = null, ring_12 = null, ring_13 = null, ring_14 = null, ring_15 = null;
    String ring_id_2, ring_id_3 = null, ring_id_4 = null, ring_id_5 = null, ring_id_6 = null, ring_id_7 = null, ring_id_8 = null, ring_id_9 = null, ring_id_10 = null, ring_id_11 = null, ring_id_12 = null, ring_id_13 = null, ring_id_14 = null, ring_id_15 = null;
    File filePath;
    //String ff_ring, fm_ring, fff_ring, ffm_ring, fmf_ring ;
    //String m_m_ring,

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pay_degree);

        initFunction();
        initViewPage();

        createPdf();
        readPDF();

    }

    private void initViewPage() {


        if (f_ring != null) {
            Birds birds;
            birds = SearchData(f_ring);

            if (birds != null) {
                if (!birds.getFather_ring().equals(null)) {
                    //ring_4 = birds;
                    PayDegreeSearchStep4(birds.getFather_ring());
                }

                if (!birds.getMother_ring().equals(null)) {
                    //ring_5 = birds;
                    PayDegreeSearchStep5(birds.getMother_ring());
                }
                ring_2 = birds;

            } else {
                ring_id_2 = f_ring;
            }

        }

        if (m_ring != null) {

            Birds birds;
            birds = SearchData(m_ring);

            if (birds != null) {


                if (!birds.getFather_ring().equals(null)) {
                    //               ring_6 = birds;
                    PayDegreeSearchStep6(birds.getFather_ring());
                }

                if (!birds.getMother_ring().equals(null)) {
                    //             ring_7 = birds;
                    PayDegreeSearchStep7(birds.getMother_ring());
                }
                ring_3 = birds;
            } else {
                ring_id_3 = m_ring;
            }
        }

    }

    private Birds SearchData(String ring) {

        Birds birds = null;

        Toast.makeText(this, ring, Toast.LENGTH_SHORT).show();

        for (int i = 0; allBirds.size() > i; i++) {

            if (allBirds.get(i).getRing().equals(ring)) {
                birds = allBirds.get(i);

                return birds;
            }
        }


        return birds;
    }

    private void PayDegreeSearchStep7(String mother_ring) {
        Birds birds;
        birds = SearchData(mother_ring);

        if (birds != null) {
            if (!birds.getFather_ring().equals(null)) {
                //ring_14 = birds;
                PayDegreeSearchStep14(birds.getFather_ring());
            }

            if (!birds.getMother_ring().equals(null)) {
                //ring_15 = birds;
                PayDegreeSearchStep15(birds.getMother_ring());
            }
        }
        ring_id_7 = mother_ring;
    }

    private void PayDegreeSearchStep15(String mother_ring) {

        Birds birds;
        birds = SearchData(mother_ring);

        if (birds != null) {
            ring_15 = birds;
            //PayDegreeSearchStep15(birds.getMother_ring());
        }
        ring_id_15 = mother_ring;
    }

    private void PayDegreeSearchStep14(String father_ring) {
        Birds birds;
        birds = SearchData(father_ring);

        if (birds != null) {
            ring_14 = birds;
            //PayDegreeSearchStep14(birds.getFather_ring());
        }
        ring_id_14 = father_ring;
    }

    private void PayDegreeSearchStep6(String father_ring) {

        Birds birds;
        birds = SearchData(father_ring);
        if (birds != null) {

            if (!birds.getFather_ring().equals(null)) {
                //ring_12 = birds;
                PayDegreeSearchStep12(birds.getFather_ring());
            }

            if (!birds.getMother_ring().equals(null)) {
                //ring_ = birds;
                PayDegreeSearchStep13(birds.getMother_ring());
            }
        }
        ring_id_6 = father_ring;

    }

    private void PayDegreeSearchStep12(String father_ring) {


        Birds birds;
        birds = SearchData(father_ring);

        if (birds != null) {
            ring_12 = birds;
            //PayDegreeSearchStep15(birds.getMother_ring());
        }
        ring_id_12 = father_ring;
    }

    private void PayDegreeSearchStep13(String mother_ring) {

        Birds birds;
        birds = SearchData(mother_ring);

        if (birds != null) {
            ring_13 = birds;
            //PayDegreeSearchStep15(birds.getMother_ring());
        }
        ring_id_13 = mother_ring;
    }

    private void PayDegreeSearchStep5(String mother_ring) {
        Birds birds;
        birds = SearchData(mother_ring);

        if (birds != null) {
            if (!birds.getFather_ring().equals(null)) {
                //ring_10 = birds;
                PayDegreeSearchStep10(birds.getFather_ring());
            }

            if (!birds.getMother_ring().equals(null)) {
                //ring_11 = birds;
                PayDegreeSearchStep11(birds.getMother_ring());
            }
            ring_5 = birds;
        }
        ring_id_5 = mother_ring;

    }

    private void PayDegreeSearchStep11(String mother_ring) {

        Birds birds;
        birds = SearchData(mother_ring);

        if (birds != null) {
            ring_11 = birds;
            //PayDegreeSearchStep15(birds.getMother_ring());
        }
        ring_id_11 = mother_ring;
    }

    private void PayDegreeSearchStep10(String father_ring) {

        Birds birds;
        birds = SearchData(father_ring);

        if (birds != null) {
            ring_10 = birds;
            //PayDegreeSearchStep15(birds.getMother_ring());
        }
        ring_id_10 = father_ring;
    }

    private void PayDegreeSearchStep4(String father_ring) {

        Birds birds;
        birds = SearchData(father_ring);

        if (birds != null) {
            if (!birds.getFather_ring().equals(null)) {
                //ring_8 = birds;
                PayDegreeSearchStep8(birds.getFather_ring());
            }

            if (!birds.getMother_ring().equals(null)) {
                //ring_9 = birds;
                PayDegreeSearchStep9(birds.getMother_ring());
            }
            ring_4 = birds;
        }
        ring_id_4 = father_ring;
    }

    private void PayDegreeSearchStep9(String mother_ring) {

        Birds birds;
        birds = SearchData(mother_ring);

        if (birds != null) {
            ring_9 = birds;
            //PayDegreeSearchStep15(birds.getMother_ring());
        }
        ring_id_9 = mother_ring;

    }

    private void PayDegreeSearchStep8(String father_ring) {

        Birds birds;
        birds = SearchData(father_ring);

        if (birds != null) {
            ring_8 = birds;
            //PayDegreeSearchStep15(birds.getMother_ring());
        }
        ring_id_8 = father_ring;
    }

    private void initFunction() {
        ring = getIntent().getStringExtra("ring");
        sale = getIntent().getStringExtra("sale");
        raceing = getIntent().getStringExtra("race");
        f_ring = getIntent().getStringExtra("f_ring");
        m_ring = getIntent().getStringExtra("m_ring");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createPdf() {

        // create a new document
        PdfDocument document = null;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Do the file write
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                document = new PdfDocument();
            }
        } else {
            // Request permission from the user
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }

        // crate a page description
        PdfDocument.PageInfo pageInfo = null;


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Do the file write
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
            }
        } else {
            // Request permission from the user
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }


        // start a page
        PdfDocument.Page page = null;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Do the file write
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                page = document.startPage(pageInfo);
            }
        } else {
            // Request permission from the user
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }


        Canvas canvas = null;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Do the file write
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                canvas = page.getCanvas();
            }
        } else {
            // Request permission from the user
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }


        Paint paint = new Paint();


        Bitmap sourceBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.trans_logo_water_marks);
        Bitmap b = Bitmap.createScaledBitmap(sourceBitmap, 300, 400, true);
        // paint.setColor(Color.TRANSPARENT);
        canvas.drawBitmap(b, 150, 220, null);



     /*   paint.setColor(Color.RED);
        canvas.drawCircle(50, 50, 30, paint);
*/
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.pigeon_pay_degree_icon);
        Bitmap bitmap = Bitmap.createScaledBitmap(icon, 60, 60, true);
        // paint.setColor(Color.TRANSPARENT);
        canvas.drawBitmap(bitmap, 20, 20, null);

     /*   paint.setColor(Color.BLACK);
        canvas.drawText(sometext, 280, 50, paint);
*/

        //Breeder info
        //paint.setColor(getColor(R.color.company));
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        canvas.drawText(birdOwner.getName(), 440, 20, paint);
        canvas.drawText(birdOwner.getId(), 440, 30, paint);
        canvas.drawText(birdOwner.getAddress(), 440, 40, paint);

        if(!sale.isEmpty())
        {
            paint.setColor(Color.RED);
            paint.setTextSize(20.0f);
            canvas.drawText(birdOwner.getName() +" Sale This Bird", 270, 750, paint);
        }


        //step1
        paint.setColor(Color.BLUE);
        canvas.drawText(ring, 40, 421, paint);

        paint.setColor(Color.BLUE);
        canvas.drawText(raceing, 40, 432, paint);


        // canvas.drawPicture(getResources().getDrawable(R.drawable.pegion_profile,));


        //for line
        paint.setColor(Color.BLUE);
        canvas.drawLine(150, 300, 150, 420, paint);
        paint.setColor(Color.MAGENTA);
        canvas.drawLine(150, 420, 150, 540, paint);


        //bam pashe  majhe line
        canvas.drawLine(140, 421, 150, 421, paint);
        paint.setColor(Color.BLUE);
        if (f_ring != null) {

            //1.1
            //pashe  dane line upore
            canvas.drawLine(150, 300, 160, 300, paint);
            paint.setColor(Color.BLUE);
            canvas.drawText(f_ring, 165, 300, paint);


            if (ring_id_4 != null) {
                canvas.drawText(ring_id_4, 165, 310, paint);

                if (ring_4 != null)
                {
                    paint.setColor(Color.BLUE);
                    canvas.drawText(ring_4.getRecentRace(), 165, 320, paint);
                    canvas.drawText(ring_4.getName(), 165, 310, paint);
                }

            }
        }

        if (m_ring != null) {

            //1.2
            //pashe dane line niche
            paint.setColor(Color.MAGENTA);
            canvas.drawLine(150, 540, 160, 540, paint);
            paint.setColor(Color.MAGENTA);

            canvas.drawText(m_ring, 165, 540, paint);
            if (ring_2 != null) {
                canvas.drawText(ring_2.getName(), 165, 550, paint);
                paint.setColor(Color.MAGENTA);
                canvas.drawText(ring_2.getRecentRace(), 165, 560, paint);
            }
        }


        /* canvas.drawRect(200, 20, 500, 100, paint); */

        //  Bitmap b = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)




        /*  Bitmap animation = BitmapFactory.decodeResource(getResources(), R.drawable.pegion_profile, mBitmapOptions); //Get a bitmap from a image file

        // Create a bitmap for the part of the screen that needs updating.
        Bitmap bitmap = Bitmap.createBitmap(animation.getWidth(), animation.getHeight(), BITMAP_CONFIG);
        bitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        //Canvas canvas = new Canvas(bitmap);
        */

        //bitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);


        if (ring_id_4 != null) {
            //1.1.1
            paint.setColor(Color.BLUE);
            //line
            canvas.drawLine(280, 250, 280, 300, paint);
            paint.setColor(Color.MAGENTA);
            canvas.drawLine(280, 300, 280, 350, paint);
            //pashe  dane line upore
            paint.setColor(Color.BLUE);
            canvas.drawLine(280, 250, 290, 250, paint);
            paint.setColor(Color.BLUE);

            canvas.drawText(ring_id_4, 290, 250, paint);

            if (ring_4 != null) {

                canvas.drawText(ring_4.getName(), 290, 260, paint);
                paint.setColor(Color.BLUE);
                canvas.drawText(ring_4.getRecentRace(), 290, 270, paint);
            }
        }
        if (ring_id_5 != null) {

            //1.1.2
            //pashe dane niche
            paint.setColor(Color.MAGENTA);
            canvas.drawLine(280, 350, 290, 350, paint);
            canvas.drawText(ring_id_5, 290, 355, paint);

            if (ring_5 != null) {

                canvas.drawText(ring_5.getName(), 290, 365, paint);
                paint.setColor(Color.MAGENTA);
                canvas.drawText(ring_5.getRecentRace(), 290, 375, paint);
            }

        }
        if (ring_id_6 != null) {
//1.2
            //1.2.1
            //line
            paint.setColor(Color.BLUE);
            canvas.drawLine(280, 500, 280, 550, paint);
            paint.setColor(Color.MAGENTA);
            canvas.drawLine(280, 550, 280, 600, paint);
            //upore
            paint.setColor(Color.BLUE);
            canvas.drawLine(280, 500, 290, 500, paint);
            paint.setColor(Color.BLUE);

            canvas.drawText(ring_id_6, 290, 500, paint);


            if (ring_6 != null) {

                canvas.drawText(ring_6.getName(), 290, 510, paint);
                canvas.drawText(ring_6.getRecentRace(), 290, 520, paint);

            }
        }
        if (ring_id_7 != null) {

            //1.2.2
            //niche
            paint.setColor(Color.MAGENTA);
            canvas.drawLine(280, 600, 290, 600, paint);
            paint.setColor(Color.MAGENTA);
            canvas.drawText(ring_id_7, 290, 600, paint);


            if (ring_7 != null) {
                canvas.drawText(ring_7.getName(), 290, 610, paint);
                canvas.drawText(ring_7.getRecentRace(), 290, 620, paint);
            }
        }
        if (ring_id_8 != null) {
//1.1.1.1
            //line
            paint.setColor(Color.BLUE);
            canvas.drawLine(410, 190, 410, 240, paint);
            paint.setColor(Color.MAGENTA);
            canvas.drawLine(410, 240, 410, 290, paint);
            //pashe  dane line upore
            paint.setColor(Color.BLUE);
            canvas.drawLine(410, 190, 420, 190, paint);
            paint.setColor(Color.BLUE);

            canvas.drawText(ring_id_8, 425, 190, paint);


            if (ring_8 != null) {

                canvas.drawText(ring_8.getName(), 425, 200, paint);
                paint.setColor(Color.BLUE);
                canvas.drawText(ring_8.getRecentRace(), 425, 210, paint);
            }
        }
        if (ring_id_9 != null) {
            //1.1.1.2
            //pashe  dane line niche
            paint.setColor(Color.MAGENTA);
            canvas.drawLine(410, 290, 420, 290, paint);
            paint.setColor(Color.MAGENTA);
            canvas.drawText(ring_id_9, 425, 280, paint);


            if (ring_9 != null) {


                canvas.drawText(ring_9.getName(), 425, 290, paint);
                paint.setColor(Color.MAGENTA);
                canvas.drawText(ring_9.getRecentRace(), 425, 300, paint);
            }

        }
        if (ring_id_10 != null) {
            //1.1.2.1
            //line
            paint.setColor(Color.BLUE);
            canvas.drawLine(410, 315, 410, 365, paint);
            paint.setColor(Color.MAGENTA);
            canvas.drawLine(410, 365, 410, 415, paint);
            //pashe  dane line upore
            paint.setColor(Color.BLUE);
            canvas.drawLine(410, 315, 420, 315, paint);
            paint.setColor(Color.BLUE);
            canvas.drawText(ring_id_10, 425, 315, paint);


            if (ring_10 != null) {

                canvas.drawText(ring_10.getName(), 425, 325, paint);
                paint.setColor(Color.BLUE);
                canvas.drawText(ring_10.getRecentRace(), 425, 335, paint);
            }
        }
        if (ring_id_10 != null) {


            //1.1.2.2
            //niche
            paint.setColor(Color.MAGENTA);
            canvas.drawLine(410, 415, 420, 415, paint);
            paint.setColor(Color.MAGENTA);

            canvas.drawText(ring_id_11, 425, 410, paint);

            if (ring_11 != null) {
                canvas.drawText(ring_11.getName(), 425, 420, paint);
                paint.setColor(Color.MAGENTA);
                canvas.drawText(ring_11.getRecentRace(), 425, 430, paint);
            }
        }


        if (ring_id_12 != null) {

            //1.2.1.1
            //line
            paint.setColor(Color.BLUE);
            canvas.drawLine(410, 440, 410, 490, paint);
            paint.setColor(Color.MAGENTA);
            canvas.drawLine(410, 490, 410, 540, paint);
            //pashe  dane line upore
            paint.setColor(Color.BLUE);
            canvas.drawLine(410, 440, 420, 440, paint);
            canvas.drawText(ring_id_12, 425, 450, paint);


            if (ring_12 != null) {


                canvas.drawText(ring_12.getName(), 425, 460, paint);
                paint.setColor(Color.BLUE);
                canvas.drawText(ring_12.getRecentRace(), 425, 470, paint);
            }

        }

        if (ring_id_13 != null) {

            //1.2.1.2
            //pashe  dane line niche
            paint.setColor(Color.MAGENTA);
            canvas.drawLine(410, 540, 420, 540, paint);
            canvas.drawText(ring_id_13, 425, 520, paint);

            if (ring_13 != null) {

                canvas.drawText(ring_13.getName(), 425, 530, paint);
                canvas.drawText(ring_13.getRecentRace(), 425, 540, paint);

            }
        }

        if (ring_id_14 != null) {

            //1.2.2.1
            //line
            paint.setColor(Color.BLUE);
            canvas.drawLine(410, 570, 410, 620, paint);
            paint.setColor(Color.MAGENTA);
            canvas.drawLine(410, 620, 410, 670, paint);
            //upore
            paint.setColor(Color.BLUE);
            canvas.drawLine(410, 570, 420, 570, paint);

            canvas.drawText(ring_id_14, 425, 570, paint);


            if (ring_14 != null) {
                canvas.drawText(ring_14.getName(), 425, 580, paint);
                canvas.drawText(ring_14.getRecentRace(), 425, 590, paint);
            }
        }
        if (ring_id_15 != null) {
            //niche
            paint.setColor(Color.MAGENTA);
            canvas.drawLine(410, 670, 420, 670, paint);
            canvas.drawText(ring_id_15, 425, 670, paint);


            if (ring_15 != null) {
                canvas.drawText(ring_15.getName(), 425, 680, paint);
                canvas.drawText(ring_15.getRecentRace(), 425, 690, paint);
            }
        }
        /*   paint.setColor(Color.YELLOW);
        canvas.drawRect(33, 33, 77, 60, paint );*/


        // todo:  Bottom Line Design

        paint.setStrokeWidth(5);
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        canvas.drawLine(20, 780, 575, 790, paint);

        /*paint.setColor(Color.BLUE);*/


        /// Bottom Info in Pigeon Pro

        //Bitmap ssbd_logo = ;
        Bitmap ssbd_logo = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ssbd), 25, 25, true);// paint.setColor(Color.TRANSPARENT);

        canvas.drawBitmap(ssbd_logo, 365, 803, null);
        canvas.drawText("spontbd.com", 400, 810, paint);
        canvas.drawText("fb/@spontbd", 400, 820, paint);
        canvas.drawText("spontbd@gmail.com", 400, 830, paint);

        Bitmap youtube_logo = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ssbd), 25, 25, true);
        // paint.setColor(Color.TRANSPARENT);

        canvas.drawBitmap(youtube_logo, 110, 803, null);

        canvas.drawText("youtube.com/pigeonpettips", 150, 810, paint);
        canvas.drawText("fb/@pigeontips", 150, 820, paint);
        //canvas.drawText("securesoftofficial@gmail.com", 400, 830, paint);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Do the file write
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                document.finishPage(page);
            }
        } else {

            // Request permission from the user
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }


        // draw text on the graphics object of the page

        // Create Page 2
        /*    pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 2).create();
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();

        paint = new Paint();
        paint.setColor(Color.BLUE);

        canvas.drawCircle(100, 100, 100, paint);
        document.finishPage(page);
*/

        // write the document content
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/SecureSoft/PDF/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path + "PayDegree" + ring + ".pdf";
        filePath = new File(targetPdf);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                document.writeTo(new FileOutputStream(filePath));
            }
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error " + e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        // close the document
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            document.close();
        }
    }

    private void readPDF() {
        PDFView pdfView = findViewById(R.id.pdfView);
        pdfView.fromFile(filePath)
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(true)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .pageFitPolicy(FitPolicy.WIDTH)
                .load();
    }

}
