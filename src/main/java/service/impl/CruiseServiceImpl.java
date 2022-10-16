package service.impl;

import dao.CruiseDAO;
import dao.MySQLConnectorManager;
import domain.Cruise;
import domain.builder.CruiseBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.CruiseService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The {@code CruiseServiceImpl} class is a service implementation
 * of {@code CruiseService} interface
 */
public class CruiseServiceImpl implements CruiseService {
    private final CruiseDAO CRUISE_DAO;
    private static final Logger LOGGER = LogManager.getLogger(CruiseServiceImpl.class);

    public CruiseServiceImpl(CruiseDAO CRUISE_DAO) {
        this.CRUISE_DAO = CRUISE_DAO;
    }

    /**
     * Responsible for saving Cruise to DB
     *
     * @param cruise instance of {@code Cruise} entity class
     * @return int value of new cruise Id
     */
    @Override
    public int addNewCruise(Cruise cruise) throws SQLException {

        return CRUISE_DAO.addNewCruise(cruise);
    }

    /**
     * Responsible for getting the list of all Cruises from DB.
     *
     * @return {@code List<Cruise>} the list of all Cruises from DB.
     */
    @Override
    public List<Cruise> getAllCruises() {

        List<Cruise> cruises = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = CRUISE_DAO.getAllCruises(connection);

            cruises = getCruisesFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cruises;
    }

    /**
     * Responsible for building the list of Cruises instance from the list of data encapsulated in ResultSet.
     *
     * @param resultSet the {@code ResultSet} from {@code getAllCruises()} method.
     * @return {@code List<Cruise>} from ResultSet.
     * @throws SQLException when persist DB fails
     */
    private List<Cruise> getCruisesFromResultSet(ResultSet resultSet) throws SQLException {

        List<Cruise> cruises = new ArrayList<>();

        while (resultSet.next()) {

            Cruise cruise = new CruiseBuilder()
                    .buildId(resultSet.getInt(1))
                    .buildStartDate(resultSet.getDate(2).toLocalDate())
                    .buildFinishDate(resultSet.getDate(3).toLocalDate())
                    .buildRoute(resultSet.getString(4))
                    .buildShipId(resultSet.getInt(5))
                    .buildDuration()
                    .build();

            cruises.add(cruise);
        }
        return cruises;
    }

    /**
     * Responsible for building the list of ShipIds instance from the list of data encapsulated in ResultSet.
     *
     * @param resultSet the {@code ResultSet} from {@code getShipIdListByDateDuration()} method.
     * @return {@code List<Integer>} from ResultSet.
     * @throws SQLException when persist DB fails
     */
    private List<Integer> getShipIdsFromResultSet(ResultSet resultSet) throws SQLException {

        List<Integer> shipIds = new ArrayList<>();

        while (resultSet.next()) {

            shipIds.add(resultSet.getInt(1));
        }
        return shipIds;
    }

    /**
     * Responsible for building the list of CruiseIds instance from the list of data encapsulated in ResultSet.
     *
     * @param resultSet the {@code ResultSet} from {@code getCruiseIdListByStartDateDuration()} method.
     * @return {@code List<Integer>} from ResultSet.
     * @throws SQLException when persist DB fails
     */
    private List<Integer> getCruiseIdsFromResultSet(ResultSet resultSet) throws SQLException {

        List<Integer> cruiseIds = new ArrayList<>();

        while (resultSet.next()) {

            cruiseIds.add(resultSet.getInt(1));
        }
        return cruiseIds;
    }

    /**
     * Responsible for deleting specified Cruise from DB.
     *
     * @param cruiseId instance of {@code int} Parameter specifies Cruise.
     * @throws SQLException when persist DB fails
     */
    @Override
    public void deleteCruiseById(int cruiseId) throws SQLException {
        CRUISE_DAO.deleteCruiseById(cruiseId);
    }

    /**
     * Responsible for updating Cruise in DB
     *
     * @param cruise the {@code Cruise} entity class .
     * @throws SQLException when persist DB fails
     */
    @Override
    public void updateCruise(Cruise cruise) throws SQLException {
        CRUISE_DAO.updateCruise(cruise);
    }

    /**
     * Responsible for getting Cruise instance with specified id.
     *
     * @param cruiseId the {@code int} parameter specifies Cruise.
     * @return {@code Cruise} instance with specified id.
     */
    @Override
    public Cruise getCruiseById(int cruiseId) {

        Cruise cruise = new Cruise();

        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = CRUISE_DAO.getCruiseById(cruiseId, connection);

            cruise = getCruiseFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cruise;
    }

    /**
     * Responsible for building Cruise instance from ResultSet.
     *
     * @param resultSet the {@code ResultSet} from {@code getCruiseById()} method instance
     *                  encapsulating data of Cruise.
     * @return {@code Cruise} from ResultSet.
     * @throws SQLException when persist DB fails
     */
    private Cruise getCruiseFromResultSet(ResultSet resultSet) throws SQLException {

        Cruise cruise = new Cruise();

        while (resultSet.next()) {

            cruise = new CruiseBuilder()
                    .buildId(resultSet.getInt(1))
                    .buildStartDate(resultSet.getDate(2).toLocalDate())
                    .buildFinishDate(resultSet.getDate(3).toLocalDate())
                    .buildRoute(resultSet.getString(4))
                    .buildShipId(resultSet.getInt(5))
                    .buildDuration()
                    .build();
        }
        return cruise;
    }

    /**
     * Responsible for getting shipId List with specified LocalDate and int from DB.
     *
     * @param startDate instance of {@code LocalDate} Parameter specifies local date.
     * @param period    instance of {@code int} Parameter specifies period.
     * @return {@code List} of {@code Integer} with specified shipIds List.
     */
    public List<Integer> getShipIdListByDateDuration(LocalDate startDate, int period) {

        List<Integer> finalShpIds = new ArrayList<>();
        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = CRUISE_DAO.getShipIdListByStartDate(startDate, connection);

            List<Integer> shpIds = getShipIdsFromResultSet(resultSet);

            List<Cruise> allCruises = getAllCruises();
            for (Cruise cruise : allCruises) {
                if (cruise.getDuration() >= period && shpIds.contains(cruise.getShipId())) {
                    finalShpIds.add(cruise.getShipId());
                }
            }

            MySQLConnectorManager.commitTransaction(connection);
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Set<Integer> set = new HashSet<Integer>(finalShpIds);
        finalShpIds.clear();
        finalShpIds.addAll(set);
        return finalShpIds;
    }

    /**
     * Responsible for getting cruiseId  with specified LocalDate and String from DB.
     *
     * @param startDate instance of {@code LocalDate} Parameter specifies local date.
     * @param route     instance of {@code String} Parameter specifies route.
     * @return int with specified cruiseId.
     */
    public int getCruiseIdByDateRoute(LocalDate startDate, String route) {
        int cruiseId = 0;
        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = CRUISE_DAO.getCruiseIdByDateRoute(startDate, route, connection);

            cruiseId = getCruiseIdsFromResultSet(resultSet).get(0);

            MySQLConnectorManager.commitTransaction(connection);
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cruiseId;
    }

    /**
     * Responsible for getting cruiseId List with specified LocalDate and int from DB.
     *
     * @param startDate instance of {@code LocalDate} Parameter specifies local date.
     * @param period    instance of {@code int} Parameter specifies period.
     * @return {@code List} of {@code Integer} with specified cruiseIds List.
     */
    public List<Integer> getCruiseIdListByStartDateDuration(LocalDate startDate, int period) {

        List<Integer> finalCruiseIds = new ArrayList<>();
        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = CRUISE_DAO.getCruiseIdListByStartDate(startDate, connection);

            List<Integer> cruiseIds = getCruiseIdsFromResultSet(resultSet);

            for (Integer cruiseId : cruiseIds) {
                ResultSet cruiseResultId = CRUISE_DAO.getCruiseById(cruiseId, connection);
                if (getCruiseFromResultSet(cruiseResultId).getDuration() >= period) {
                    finalCruiseIds.add(cruiseId);
                }
            }
            if (finalCruiseIds.size() == 0) {
                LOGGER.info("No cruises for " + period + " days on " + startDate.toString() + " are available");
            }
            MySQLConnectorManager.commitTransaction(connection);
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Set<Integer> set = new HashSet<Integer>(finalCruiseIds);
        finalCruiseIds.clear();
        finalCruiseIds.addAll(set);
        return finalCruiseIds;
    }

}
