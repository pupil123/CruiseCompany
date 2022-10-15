package service;


import domain.Ship;

import java.sql.SQLException;
import java.util.List;

/**
 * The {@code ShipService} interface is responsible for processing business logic
 * with {@code Ship} entity class
 */
public interface ShipService {

    /**
     * Receives ship and saves it into DB
     *
     * @param ship the instance of {@code Ship} entity class
     * @return int of the new ship Id from DB.
     */
    int addNewShip(Ship ship) throws SQLException;

    /**
     * Responsible for getting the list of all Ships from DB.
     *
     * @return {@code List<Ship>} the list of all Ships from DB.
     */
    List<Ship> getAllShips() throws SQLException;

    /**
     * Responsible for deleting specified Ship from DB.
     *
     * @param shipId the {@code int} parameter specifies ship.
     * */
    void deleteShipById(int shipId) throws SQLException;

    /**
     * Responsible for updating Ship in DB
     *
     * @param ship the {@code Ship} entity class .
     */
    void updateShip (Ship ship) throws SQLException;

    /**
     * Responsible for getting Ship instance with specified id.
     *
     * @param shipId the {@code int} parameter specifies Ship.
     * @return {@code Ship} instance with specified id.
     */
    Ship getShipById(int shipId) throws SQLException;


}
