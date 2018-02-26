package lab1;

import java.awt.Point;
import java.util.LinkedList;

import lab1.distanceBetweenPoints;

/**
 * @author Grupp 36, Andreas Johansson, 8711044654
 *
 */

public class uppg2 {

	private static int k;
	public static Node[] q;
	public static LinkedList <Node> u;
	public static boolean debug = true;
	/** this Node is the building block for our list. */
	
	private static class Node implements Comparable<Node> {
		Point p = null;
		double imp = 990000000;  //importance of the point
		int nbr = -1;   // original position in list, only for debug

		private Node next = null; // The link to the next node.
		private Node prev = null; // The link to the previous node.

		private Node( Point p, int nbr ) { // nbr stores the original position in list
			this.p = p;
			this.nbr = nbr;
		}

		public int compareTo( Node n ) {
			double diff= imp-n.imp;
			if (diff<0) { return -1;
			} else if (diff > 0) { return +1;
			} else { return 0; }
		}
		
		public String toString() {
			return (p.toString() + " [nr=" + nbr + ",imp=" + imp + "]");
		}
	} //end class Node
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Point a = new Point(0,0);
		Point b = new Point(0,10);
		Point c = new Point(1,10);
		Point d = new Point(2,0);
		Point e = new Point(3,0);
		Point f = new Point(3,0);
		Point g = new Point(4,0);
		Point h = new Point(5,0);
		Point i = new Point(2,0);
		Point j = new Point(0,0);

		q = new Node[10];
		q[0] = new Node(a,0);
		q[1] = new Node(b,1);
		q[2] = new Node(c,2);
		q[3] = new Node(d,3);
		q[4] = new Node(e,4);
		q[5] = new Node(f,5);
		q[6] = new Node(g,6);
		q[7] = new Node(h,7);
		q[8] = new Node(i,8);
		q[9] = new Node(j,9);
		
		LinkedList <Node> u = new LinkedList();
		u.add(q[0]);
		u.add(q[1]);
		u.add(q[2]);
		u.add(q[3]);
		u.add(q[4]);
		u.add(q[5]);
		u.add(q[6]);
		u.add(q[7]);
		u.add(q[8]);
		u.add(q[9]);

		k = 4;

		System.out.println("Vektorns längd: "+q.length+"\n");
		reduceArrayToKElements(k,q);
	}

	private static void reduceArrayToKElements(int k,Node[] q) {
		// TODO Auto-generated method stub
		
		if(debug) System.out.println(q[0].imp);
		for (int i = 1; i < q.length-1; i++) {
			if(debug) System.out.print("Punkt nr:"+i+" ");				
			q[i].imp = importanceOfP(q[i-1].p,q[i].p,q[i+1].p);
			if(debug) System.out.println(q[i].toString());
		}
		if(debug) System.out.println(q[q.length-1].imp);

		while (q.length > k) {
			double minImp = 1.0E300;
			int elemRemove = 0;
			for (int i = 1; i < q.length-1; i++) {
				if (q[i].imp < minImp) {
					elemRemove = i;
					minImp = q[i].imp;
					if(debug) System.out.println("To be removed is "+q[i].nbr+" worth "+q[i].imp);
				}
			}
			if(debug) System.out.println("Removing "+q[elemRemove].nbr+" worth "+q[elemRemove].imp);
			
			if (elemRemove > 2)
			q[elemRemove-1].imp = importanceOfP(q[elemRemove-2].p,q[elemRemove-1].p,q[elemRemove+1].p);
			if (elemRemove < q.length - 2)
			q[elemRemove+1].imp = importanceOfP(q[elemRemove-1].p,q[elemRemove+1].p,q[elemRemove+2].p);
			
			for (int i = elemRemove; i < q.length - 1; i++) q[i] = q[i+1];
			Node[] newQ = new Node[q.length-1];
			System.arraycopy(q, 0, newQ, 0, q.length - 1);
			q = newQ;
		}

		if(debug) System.out.println("Vektorns längd: "+q.length+"\n");
		if(debug) System.out.println(q[0].imp);
		for (int i = 1; i < q.length-1; i++) {
			if(debug) System.out.print(q[i].nbr+" Punkt nr:"+i+" ");				
			q[i].imp = importanceOfP(q[i-1].p,q[i].p,q[i+1].p);
			if(debug) System.out.println(q[i].toString());
		}

		System.out.println("Vektorns längd: "+q.length+"\n");
		
	}

	public static double distanceBetweenPoints(Point a, Point b) {
		//return Point.distance(a.getX(), a.getY(), b.getX(), b.getY());
		return a.distance(b);
	}

	private static double importanceOfP(Point l, Point p, Point r) {
		double l1 = l.distance(p); // use Points distance function :-)
		double l2 = p.distance(r);
		double l3 = l.distance(r);
		/* debug
		System.out.println("punkterna (l: p: r): " + l + ": " + p + ": " + r); 
		System.out.println("l-p= " + String.format( "%5.2f ", l1) 
						+ " p-r= " + String.format( "%5.2f ", l2) 
						+ " l-r= " + String.format( "%5.2f ", l3) 
						+ " (l1+l2-l3)= " + String.format( "%5.2f ", (l1+l2-l3)) );
		System.out.println();
		// end debug
		 */
		return l1+l2-l3;
	}

	public uppg2(Point arg[]) {

		final Point[] originalPolygon = arg;
		Point[] simplifiedPolygon = originalPolygon;
	}
}