package uk.ac.ed.inf;

import uk.ac.ed.inf.ilp.data.LngLat;
import uk.ac.ed.inf.ilp.data.NamedRegion;
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
    @Override
    public boolean isInRegion(LngLat position, NamedRegion region) {
        int counter = 0;
        LngLat[] namedVertices = region.vertices();
        // Iterate through all the vertices
        for (int i = 0; i < namedVertices.length; i++){
            int b = i+1;
            // Add a condition so that [n] is looped back to [0] so all vertices are connected
            if (i == namedVertices.length-1){
                b = 0;
            }
            // Check if the point = a vertices (means it lies on the boundary, so we can end the loop
            if (position.lat() == namedVertices[i].lat() && position.lng() == namedVertices[i].lng()){
                counter = 1;
                break;
            }
            // Find gradient between i and i+1
            double verticesGradient = (namedVertices[b].lat() - namedVertices[i].lat()) /
                    (namedVertices[b].lng() - namedVertices[i].lng());
            // Check if the position is on the line that ranges from i to b I.E. the boundary, so we can end the loop
            if (position.lat()-namedVertices[i].lat() == verticesGradient * (position.lng() - namedVertices[i].lng())){
                if(position.lng() >= Math.min(namedVertices[i].lng(),namedVertices[b].lng()) &&
                        position.lng() <= Math.max(namedVertices[i].lng(),namedVertices[b].lng()) &&
                        position.lat() >= Math.min(namedVertices[i].lat(),namedVertices[b].lat()) &&
                        position.lat() <= Math.max(namedVertices[i].lat(),namedVertices[b].lat())){
                    counter = 1;
                    break;
                }
            }
            // Implement the Ray Casting Algo to find if the point(x,y) is within the polygon vertices
            // Check that Pos(y) lies between vertices i(y) -> i+1(y)
            if(position.lat() >= Math.min(namedVertices[i].lat(), namedVertices[b].lat())
                    && position.lat() <= Math.max(namedVertices[i].lat(), namedVertices[b].lat())){
                // Check that Pos(x) lies to the right of vertices i(x) or i+1(x)
                if (position.lng() < namedVertices[i].lng()
                        || position.lng() < namedVertices[b].lng()){
                    // Check that x intersects the line in which case we can increase the counter
                    double xIntersect = namedVertices[i].lng() + ((position.lat()-namedVertices[i].lat()) / verticesGradient);
                    if (xIntersect > position.lng()){
                        counter++;
                    }
                }
            }
        }
        // An odd counter returns true as per the Ray Casting Algo
        return counter % 2 != 0;
    }
    boolean isValidAngle(double angle) {
        // helper method for nextPosition
        double[] validAngles = {0, 22.5, 45, 67.5, 90, 112.5, 135, 157.5, 180,
                202.5, 225, 247.5, 270, 292.5, 315, 337.5};
        for (double validAngle : validAngles) {
            if (angle == validAngle) {
                return true;
            }
        }
        return false;
    }
    @Override
    public LngLat nextPosition(LngLat startPosition, double angle) {
        //First check for all the valid angles in the 16 point directions
        if (!isValidAngle(angle)) {
            throw new IllegalArgumentException("Invalid angle. Angle must be one of the 16-point compass directions.");
        }
        double moveDistance = 0.00015;
        // Use trig to find the new Lng and Lats
        // Start by finding the amount we have to increase by

        // X and Y movements based upon the angle (using trig)
        double increaseLngBy = moveDistance * Math.cos(Math.toRadians(angle));
        double increaseLatBy = moveDistance * Math.sin(Math.toRadians(angle));

        // Calculate the nextPosition
        double newLng = startPosition.lng() + increaseLngBy;
        double newLat = startPosition.lat() + increaseLatBy;

        return new LngLat(newLng,newLat);
    }
}

