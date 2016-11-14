package model;

import javafx.beans.property.*;

/**
 * Created by kaliq on 11/1/2016.
 */
public class Location {

    private final IntegerProperty _pk = new SimpleIntegerProperty();
    private final StringProperty _name = new SimpleStringProperty();
    private final DoubleProperty _latitude = new SimpleDoubleProperty();
    private final DoubleProperty _longitude = new SimpleDoubleProperty();


    /**
     * This method is a getter for the pk.
     * @return the pk of the location.
     */
    public int getPK() { return _pk.get(); }

    /**
     * this method is a setter for the pk.
     * @param pk the pk that is going to be set for a location object.
     */
    public void setPK(int pk) { _pk.set(pk); }

    /**
     * This method is a getter for the name of the location.
     * @return the name of the location.
     */
    public String getName() { return _name.get(); }

    /**
     * This method is a setter for the name of the location.
     * @param name the name of the location
     */
    public void setName(String name) { _name.set(name); }

    /**
     * This method is a getter for the latitude of the location.
     * @return the latitude of the location.
     */
    public double getLatitude() { return _latitude.get(); }

    /**
     * This method is the setter for the latitude of the location.
     * @param latitude the latitude of the location
     */
    public void setLatitude(double latitude) { _latitude.set(latitude); }

    /**
     * This method is a getter for the longitude of the location.
     * @return the longitude of the location.
     */
    public double getLongitude() { return _longitude.get(); }

    /**
     * This method is the setter for the longitude of the location.
     * @param longitude the longitude of the location
     */
    public void setLongitude(double longitude) { _longitude.set(longitude); }

    /**
     * This method initializes a location with a pk of -1
     */
    public Location() {
        _pk.set(-1);
    }

    /**
     * This method initializes a location with the passed in data
     * @param plainData the data to be assigned to the location
     */
    public Location (Data plainData) {
        _pk.set(plainData.pk);
        _name.set(plainData.name);
        _latitude.set(plainData.latitude);
        _longitude.set(plainData.longitude);
    }

    /**
     * This method takes the plain data and returns its different attributes
     * @return the different attriubutes of the data
     */
    public Data getPlainData() {
        return new Data(_pk.get(), _name.get(), _latitude.get(), _longitude.get());
    }

    /**
     * This is the class that defines a data object and its creation
     */
    public class Data {
        private int pk;
        private String name;
        private double latitude;
        private double longitude;

        public Data(int pk, String name, double latitude, double longitude) {
            this.pk = pk;
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Location))return false;
        Location that = (Location) other;
        if (that._pk == this._pk) return true;
        return false;
    }

    @Override
    public String toString(){
        return _name.get() + " (" + _latitude.get() + ", " + _longitude.get() + ")";
    }
}
