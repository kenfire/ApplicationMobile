package fr.hosomi.serverlook;

import android.content.Context;
import android.widget.ArrayAdapter;

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

}
