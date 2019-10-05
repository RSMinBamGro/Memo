package com.example.memo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView_memos = null;

    MemoAdapter adapter = null;

    Button button_title_edit = null;

    public MainActivity () {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        recyclerView_memos = (RecyclerView) findViewById(R.id.recyclerView_memos);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_memos.setLayoutManager(layoutManager);

        adapter = new MemoAdapter("/data/data/com.example.memo/files");
        recyclerView_memos.setAdapter(adapter);


        button_title_edit = (Button) findViewById(R.id.button_title_edit);
        button_title_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_toEditMemoActivity = new Intent(MainActivity.this, EditMemoActivity.class);
                startActivity(intent_toEditMemoActivity);
            }
        });
    }

    @Override
    public void onResume () {
        super.onResume();
        adapter.update("/data/data/com.example.memo/files");
    }
}
