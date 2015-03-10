package fr.hosomi.serverlook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {
    static final private int MENU_PREFERENCES = Menu.FIRST;
    private Button btn_tmp_baie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// Button
        this.btn_tmp_baie = (Button) findViewById(R.id.btn_tmp_baie);

        this.btn_tmp_baie.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View view){
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
                startActivityForResult(i, 1);
                return true;
            }
        }
        return false;
    }


}
