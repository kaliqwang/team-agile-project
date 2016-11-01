package model.persist;

/**
 * Interface for a generic data access object
 * @param <T> type of the underlying data object
 * @param <PK> primary key of the object
 */
public interface GenericDAO<T,PK> {
    /**
     * Adds a new object to the database
     * @param newObj the object to be added
     * @return true if successful
     */
    boolean persist(T newObj);

    /**
     * Updates the database with an object
     * @param pKey the primary key of the object to update
     * @param toUpdate the object to be updated
     * @return true if successful
     */
    boolean update(PK pKey, T toUpdate);

    /**
     * Removes an object from the data
     * @param pKey the primary key of the object to update
     * @return true if successful
     */
    boolean remove(PK pKey);

    /**
     * Gets an object from the database
     * @param pKey the primary key of the database
     * @return the object that was retrieved
     */
    T get(PK pKey);


}
