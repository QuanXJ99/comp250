package comp557.a4;


import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 * Class for a plane at y=0.
 * 
 * This surface can have two materials.  If both are defined, a 1x1 tile checker 
 * board pattern should be generated on the plane using the two materials.
 */
public class Plane extends Intersectable {
    
	/** The second material, if non-null is used to produce a checker board pattern. */
	Material material2;
	
	/** The plane normal is the y direction */
	public static final Vector3d n = new Vector3d( 0, 1, 0 );
    
    /**
     * Default constructor
     */
    public Plane() {
    	super();
    }
    
   

        
    @Override
    public void intersect( Ray ray, IntersectResult result ) {
    	Vector3d p1=new Vector3d(ray.eyePoint);
    	double t=-1.0;
    	//p1.sub(ray.eyePoint);
    	p1.negate();
    	if(ray.viewDirection.dot(n)==0.0) {
    		return;
    	}
    	
    	t=(p1.dot(n))/(ray.viewDirection.dot(n));
    	
    	
    	if(t>1e-9 && t<result.t) {
        	result.t=t;

        	Point3d p=new Point3d();
        	ray.getPoint(t, p);
        	
        	result.n.set(n);
        	result.p.set(p);
        	
        	int a=((int)Math.floor(result.p.x))%2;
        	int b=((int)Math.floor(result.p.z))%2;
        	
        	boolean check=((b==0&&a==0)||(b!=0&&a!=0));
        	if(check) {
        		result.material=material;
        	}else {
        		if(material2!=null) {
        		result.material=material2;
        		}else{
        		result.material=material;
        		}
        		
        	}
        }else {
    		return;
    	}
    	
    
    	
    	
    	
    }
    	
        // TODO: Objective 4: intersection of ray with plane
    	
    }
    

