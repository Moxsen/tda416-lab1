/**
 * 
 */
package lab1;

import java.awt.Point;

/**
 * @author Grupp 36, Andreas Johansson, 8711044654
 *
 */
public class distanceBetweenPoints {

	public static double distanceBetweenPoints(int x1, int y1, int x2, int y2) {
		return(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
	}
	
	public static double distanceBetweenPoints(Point a, Point b) {
		//return Point.distance(a.getX(), a.getY(), b.getX(), b.getY());
		return a.distance(b);
	}

}
