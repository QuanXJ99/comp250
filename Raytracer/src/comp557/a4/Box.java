package comp557.a4;



import javax.vecmath.Point3d;


/**
 * A simple box class. A box is defined by it's lower (@see min) and upper (@see max) corner. 
 */
public class Box extends Intersectable {

	public Point3d max;
	public Point3d min;
	
    /**
     * Default constructor. Creates a 2x2x2 box centered at (0,0,0)
     */
    public Box() {
    	super();
    	this.max = new Point3d( 1, 1, 1 );
    	this.min = new Point3d( -1, -1, -1 );
    }	
    
    public static void swap(double a, double b) {
    	double tmp=a;
    	a=b;
    	b=tmp;
    }

	@Override
	public void intersect(Ray ray, IntersectResult result) {
		double tnear=Double.NEGATIVE_INFINITY;
		double tfar=Double.POSITIVE_INFINITY;
		double t1,t2;
		double[] eye= {ray.eyePoint.x,ray.eyePoint.y,ray.eyePoint.z};
		double[] view= {ray.viewDirection.x,ray.viewDirection.y,ray.viewDirection.z};
		double [] minv= {min.x,min.y,min.z};
		double [] maxv= {max.x,max.y,max.z};
		boolean flag=true;
		
		for(int i=0;i<3;i++) {
			if(view[i]==0.0) {
				flag=false;
				
			}else {
				t1=(minv[i]-eye[i])/view[i];
				t2=(maxv[i]-eye[i])/view[i];
				if(t1>t2) {
					double tmp=t1;
					t1=t2;
					t2=tmp;
				}
				if(t1>tnear) {
					tnear=t1;
				}
				if(t2<tfar) {
					tfar=t2;
				}
				if(tnear>tfar||tfar<0) {
					flag=false;
				}
			}
		}
	
		if(flag==false) {
			return;
		}else {
			result.t=tnear;
			Point3d p=new Point3d();
			ray.getPoint(tnear, p);
			result.p.set(p);
			double epsilon=0.001;
			if(Math.abs(p.x-min.x)<epsilon) {
				result.n.set(-1,0,0);
			}else if(Math.abs(p.x-max.x)<epsilon) {
				result.n.set(1,0,0);
			}else if(Math.abs(p.y-min.y)<epsilon) {
				result.n.set(0,-1,0);
			}else if(Math.abs(p.y-max.y)<epsilon) {
				result.n.set(0,1,0);
			}else if(Math.abs(p.z-min.z)<epsilon) {
				result.n.set(0,0,-1);
			}else if(Math.abs(p.z-max.z)<epsilon) {
				result.n.set(0,0,1);
			}
			result.n.normalize();
			result.material=this.material;
			
		}
		
		
		
		
		
		
		
		// TODO: Objective 6: intersection of Ray with axis aligned box
	}	

}
