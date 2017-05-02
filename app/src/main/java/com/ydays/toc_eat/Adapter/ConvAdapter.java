package com.ydays.toc_eat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ydays.toc_eat.Class.Conversation;
import com.ydays.toc_eat.toc_eat.R;

import java.util.ArrayList;

/**
 * Created by clemb on 05/04/2017.
 */

public class ConvAdapter extends BaseAdapter{

    ArrayList<Conversation> lesConv;
    private static LayoutInflater inflater=null;
    Context context;

    public ConvAdapter(ArrayList<Conversation> convs, Context c) {
        lesConv = convs;

        context=c;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lesConv.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView;
        rowView = inflater.inflate(R.layout.conv_listview_row, null);

        ImageView img = (ImageView) rowView.findViewById(R.id.Image);
        //img.setImageResource(R.drawable.party);

        TextView tvTitre=(TextView) rowView.findViewById(R.id.title);
        tvTitre.setText(lesConv.get(position).title);

        TextView tvVille=(TextView) rowView.findViewById(R.id.receverName);
        tvVille.setText(lesConv.get(position).recipient_id);

        TextView tvDate=(TextView) rowView.findViewById(R.id.update);
        tvDate.setText(lesConv.get(position).updated_at);

        return rowView;
    }

}
