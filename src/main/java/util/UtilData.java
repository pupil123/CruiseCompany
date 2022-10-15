package util;



import controller.Command;
import controller.impl.cruise.*;
import controller.impl.order.*;
import controller.impl.ship.*;
import controller.impl.user.*;
import dao.CruiseDAO;
import dao.OrderDAO;
import dao.ShipDAO;
import dao.UserDAO;
import dao.impl.CruiseDAOImpl;
import dao.impl.OrderDAOImpl;
import dao.impl.ShipDAOImpl;
import dao.impl.UserDAOImpl;
import service.CruiseService;
import service.OrderService;
import service.ShipService;
import service.UserService;
import service.impl.CruiseServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.ShipServiceImpl;
import service.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static util.Constants.*;


/**
 * The {@code UtilData} class is responsible for holding all service collections including application data
 */
public class UtilData {

    public static final Map<String, Command> COMMANDS_MAP = new HashMap<>();

    static {
        ShipDAO shipDAO = new ShipDAOImpl();
        ShipService shipService = new ShipServiceImpl(shipDAO);
        CruiseDAO cruiseDAO = new CruiseDAOImpl();
        CruiseService cruiseService = new CruiseServiceImpl(cruiseDAO);
        UserDAO userDAO = new UserDAOImpl();
        UserService userService = new UserServiceImpl(userDAO);
        OrderDAO orderDAO = new OrderDAOImpl();
        OrderService orderService = new OrderServiceImpl(orderDAO);


        COMMANDS_MAP.put(REGISTER_NEW_ORDER_COMMAND, new AddNewOrderCommand(orderService));
        COMMANDS_MAP.put(REGISTER_NEW_SHIP_COMMAND, new AddNewShipCommand(shipService));
        COMMANDS_MAP.put(REGISTER_NEW_CRUISE_COMMAND, new AddNewCruiseCommand(cruiseService));
        COMMANDS_MAP.put(REGISTER_DELETE_CRUISE_BY_ID_COMMAND, new DeleteCruiseByIdCommand(cruiseService));
        COMMANDS_MAP.put(REGISTER_DELETE_SHIP_BY_ID_COMMAND, new DeleteShipByIdCommand(shipService));
        COMMANDS_MAP.put(REGISTER_UPDATE_CRUISE_COMMAND, new UpdateCruiseCommand(cruiseService));
        COMMANDS_MAP.put(REGISTER_UPDATE_SHIP_COMMAND, new UpdateShipCommand(shipService));
        COMMANDS_MAP.put(REGISTER_GET_CRUISE_BY_ID_COMMAND, new GetCruiseByIdCommand(cruiseService));
        COMMANDS_MAP.put(REGISTER_GET_SHIP_BY_ID_COMMAND, new GetShipByIdCommand(shipService));
        COMMANDS_MAP.put(REGISTER_GET_ALL_SHIPS_COMMAND, new GetAllShipsCommand(shipService));
        COMMANDS_MAP.put(REGISTER_GET_ALL_CRUISES_COMMAND, new GetAllCruisesCommand(cruiseService));
        COMMANDS_MAP.put(REGISTER_NEW_USER_COMMAND, new AddNewUserCommand(userService));
        COMMANDS_MAP.put(REGISTER_DELETE_USER_BY_ID_COMMAND, new DeleteUserByIdCommand(userService));
        COMMANDS_MAP.put(REGISTER_UPDATE_USER_COMMAND, new UpdateUserCommand(userService));
        COMMANDS_MAP.put(REGISTER_GET_USER_BY_ID_COMMAND, new GetUserByIdCommand(userService));
        COMMANDS_MAP.put(REGISTER_GET_ALL_USERS_COMMAND, new GetAllUsersCommand(userService));
        COMMANDS_MAP.put(REGISTER_NEW_ORDER_COMMAND, new AddNewOrderCommand(orderService));
        COMMANDS_MAP.put(REGISTER_UPDATE_ORDER_COMMAND, new UpdateOrderCommand(orderService));
        COMMANDS_MAP.put(REGISTER_DELETE_ORDER_BY_ID_COMMAND, new DeleteOrderByIdCommand(orderService));
        COMMANDS_MAP.put(REGISTER_GET_ALL_ORDERS_COMMAND, new GetAllOrdersCommand(orderService));
        COMMANDS_MAP.put(REGISTER_GET_ORDER_BY_ID_COMMAND, new GetOrderByIdCommand(orderService));
        COMMANDS_MAP.put(REGISTER_GET_SHIPS_BY_DATE_DURATION_COMMAND, new GetShipIdsByDateDurationCommand(cruiseService));
    }

   /* static {


        RouteDAO routeDAO = new JDBCRouteDAO();
        StationDAO stationDAO = new JDBCStationDAO();
        TrainDAO trainDAO = new JDBCTrainDAO();
        TicketDAO ticketDAO = new JDBCTicketDAO();
        UserDAO userDAO = new JDBCUserDAO();

        MailService mailService = DAOFactory.getDAOFactory().getMailService();
        StationService stationService = DAOFactory.getDAOFactory().getStationService(stationDAO, routeDAO);
        RouteService routeService = DAOFactory.getDAOFactory().getRouteService(routeDAO, stationService, trainDAO, stationDAO);
        TicketService ticketService = DAOFactory.getDAOFactory().getTicketService(ticketDAO, stationService, mailService);
        TrainService trainService = DAOFactory.getDAOFactory().getTrainService(trainDAO);
        UserService userService = DAOFactory.getDAOFactory().getUserService(userDAO);

        COMMANDS_MAP.put(REGISTER_NEW_USER_COMMAND, new RegisterNewUserCommand(userService));
        COMMANDS_MAP.put(VALIDATE_USER_PASSWORD_COMMAND, new ValidateUserPasswordCommand(userService));
        COMMANDS_MAP.put(ADD_NEW_STATION_COMMAND, new AddNewStationCommand(stationService));
        COMMANDS_MAP.put(GET_ALL_STATIONS_COMMAND, new GetAllStationsCommand(stationService));
        COMMANDS_MAP.put(GET_STATION_BY_ID_COMMAND, new GetStationByIdCommand(stationService));
        COMMANDS_MAP.put(UPDATE_STATION_COMMAND, new UpdateStationCommand(stationService));
        COMMANDS_MAP.put(DELETE_STATION_COMMAND, new DeleteStationCommand(stationService));
        COMMANDS_MAP.put(ADD_NEW_ROUTE_COMMAND, new AddNewRouteCommand(routeService));
        COMMANDS_MAP.put(GET_ALL_ROUTES_COMMAND, new GetAllRoutesCommand(routeService));
        COMMANDS_MAP.put(GET_ROUTE_BY_ID_COMMAND, new GetRouteByIdCommand(routeService));
        COMMANDS_MAP.put(ADD_INTERMEDIATE_STATION_COMMAND, new AddIntermediateStationCommand(routeService, stationService));
        COMMANDS_MAP.put(GET_INTERMEDIATE_STATIONS_BY_ROUTE, new GetIntermediateStationsByRouteCommand(routeService));
        COMMANDS_MAP.put(DELETE_INTERMEDIATE_STATION_BY_ID_COMMAND, new DeleteIntermediateStationByIdCommand(stationService));
        COMMANDS_MAP.put(DELETE_ROUTE_BY_ID_COMMAND, new DeleteRouteByIdCommand(routeService));
        COMMANDS_MAP.put(ADD_NEW_TRAIN_COMMAND, new AddNewTrainCommand(trainService));
        COMMANDS_MAP.put(GET_ALL_TRAINS_COMMAND, new GetAllTrainsCommand(trainService));
        COMMANDS_MAP.put(GET_TRAIN_BY_ID_COMMAND, new GetTrainByIdCommand(trainService));
        COMMANDS_MAP.put(UPDATE_TRAIN_COMMAND, new UpdateTrainCommand(trainService));
        COMMANDS_MAP.put(DELETE_TRAIN_COMMAND, new DeleteTrainByIdCommand(trainService));
        COMMANDS_MAP.put(SET_TRAIN_COMMAND, new SetTrainToRouteCommand(routeService));
        COMMANDS_MAP.put(SHOW_TRAINS_COMMAND, new ShowTrainsBetweenStationsCommand(trainService));
        COMMANDS_MAP.put(GET_TICKETS_COUNT_COMMAND, new GetTicketsCountCommand(ticketService));
        COMMANDS_MAP.put(GET_STATIONS_BY_TRIP_COMMAND, new GetIntermediateStationsByTripCommand(stationService));
        COMMANDS_MAP.put(BUY_TICKETS_COMMAND, new BuyTicketsCommand(ticketService));
    }*/

}
