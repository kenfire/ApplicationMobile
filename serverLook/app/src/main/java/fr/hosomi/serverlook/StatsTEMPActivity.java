package fr.hosomi.serverlook;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by kenzo on 10/03/2015.
 */
public class StatsTEMPActivity extends ActionBarActivity {
    static final private int MENU_PREFERENCES = Menu.FIRST;
    private Button btn_courbe_stats;
    private ListView listeView;
    private Button btnListe;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnListe = (Button) findViewById(R.id.btn_list);
        setContentView(R.layout.activity_stats_temp);
        // Liste température
        final ArrayList<TEMP> arrayTemp = new ArrayList<>();
        this.listeView = (ListView) this.findViewById(R.id.listTemp);
        final ArrayAdapter<TEMP> arrayAdapt;
        arrayAdapt = new ArrayUsageTEMPAdapter(this, 0, arrayTemp);
        this.listeView.setAdapter(arrayAdapt);

        this.btn_courbe_stats = (Button) findViewById(R.id.btn_courbe_stats);

        this.btn_courbe_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatsTEMPActivity.this, PlotTEMPActivity.class);
                startActivity(intent);
            }
        });

        btnListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Thread thread = new Thread() {
                    int state = 0;

                    @Override
                    public void run() {
                        try {
                            arrayF.clear();
                            ResultSet result = clientBDD.getTableFournisseurs();
                            while (result.next()) {
                                String idEnt = result.getString("NF");
                                String nomEnt = result.getString("nomF");
                                String statutEnt = result.getString("statut");
                                String villeEnt = result.getString("ville");
                                arrayF.add(new Fournisseur(idEnt, nomEnt, statutEnt, villeEnt));
                            }
                            result.close();
                            if (arrayF.isEmpty()) {
                                arrayF.add(new Fournisseur("", "Vide", "", ""));
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    arrayAdapt.notifyDataSetChanged();
                                    wait.dismiss();
                                }
                            });
                            AlertDialog.Builder alertB = new AlertDialog.Builder(MainActivity.this);
                            alertB.setTitle("Changement en cours");
                            alertB.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            wait.dismiss();
                        }
                    }
                };
                wait.setMessage(getResources().getString(R.string.btn_list));
                wait.show();
                thread.start();
            }
        });

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
