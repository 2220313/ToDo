package jp.ac.ecc.se.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DedailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dedail);

        SharedPreferences pref = getSharedPreferences("SaveData", Context.MODE_PRIVATE);

        TextView TitleView = findViewById(R.id.TitleView);
        TextView MultiView = findViewById(R.id.MultiView);

        Button compButton = findViewById(R.id.CompletedButton);

        ImageView imageView = findViewById(R.id.imageView2);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        Intent i = getIntent();
        Uri imageUri = (i != null)?i.getParcelableExtra("image"):null;

        TitleView.setText(title);
        MultiView.setText(content);
        imageView.setImageURI(imageUri);

        compButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(title,null);
                editor.putString(content,null);
                editor.apply();
                TitleView.setText(null);
                MultiView.setText(null);
                imageView.setImageURI(null);
                Intent intent = new Intent(getApplicationContext(),ListActivity.class);
                startActivity(intent);
            }
        });
    }
}