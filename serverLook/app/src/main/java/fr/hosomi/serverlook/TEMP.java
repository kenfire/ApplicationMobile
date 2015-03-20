package fr.hosomi.serverlook;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kenzo on 10/03/2015.
 */
public class TEMP {
    static final long serialVersionUID = 0;
    public String sdate, temp, nomBaie;
    static final Parcelable.Creator<TEMP> CREATOR = null;

    public TEMP(String sdate,String temp,String nomBaie)
    {
        this.sdate = sdate;
        this.temp = temp;
        this.nomBaie = nomBaie;
    }

    public String toString(){
        return "";
    }

    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel parcel, int num){

    }
}
