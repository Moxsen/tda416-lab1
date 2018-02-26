package lab1;

import java.util.PriorityQueue;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Iterator;
//import java.io.FileNotFoundException;
import java.awt.Point;
import java.awt.Color;
import java.text.DecimalFormat;

// EH 2015 01 27
// EH 2017 01 25

/**
 * This is a specialized double linked list for removing points in small figures. 
 * It is not circular, the leftmost node has a 
 * null left pointer and the rigthmost node has a null right pointer
 * The nodes store a left and a right pointer and the nodes importance.
 */
public class DLList {

	// 1.0E300 is almost infinity (approx. = Double.MAX_VALUE 
	private static final double infinity = 100000; 
	//private DrawGraph shape;
	private PriorityQueue <Node> q = new PriorityQueue<Node>();

	// values to keep the maximum values in the shape
	int maxX = 0;
	int maxY = 0;
	static boolean debug = false;
	// för att kunna få addLast att sätta in sorterbara element i DLList så krävs det att vi kan buffra element.
	Node bufferedNode;

	/** this Node is the building block for our list. */
	private static class Node implements Comparable<Node> {
		Point p = null;
		double imp = infinity;  //importance of the point
		int nbr = -1;   // original position in list, only for debug

		private Node next = null; // The link to the next node.
		private Node prev = null; // The link to the previous node.

		private Node( Point p, int nbr ) { // nbr stores the original position in list
			this.p = p;
			this.nbr = nbr;
		}

		public int compareTo( Node n ) {
			double diff= imp-n.imp;
			if(debug) System.out.println("compareTo="+diff);			

			if (diff<0) { return -1;
			} else if (diff > 0) { return +1;
			} else { return 0; }
		}

		public String toString() {
			return (p.toString() + " [nr=" + nbr + ",imp=" + imp + "]");
		}
	} //end class Node

	private Node head = null;  // A ref. to the head of the list.
	private Node tail = null;  // A ref. to the end of the list.

	private int size = 0; // The size of the list.
	// ============================================================
	// no constructor needed
	// ============================================================
	/** 
	 * Returns the largest x coordinate
	 */
	public int getMaxX() {
		return maxX;
	}
	/** 
	 * Returns the largest y coordinate
	 */
	public int getMaxY() {
		return maxY;
	}

	// ============================================================
	/**
	 * Creates a list from user input (or file by redirection).
	 * Format should be two integer points per line separated by space x y
	 * @return the number of points in the list
	 */
	public int readShape() {
		Scanner sc = new Scanner(System.in);
		int n = 0;
		while (sc.hasNext()) { // antag 2 korrekta tal per rad
			int n1 = sc.nextInt();
			if (n1 > maxX) {maxX = n1;}
			int n2 = sc.nextInt();
			if (n2 > maxY) {maxY = n2;}
			this.addLast(new Point(n1, n2));
			n++;
		}
		sc.close();
		return n;
	}
	// ============================================================
	/**
	 * Given three points, the important measure of the middle one, p, is calculated.
	 * @param l,p,r the three points
	 * @return the important measure of point p
	 */
	private static double importanceOfP(Point l, Point p, Point r) {
		double l1 = l.distance(p); // use Points distance function :-)
		double l2 = p.distance(r);
		double l3 = l.distance(r);
		if (debug) {
			System.out.println("punkterna (l: p: r): " + l + ": " + p + ": " + r); 
			System.out.println("l-p= " + String.format( "%5.2f ", l1) 
					+ " p-r= " + String.format( "%5.2f ", l2) 
					+ " l-r= " + String.format( "%5.2f ", l3) 
					+ " (l1+l2-l3)= " + String.format( "%5.2f ", (l1+l2-l3)) );
			System.out.println();
		}
		return l1+l2-l3;
	}
	// ============================================================
	/**
	 * Returns a simple iterator over the list
	 * It is not protected from updates to the list
	 * @return an iterator over the list
	 */
	public Iterator<Point> iterator() {
		return new DLListIterator();
	}
	// ============================================================
	// privat inre Iterator klass 
	private class DLListIterator implements Iterator<Point> {
		/** pointer to the current item. */
		private Node currentIndex = head;
		public boolean hasNext( ) {
			return currentIndex != null;
		}
		public Point next( ) {
			if ( !hasNext() ) {
				throw new NoSuchElementException();
			} else {
				Point p = currentIndex.p;
				currentIndex = currentIndex.next;
				return p;
			}
		}
	} // end Iterator

	// ============================================================
	// primitive text-output of the list that can be used for debugging
	// this method is used when the debug flag is given
	@Override
	public String toString() {
		int count = -1;
		StringBuilder str0 = new StringBuilder("index:      "); // index
		StringBuilder str4 = new StringBuilder("old index:  "); // original index
		StringBuilder str1 = new StringBuilder("x:          ");	// x coordinate
		StringBuilder str2 = new StringBuilder("y:          ");	// y coordinate
		StringBuilder str3 = new StringBuilder("importance: "); // importance
		Node ptr = head;
		while (ptr != null) {
			count++;
			str0.append(String.format( "%5d ", count));
			str4.append(String.format( "%5d ", ptr.nbr));
			str1.append(String.format( "%5.0f ", ptr.p.getX() ));
			str2.append(String.format( "%5.0f ", ptr.p.getY() ));
			if(ptr.imp == infinity) {
				str3.append( "  inf " );
			} else {
				str3.append( String.format( "%5.2f ", (int)(ptr.imp*100)/100.0 ) );
			}
			ptr = ptr.next;	
		}
		return (str4.toString() + "\n" + str0.toString() + "\n" 
				+ str1.toString() + "\n" + str2.toString() + "\n" 
				+ str3.toString() + "\n");
	}	

	/**
	 * Creates a "fake" list from a constant  array.
	 * can be used for debugging
	 * @return the number of points in the list
	 */
	public int createFakeShape ()  {
		int n = 0; // skall bli 25
		// meningslÃ¶sa punkter
		int[] x = {2, 4, 6, 8, 3, 5, 7, 2, 4, 6, 8, 3, 5, 7, 2, 4, 6, 8, 3, 5, 7, 2, 4, 6, 8};
		int[] y = {2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 8, 8, 8, 8};
		//int[] x = {1, 1, 2, 3, 3, 1};
		//int[] y = {1, 2, 3, 3, 2, 3};
		for (int i =0; i<x.length; i++) {
			this.addLast( new Point(x[i], y[i]) );
			n++;
		}
		System.out.println(this.q.peek().toString());
		return n;
	}	

	// ============================================================
	// ============================================================
	// ============================================================
	// this is the (only) part that you write
	// ============================================================
	// ============================================================
	// ============================================================


	/**
	 * Adds a point to the end of the list
	 * @param p point to add
	 * @throws NullPointerException if p==null
	 */	
	public void addLast(Point p) {

		Node node = new Node(p,q.size());

		if(true) System.out.println("inserting " + node.toString());

		if (q.size() == 0) {		// DLListan är tom. Sätt in elementet i början på listan.
			head = node;
		}
		else if (q.size() == 1) {	// DLListan innehåller bara ett element. Länka ihop elementen och sätt in elementet som det sista i listan.
			head.next = node;
			node.prev = head;
			tail = node;
		}
		else { //DLListan innehåller nu alltså mer än två element. Länka ihop elementen och länka om slutet på listan till att peka på detta elementet. Sätt in elementet som det sista i listan.
			tail.next = node;
			node.prev = tail;
			tail.imp = this.importanceOfP(tail.prev.p, tail.p, tail.next.p);
			q.remove(tail);
			q.add(tail);
			tail = node;
		}
		q.offer(node);

		if(debug) System.out.println(q.size() + " is the queues current size");
	} // end addLast
	// ============================================================


	/**
	 * Reduces the list to the sought-after k most important points.
	 * @param k the number of remaining points
	 * @throws NoSuchElementException if the priority queue becomes empty
	 */
	public void reduceListToKElements(int k) {
		// TODO
		if(debug) System.out.println("Resorting PQ");	

		// Calculates the initial important measure for all nodes.
		// BY REBUILDING THE LIST
		DLListIterator it = new DLListIterator();
		int i = 0;
		Node ptr = head.next;
		while (i < q.size() - 2) {
			ptr.imp = this.importanceOfP(ptr.prev.p, ptr.p, ptr.next.p);
			q.remove(ptr);
			q.add(ptr);
			ptr = ptr.next;
			i++;
		}

		// Assume there are at least 3 nodes otherwise it's all meaningless.
		if (q.size() < 3) return;

		if(debug) System.out.println("Reducing list to k elements.....");	
		
		// now reduce the list to the k most important node
		while (q.size() > k) {
			if (q.size() == 0) throw new NoSuchElementException();
			Node node = q.poll();

			if(true) System.out.println(node.nbr + " removed. New neigbours: " + node.prev.nbr + "&" + node.next.nbr);

			// recalculate importance for rem.next, neighbour to the right
			// and rem.prev, neighbour to the left	
			
			node.prev.next = node.next;
			if (node.nbr > 2) {
				//				if(debug) System.out.println("RE-calculating importance for " +node.prev.prev.nbr+ " based on " + node.prev.nbr + " and " + node.prev.next.nbr);
				node.prev.imp = this.importanceOfP(node.prev.prev.p, node.prev.p, node.prev.next.p);
				q.remove(node.prev);
				q.add(node.prev);
			}
			
			node.next.prev = node.prev;
			if (node.nbr < q.size() - 1) {
				//				if(debug) System.out.println("RE-calculating importance for " +node.next.prev.nbr+ " based on " + node.next.nbr + " and " + node.next.next.nbr);
				node.next.imp = this.importanceOfP(node.next.prev.p, node.next.p, node.next.next.p);
				q.remove(node.next);
				q.add(node.next);
			}
		}
		
	}
}
