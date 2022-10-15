package command;

import dao.impl.CruiseDAOImpl;
import dao.impl.ShipDAOImpl;
import dao.impl.UserDAOImpl;
import domain.Cruise;
import domain.Ship;
import domain.User;
import domain.builder.CruiseBuilder;
import domain.builder.ShipBuilder;
import domain.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import service.CruiseService;
import service.ShipService;
import service.UserService;
import service.impl.CruiseServiceImpl;
import service.impl.ShipServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

@RunWith(MockitoJUnitRunner.class)
public class AddNewOrderCommandTest {

    @Spy
    private HttpServletRequest request;

    private UserService userService = new UserServiceImpl(new UserDAOImpl());
    private CruiseService cruiseService = new CruiseServiceImpl(new CruiseDAOImpl());
    private ShipService shipService = new ShipServiceImpl(new ShipDAOImpl());


    @Test
    public void shouldAddNewOrder() throws SQLException {
        User user = new UserBuilder()
                .buildPassword("123456")
                .buildLogin("a")
                .buildFirstName("A")
                .buildLastName("V")
                .build();
        int userId = userService.addNewUser(user);

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
                .buildShipId(shipId)
                .build();

        int cruiseId = cruiseService.addNewCruise(cruise);

        request.setAttribute("userId", userId);
        request.setAttribute("startdate", "2022-10-10");

       
        userService.deleteUserById(userId);
        cruiseService.deleteCruiseById(cruiseId);

    }
}
