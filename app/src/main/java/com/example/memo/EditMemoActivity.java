package com.example.memo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class EditMemoActivity extends AppCompatActivity {

    private String path;
    private File file = null;

    private EditText editText;
    private Button button_save;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmemo);

        path = getIntent().getStringExtra("path");
        file = new File(path + "/" + getIntent().getStringExtra("file"));

        editText = (EditText) findViewById(R.id.editText);
        button_save = (Button) findViewById(R.id.button_save);

        if (file.exists() && file.isFile())
            load();

        editText.requestFocus(); // 获取焦点

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
        BufferedWriter writer = null;

        try {
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

    public void load () {
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        // test
        Log.d("EditMemoActivity", "succeeded");

        editText.setText(content);
    }
}
