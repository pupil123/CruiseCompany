package service;

import dao.impl.CruiseDAOImpl;
import dao.impl.ShipDAOImpl;
import domain.*;
import domain.builder.CruiseBuilder;
import domain.builder.OrderBuilder;
import domain.builder.ShipBuilder;
import domain.builder.UserBuilder;
import org.junit.Assert;
import org.junit.Test;
import service.impl.CruiseServiceImpl;
import service.impl.ShipServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CruiseServiceImplTest {

    private CruiseService cruiseService = new CruiseServiceImpl(new CruiseDAOImpl());
    private ShipService shipService = new ShipServiceImpl(new ShipDAOImpl());

    @Test
    public void shouldAddNewCruise() throws SQLException {

        Ship ship = new ShipBuilder()
                .buildCapacity(20)
                .buildNumberPortsVisited(5)
                .buildStuffs("MyStuff")
                .build();

        int shipId = shipService.addNewShip(ship);

        Cruise cruise = new CruiseBuilder()
                .buildStartDate(LocalDate.of(2021, Month.JANUARY, 5))
                .buildFinishDate(LocalDate.of(2021, Month.APRIL, 5))
                .buildRoute("MyRoute")
                .buildDuration()
                .buildShipId(shipId)
                .build();

        int cruiseId = cruiseService.addNewCruise(cruise);

        Cruise cruise1 = cruiseService.getCruiseById(cruiseId);

        Assert.assertEquals(cruise.getRoute(), cruise1.getRoute());

        deleteTestItems(cruise1);
    }

    @Test
    public void shouldUpdateCruise() throws SQLException, IOException {

        Cruise cruise1 = saveCruise();

        cruise1.setRoute("My route");

        cruiseService.updateCruise(cruise1);

        Cruise cruise3 = cruiseService.getCruiseById(cruise1.getId());

        Assert.assertEquals(cruise3.getRoute(), cruise1.getRoute());
        deleteTestItems(cruise1);

    }

    @Test
    public void shouldDeleteCruise() throws SQLException, IOException {

        List<Cruise> allCruises1 = cruiseService.getAllCruises();

        Cruise cruise1 = saveCruise();

        cruiseService.deleteCruiseById(cruise1.getId());

        List<Cruise> allCruises2 = cruiseService.getAllCruises();

        Assert.assertEquals(allCruises1, allCruises2);

        deleteTestItems(cruise1);

    }

    @Test
    public void shouldGetCruiseById() throws SQLException, IOException {

        Cruise cruise1 = saveCruise();

        Cruise cruise2 = cruiseService.getCruiseById(cruise1.getId());

        Assert.assertEquals(cruise1, cruise2);
        deleteTestItems(cruise1);
    }

    @Test
    public void shouldGetShipIdListByDateDuration() throws SQLException, IOException {
        List<Integer> finalShpIds = new ArrayList<>();
        Cruise cruise1 = saveCruise();
        List<Cruise> allCruises1 = cruiseService.getAllCruises();
        List<Integer> shipIds = new ArrayList();
        List<Integer> shipIds1 =
                cruiseService.getShipIdListByDateDuration(cruise1.getStartDate(), cruise1.getDuration());
        for (Cruise cruise : allCruises1) {
            if (cruise.getStartDate().isEqual(cruise1.getStartDate()) &&
                    cruise.getDuration() == cruise.getDuration())
                shipIds.add(cruise.getShipId());
        }
        Set<Integer> set = new HashSet<Integer>(shipIds);
        finalShpIds.clear();
        finalShpIds.addAll(set);

        boolean b = shipIds1.containsAll(finalShpIds) && finalShpIds.containsAll(shipIds1);
        Assert.assertTrue(b);
        deleteTestItems(cruise1);
    }

    @Test
    public void shouldGetCruiseIdByDateRoute() throws SQLException, IOException {

        Cruise cruise1 = saveCruise();
        List<Cruise> allCruises1 = cruiseService.getAllCruises();
        List<Integer> cruiseIds = new ArrayList();
        for (Cruise cruise : allCruises1) {
            if (cruise.getStartDate().isEqual(cruise1.getStartDate()) &&
                    cruise.getRoute().equals(cruise1.getRoute()))
                cruiseIds.add(cruise.getId());
        }
        Assert.assertEquals(cruiseIds.get(0),
                cruiseService.getCruiseIdByDateRoute(cruise1.getStartDate(), cruise1.getRoute()), 0.5);
        deleteTestItems(cruise1);
    }


    @Test
    public void shouldGetCruiseIdListByStartDateDuration() throws SQLException, IOException {
        List<Integer> finalCruiseIds = new ArrayList<>();
        Cruise cruise1 = saveCruise();
        List<Integer> cruiseIds1 =
                cruiseService.getCruiseIdListByStartDateDuration(cruise1.getStartDate(), cruise1.getDuration());
        List<Cruise> allCruises1 = cruiseService.getAllCruises();
        List<Integer> cruiseIds = new ArrayList();
        for (Cruise cruise : allCruises1) {
            if (cruise.getStartDate().isEqual(cruise1.getStartDate()) &&
                    cruise.getDuration() >= cruise1.getDuration())
                cruiseIds.add(cruise.getId());
        }
        Set<Integer> set = new HashSet<>(cruiseIds);
        finalCruiseIds.clear();
        finalCruiseIds.addAll(set);

        boolean b = finalCruiseIds.containsAll(cruiseIds1) && cruiseIds1.containsAll(finalCruiseIds);
        Assert.assertTrue(b);
        deleteTestItems(cruise1);
    }

    @Test
    public void shouldGetAllCruises() throws SQLException, IOException {
        List<Cruise> allCruises1 = cruiseService.getAllCruises();
        Cruise cruise1 = saveCruise();
        List<Cruise> allCruises2 = cruiseService.getAllCruises();

        Assert.assertEquals(allCruises1.size() + 1, allCruises2.size());
        deleteTestItems(cruise1);
    }

    private Cruise saveCruise() throws IOException, SQLException {
        Ship ship = new ShipBuilder()
                .buildCapacity(20)
                .buildNumberPortsVisited(5)
                .buildStuffs("MyStuff")
                .build();

        int shipId = shipService.addNewShip(ship);

        Cruise cruise = new CruiseBuilder()
                .buildStartDate(LocalDate.of(2021, Month.JANUARY, 5))
                .buildFinishDate(LocalDate.of(2021, Month.APRIL, 5))
                .buildDuration()
                .buildRoute("MyRoute")
                .buildShipId(shipId)
                .build();

        int cruiseId = cruiseService.addNewCruise(cruise);

        cruise.setId(cruiseId);

        return cruise;

    }

    private void deleteTestItems(Cruise cruise1) throws SQLException {
        shipService.deleteShipById(cruise1.getShipId());
        cruiseService.deleteCruiseById(cruise1.getId());
    }

}
