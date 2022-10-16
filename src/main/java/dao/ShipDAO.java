package dao;

import domain.Ship;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The {@code ShipDAO} interface is responsible for
 * connecting {@code Ship} entity class to DB
 */
public interface ShipDAO {

    /**
     * Receives ship and saves it into DB
     *
     * @param ship the instance of {@code Ship} entity class
     * @return int of the new ship Id from DB.
     * @throws SQLException when persist DB fails
     */
    int addNewShip(Ship ship) throws SQLException;

    /**
     * Receives the id of Ship and deletes ship from DB.
     *
     * @param shipId instance of {@code int} Parameter specifies the ship.
     * @throws SQLException when persist DB fails
     */
    void deleteShipById(int shipId) throws SQLException;

    /**
     * Receives ship and updates it in DB.
     *
     * @param ship instance of {@code Ship} entity class.
     * @throws SQLException when persist DB fails
     */
    void updateShip(Ship ship) throws SQLException;

    /**
     * Receives the connection and returns all the ships from DB.
     *
     * @param connection java.sql.Connection
     * @return ResultSet containing the data of all the Ships from DB.
     * @throws SQLException when persist DB fails
     */
    ResultSet getAllShips(Connection connection) throws SQLException;

    /**
     * Responsible for getting the Ship with specified id from DB.
     *
     * @param connection java.sql.Connection
     * @param shipId     the {@code int} parameter, specifies ship.
     * @return ResultSet with all the data of Ship.
     * @throws SQLException when persist DB fails
     */
    ResultSet getShipById(int shipId, Connection connection) throws SQLException;
}
