package domain.builder;

import domain.Cruise;

import java.time.LocalDate;
import java.time.Period;

/**
 {@code CruiseBuilder} responsible for building instance of entity class
 *
 */
public class CruiseBuilder {

    private Cruise cruise;

    public CruiseBuilder() {
        this.cruise = new Cruise();
    }

    public CruiseBuilder buildId(int cruiseId) {
        this.cruise.setId(cruiseId);
        return this;
    }

    public CruiseBuilder buildShipId(int shipId) {
        this.cruise.setShipId(shipId);
        return this;
    }

    public CruiseBuilder buildStartDate(LocalDate startDate) {
        this.cruise.setStartDate(startDate);
        return this;
    }

    public CruiseBuilder buildFinishDate(LocalDate finishDate) {
        this.cruise.setFinishDate(finishDate);
        return this;
    }


    public CruiseBuilder buildDuration() {

        LocalDate startDate = cruise.getStartDate();
        Period period = startDate.until(cruise.getFinishDate());

        this.cruise.setDuration(period.getDays()+365*period.getYears()+30*period.getMonths());
        return this;
    }

    public CruiseBuilder buildRoute(String route){
        this.cruise.setRoute(route);
        return this;
    }

    public Cruise build() {
        return this.cruise;
    }
}
