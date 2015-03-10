package fr.hosomi.serverlook;

import android.os.Parcelable;

/**
 * Created by kenzo on 10/03/2015.
 */
public class UsageDD {
    private static final long serialVersionUD = 0;

    public String sdate;
    public int usage;
    public long capacite;
    public long utilisation;

    public static final Parcelable.Creator <UsageDD> CREATOR = null;

}
