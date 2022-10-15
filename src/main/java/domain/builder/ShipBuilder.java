package domain.builder;

import domain.Ship;

/**
 {@code ShipBuilder} responsible for building instance of entity class
 *
 */
public class ShipBuilder {

    private Ship ship;

    public ShipBuilder() {
        ship = new Ship();
    }

    public ShipBuilder buildId(int shipId) {
        this.ship.setId(shipId);
        return this;
    }

    public ShipBuilder buildCapacity(int capacity) {
        this.ship.setCapacity(capacity);
        return this;
    }

    public ShipBuilder buildNumberPortsVisited(int numberPortsVisited) {
        this.ship.setNumberPortsVisited(numberPortsVisited);
        return this;
    }

  /*  public ShipBuilder buildRoutes(String routes) {
        this.ship.setRoutes(routes);
        return this;
    }*/

    public ShipBuilder buildStuffs(String staffs) {
        this.ship.setStaffs(staffs);
        return this;
    }

    public Ship build() {
        return this.ship;
    }
}
