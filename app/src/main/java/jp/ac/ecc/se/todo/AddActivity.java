package jp.ac.ecc.se.todo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    final int CAMERA_RESULT = 100;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        SharedPreferences pref = getSharedPreferences("SaveData", Context.MODE_PRIVATE);

        EditText TitleText = findViewById(R.id.TitleText);
        EditText MultiText = findViewById(R.id.MultiText);

        Button CameraButton = findViewById(R.id.CameraButton);
        Button CreateButton = findViewById(R.id.CreateButton);

        ImageView imageView = findViewById(R.id.imageView);

        CameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timestamp = new SimpleDateFormat("yyyymmdd_HHmmss").format(new Date());
                String fileName = "Todo_" + timestamp + "_.jpg";

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent,CAMERA_RESULT);
            }
        });

        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(TitleText.getText().toString(),TitleText.getText().toString());
                editor.putString(MultiText.getText().toString(),MultiText.getText().toString());
                editor.apply();
                Intent intent = new Intent(getApplicationContext(),ListActivity.class);
                intent.putExtra("title",TitleText.getText().toString());
                intent.putExtra("content",MultiText.getText().toString());
                intent.putExtra("image",imageUri);
                startActivity(intent);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK){
            ImageView cameraImage = findViewById(R.id.imageView);
            cameraImage.setImageURI(imageUri);
        }
    }
}