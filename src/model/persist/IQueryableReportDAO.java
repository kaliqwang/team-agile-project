package model.persist;

import model.Location;

import java.util.List;

/**
 * Interface for a queryable report data access object
 * @param <T> type of the underlying data object
 * @param <PK> primary key of the object
 */
public interface IQueryableReportDAO<T,PK> extends IGenericDAO<T,PK> {

    /**
     * Gets all the entries by location.
     * @param l location input to find entries at that location.
     * @return array list of all the entries by location.
     */
    List<T> getAllByLocation(Location l);
    /**
     * Gets all the entries by year.
     * @param y year input to find entries at that year.
     * @return array list of all the entries by location.
     */
    List<T> getAllByYear(Integer y);

}
