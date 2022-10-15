package service;

import dao.impl.ShipDAOImpl;
import domain.*;
import domain.builder.CruiseBuilder;
import domain.builder.OrderBuilder;
import domain.builder.ShipBuilder;
import domain.builder.UserBuilder;
import org.junit.Assert;
import org.junit.Test;
import service.impl.ShipServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class ShipServiceImplTest {
    private ShipService shipService = new ShipServiceImpl(new ShipDAOImpl());

    @Test
    public void shouldAddNewShip() throws SQLException, IOException {
        Ship ship1 = new ShipBuilder()
                .buildCapacity(20)
                .buildNumberPortsVisited(5)
                .buildStuffs("MyStaff")
                .build();

        int shipId = shipService.addNewShip(ship1);
        ship1.setId(shipId);
        Ship ship = shipService.getShipById(shipId);

        Assert.assertEquals(ship.getId(), shipId);
        Assert.assertEquals(ship1, ship);

        shipService.deleteShipById(shipId);

    }

    @Test
    public void shouldUpdateShip() throws SQLException, IOException {

        Ship ship1 = saveShip();

        ship1.setStaffs("MyStaff1");

        shipService.updateShip(ship1);

        Ship ship3 = shipService.getShipById(ship1.getId());

        Assert.assertEquals(ship3.getStaffs(), ship1.getStaffs());
        shipService.deleteShipById(ship1.getId());

    }

    @Test
    public void shouldDeleteShip() throws SQLException, IOException {

        List<Ship> allShips1 = shipService.getAllShips();

        Ship ship1 = saveShip();

        shipService.deleteShipById(ship1.getId());

        List<Ship> allShips2 = shipService.getAllShips();

        Assert.assertEquals(allShips1, allShips2);
    }

    @Test
    public void shouldGetAllShips() throws SQLException, IOException {
        List<Ship> allShips1 = shipService.getAllShips();
        Ship ship1 = saveShip();
        List<Ship> allShips2 = shipService.getAllShips();

        Assert.assertEquals(allShips1.size() + 1, allShips2.size());
        shipService.deleteShipById(ship1.getId());
    }

        private Ship saveShip() throws IOException, SQLException {
        Ship ship = new ShipBuilder()
                .buildCapacity(20)
                .buildNumberPortsVisited(5)
                .buildStuffs("MyStaff")
                .build();

        int shipId = shipService.addNewShip(ship);

        ship.setId(shipId);

        return ship;

    }
}
