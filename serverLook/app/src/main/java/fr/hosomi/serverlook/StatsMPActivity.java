package fr.hosomi.serverlook;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by kenzo on 10/03/2015.
 */
public class StatsMPActivity extends MainActivity{

    private static final String LIST_MP_KEY = null;
    private static final String ARRAY_MP_KEY = null;

    public Button btnPlotMp;
    private ListView listeView;
    private ProgressDialog dialogP;
    private Button btn_courbe_stats;

    // récupération de données
    public final static String KEY_ARRAY_MP = "arrayMp";
    final ArrayList<UsageMP> arrayMp = new ArrayList<>();

    //BDD
    private ClientSQLmetier clientBDD;
    private String ip;
    private String port;
    private String username;
    private String password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_mp);
        this.updateAttributsFromPreferences();


        try {
            this.clientBDD = new ClientSQLmetier(this.ip, this.port, "Supervision", this.username, this.password, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Liste température
        //  final ArrayList<TEMP> arrayTemp = new ArrayList<>();
        this.listeView = (ListView) this.findViewById(R.id.listMp);
        final ArrayAdapter<UsageMP> arrayAdapt;
        arrayAdapt = new ArrayUsageMPAdapter(this, 0, arrayMp);
        this.listeView.setAdapter(arrayAdapt);

        this.btn_courbe_stats = (Button) findViewById(R.id.btn_courbe_stats);

        /*
        * Courbe de statistic
         */
        this.btn_courbe_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatsMPActivity.this, PlotMPActivity.class);
                intent.putExtra(KEY_ARRAY_MP,arrayMp);
                startActivity(intent);
            }
        });
        final ProgressDialog wait = new ProgressDialog(this);


        /*
        *   Liste des températures
         */
        final Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    arrayMp.clear();
                    ResultSet result = clientBDD.getTableUsageMP(10);
                    while (result.next()) {
                        String sdate = result.getString("date");
                        String nbprocs = result.getString("nbProcs");
                        String ump1 = result.getString("usageMP1");
                        String ump2 = result.getString("usageMP2");
                        String ump3 = result.getString("usageMP3");
                        String ump4 = result.getString("usageMP4");
                        String ump5 = result.getString("usageMP5");
                        String ump6 = result.getString("usageMP6");
                        String ump7 = result.getString("usageMP7");
                        String ump8 = result.getString("usageMP8");

                        arrayMp.add(new UsageMP(sdate, nbprocs, ump1, ump2, ump3, ump4, ump5, ump6, ump7, ump8));
                    }
                    result.close();
                    if (arrayMp.isEmpty()) {
                        arrayMp.add(new UsageMP());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            arrayAdapt.notifyDataSetChanged();
                            wait.dismiss();
                        }
                    });
                    AlertDialog.Builder alertB = new AlertDialog.Builder(StatsMPActivity.this);
                    alertB.setTitle("Changement en cours");
                    alertB.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    AlertDialog.Builder alertB = new AlertDialog.Builder(StatsMPActivity.this);
                    alertB.setTitle("Changement échoué !");
                    wait.dismiss();
                }
            }
        };
        wait.setMessage(getResources().getString(R.string.btn_list));
        wait.show();
        thread.start();


    }

    public void updateAttributsFromPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip = prefs.getString(PreferencesFragments.PREFKEY_IPSERVEUR, "82.233.223.249");
        port = prefs.getString(PreferencesFragments.PREFKEY_PORTSERVEUR, "1433");
        username = prefs.getString(PreferencesFragments.PREFKEY_USERNAME, "supervision");
        password = prefs.getString(PreferencesFragments.PREFKEY_PASSWORD, "Password1234");
    }
}
