package com.mahya.appsolution;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SplashActivity extends AppCompatActivity {

    private final int splash_display_length = 2000;
    private ProgressBar progressBarSplash;
    private ImageView imageViewSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageViewSplash = (ImageView) findViewById(R.id.imageViewSplash);
        progressBarSplash = (ProgressBar) findViewById(R.id.progressBarSplash);
        progressBarSplash.setVisibility(View.VISIBLE);

        //downloadImage("logo.JPG", imageViewSplash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(mainIntent);
                finish();
                progressBarSplash.setVisibility(View.INVISIBLE);
            }
        }, splash_display_length);

    }

    // download  image from fireBase
    private void downloadImage(String s, final ImageView iv) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://appsolution-669ae.appspot.com").child(s);
        final long ONE_MEGABYTE = 1024 * 1024;

        //download file as a byte array
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                iv.setImageBitmap(bitmap);
                if(bitmap.equals(null)) {
                    progressBarSplash.setVisibility(View.VISIBLE);
                    Toast.makeText(SplashActivity.this, "Connection Error!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
