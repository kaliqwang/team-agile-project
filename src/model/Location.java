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

    public int getPK() { return _pk.get(); }
    public void setPK(int pk) { _pk.set(pk); }

    public String getName() { return _name.get(); }
    public void setName(String name) { _name.set(name); }

    public double getLatitude() { return _latitude.get(); }
    public void setLatitude(double latitude) { _latitude.set(latitude); }

    public double getLongitude() { return _longitude.get(); }
    public void setLongitude(double longitude) { _longitude.set(longitude); }

    public Location() {
        _pk.set(-1);
    }

    public Location (Data plainData) {
        _pk.set(plainData.pk);
        _name.set(plainData.name);
        _latitude.set(plainData.latitude);
        _longitude.set(plainData.longitude);
    }

    public Data getPlainData() {
        return new Data(_pk.get(), _name.get(), _latitude.get(), _longitude.get());
    }

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
