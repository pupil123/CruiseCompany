package domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.Objects;

/**
 {@code Cruise} entity class
 *
 */
public class Cruise {
    private int id;
    private int shipId;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate finishDate;
    private int duration;
    private String route;

    public void setId(int id) {
        this.id = id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public int getId() {
        return id;
    }

    public int getShipId() {
        return shipId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public int getDuration() {
        return duration;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cruise)) return false;
        Cruise cruise = (Cruise) o;
        return getId() == cruise.getId() &&
                getShipId() == cruise.getShipId() &&
                getDuration() == cruise.getDuration() &&
                getStartDate().equals(cruise.getStartDate()) &&
                getFinishDate().equals(cruise.getFinishDate()) &&
                getRoute().equals(cruise.getRoute());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getShipId(), getStartDate(), getFinishDate(), getDuration(), getRoute());
    }
}
