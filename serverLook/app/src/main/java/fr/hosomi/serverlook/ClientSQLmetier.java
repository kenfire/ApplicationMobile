package fr.hosomi.serverlook;

import java.sql.Connection;

/**
 * Created by kenzo on 10/03/2015.
 */
public class ClientSQLmetier {

    private String serveurBDD;
    private String nomBDD;
    private String userBDD;
    private String mdpBDD;
    private String portBDD;
    private String connexionStringBDD;
    private Connection conn;

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




}
