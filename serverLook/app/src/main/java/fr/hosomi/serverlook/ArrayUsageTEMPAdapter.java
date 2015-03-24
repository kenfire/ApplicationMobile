package fr.hosomi.serverlook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kenzo on 10/03/2015.
 */
public class ArrayUsageTEMPAdapter extends ArrayAdapter<TEMP> {
    private ArrayList<TEMP> objets;

    public ArrayUsageTEMPAdapter(Context context, int textViewResourceId, ArrayList<TEMP> objects) {
        super(context, textViewResourceId, objects);
        this.objets = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Vue à mettre à jour
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_perso, null);
        }

        TEMP fcourant = objets.get(position);
        if (fcourant != null) {
            TextView tv_sdate = (TextView) v.findViewById(R.id.sdate);
            TextView tv_temp = (TextView) v.findViewById(R.id.temp);
            TextView tv_nomBaie = (TextView) v.findViewById(R.id.nomBaie);
            ImageView icone = (ImageView) v.findViewById(R.id.thermo);
            if (tv_sdate != null){
                tv_sdate.setText(fcourant.sdate);
            }
            if (tv_temp != null){
                tv_temp.setText(fcourant.temp);
            }
            if (tv_nomBaie != null){
                tv_nomBaie.setText(fcourant.nomBaie);
            }
            if (icone != null){
                icone.setImageResource(R.drawable.thermo);
            }
        }
        return v;
    }


}
