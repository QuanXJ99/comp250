package comp557.a4;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 * A simple sphere class.
 */
public class Sphere extends Intersectable {
    
	/** Radius of the sphere. */
	public double radius = 1;
    
	/** Location of the sphere center. */
	public Point3d center = new Point3d( 0, 0, 0 );
    
    /**
     * Default constructor
     */
    public Sphere() {
    	super();
    }
    
    /**
     * Creates a sphere with the request radius and center. 
     * 
     * @param radius
     * @param center
     * @param material
     */
    public Sphere( double radius, Point3d center, Material material ) {
    	super();
    	this.radius = radius;
    	this.center = center;
    	this.material = material;
    }
    
    @Override
    public void intersect( Ray ray, IntersectResult result ) {
    	Point3d p=new Point3d();
    	double t=-1.0;
    	
    	Point3d a=new Point3d();
    	a.sub(ray.eyePoint, center);
    	double b=ray.viewDirection.dot(new Vector3d(a.x,a.y,a.z));
    	double c=ray.viewDirection.dot(ray.viewDirection);
    	double e=((new Vector3d(a.x,a.y,a.z)).dot(new Vector3d(a.x,a.y,a.z)))-(radius*radius);
    	double disc=b*b-c*e;
    	if(disc<0) {
    		return;
    	}else {
    		double t1=(-b+Math.sqrt(disc))/c;
    		
    		double t2=(-b-Math.sqrt(disc))/c;
    	
    		t=Math.min(t1,t2);
    		
    	}
    	if(t>1e-9 && t<result.t) {
        	result.t=t;
    	}
    	
    	
    	
    	ray.getPoint(t, p);
    	Vector3d normal=new Vector3d(p);
    	normal.sub(center);
    	normal.scale(1/radius);
    	result.n.set(normal);
    	result.p.set(p);
    	result.material=this.material;
    	
        // TODO: Objective 2: intersection of ray with sphere
	
    }
    
}
