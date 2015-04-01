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
public class ArrayUsageDDAdapter extends ArrayAdapter<UsageDD> {
    private ArrayList<UsageDD> objets;

    public ArrayUsageDDAdapter(Context context, int textViewResourceId, ArrayList<UsageDD> objects) {
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

        UsageDD fcourant = objets.get(position);
        if (fcourant != null) {
            TextView tv_sdate = (TextView) v.findViewById(R.id.sdate);
            TextView tv_usage = (TextView) v.findViewById(R.id.usage);
            TextView tv_capacite = (TextView) v.findViewById(R.id.capacite);
            TextView tv_utilisation = (TextView) v.findViewById(R.id.utilisation);
            if (tv_sdate != null){
                tv_sdate.setText(fcourant.sdate);
            }
            if (tv_usage != null){
                tv_usage.setText(fcourant.usage+"°C");
            }
            if (tv_capacite != null){
                tv_capacite.setText(fcourant.capacite);
            }
            if (tv_utilisation != null){
                tv_utilisation.setText(fcourant.utilisation);
            }
        }
        return v;
    }
}
