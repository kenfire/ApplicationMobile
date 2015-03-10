package fr.hosomi.serverlook;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class PreferencesFragments extends PreferenceFragment
{
    public static final String PREFKEY_IPSERVEUR = "PREFKEY_IPSERVEUR";
    public static final String PREFKEY_PORTSERVEUR = "PREFKEY_PORTSERVEUR";
    public static final String PREFKEY_USERNAME = "PREFKEY_USERNAME";
    public static final String PREFKEY_PASSWORD = "PREFKEY_PASSWORD";

    public static final String PREFKEY_IPSNMP = "PREFKEY_IPSNMP";
    public static final String PREFKEY_PORTSNMP = "PREFKEY_PORTSNMP";
    public static final String PREFKEY_COMSNMP = "PREFKEY_COMSNMP";

    public static final String PREFKEY_IPTEMP = "PREFKEY_IPTEMP";
    public static final String PREFKEY_PORTTEMP = "PREFKEY_PORTTEMP";
    public static final String PREFKEY_COMTEMP = "PREFKEY_COMTEMP";



    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        addPreferencesFromResource(R.xml.userpreferences);
    }
}