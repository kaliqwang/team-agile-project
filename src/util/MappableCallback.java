package util;

import com.lynden.gmapsfx.javascript.object.LatLong;

/**
 * Created by Max Yang on 10/26/2016.
 */
public interface MappableCallback {
    void coordsSelected(LatLong coords);
}
