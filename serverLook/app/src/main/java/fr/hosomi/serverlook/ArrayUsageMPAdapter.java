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
public class ArrayUsageMPAdapter extends ArrayAdapter<UsageMP> {
    private ArrayList<UsageMP> objets;

    public ArrayUsageMPAdapter(Context context, int textViewResourceId, ArrayList<UsageMP> objects) {
        super(context, textViewResourceId, objects);
        this.objets = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Vue à mettre à jour
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.result_mp, null);
        }

        UsageMP fcourant = objets.get(position);
        if (fcourant != null) {
            TextView tv_sdate = (TextView) v.findViewById(R.id.sdate);
            TextView tv_procs = (TextView) v.findViewById(R.id.nbprocs);
            TextView tv_ump1 = (TextView) v.findViewById(R.id.ump1);
            TextView tv_ump2 = (TextView) v.findViewById(R.id.ump2);
            TextView tv_ump3 = (TextView) v.findViewById(R.id.ump3);
            TextView tv_ump4 = (TextView) v.findViewById(R.id.ump4);
            TextView tv_ump5 = (TextView) v.findViewById(R.id.ump5);
            TextView tv_ump6 = (TextView) v.findViewById(R.id.ump6);
            TextView tv_ump7 = (TextView) v.findViewById(R.id.ump7);
            TextView tv_ump8 = (TextView) v.findViewById(R.id.ump8);

            if (tv_sdate != null){
                tv_sdate.setText(fcourant.sdate);
            }
            if (tv_procs != null){
                tv_procs.setText(fcourant.nbprocs);
            }
            if (tv_ump1 != null){
                tv_ump1.setText(fcourant.ump1);
            }
            if (tv_ump2 != null){
                tv_ump2.setText(fcourant.ump2);
            }
            if (tv_ump3 != null){
                tv_ump3.setText(fcourant.ump3);
            }
            if (tv_ump4 != null){
                tv_ump4.setText(fcourant.ump4);
            }
            if (tv_ump5 != null){
                tv_ump5.setText(fcourant.ump5);
            }
            if (tv_ump6 != null){
                tv_ump6.setText(fcourant.ump6);
            }
            if (tv_ump7 != null){
                tv_ump7.setText(fcourant.ump7);
            }
            if (tv_ump8 != null){
                tv_ump8.setText(fcourant.ump8);
            }
        }
        return v;
    }
}
