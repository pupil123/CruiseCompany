package dao.impl;

import dao.CommonsOperable;
import dao.MySQLConnectorManager;
import dao.ShipDAO;
import domain.Ship;

import java.sql.*;

import static util.Constants.*;


/**
 * The {@code ShipDaoImpl} class is a JDBC implementation
 * of {@code ShipDao} interface
 */
public class ShipDAOImpl implements ShipDAO, CommonsOperable {

//    private static final Logger LOGGER = LogManager.getLogger(ShipDAOImpl.class);

    /**
     * Receives ship and saves it into DB
     *
     * @param ship the instance of {@code Ship} entity class
     * @return int of the new ship Id from DB.
     */
    @Override
    public int addNewShip(Ship ship) throws SQLException {

        int saveId = 0;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_ADD_NEW_SHIP, Statement.RETURN_GENERATED_KEYS)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setInt(1, ship.getCapacity());
            statement.setInt(2, ship.getNumberPortsVisited());
            statement.setString(3, ship.getStaffs());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    saveId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating ship failed, no ID obtained.");
                }
            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            throw new SQLException(COULD_NOT_PERSIST_SHIP);
        }

        return saveId;
    }

    /**
     * Receives the id of Ship and deletes ship from DB.
     *
     * @param shipId instance of {@code int} Parameter specifies the ship.
     */
    @Override
    public void deleteShipById(int shipId) throws SQLException {
        deleteItemById(shipId, SQL_DELETE_CRUISE_BY_SHIP_ID);
        deleteItemById(shipId, SQL_DELETE_SHIP_BY_ID);
    }

    /**
     * Receives ship and updates it in DB.
     *
     * @param ship instance of {@code Ship} entity class.
     */
    @Override
    public void updateShip(Ship ship) throws SQLException {
        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_UPDATE_SHIP)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setInt(1, ship.getCapacity());
            statement.setInt(2, ship.getNumberPortsVisited());
            //   statement.setString(3, ship.getRoutes());
            statement.setString(3, ship.getStaffs());
            statement.setInt(4, ship.getId());

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            throw new SQLException(COULD_NOT_PERSIST_SHIP);
        }
    }

    /**
     * Receives the connection and returns all the ships from DB.
     *
     * @param connection java.sql.Connection
     * @return ResultSet containing the data of all the Ships from DB.
     */
    @Override
    public ResultSet getAllShips(Connection connection) throws SQLException {

        return getAllItems(connection, SQL_GET_ALL_SHIPS);
    }

    /**
     * Responsible for getting the Ship with specified id from DB.
     *
     * @param connection java.sql.Connection
     * @param shipId    the {@code int} parameter, specifies ship.
     * @return ResultSet with all the data of Ship.
     */
    @Override
    public ResultSet getShipById(int shipId, Connection connection) throws SQLException {

        return getItemById(connection, SQL_GET_SHIP_BY_ID, shipId);

    }
}
