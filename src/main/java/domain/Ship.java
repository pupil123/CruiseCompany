package domain;

import java.util.Objects;

/**
 {@code Ship} entity class
 *
 */
public class Ship {

    private int id;
    private int capacity;
    private int numberPortsVisited;
    private String staffs;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setNumberPortsVisited(int numberPortsVisited) {
        this.numberPortsVisited = numberPortsVisited;
    }

  /*  public void setRoutes(String routes) {
        this.routes = routes;
    }*/

    public void setStaffs(String staffs) {
        this.staffs = staffs;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNumberPortsVisited() {
        return numberPortsVisited;
    }

   /* public String getRoutes() {
        return routes;
    }

*/    public String getStaffs() {
        return staffs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ship)) return false;
        Ship ship = (Ship) o;
        return getId() == ship.getId() &&
                getCapacity() == ship.getCapacity() &&
                getNumberPortsVisited() == ship.getNumberPortsVisited() &&
                getStaffs().equals(ship.getStaffs());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCapacity(), getNumberPortsVisited(), getStaffs());
    }
}
