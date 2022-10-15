package util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Constants {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final String FRONT_CONTROLLER_SERVLET = "/seaport";
    public static final String FRONT_CONTROLLER_SERVLET_COMMON = "/seaport/*";
    public static final String MAIN_CONTROLLER_SERVLET = "/main/page";

    public static final String USER_CONTROLLER_SERVLET = "/user";
    public static final String ADMIN_ORDER_STATUS_CONTROLLER_SERVLET = "/admin/order/status";
    public static final String USER_CONTROLLER_SERVLET_COMMON = "/user/*";
    public static final String USER_MAKES_ORDER_SERVLET = "/user/order";
    public static final String DISPLAY_ADD_ORDER_SERVLET="/display/addorder";
    public static final String DISPLAY_ADD_USER_SERVLET="/display/adduser";
    public static final String DISPLAY_ADD_CRUISE_SERVLET ="/display/addcruise";
    public static final String DISPLAY_ADD_SHIP_SERVLET ="/display/addship";

    //filters
    public static final String FILTER_CRUISE = "/filter/cruise";
    public static final String FILTER_CRUISE_OUT = "/filter/cruise/out";
    public static final String FILTER_ORDER = "/filter/order";
    public static final String FILTER_ORDER_OUT = "/filter/order/out";
    public static final String FILTER_ORDER_STATUS = "/filter/order/status";
    public static final String FILTER_REGIST = "/filter/regist";
    public static final String FILTER_REGIST_OUT = "/filter/regist/out";
    public static final String FILTER_INDEX_OUT = "/filter/index/out";
    public static final String FILTER_SHIP = "/filter/ship";
    public static final String FILTER_SHIP_OUT = "/filter/ship/out";

    //ship
    public static final String SQL_ADD_NEW_SHIP =
            "INSERT INTO Ship(capacity, number_ports_visited, staffs) VALUES (?,?,?);";
    public static final String SQL_DELETE_SHIP_BY_ID = "DELETE FROM Ship WHERE ship_id = ?;";
    public static final String SQL_UPDATE_SHIP =
            "UPDATE Ship SET capacity = ?, number_ports_visited = ?, staffs = ? WHERE ship_id = ?;";
    public static final String SQL_GET_ALL_SHIPS = "SELECT * FROM Ship";
    public static final String SQL_GET_SHIP_BY_ID = "SELECT * FROM Ship WHERE ship_id = ?";


    //cruise
    public static final String SQL_ADD_NEW_CRUISE =
            "INSERT INTO Cruise(start_date, finish_date, route, FK_ship) VALUES (?,?,?,?);";
    public static final String SQL_DELETE_CRUISE_BY_ID = "DELETE FROM Cruise WHERE cruise_id = ?;";
    public static final String SQL_DELETE_CRUISE_BY_SHIP_ID =
            "DELETE FROM Cruise WHERE FK_ship = ?;";
    public static final String SQL_UPDATE_CRUISE =
            "UPDATE Cruise SET start_date = ?, finish_date = ?, route = ?, FK_ship = ? WHERE cruise_id = ?";
    public static final String SQL_GET_ALL_CRUISES = "SELECT * FROM Cruise";
    public static final String SQL_GET_CRUISE_BY_ID = "SELECT * FROM Cruise WHERE cruise_id = ?";
    public static final String SQL_GET_SHIP_BY_DATE =
            "SELECT FK_ship FROM Cruise WHERE start_date = ?";
    public static final String SQL_GET_CRUISEIDS_BY_DATE = "SELECT cr.cruise_id FROM Cruise AS cr where cr.start_date = ?";
    public static final String SQL_GET_CRUISE_ID_BY_DATE_ROUTE = "SELECT cr.cruise_id FROM Cruise AS cr INNER JOIN Ship AS sh ON cr.FK_ship = sh.ship_id WHERE cr.start_date = ? AND sh.routes = ?";
    public static final String SQL_GET_CRUISE_ID_BY_DATE_ROUTE2 = "SELECT cr.cruise_id FROM Cruise AS cr WHERE cr.start_date = ? AND cr.route = ?";
    //user
    public static final String SQL_ADD_NEW_USER =
            "INSERT INTO User(first_name, last_name, administrator, login, password_) VALUES (?,?,?,?,?);";
    public static final String SQL_DELETE_USER_BY_ID = "DELETE FROM User WHERE user_id = ?;";
    public static final String SQL_UPDATE_USER =
            "UPDATE User SET first_name = ?, last_name = ?, administrator = ?, login = ?, password_ = ? WHERE user_id = ?;";
    public static final String SQL_GET_ALL_USERS = "SELECT * FROM User";
    public static final String SQL_COUNT_USERS_WITH_LOGIN = "SELECT COUNT(*) FROM User u WHERE u.login = ? AND " +
            " u.password_ = ?";

    public static final String SQL_IS_ADMIN_USER = "SELECT u.administrator FROM User u WHERE u.login = ? AND u.password_ = ?";
    public static final String SQL_GET_USER_BY_ID = "SELECT * FROM User WHERE user_id = ?";
    public static final String SQL_GET_USER_ID_BY_LOG_PAS = "SELECT u.user_id FROM User u WHERE u.login = ? AND u.password_ = ?";
    //order
    public static final String SQL_ADD_NEW_ORDER =
            "INSERT INTO Order_(FK_user, FK_cruise, status, date) VALUES (?,?,?,?);";
    public static final String SQL_DELETE_ORDER_BY_ID = "DELETE FROM Order_ WHERE order_id = ?;";
        public static final String SQL_UPDATE_ORDER =
            "UPDATE Order_ SET FK_user = ?, FK_cruise = ?, status = ?, date = ? WHERE order_id = ?";
    public static final String SQL_GET_ALL_ORDERS = "SELECT * FROM Order_";
    public static final String SQL_GET_ORDER_BY_ID = "SELECT * FROM Order_ WHERE order_id = ?";
    public static final String SQL_GET_NUM_PAID_ORDERS_BY_CRUISEID =
            "SElECT COUNT(or_.order_id) AS count_ids FROM Order_ AS or_ INNER JOIN Cruise AS cr ON cr.cruise_id=or_.FK_cruise WHERE or_.status = 'PAID' AND cr.cruise_id = ?";
    public static final String SQL_GET_USERSIDS_FROM_ORDER_BY_CRUISEID =
            "SELECT DISTINCT us.user_id FROM Order_ AS ord INNER JOIN Cruise AS cr ON ord.FK_cruise = cr.cruise_id INNER JOIN User AS us ON ord.FK_user = us.user_id WHERE ord.FK_cruise = ?";
    public static final String SQL_GET_ORDERSIDS_BY_CRUISEID_AND_USERID =
            "SELECT  ord.order_id FROM Order_ as ord INNER JOIN Cruise AS cr ON ord.FK_cruise = cr.cruise_id INNER JOIN User AS us ON ord.FK_user = us.user_id WHERE ord.FK_cruise = ? AND ord.FK_user = ?";
    public static final String SQL_GET_ORDERSIDS_BY_CRUISE_FINISH_DATE ="SElECT or_.order_id FROM Order_ AS or_ INNER JOIN Cruise AS cr ON cr.cruise_id = or_.FK_cruise WHERE cr.finish_date = ?";

    //command ship urls:
    static final String REGISTER_NEW_SHIP_COMMAND = "/ship/add";
    static final String REGISTER_DELETE_SHIP_BY_ID_COMMAND = "/ship/delete";
    static final String REGISTER_UPDATE_SHIP_COMMAND = "/ship/update";
    static final String REGISTER_GET_SHIP_BY_ID_COMMAND = "/ship/find";
    static final String REGISTER_GET_ALL_SHIPS_COMMAND = "/ship/all";

    //command cruise urls:
    static final String REGISTER_NEW_CRUISE_COMMAND = "/cruise/add";
    static final String REGISTER_DELETE_CRUISE_BY_ID_COMMAND = "/cruise/delete";
    static final String REGISTER_UPDATE_CRUISE_COMMAND = "/admin/cruise/update";
    static final String REGISTER_GET_CRUISE_BY_ID_COMMAND = "/cruise/find";
    static final String REGISTER_GET_ALL_CRUISES_COMMAND = "/cruise/all";
    static final String REGISTER_GET_SHIPS_BY_DATE_DURATION_COMMAND = "/admin/cruise/ships";

    //command user urls:
    static final String REGISTER_NEW_USER_COMMAND = "/user/add";
    static final String REGISTER_DELETE_USER_BY_ID_COMMAND = "/user/delete";
    static final String REGISTER_UPDATE_USER_COMMAND = "/user/update";
    static final String REGISTER_GET_USER_BY_ID_COMMAND = "/user/find";
    static final String REGISTER_GET_ALL_USERS_COMMAND = "/user/all";

    //command order urls:
    static final String REGISTER_NEW_ORDER_COMMAND = "/order/add";
    static final String REGISTER_DELETE_ORDER_BY_ID_COMMAND = "/order/delete";
    static final String REGISTER_UPDATE_ORDER_COMMAND = "/order/update";
    static final String REGISTER_GET_ORDER_BY_ID_COMMAND = "/order/find";
    static final String REGISTER_GET_ALL_ORDERS_COMMAND = "/order/all";

    public static final String CONTENT_TYPE = "application/json";
    public static final String ENCODING = "UTF-8";

    public static final String COULD_NOT_PERSIST_CRUISE = "Could not persist Cruise";
    public static final String CREATING_CRUISE_FAILED_NO_ID_OBTAINED = "Creating cruise failed, no ID obtained.";
    public static final String COULD_NOT_PERSIST_ORDER = "Could not persist order";
    public static final String COULD_NOT_PERSIST_SHIP = "Could not persist ship";
    public static final String COULD_NOT_PERSIST_USER = "Could not persist user";
}
