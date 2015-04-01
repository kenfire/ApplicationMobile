package fr.hosomi.serverlook;

import android.os.Parcelable;

/**
 * Created by kenzo on 10/03/2015.
 */
public class UsageDD {
    private static final long serialVersionUD = 123456789L;

    public String sdate;
    public String usage;
    public String capacite;
    public String utilisation;

    public static final Parcelable.Creator <UsageDD> CREATOR = null;

    public UsageDD(String sdate,String usage,String capacite,String utilisation)
    {
        this.sdate = sdate;
        this.usage = usage;
        this.capacite = capacite;
        this.utilisation = utilisation;
    }
}
