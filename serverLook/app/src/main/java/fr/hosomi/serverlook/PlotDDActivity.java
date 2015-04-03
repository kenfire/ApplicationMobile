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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Adrien on 03/04/2015.
 */
public class PlotDDActivity extends ActionBarActivity {

    static final private int MENU_PREFERENCES = Menu.FIRST;
    private XYPlot dd_plot;
    private Intent intent;
    public int test;
    private ArrayList data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_dd_activity);

        this.intent = getIntent();
        this.data = this.intent.getParcelableArrayListExtra(StatsDDActivity.KEY_ARRAY_DD);

        dd_plot = (XYPlot) findViewById(R.id.dd_plot);
        Number[] time = new Number[data.size()];
        Number[] pourcentageDD = new Number[data.size()];

        UsageDD tmp;
        Double lowestUsage = 0.;
        Double highestUsage = 0.;
        String startDate = "";
        String endDate = "";

       for (int i=0; i<data.size();i++) {

            tmp = (UsageDD) data.get(i);
            time[i]=(double) ((double) i/ (double) data.size())*100.0;
           pourcentageDD[i]=((Double.parseDouble(tmp.utilisation)/Double.parseDouble(tmp.capacite))*100);

            if (i==0) {
                lowestUsage = (Double.parseDouble(tmp.utilisation)/Double.parseDouble(tmp.capacite))*100;
                highestUsage = (Double.parseDouble(tmp.utilisation)/Double.parseDouble(tmp.capacite))*100;
                startDate = tmp.sdate;
            }
            else {
                if ((Double.parseDouble(tmp.utilisation)/Double.parseDouble(tmp.capacite))*100 < lowestUsage){
                    lowestUsage = (Double.parseDouble(tmp.utilisation)/Double.parseDouble(tmp.capacite))*100;
                }
                if ((Double.parseDouble(tmp.utilisation)/Double.parseDouble(tmp.capacite))*100 > highestUsage) {
                    highestUsage = (Double.parseDouble(tmp.utilisation)/Double.parseDouble(tmp.capacite))*100;
                }
                if (i+1 == data.size()){
                    endDate = tmp.sdate;
                }
            }

        }

        dd_plot.setTitle("Usage DD du " + startDate + " au " + endDate);

        dd_plot.setRangeBoundaries(lowestUsage-0.1, highestUsage+0.1, BoundaryMode.FIXED);

        XYSeries data = new SimpleXYSeries(
                Arrays.asList(time),
                Arrays.asList(pourcentageDD),
                "Pourcentage utilisation DD");

        LineAndPointFormatter dataFormat = new LineAndPointFormatter();
        dataFormat.setPointLabelFormatter(new PointLabelFormatter());
        dataFormat.configure(getApplicationContext(),
                R.xml.line_point_formatter_with_data_format);

        dd_plot.addSeries(data, dataFormat);
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
