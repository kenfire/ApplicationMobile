package fr.hosomi.serverlook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Created by Adrien on 10/03/2015.
 */
public class PlotTEMPActivity extends ActionBarActivity{

    static final private int MENU_PREFERENCES = Menu.FIRST;
    private XYPlot temperature_plot;
    private Intent intent;
    public int test;
    private ArrayList data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_temp_activity);

        this.intent = getIntent();
        this.data = this.intent.getParcelableArrayListExtra(StatsTEMPActivity.KEY_ARRAY_TEMP);

        temperature_plot = (XYPlot) findViewById(R.id.temperature_plot);
        Number[] time = new Number[data.size()];
        Number[] temperature = new Number[data.size()];
        TEMP tmp;
        Double lowestTemp = 0.;
        Double highestTemp = 0.;
        String startDate = "";
        String endDate = "";

        for (int i=0; i<data.size();i++) {

            tmp = (TEMP) data.get(i);
            time[i]=(double) ((double) i/ (double) data.size())*100.0;
            temperature[i]=Double.parseDouble(tmp.temp);

            if (i==0) {
                lowestTemp = Double.parseDouble(tmp.temp);
                highestTemp = Double.parseDouble(tmp.temp);
                startDate = tmp.sdate;
            }
            else {
                if (Double.parseDouble(tmp.temp) < lowestTemp){
                    lowestTemp = Double.parseDouble(tmp.temp);
                }
                if (Double.parseDouble(tmp.temp) > highestTemp) {
                    highestTemp = Double.parseDouble(tmp.temp);
                }
                if (i+1 == data.size()){
                    endDate = tmp.sdate;
                }
            }

        }
       temperature_plot.setTitle("Températures du " + startDate + " au " + endDate);

        temperature_plot.setRangeBoundaries(lowestTemp-5, highestTemp+5, BoundaryMode.FIXED);

        XYSeries data = new SimpleXYSeries(
                Arrays.asList(time),
                Arrays.asList(temperature),
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


