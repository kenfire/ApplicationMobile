package fr.hosomi.serverlook;

import android.os.Parcelable;

/**
 * Created by kenzo on 1""/""3/2""15.
 */
public class UsageMP {
    private static final long serialVersionUD = 123456789L;

    public String sdate;
    public String nbprocs;
    public String ump1, ump2, ump3, ump4, ump5, ump6, ump7, ump8;
    public static final Parcelable.Creator <UsageDD> CREATOR = null;

    public UsageMP(String sdate, String nbprocs, String ump1,String ump2,String ump3,String ump4,String ump5,String ump6,String ump7,String ump8)
    {
        this.sdate = sdate;
        this.nbprocs = nbprocs;
        this.ump1 = ump1;
        this.ump2 = ump2;
        this.ump3 = ump3;
        this.ump4 = ump4;
        this.ump5 = ump5;
        this.ump6 = ump6;
        this.ump7 = ump7;
        this.ump8 = ump8;
    }

    public UsageMP()
    {
        this.sdate = "";
        this.nbprocs = "";
        this.ump1 = "";
        this.ump2 = "";
        this.ump3 = "";
        this.ump4 = "";
        this.ump5 = "";
        this.ump6 = "";
        this.ump7 = "";
        this.ump8 = "";
    }
}
