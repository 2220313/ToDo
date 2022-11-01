package jp.ac.ecc.se.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    ArrayAdapter<String>  adapter;
    List<String> titles;
    int n = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        SharedPreferences pref = getSharedPreferences("SaveData", Context.MODE_PRIVATE);

        ListView listView = (ListView) findViewById(R.id.ToDoList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        titles = new ArrayList<String>();

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");

        Uri imageUri = (intent != null)?intent.getParcelableExtra("image"):null;

        if (title != null) adapter.add(title);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),DedailActivity.class);
                intent.putExtra("title","title".toString());
                intent.putExtra("content",content);
                intent.putExtra("image",imageUri);
                startActivity(intent);
            }
        });

        Button newButton = findViewById(R.id.NewButton);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
        n++;
    }
}