package com.example.memo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditMemoActivity extends AppCompatActivity {

    EditText editText;

    Button button_save;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmemo);

        editText = (EditText) findViewById(R.id.editText);
        button_save = (Button) findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = editText.getText().toString();
                save(inputText);

                Toast.makeText(EditMemoActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    public void save (String inputText) {
        File file;
        BufferedWriter writer = null;

        try {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss"); // 会导致一分钟之内的文件被覆盖

            file = new File("/data/data/com.example.memo/files/" + dateFormat.format(date));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(inputText);

            // test
            Log.d("EditMemoActivity", "succeeded");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
