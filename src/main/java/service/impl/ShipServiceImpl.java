package service.impl;

import dao.MySQLConnectorManager;
import dao.ShipDAO;
import domain.Ship;
import domain.builder.ShipBuilder;
import service.ShipService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ShipServiceImpl} class is a service implementation
 * of {@code ShipService} interface
 */
public class ShipServiceImpl implements ShipService {

    private final ShipDAO SHIP_DAO;

    public ShipServiceImpl(ShipDAO SHIP_DAO) {
        this.SHIP_DAO = SHIP_DAO;
    }

    /**
     * Receives ship and saves it into DB
     *
     * @param ship the instance of {@code Ship} entity class
     * @return int of the new ship Id from DB.
     */
    @Override
    public int addNewShip(Ship ship) throws SQLException {

        return SHIP_DAO.addNewShip(ship);

    }

    /**
     * Responsible for getting the list of all Ships from DB.
     *
     * @return {@code List<Ship>} the list of all Ships from DB.
     */
    @Override
    public List<Ship> getAllShips() {

        List<Ship> ships = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = SHIP_DAO.getAllShips(connection);

            ships = getShipsFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);
            resultSet.close();

        } catch (SQLException e) {

        }
        return ships;

    }

    /**
     * Responsible for building the list of Ships instance from the list of data encapsulated in ResultSet.
     *
     * @param resultSet the {@code ResultSet} from {@code getAllShips()} method.
     * @return {@code List<Ship>} from ResultSet.
     */
    private List<Ship> getShipsFromResultSet(ResultSet resultSet) throws SQLException {

        List<Ship> ships = new ArrayList<>();

        while (resultSet.next()) {

            Ship ship = new ShipBuilder()
                    .buildId(resultSet.getInt(1))
                    .buildCapacity(resultSet.getInt(2))
                    .buildNumberPortsVisited(resultSet.getInt(3))
                    //      .buildRoutes(resultSet.getString(4))
                    .buildStuffs(resultSet.getString(4))
                    .build();

            ships.add(ship);
        }

        return ships;
    }

    /**
     * Responsible for deleting specified Ship from DB.
     *
     * @param shipId the {@code int} parameter specifies Ship.
     */
    @Override
    public void deleteShipById(int shipId) throws SQLException {

        SHIP_DAO.deleteShipById(shipId);
    }

    /**
     * Responsible for updating Ship in DB
     *
     * @param ship the {@code Ship} entity class .
     */
    @Override
    public void updateShip(Ship ship) throws SQLException {
        SHIP_DAO.updateShip(ship);
    }

    /**
     * Responsible for getting Ship instance with specified id.
     *
     * @param shipId the {@code int} parameter specifies Ship.
     * @return {@code Ship} instance with specified id.
     */
    @Override
    public Ship getShipById(int shipId) {

        Ship ship = new Ship();

        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = SHIP_DAO.getShipById(shipId, connection);

            ship = getShipFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

        }

        return ship;
    }

    /**
     * Responsible for building Ship instance from ResultSet.
     *
     * @param resultSet the {@code ResultSet} from {@code getShipById()} method instance
     *                  encapsulating data of Ship.
     * @return {@code Ship} from ResultSet.
     */
    private Ship getShipFromResultSet(ResultSet resultSet) throws SQLException {

        Ship ship = new Ship();

        while (resultSet.next()) {

            ship = new ShipBuilder()
                    .buildId(resultSet.getInt(1))
                    .buildCapacity(resultSet.getInt(2))
                    .buildNumberPortsVisited(resultSet.getInt(3))
                    .buildStuffs(resultSet.getString(4))
                    .build();
        }
        return ship;
    }
}
