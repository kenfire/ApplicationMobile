package fr.hosomi.serverlook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import com.adventnet.snmp.beans.SnmpServer;
import com.adventnet.snmp.beans.SnmpTarget;

import java.util.concurrent.ExecutionException;


public class MainActivity extends ActionBarActivity {
    static final private int MENU_PREFERENCES = Menu.FIRST;
    static final private int CODE_REQUETE_PREFERENCES = 1;
    private Button btn_tmp_baie;
    private Button btn_usage_proc;
    private Button btn_usage_hdd;
    private Button btn_temp;
    private Button btn_processor;
    private Button btn_disk;

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


    private TextView text_temp;
    private TextView text_proc;
    private TextView text_disk;



    private AsyncTask<String, Void, String> taskSonde;
    private AsyncTask<String, Void, String> taskProcessor;
    private AsyncTask<String, Void, String> taskDisk;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_temp = (TextView) findViewById(R.id.txt_temp);
        text_proc = (TextView) findViewById(R.id.text_proc);
        text_temp.setText("Non mesuré ");
        text_proc.setText("Non mesuré ");

        text_disk = (TextView) findViewById(R.id.text_disk);
        text_disk.setText("Non mesuré ");

        this.updateAttributsFromPreferences();
        // Button
        this.btn_tmp_baie = (Button) findViewById(R.id.btn_tmp_baie);
        this.btn_usage_proc = (Button) findViewById(R.id.btn_usage_proc);
        this.btn_usage_hdd = (Button) findViewById(R.id.btn_usage_hdd);

        this.btn_temp = (Button) findViewById(R.id.btn_temp);
        this.btn_processor = (Button) findViewById(R.id.btn_processor);
        this.btn_disk = (Button) findViewById(R.id.btn_disk);

        this.btn_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskSonde = new SnmpGetTaskSonde();
                taskSonde.execute("oid");
                try {
                    text_temp.setText(taskSonde.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        this.btn_processor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskProcessor = new SnmpGetProcessor();
                taskProcessor.execute("oid");
                try {
                    text_proc.setText(taskProcessor.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        this.btn_disk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskDisk = new SnmpGetDisk();
                taskDisk.execute("oid");
                try {
                    text_disk.setText(taskDisk.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });



        this.btn_tmp_baie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StatsTEMPActivity.class);
                startActivity(intent);
            }
        });

        this.btn_usage_proc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StatsMPActivity.class);
                startActivity(intent);
            }
        });
        this.btn_usage_hdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StatsDDActivity.class);
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

            temp = target.snmpGet() + "°C";

            return temp;
        }
        @Override
        public void onPostExecute(String temp){
            dialog.dismiss();

        }



    }

    private class SnmpGetProcessor extends AsyncTask<String, Void, String> {


        public SnmpTarget target= new SnmpTarget();
        public String[] proc;
        public String procList;
        private ProgressDialog dialog;

        public SnmpGetProcessor(){
            dialog = new ProgressDialog(MainActivity.this);

        }

        @Override
        public void onPreExecute(){
            dialog.setMessage("Chargement des données processeurs");
            dialog.show();
        }
        @Override
        public String doInBackground(String... arg){

            target.setTargetHost(ipSnmp);
            target.setTargetPort(Integer.parseInt(portSnmp));
            target.setCommunity(comSnmp);
            target.setSnmpVersion(SnmpServer.VERSION1);
            target.setTimeout(10);
            target.setRetries(1);

            String listOID[] = {
                    ".1.3.6.1.2.1.25.3.3.1.2.2",
                    ".1.3.6.1.2.1.25.3.3.1.2.3",
                    ".1.3.6.1.2.1.25.3.3.1.2.4",
                    ".1.3.6.1.2.1.25.3.3.1.2.5",
                    ".1.3.6.1.2.1.25.3.3.1.2.6",
                    ".1.3.6.1.2.1.25.3.3.1.2.7",
                    ".1.3.6.1.2.1.25.3.3.1.2.8",
                    ".1.3.6.1.2.1.25.3.3.1.2.9"
                        };

            target.setObjectIDList(listOID);

            proc = target.snmpGetList();

            int i;
            procList = "";
            for(i=0;i<8;i++){
                procList += proc[i] + "% - ";
            }

            return procList;
        }
        @Override
        public void onPostExecute(String procList){
            dialog.dismiss();

        }



    }


    private class SnmpGetDisk extends AsyncTask<String, Void, String> {


        public SnmpTarget target= new SnmpTarget();
        public String[] disk;
        public String diskInfo;
        private ProgressDialog dialog;

        public SnmpGetDisk(){
            dialog = new ProgressDialog(MainActivity.this);

        }

        @Override
        public void onPreExecute(){
            dialog.setMessage("Chargement des données du disque");
            dialog.show();
        }
        @Override
        public String doInBackground(String... arg){

            target.setTargetHost(ipSnmp);
            target.setTargetPort(Integer.parseInt(portSnmp));
            target.setCommunity(comSnmp);
            target.setSnmpVersion(SnmpServer.VERSION1);
            target.setTimeout(10);
            target.setRetries(1);

            String listOID[] = {
                    ".1.3.6.1.2.1.25.2.3.1.6.1",
                    ".1.3.6.1.2.1.25.2.3.1.5.1",
            };

            target.setObjectIDList(listOID);

            disk = target.snmpGetList();

            diskInfo = "Disque de capacité = " + disk[1] + " octets et d'utilisation = " + disk[0] + " octets";

            return diskInfo;
        }
        @Override
        public void onPostExecute(String procList){
            dialog.dismiss();

        }



    }
}
