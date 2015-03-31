package fr.hosomi.serverlook;

import android.content.Context;
import android.widget.ArrayAdapter;

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
}
