package dao.impl;

import dao.CommonsOperable;
import dao.CruiseDAO;
import dao.MySQLConnectorManager;
import domain.Cruise;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;

import static util.Constants.*;


/**
 * The {@code CruiseDaoImpl} class is a JDBC implementation
 * of {@code CruiseDao} interface
 */
public class CruiseDAOImpl implements CruiseDAO, CommonsOperable {

    /**
     * Receives cruise and saves it into DB
     *
     * @param cruise the instance of {@code Cruise} entity class
     * @return int of the new cruise Id from DB.
     */
    @Override
    public int addNewCruise(Cruise cruise) throws SQLException {

        int saveId = 0;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_ADD_NEW_CRUISE, Statement.RETURN_GENERATED_KEYS)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setString(1, cruise.getStartDate().toString());
            statement.setString(2, cruise.getFinishDate().toString());
            statement.setString(3, cruise.getRoute());
            statement.setInt(4, cruise.getShipId());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    saveId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException(CREATING_CRUISE_FAILED_NO_ID_OBTAINED);
                }
            }
            MySQLConnectorManager.commitTransaction(connection);
        } catch (SQLException e) {

            throw new SQLException(COULD_NOT_PERSIST_CRUISE);
        }
        return saveId;
    }

    /**
     * Receives the id of Cruise and deletes cruise from DB.
     *
     * @param cruiseId instance of {@code int} Parameter specifies the Cruise.
     */
    @Override
    public void deleteCruiseById(int cruiseId) throws SQLException {

        deleteItemById(cruiseId, SQL_DELETE_CRUISE_BY_ID);
    }

    /**
     * Receives cruise and updates it in DB.
     *
     * @param cruise instance of {@code Cruise} entity class.
     */

    @Override
    public void updateCruise(Cruise cruise) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_UPDATE_CRUISE)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setString(1, cruise.getStartDate().toString());
            statement.setString(2, cruise.getFinishDate().toString());
            statement.setString(3, cruise.getRoute());
            statement.setInt(4, cruise.getShipId());

            statement.setInt(5, cruise.getId());

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            throw new SQLException(COULD_NOT_PERSIST_CRUISE);
        }

    }

    /**
     * Receives the connection and returns all the cruises from DB.
     *
     * @param connection java.sql.Connection
     * @return ResultSet containing the data of all the Cruises from DB.
     */
    @Override
    public ResultSet getAllCruises(Connection connection) throws SQLException {

        return getAllItems(connection, SQL_GET_ALL_CRUISES);
    }

    /**
     * Responsible for getting the Cruise with specified id from DB.
     *
     * @param connection java.sql.Connection
     * @param cruiseId   the {@code int} parameter, specifies cruise.
     * @return ResultSet with all the data of Cruise.
     */
    @Override
    public ResultSet getCruiseById(int cruiseId, Connection connection) throws SQLException {

        return getItemById(connection, SQL_GET_CRUISE_BY_ID, cruiseId);

    }

    /**
     * Responsible for getting shipId List with specified LocalDate from DB.
     *
     * @param connection java.sql.Connection
     * @param startDate  instance of {@code LocalDate} Parameter specifies local date.
     * @return ResultSet with data of shipId List.
     */
    @Override
    public ResultSet getShipIdListByStartDate(LocalDate startDate, Connection connection) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(SQL_GET_SHIP_BY_DATE);

        statement.setString(1, startDate.toString());

        return statement.executeQuery();
    }

    /**
     * Responsible for getting cruiseId List with specified LocalDate from DB.
     *
     * @param connection java.sql.Connection
     * @param startDate  instance of {@code LocalDate} Parameter specifies local date.
     * @return ResultSet  with data of cruiseId List.
     */
    @Override
    public ResultSet getCruiseIdListByStartDate(LocalDate startDate, Connection connection) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(SQL_GET_CRUISEIDS_BY_DATE);

        statement.setString(1, startDate.toString());

        return statement.executeQuery();
    }

    /**
     * Responsible for getting cruiseId with specified LocalDate and String of route from DB.
     *
     * @param connection java.sql.Connection
     * @param startDate  instance of {@code LocalDate} Parameter specifies local date
     * @param route      instance of {@code String} Parameter specifies local date and cruise route.
     * @return ResultSet with data of cruiseId.
     */
    @Override
    public ResultSet getCruiseIdByDateRoute(LocalDate startDate, String route, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_GET_CRUISE_ID_BY_DATE_ROUTE2);

        statement.setString(1, startDate.toString());
        statement.setString(2, route);

        return statement.executeQuery();
    }
}

