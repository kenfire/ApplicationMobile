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
}
