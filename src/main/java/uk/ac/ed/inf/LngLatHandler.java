package uk.ac.ed.inf;

import uk.ac.ed.inf.ilp.data.LngLat;
import uk.ac.ed.inf.ilp.interfaces.LngLatHandling;
import java.lang.Math;

public class LngLatHandler implements LngLatHandling {
    @Override
    public double distanceTo (LngLat startPosition, LngLat endPosition){
        // Implement the distance Algo from specification.
        // get x1 from (x1,y1) -> startPosition
        double x1 = startPosition.lng();
        double x2 = endPosition.lng();
        double y1 = startPosition.lat();
        double y2 = endPosition.lat();

        // Apply the Algo (This is measured in Degrees)
       return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));

    }

    public boolean isCloseTo (LngLat startPosition, LngLat otherPosition){
        // *Simplified if statement* : If less than 0.00015 return True
        // This is taken directly from the specification
        return distanceTo(startPosition, otherPosition) < 0.00015;
    }
}
