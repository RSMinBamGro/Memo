package com.example.memo;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {

    private String path;

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

        this.path = path;

        updateAdapter();
    }

    @Override
    public ViewHolder onCreateViewHolder (final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memopreview, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.textView_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = fileList.get(holder.getAdapterPosition());
                Intent intent_toEditActivity = new Intent(parent.getContext(), EditMemoActivity.class);
                intent_toEditActivity.putExtra("path", path);
                intent_toEditActivity.putExtra("file", file.getName());
                parent.getContext().startActivity(intent_toEditActivity);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder (ViewHolder holder, int position) {
        File file = fileList.get(position);

        // 设置右侧文本框内容
        String s = file.getName();
        String[] ss = s.split("_");

        if (ss.length >= 3)
            holder.textView_date.setText(ss[0] + "/" + ss[1] + "/" + ss[2]);
        else holder.textView_date.setText(file.getName());

        // 设置左侧文本框内容
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

    public void updateAdapter () {
        fileList.clear();

        File directory = new File(path);
        File[] files = directory.listFiles();

        // test
        Integer i = files.length;
        Log.d("MemoAdapter", i.toString());

        for (File file : files) {
            fileList.add(file);
        }
    }

}

