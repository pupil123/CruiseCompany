package service;


import domain.Cruise;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * The {@code CruiseService} interface is responsible for processing business logic
 * with {@code Cruise} entity class
 */
public interface CruiseService {

    /**
     * Responsible for saving Cruise to DB
     *
     * @param cruise instance of {@code Cruise} entity class
     * @return int value of new cruise Id
     * @throws SQLException when persist DB fails
     */
    int addNewCruise(Cruise cruise) throws SQLException;

    /**
     * Responsible for getting the list of all Cruises from DB.
     *
     * @return {@code List<Cruise>} the list of all Cruises from DB.
     * @throws SQLException when persist DB fails
     */
    List<Cruise> getAllCruises() throws SQLException;

    /**
     * Responsible for deleting specified Cruise from DB.
     *
     * @param cruiseId instance of {@code int} parameter specifies Cruise.
     * @throws SQLException when persist DB fails
     */
    void deleteCruiseById(int cruiseId) throws SQLException;

    /**
     * Responsible for updating Cruise in DB
     *
     * @param cruise the {@code Cruise} entity class
     * @throws SQLException when persist DB fails
     */
    void updateCruise(Cruise cruise) throws SQLException;

    /**
     * Responsible for getting Cruise instance with specified id.
     *
     * @param cruiseId instance of {@code int} Parameter specifies Cruise.
     * @return {@code Cruise} instance.
     * @throws SQLException when persist DB fails
     */
    Cruise getCruiseById(int cruiseId) throws SQLException;

    /**
     * Responsible for getting shipId List with specified LocalDate and int from DB.
     *
     * @param startDate instance of {@code LocalDate} Parameter specifies local date.
     * @param days      instance of {@code int} Parameter specifies period.
     * @return {@code List} of {@code Integer} with specified shipIds List.
     * @throws SQLException when persist DB fails
     */
    List<Integer> getShipIdListByDateDuration(LocalDate startDate, int days) throws SQLException;

    /**
     * Responsible for getting cruiseId  with specified LocalDate and String from DB.
     *
     * @param startDate instance of {@code LocalDate} Parameter specifies local date.
     * @param route     instance of {@code String} Parameter specifies route.
     * @return int with specified cruiseId.
     * @throws SQLException when persist DB fails
     */
    int getCruiseIdByDateRoute(LocalDate startDate, String route) throws SQLException;

    /**
     * Responsible for getting cruiseId List with specified LocalDate and int from DB.
     *
     * @param startDate instance of {@code LocalDate} Parameter specifies local date.
     * @param period    instance of {@code int} Parameter specifies period.
     * @return {@code List} of {@code Integer} with specified cruiseIds List.
     * @throws SQLException when persist DB fails
     */
    List<Integer> getCruiseIdListByStartDateDuration(LocalDate startDate, int period) throws SQLException;


}
