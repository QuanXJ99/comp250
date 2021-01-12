package comp557.a4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import comp557.a4.PolygonSoup.Vertex;

public class Mesh extends Intersectable {
	
	/** Static map storing all meshes by name */
	public static Map<String,Mesh> meshMap = new HashMap<String,Mesh>();
	
	/**  Name for this mesh, to allow re-use of a polygon soup across Mesh objects */
	public String name = "";
	
	/**
	 * The polygon soup.
	 */
	public PolygonSoup soup;

	public Mesh() {
		super();
		this.soup = null;
	}			
		
	@Override
	public void intersect(Ray ray, IntersectResult result) {
		
		 List<int[]> faceList=soup.faceList;
		 List<Vertex> vertexList=soup.vertexList;
		 for(int[] f : faceList) {
				Vertex a=vertexList.get(f[0]);
				Vertex b=vertexList.get(f[1]);
				Vertex c=vertexList.get(f[2]);
				Point3d ap=new Point3d(a.p);
				Point3d bp=new Point3d(b.p);
				Point3d cp=new Point3d(c.p);
				Vector3d u=new Vector3d(bp);//b-a
				u.sub(ap);
				Vector3d v=new Vector3d(cp);//c-a
				v.sub(ap);
				Vector3d n=new Vector3d();
				n.cross(u, v);
				//n.normalize();
				Vector3d bc=new Vector3d();
				bc.sub(cp,bp);
				Vector3d ac=new Vector3d();
				ac.sub(ap,cp);
				
				Vector3d e=new Vector3d ();
				e.sub(ap,ray.eyePoint);
				double t=e.dot(n)/ray.viewDirection.dot(n);
				
				
				if( t>1e-9 && t<result.t) {
					Point3d x=new Point3d();
					x.scaleAdd(t, ray.viewDirection,ray.eyePoint);
					Vector3d ax = new Vector3d();
					Vector3d bx = new Vector3d();
					Vector3d cx = new Vector3d();
					ax.sub(x, ap);
					bx.sub(x, bp);
					cx.sub(x, cp);
					ax.cross(u, ax);
					bx.cross(bc, bx);
					cx.cross(ac, cx);
					
					if(ax.dot(n)>1e-9 && bx.dot(n)>1e-9 && cx.dot(n)>1e-9) {
						result.t=t;
						Point3d p=new Point3d();
						ray.getPoint(t, p);
						result.p.set(p);
						result.n.set(n);
						result.n.normalize();
						result.material=material;
					}
				}
				
				
			/*	boolean flag=true;
				double ei=(ap.y-cp.y)*(ray.viewDirection.z)-(ray.viewDirection.y)*(ap.z-cp.z);
				double gf=(ray.viewDirection.x)*(ap.z-cp.z)-(ap.x-cp.x)*(ray.viewDirection.z);
				double dh=(ap.x-cp.x)*(ray.viewDirection.y)-(ap.y-cp.y)*(ray.viewDirection.x);
				double ak=(ap.x-bp.x)*(ap.y-ray.eyePoint.y)-(ap.x-ray.eyePoint.x)*(ap.y-bp.y);
				double jc=(ap.x-ray.eyePoint.x)*(ap.z-bp.z)-(ap.x-bp.x)*(ap.z-ray.eyePoint.z);
				double bl=(ap.y-bp.y)*(ap.z-ray.eyePoint.z)-(ap.y-ray.eyePoint.y)*(ap.z-bp.z);
				double M=(ap.x-bp.x)*ei+(ap.y-bp.y)*gf+(ap.z-bp.z)*dh;
				t=(-1.0)*((ap.z-cp.z)*ak+(ap.y-cp.y)*jc+(ap.x-cp.x)*bl)/M;
				if(t<1e-9 || t>result.t) {
					flag=false;
				}
				double gamma=((ray.viewDirection.z)*ak+(ray.viewDirection.y)*jc+(ray.viewDirection.x)*bl)/M;
				if(gamma<0.0 || gamma>1) {
					flag=false;
				}
				double beta=((ap.x-ray.eyePoint.x)*ei+(ap.y-ray.eyePoint.y)*gf+(ap.z-ray.eyePoint.z)*dh)/M;
				if(beta<0 || beta>(1-gamma)) {
					flag=false;
				}
				
				if(t<result.t && t>1e-9&& gamma>0 && gamma<1 && beta>0&& beta<(1-gamma)) {
					//if(t<result.t &&t>1e-9) {
						result.t=t;
						Point3d p=new Point3d();
						ray.getPoint(t, p);
						result.p.set(p);
						result.n.set(n);
						result.n.normalize();
						result.material=this.material;
					//}
				}*/
				
			
			
				
			
		 }
		// TODO: Objective 7: ray triangle intersection for meshes
		
	}

}
