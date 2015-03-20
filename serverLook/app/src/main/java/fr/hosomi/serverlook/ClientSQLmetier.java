package fr.hosomi.serverlook;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by kenzo on 10/03/2015.
 */
public class ClientSQLmetier {

    private static final String TAG = "ClientSQLMetier";
    private String serveurBDD;
    private String nomBDD;
    private String userBDD;
    private String mdpBDD;
    private String portBDD;
    private String connexionStringBDD;
    private Connection conn = null;

    public ClientSQLmetier(String serveur, String port, String bdd, String user, String mdp, int timeout) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        this.setServeurBDD(serveur);
        this.setNomBDD(bdd);
        this.setUserBDD(user);
        this.setMdpBDD(mdp);
        String to = String.valueOf(timeout);
        setConnexionStringBDD("jdbc:jtds:sqlserver://"+getServeurBDD().toString()+":"+port.toString()+"/"+bdd+";encrypt=false;instance=SQLEXPRESS;loginTimeout="+to+";socketTimeout="+to+";");
        Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
        DriverManager.setLoginTimeout(timeout);
    }

    public ResultSet getTableTEMP() throws SQLException
    {
        if( conn == null )
            conn = DriverManager.getConnection(this.connexionStringBDD,this.userBDD, this.mdpBDD);
        Log.i(TAG, "open BDD");
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("select * from Temperatures");
        return result;
    }

    public ResultSet getTableUsageDD() throws SQLException
    {
        if( conn == null )
            conn = DriverManager.getConnection(this.connexionStringBDD,this.userBDD, this.mdpBDD);
        Log.i(TAG, "open BDD");
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("select * from UsageDD");
        return result;
    }

    public ResultSet getTableUsageMP() throws SQLException
    {
        if( conn == null )
            conn = DriverManager.getConnection(this.connexionStringBDD,this.userBDD, this.mdpBDD);
        Log.i(TAG, "open BDD");
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("select * from UsageMP");
        return result;
    }

    public String getServeurBDD() {
        return serveurBDD;
    }

    public void setServeurBDD(String serveurBDD) {
        this.serveurBDD = serveurBDD;
    }

    public String getNomBDD() {
        return nomBDD;
    }

    public void setNomBDD(String nomBDD) {
        this.nomBDD = nomBDD;
    }

    public String getUserBDD() {
        return userBDD;
    }

    public void setUserBDD(String userBDD) {
        this.userBDD = userBDD;
    }

    public String getMdpBDD() {
        return mdpBDD;
    }

    public void setMdpBDD(String mdpBDD) {
        this.mdpBDD = mdpBDD;
    }

    public String getPortBDD() {
        return portBDD;
    }

    public void setPortBDD(String portBDD) {
        this.portBDD = portBDD;
    }

    public String getConnexionStringBDD() {
        return connexionStringBDD;
    }

    public void setConnexionStringBDD(String connexionStringBDD) {
        this.connexionStringBDD = connexionStringBDD;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void finalize()
    {
        if(conn != null)
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }


}
