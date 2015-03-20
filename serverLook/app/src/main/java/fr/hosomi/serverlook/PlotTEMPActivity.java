package fr.hosomi.serverlook;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;


import java.util.Arrays;

/**
 * Created by Adrien on 10/03/2015.
 */
public class PlotTEMPActivity extends ActionBarActivity{

    static final private int MENU_PREFERENCES = Menu.FIRST;
    private XYPlot temperature_plot;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_temp_activity);

        temperature_plot = (XYPlot) findViewById(R.id.temperature_plot);

        Number[] time = {1, 8, 5, 2, 7, 4};
        Number[] temperature = {1, 3, 5, 7, 9, 15};

        XYSeries data = new SimpleXYSeries(
                Arrays.asList(temperature),
                Arrays.asList(time),
                "Température baie en °C");

        LineAndPointFormatter dataFormat = new LineAndPointFormatter();
        dataFormat.setPointLabelFormatter(new PointLabelFormatter());
        dataFormat.configure(getApplicationContext(),
                R.xml.line_point_formatter_with_data_format);

        temperature_plot.addSeries(data, dataFormat);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(0, MENU_PREFERENCES, Menu.NONE, R.string.action_settings);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


