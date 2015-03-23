package fr.hosomi.serverlook;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.Thread;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by kenzo on 10/03/2015.
 */
public class StatsTEMPActivity extends ActionBarActivity {
    static final private int MENU_PREFERENCES = Menu.FIRST;
    static final private int CODE_REQUETE_PREFERENCES = 1;
    private Button btn_courbe_stats;
    private ListView listeView;
    private Button btnListe;
    private ClientSQLmetier clientBDD;

    private String ip;
    private String port;
    private String username;
    private String password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_temp);
        this.btnListe = (Button) findViewById(R.id.btn_list);

        try {
            //this.clientBDD = new ClientSQLmetier(this.ip, this.port, "Supervision", this.username, this.password, 5);
            this.clientBDD = new ClientSQLmetier("82.233.233.249", "1433", "Supervision", "supervision,", "Password1234", 50);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        final ProgressDialog wait = new ProgressDialog(this);

        this.btnListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            arrayTemp.clear();
                            ResultSet result = clientBDD.getTableTEMP();
                            while (result.next()) {
                                String sdate = result.getString("date");
                                String temp = result.getString("temp");
                                String nomBaie = result.getString("MachineName");
                                arrayTemp.add(new TEMP(sdate, temp, nomBaie));
                            }
                            result.close();
                            if (arrayTemp.isEmpty()) {
                                arrayTemp.add(new TEMP("", "", "Vide"));
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    arrayAdapt.notifyDataSetChanged();
                                    wait.dismiss();
                                }
                            });
                            AlertDialog.Builder alertB = new AlertDialog.Builder(StatsTEMPActivity.this);
                            alertB.setTitle("Changement en cours");
                            alertB.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            AlertDialog.Builder alertB = new AlertDialog.Builder(StatsTEMPActivity.this);
                            alertB.setTitle("Changement échoué !");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REQUETE_PREFERENCES)
            this.updateAttributsFromPreferences();
    }

    public void updateAttributsFromPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip = prefs.getString(PreferencesFragments.PREFKEY_IPSERVEUR, "82.233.223.249");
        port = prefs.getString(PreferencesFragments.PREFKEY_PORTSERVEUR, "1433");
        username = prefs.getString(PreferencesFragments.PREFKEY_USERNAME, "supervision");
        password = prefs.getString(PreferencesFragments.PREFKEY_PASSWORD, "Password1234");
    }
}
