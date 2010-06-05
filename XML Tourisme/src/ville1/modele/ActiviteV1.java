/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ville1.modele;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Guillaume
 */
class ActiviteV1 {

    private String libelle;
    private String type;
    private int nbPlaceMax;

    public ActiviteV1(String libelle, String type, int nbPlaceMax) {
        this.libelle = libelle;

        this.type = type;
        this.nbPlaceMax = nbPlaceMax;
    }

    public String getLibelle() {
        return libelle;
    }

    public int getNbPlaceMax() {
        return nbPlaceMax;
    }

    public String getType() {
        return type;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setNbPlaceMax(int nbPlaceMax) {
        this.nbPlaceMax = nbPlaceMax;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ActiviteV1 getByLib(String libelle) throws SQLException {
        ActiviteV1 result = null;
        Connection connection = DatabaseV1.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM produit WHERE no_produit=" + libelle);
        if (rs.next()) {
            result = new ActiviteV1(rs.getString("libelle"), rs.getString("type"), rs.getInt("NbPlaceMax"));
        }
        rs.close();
        stmt.close();
        connection.close();
        return result;
    }

    public static boolean estLibre(String libelle) throws SQLException {
        
        boolean result = true;
        Connection connection = DatabaseV1.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT PlaceRest FROM reservation WHERE libelle=" + libelle);
        if(rs.getInt("PlaceRest")==0){
            result = false;
        }
        return result;

    }
}
