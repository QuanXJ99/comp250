package comp557.a4;

import java.util.ArrayList;

import javax.vecmath.Point3d;




public class Metaballs extends Intersectable {
	
	ArrayList<Sphere> spheres = new ArrayList<Sphere>();

	double thresh = 4;

	double eps = 0.001;
	
	ArrayList<Point3d> centers=new ArrayList<Point3d>();
	
	//double radius=0.0;
	
	//public ArrayList<Sphere> boundingSpheres = new ArrayList<>();
	
	
	
	public Metaballs() {
		super();
	}

	
	@Override
	public void intersect(Ray ray, IntersectResult result) {
	


    }
}

