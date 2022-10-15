package dao;

import domain.Cruise;
import domain.Ship;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * The {@code CruiseDAO} interface is responsible for
 * connecting {@code Cruise} entity class to DB
 */
public interface CruiseDAO {

    /**
     * Responsible for saving Cruise to DB
     *
     * @param cruise instance of {@code Cruise} entity class
     * @return int value of new cruise Id
     */
    int addNewCruise(Cruise cruise) throws SQLException;

    /**
     * Responsible for deleting Cruise from DB
     *
     * @param cruiseId instance of {@code int} Parameter specifies cruise.
     */
    void deleteCruiseById(int cruiseId) throws SQLException;

    /**
     * Responsible for updating Cruise in DB
     *
     * @param cruise the {@code Cruise} entity class .
     */
    void updateCruise(Cruise cruise) throws SQLException;

    /**
     * Responsible for getting the data of all Cruises from DB
     *
     * @param connection java.sql.Connection
     * @return Resultset with data of all cruises
     */
    ResultSet getAllCruises(Connection connection) throws SQLException;

    /**
     * Responsible for getting the Cruise with specified id from DB.
     *
     * @param connection java.sql.Connection
     * @param cruiseId   instance of {@code int} Parameter specifies cruise.
     * @return Resultset with data of specified cruise
     */
    ResultSet getCruiseById(int cruiseId, Connection connection) throws SQLException;

    /**
     * Responsible for getting the Cruise with specified id from DB.
     *
     * @param connection java.sql.Connection
     * @param startDate  instance of {@code LocalDate} Parameter specifies local date.
     * @return ResultSet with data of shipId List.
     */
    ResultSet getShipIdListByStartDate(LocalDate startDate, Connection connection) throws SQLException;

    /**
     * Responsible for getting cruiseId with specified LocalDate and String of route from DB.
     *
     * @param connection java.sql.Connection
     * @param startDate instance of {@code LocalDate} Parameter specifies local date
     * @param route instance of {@code String} Parameter specifies local date and cruise route.
     * @return ResultSet with data of cruiseId.
     */
    ResultSet getCruiseIdByDateRoute(LocalDate startDate, String route, Connection connection) throws SQLException;

    /**
     * Responsible for getting cruiseId List with specified LocalDate from DB.
     *
     * @param connection java.sql.Connection
     * @param startDate   instance of {@code LocalDate} Parameter specifies local date.
     * @return ResultSet  with data of cruiseId List.
     */
    ResultSet getCruiseIdListByStartDate(LocalDate startDate, Connection connection) throws SQLException;
}
