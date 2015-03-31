package fr.hosomi.serverlook;

import android.os.Parcelable;

/**
 * Created by kenzo on 10/03/2015.
 */
public class UsageMP {
    private static final long serialVersionUD = 123456789L;

    public String sdate;
    public int nbprocs;
    public int ump1, ump2, ump3, ump4, ump5, ump6, ump7, ump8;
    public static final Parcelable.Creator <UsageDD> CREATOR = null;

    public UsageMP(String sdate, int nbprocs, int ump1,int ump2,int ump3,int ump4,int ump5,int ump6,int ump7,int ump8)
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
        this.nbprocs = 0;
        this.ump1 = 0;
        this.ump2 = 0;
        this.ump3 = 0;
        this.ump4 = 0;
        this.ump5 = 0;
        this.ump6 = 0;
        this.ump7 = 0;
        this.ump8 = 0;
    }
}
