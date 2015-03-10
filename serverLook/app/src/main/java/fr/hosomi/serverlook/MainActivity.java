package fr.hosomi.serverlook;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {
    static final private int MENU_PREFERENCES = Menu.FIRST;
    static final private int CODE_REQUETE_PREFERENCES = 1;
    private Button btn_tmp_baie;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// Button
        this.btn_tmp_baie = (Button) findViewById(R.id.btn_tmp_baie);

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
}
