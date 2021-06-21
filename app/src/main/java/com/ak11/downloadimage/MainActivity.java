package com.ak11.downloadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDownload = findViewById(R.id.btnDownloadImage);
        imageView = findViewById(R.id.imageView);

        btnDownload.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {

        DownloadImageTask downloadImageTask = new DownloadImageTask(this);
        downloadImageTask.execute("https://media.distractify.com/brand-img/DzXomlJSH/0x0/lana-rhoades-1622663798245.png");

    }

    private class DownloadImageTask extends AsyncTask < String,Void,Bitmap >{


        ProgressDialog progressDialog;
        Context context;

        public DownloadImageTask(Context context){
            this.context = context;
            progressDialog = new ProgressDialog(context);



        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Image is being downloaded");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            imageView.setImageBitmap(bitmap);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }


            super.onPostExecute(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String stringUrl = strings[0];
            Bitmap bitmap = null;
            try{
                URL url = new URL(stringUrl);
                InputStream inputStream = url.openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);


            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
            }


            return bitmap;
        }
    }



}