package fr.hosomi.serverlook;

import android.os.Parcelable;

/**
 * Created by kenzo on 10/03/2015.
 */
public class UsageDD {
    private static final long serialVersionUD = 123456789L;

    public String sdate;
    public int usage;
    public long capacite;
    public long utilisation;

    public static final Parcelable.Creator <UsageDD> CREATOR = null;

    public UsageDD(String sdate,int usage,long capacite,long utilisation)
    {
        this.sdate = sdate;
        this.usage = usage;
        this.capacite = capacite;
        this.utilisation = utilisation;
    }
}
