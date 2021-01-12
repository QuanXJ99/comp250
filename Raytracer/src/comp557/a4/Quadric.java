package comp557.a4;

import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;


public class Quadric extends Intersectable {
    
	/**
	 * Radius of the sphere.
	 */
	public Matrix4d Q = new Matrix4d();
	public Matrix3d A = new Matrix3d();
	public Vector3d B = new Vector3d();
	public double C;
	
	/**
	 * The second material, e.g., for front and back?
	 */
	Material material2 = null;
	
	public Quadric() {
		
	}
	
	
	
	@Override
	public void intersect(Ray ray, IntersectResult result) {
		
		
		Vector3d eye=new Vector3d(ray.eyePoint);
		Vector3d dir=new Vector3d(ray.viewDirection);
		
		Vector3d tmp=new Vector3d();
		
		A.transform(dir, tmp);
		double b=new Vector3d(ray.eyePoint).dot(tmp);
		double a=ray.viewDirection.dot(tmp);
		
		A.transform(eye,tmp);
		b +=ray.viewDirection.dot(tmp)-2*B.dot(dir);
		double c=eye.dot(tmp);
		c += C-2*B.dot(eye);
		
		double disc=b*b-4*a*c;
		
		if(disc>=0) {
			double t1=(-b+Math.sqrt(disc))/(2*a);
			double t2=(-b-Math.sqrt(disc))/(2*a);
			double t=Math.min(Math.max(t1, 0), Math.max(t2, 0));
			if(t<result.t && t>1e-9) {
				result.t=t;
				result.material=this.material;
				//Point3d p=new Point3d();
				ray.getPoint(t, result.p);
				//result.p.set(p);
				A.transform(result.p,result.n);
				result.n.scale(2);
				tmp=new Vector3d(B);
				tmp.scale(2);
				result.n.sub(tmp);
				result.n.normalize();
			}
		}
		
		
	}
	
}
