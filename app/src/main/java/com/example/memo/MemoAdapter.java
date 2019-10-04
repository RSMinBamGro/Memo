package com.example.memo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Text;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_content;
        TextView textView_date;

        public ViewHolder (View view) {
            super(view);
            textView_content = (TextView) view.findViewById(R.id.textView_content);
            textView_date = (TextView) view.findViewById(R.id.textView_date);
        }
    }

    private List<File> fileList;

    public MemoAdapter (String path) {
        fileList = new ArrayList<>();

        File directory = new File(path);
        File[] files = directory.listFiles();
        for (File file : files) {
            fileList.add(file);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder (ViewHolder holder, int position) {
        File file = fileList.get(position);
        holder.textView_date.setText(file.getName());
        holder.textView_content.setText("");

        FileInputStream in = null;
        BufferedReader reader = null;

        try {
            in = holder.itemView.getContext().openFileInput(file.getName());
            reader = new BufferedReader(new InputStreamReader(in));
            holder.textView_content.setText(reader.readLine());
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
    }

    @Override
    public int getItemCount () {
        return fileList.size();
    }
}

