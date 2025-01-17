/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airline.reservation.system.databaseInterfaceLayer;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lapras
 */
// Passenger Data Access Object
public class PassengerDAO {

    private final Connection CON;

    public PassengerDAO(Connection con) {
        this.CON = con;
    }

    public boolean addPassenger(Passenger p) {
        try ( PreparedStatement pre_stmt = CON.prepareStatement(Queries.INSERT_PASSENGER)) {
            pre_stmt.setString(1, p.name);
            pre_stmt.setString(2, p.email);
            pre_stmt.setString(3, p.password);
            pre_stmt.setBoolean(4, p.is_admin);
            pre_stmt.executeQuery();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PassengerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean removePassenger(int id) {
        if (getPassenger(id) == null) {
            return false;
        }
        try ( PreparedStatement pre_stmt = CON.prepareStatement(Queries.DELETE_PASSENGER)) {
            pre_stmt.setInt(1, id);
            pre_stmt.executeQuery();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PassengerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Passenger getPassenger(int id) {
        try ( PreparedStatement pre_stmt = CON.prepareStatement(Queries.GET_PASSENGER_BY_PASSENGERID)) {
            pre_stmt.setInt(1, id);
            ResultSet res = pre_stmt.executeQuery();
            if (!res.next()) { // No passenger found
                return null;
            }
            return new Passenger(res);
        } catch (SQLException ex) {
            Logger.getLogger(PassengerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Passenger loginPassenger(String email, String password) {
        try ( PreparedStatement pre_stmt = CON.prepareStatement(Queries.GET_PASSENGER_BY_EMAIL_AND_PASSWORD)) {
            pre_stmt.setString(1, email);
            pre_stmt.setString(2, password);

            ResultSet res = pre_stmt.executeQuery();
            if (!res.next()) {
                return null;
            }
            return new Passenger(res);
        } catch (SQLException ex) {
            Logger.getLogger(PassengerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void showTable(Connection con) {
        String query = Queries.GET_ALL_PASSENGER;
        try ( Statement stmt = con.createStatement()) {
            ResultSet res = stmt.executeQuery(query);

            // Print query result
            while (res.next()) {
                String dataRow = res.getInt(1) + "  " + res.getString(2) + "  " + res.getString(3) + "  " + res.getString(4) + " " + res.getBoolean(5);
                System.out.println(dataRow);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PassengerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
