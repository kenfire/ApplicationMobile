package fr.hosomi.serverlook;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;

import com.adventnet.snmp.beans.SnmpServer;
import com.adventnet.snmp.beans.SnmpTarget;


public class MainActivity extends ActionBarActivity {
    static final private int MENU_PREFERENCES = Menu.FIRST;
    static final private int CODE_REQUETE_PREFERENCES = 1;
    private Button btn_tmp_baie;
    private Button btn_temp;

    private String ipSql = null;
    private String portSql = null;
    private String userSql = null;
    private String passSql = null;

    private String ipSnmp = null;
    private String portSnmp = null;
    private String comSnmp = null;

    private String ipTemp = null;
    private String portTemp = null;
    private String comTemp = null;

    private AsyncTask<String, Void, String> TaskSonde;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.updateAttributsFromPreferences();
// Button
        this.btn_tmp_baie = (Button) findViewById(R.id.btn_tmp_baie);
        this.btn_temp = (Button) findViewById(R.id.btn_temp);

        this.btn_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskSonde = new SnmpGetTaskSonde();
                TaskSonde.execute("oid");
            }
        });

        this.btn_tmp_baie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StatsTEMPActivity.class);
                startActivity(intent);
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
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case (MENU_PREFERENCES): {
                Class c = SetPreferencesFragmentActivity.class;
                Intent i = new Intent(this, c);
                startActivityForResult(i, CODE_REQUETE_PREFERENCES);
                return true;
            }
        }
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode,Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_REQUETE_PREFERENCES)

            this.updateAttributsFromPreferences();
    }

    private void updateAttributsFromPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.ipSql = prefs.getString( PreferencesFragments.PREFKEY_IPSERVEUR, "83.233.223.249");
        this.portSql =  prefs.getString(PreferencesFragments.PREFKEY_PORTSERVEUR, "1433");
        this.userSql = prefs.getString( PreferencesFragments.PREFKEY_USERNAME, "supervision");
        this.passSql =  prefs.getString(PreferencesFragments.PREFKEY_PASSWORD, "Password1234");

        this.ipSnmp =  prefs.getString(PreferencesFragments.PREFKEY_IPSNMP, "82.233.223.249");
        this.portSnmp =  prefs.getString(PreferencesFragments.PREFKEY_PORTSNMP, "161");
        this.comSnmp =  prefs.getString(PreferencesFragments.PREFKEY_COMSNMP, "DataCenterVDR");

        this.ipTemp =  prefs.getString(PreferencesFragments.PREFKEY_IPTEMP, "82.233.223.249");
        this.portTemp =  prefs.getString(PreferencesFragments.PREFKEY_PORTTEMP, "1610");
        this.comTemp =  prefs.getString(PreferencesFragments.PREFKEY_COMTEMP, "DataCenterVDR");
    }

    private class SnmpGetTaskSonde extends AsyncTask<String, Void, String> {
        public boolean erreurFlag = false;
        public SnmpTarget target= new SnmpTarget();
        public String temp;
        private ProgressDialog dialog;

        public SnmpGetTaskSonde(){
            dialog = new ProgressDialog(MainActivity.this);
        }

@Override
        public void onPreExecute(){
            dialog.setMessage("Chargement de la tempèrature");
            dialog.show();
        }
@Override
        public String doInBackground(String... arg){

            target.setTargetHost(ipTemp);
            target.setTargetPort(Integer.parseInt(portTemp));
            target.setCommunity(comTemp);
            target.setSnmpVersion(SnmpServer.VERSION1);

            target.setObjectID(".1.3.6.1.4.1.21796.4.1.3.1.4.1");

            temp = target.snmpGet();

            return temp;
        }
@Override
        public void onPostExecute(String temp){
            dialog.dismiss();
            Log.d("%s", temp);
        }

    }

}
