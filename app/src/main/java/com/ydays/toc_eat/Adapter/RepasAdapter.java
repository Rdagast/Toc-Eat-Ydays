package com.ydays.toc_eat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ydays.toc_eat.Class.Repas;
import com.ydays.toc_eat.toc_eat.R;

import java.util.ArrayList;

/**
 * Created by benjaminthomas on 03/05/2017.
 */

public class RepasAdapter extends BaseAdapter {

    ArrayList<Repas> repas;
    private static LayoutInflater inflater=null;
    Context context;

    public RepasAdapter(ArrayList<Repas> r,Context c){
        this.repas = r;
        this.context = c;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return repas.size();
    }

    @Override
    public Object getItem(int position) {
        return repas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return repas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        rowView = inflater.inflate(R.layout.repas_listview_row, null);


        TextView tvTitre=(TextView) rowView.findViewById(R.id.tvRepasTitle);
        tvTitre.setText(repas.get(position).getTitle());

        TextView tvDesc=(TextView) rowView.findViewById(R.id.desc);
        tvDesc.setText(repas.get(position).getDescription());

        TextView tvPa=(TextView) rowView.findViewById(R.id.participation);
        tvPa.setText(repas.get(position).getParticipation().toString()+"€");

        return rowView;
    }
}
