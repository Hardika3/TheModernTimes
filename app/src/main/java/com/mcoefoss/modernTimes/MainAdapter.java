package com.mcoefoss.modernTimes;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private String[] numberWord;
    private Bitmap[] numberImages;
    public MainAdapter(Context c,String[] numberWord,Bitmap[] numberImages)
    {
        context = c;
        this.numberWord = numberWord;
        this.numberImages = numberImages;
    }

    @Override
    public int getCount() {
        return numberWord.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i,View convertView ,ViewGroup parent) {
        if(inflater==null)
        {
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.row_item,null);
        }
        ImageView imageView=convertView.findViewById(R.id.image_view);
        TextView textView=convertView.findViewById(R.id.text_view);

        imageView.setImageBitmap(numberImages[i]);
        textView.setText(numberWord[i]);
        return convertView;
    }
}
