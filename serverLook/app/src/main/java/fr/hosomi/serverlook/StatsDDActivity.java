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
public class StatsDDActivity extends MainActivity {
    private static final String LISTE_DD_KEY ="";
    static final private int CODE_REQUETE_PREFERENCES = 1;

    public Button btnPlotDD;

    private ListView listeView;
    private ProgressDialog dialogP;

    // récupération de données
    public final static String KEY_ARRAY_DD = "arrayDd";
    final ArrayList<UsageDD> arrayDd = new ArrayList<>();

    //BDD
    private ClientSQLmetier clientBDD;
    private String ip;
    private String port;
    private String username;
    private String password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_dd);
        this.updateAttributsFromPreferences();


        try {
            this.clientBDD = new ClientSQLmetier(this.ip, this.port, "Supervision", this.username, this.password, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Liste température
        //  final ArrayList<TEMP> arrayDd = new ArrayList<>();
        this.listeView = (ListView) this.findViewById(R.id.listDd);
        final ArrayAdapter<UsageDD> arrayAdapt;
        arrayAdapt = new ArrayUsageDDAdapter(this, 0, arrayDd);
        this.listeView.setAdapter(arrayAdapt);

        this.btnPlotDD = (Button) findViewById(R.id.btnPlotDD);

        /*
        * Courbe de statistic
         */
        this.btnPlotDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatsDDActivity.this, PlotDDActivity.class);
                //envois de la liste de température
                intent.putExtra(KEY_ARRAY_DD,arrayDd);
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
                    arrayDd.clear();
                    ResultSet result = clientBDD.getTableUsageDD(10);
                    while (result.next()) {
                        String sdate = result.getString("date");
                        String usage = result.getString("usage");
                        String capacite = result.getString("capacité");
                        String utilisation = result.getString("utilisé");
                        arrayDd.add(new UsageDD( sdate, usage, capacite, utilisation));
                    }
                    result.close();
                    if (arrayDd.isEmpty()) {
                        arrayDd.add(new UsageDD("","Vide","",""));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            arrayAdapt.notifyDataSetChanged();
                            wait.dismiss();
                        }
                    });
                    AlertDialog.Builder alertB = new AlertDialog.Builder(StatsDDActivity.this);
                    alertB.setTitle("Changement en cours");
                    alertB.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    AlertDialog.Builder alertB = new AlertDialog.Builder(StatsDDActivity.this);
                    alertB.setTitle("Changement échoué !");
                    wait.dismiss();
                }
            }
        };
        wait.setMessage(getResources().getString(R.string.btn_list));
        wait.show();
        thread.start();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
