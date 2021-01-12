package comp557.a4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 * Simple scene loader based on XML file format.
 */
public class Scene {
    
    /** List of surfaces in the scene */
    public List<Intersectable> surfaceList = new ArrayList<Intersectable>();
	
	/** All scene lights */
	public Map<String,Light> lights = new HashMap<String,Light>();

    /** Contains information about how to render the scene */
    public Render render;
    
    /** The ambient light colour */
    public Color3f ambient = new Color3f();
    
    public int threadNum=1;
    
    public int maxDepth=4;
    
    public boolean blur=false;
   

    /** 
     * Default constructor.
     */
    public Scene() {
    	this.render = new Render();
    }
    
    public void render(boolean showPanel) {
    	 
        Camera cam = render.camera; 
        int w = cam.imageSize.width;
        int h = cam.imageSize.height;
        
        render.init(w, h, showPanel);
        
        for(int i=0;i<threadNum;i++) {
        	RenderThread t=new RenderThread(this,cam,w,h,i);
        	t.start();
        	
        	 //render.waitDone();
        	 
        	// render.save();
        
        }
        // wait for render viewer to close
        render.waitDone();
        
        // save the final render image
        render.save();
    	}
    /**
     * renders the scene
     */
    class RenderThread extends Thread  {
    	int w;
    	int h;
    	Scene scene;
    	Camera cam;
    	int index;
    	
        
    	RenderThread(Scene scene,Camera cam,int w, int h,int i){
    		this.scene=scene;
    		this.cam=cam;
    		this.w=w;
    		this.h=h;
    		this.index=i;
    	}
    	public void run() {
        int low=(index*h)/scene.threadNum;
        int high=((index+1)*h)/scene.threadNum;
       
        int camN;
        double d;
       
        for ( int j = low; j < high && !render.isDone(); j++ ) {
            for ( int i = 0; i < w && !render.isDone(); i++ ) {
                       	Color3f  rgb=new Color3f();
                       //	Ray ray=new Ray();
                       	ArrayList<Ray> rays=new ArrayList<Ray>();
            			//Ray ray=new Ray();
            			
            			double n=(int)Math.sqrt(render.samples)+1;
            			
            			if(blur) {
            				camN=6;
            				d=0.01;
            			}else {
            				camN=1;
            				d=0;
            			}
            			for(int num=0;num<camN;num++) {
            				Camera current=new Camera();
            				current.from.x=cam.from.x+(num/3-1)*(cam.from.z-cam.to.z)*d;
            				current.from.y=cam.from.y+(num%3-1)*(cam.from.z-cam.to.z)*d;
            				current.from.z=cam.from.z;
            				current.fovy=cam.fovy;
            				current.imageSize=cam.imageSize;
            				current.up.set(cam.up);
            				current.to.set(cam.to);
            				
            			
            			
            			
            				double[] offset=new double[2];
            			
            				
            				
            				for(int p=0;p<n;p++) {
            					for(int q=0;q<n;q++) {
            						if(render.jitter) {
            						offset[0]=(p+0.5)/n;
                    				offset[1]=(q+0.5)/n;
            						}else {
            						offset[0]=0.5;
            						offset[1]=0.5;
            						}
            						
            						Ray ray=new Ray();
            						generateRay(i, j, offset, current , ray);
            						rays.add(ray);
            					}
            				}
            			}
            			
            				
            				Color3f tmpc=new Color3f();
            				for(Ray r:rays) {
            					IntersectResult result=new IntersectResult();
                				for(Intersectable a: surfaceList) {
                    				a.intersect(r, result);
                    			}
                				Color3f color=new Color3f(render.bgcolor);
                    			if(result.t!=Double.POSITIVE_INFINITY) {
                    				color=computeLight(result,r,maxDepth);
                    			}
                    			tmpc.x+=color.x;
                				tmpc.y+=color.y;
                				tmpc.z+=color.z;
                    			//tmpc.add(color);
                    				//tmpc.set(render.bgcolor);
                    		}
            			
            				
            				tmpc.scale((float)(1.0f/(n*n)));
            				tmpc.scale(1.0f/camN);
            				rgb.set(tmpc);
            				
            						rgb.clamp(0, 1);
            						int r = (int)(255*rgb.x);
            						int g = (int)(255*rgb.y);
            						int b = (int)(255*rgb.z);
            						int a = 255;
            						int	argb =(a<<24 | r<<16 | g<<8 | b); 
            				    
            	  
            	
            	
                // TODO: Objective 1: generate a ray (use the generateRay method)
            	
                // TODO: Objective 2: test for intersection with scene surfaces
            	
                // TODO: Objective 3: compute the shaded result for the intersection point (perhaps requiring shadow rays)
                
            	// Here is an example of how to calculate the pixel value.
            
                
                // update the render image
                render.setPixel(i, j, argb);
            	
            
                
            }
        }
        //render.waitDone();
        
    	}
    	
        
    }
    
    
    private Color3f computeLight(IntersectResult result,Ray ray,int maxDepth) {
    	Color3f color=new Color3f(0,0,0);
    	
    	color.set(ambient.x*result.material.diffuse.x,ambient.y*result.material.diffuse.y,ambient.z*result.material.diffuse.z);
    	ArrayList<Light> ls=new ArrayList<>();
    	for(Light light:lights.values()) {
    		if(light.type.equalsIgnoreCase("area")) {
    			Light[] area=new Light[9];
    			for(int n=0;n<9;n++) {
    				area[n]=new Light();
    				area[n].from.x=light.from.x+Math.random();
    				area[n].from.y=light.from.y;
    				area[n].from.z=light.from.z+Math.random();
    				area[n].power=light.power/9;
    				ls.add(area[n]);
    			}
    		}else {
    			ls.add(light);
    		}
    	}
    	
    	Color3f shading=new Color3f(0,0,0);
    	
    		for(int i=0;i<ls.size();i++) {
    		IntersectResult shadowResult = new IntersectResult();
			Ray shadowRay = new Ray();
			
			if(inShadow(result, ls.get(i), surfaceList, shadowResult, shadowRay)) {
				//break;
				continue;
			}
			
			if(result.material!=null) {
				shading.set(0,0,0);
				Vector3d lightD = new Vector3d();
		    	lightD.sub(ls.get(i).from,result.p);
		    	lightD.normalize();
		    	
		    	double diffuse=Math.max(result.n.dot(lightD),0.0);
		    	Vector3d viewDirection=new Vector3d(ray.eyePoint);
		    	viewDirection.sub(result.p);
		    	viewDirection.normalize();
		    	Vector3d halfVector =new Vector3d();
		    	halfVector.add(lightD, viewDirection);
		    	halfVector.normalize();
		    	double specular=Math.max(0.0,result.n.dot(halfVector));
		    	
		    	if(diffuse==0.0) {
		    		specular=0.0;
		    	}else {
		    		specular = Math.pow( specular, result.material.shinyness );
		    	}
		    	
		    	Color3f scatteredLight=new Color3f();
		    	Color3f reflectedLight=new Color3f();
		    	float diffusex=(float) (ls.get(i).color.x*ls.get(i).power*(result.material.diffuse.x*diffuse));
		    	float specularx=(float) (ls.get(i).color.x*ls.get(i).power*result.material.specular.x*specular);
		    	float diffusey=(float) (ls.get(i).color.y*ls.get(i).power*(result.material.diffuse.y*diffuse));
		    	float speculary=(float) (ls.get(i).color.y*ls.get(i).power*result.material.specular.y*specular);
		    	float diffusez=(float) (ls.get(i).color.z*ls.get(i).power*(result.material.diffuse.z*diffuse));
		    	float specularz=(float) (ls.get(i).color.z*ls.get(i).power*result.material.specular.z*specular);
		    	//Color3f rgb=new Color3f();
		    	scatteredLight.set(diffusex,diffusey,diffusez);
		    	reflectedLight.set(specularx,speculary,specularz);
		    	shading.add(scatteredLight,reflectedLight);
		    	color.add(shading);
			}
			//color.add(shading);
		
			
			if("reflection".equals(result.material.type)&&maxDepth>0) {
				shading.set(0,0,0);
				Ray reflectRay=new Ray();
				IntersectResult reflectResult=new IntersectResult();
				Vector3d r=new Vector3d();
				Vector3d normal=new Vector3d(result.n);
				double a=2*ray.viewDirection.dot(result.n);
				normal.scale(a);
				r.sub(ray.viewDirection,normal);
				r.normalize();
				double epsilon=0.00001;
				Point3d p=new Point3d();
				p.scaleAdd(epsilon, r, result.p);
				reflectRay.set(p, r);
				for(Intersectable surface:surfaceList) {
					surface.intersect(reflectRay,reflectResult);
				}
				if(reflectResult.material!=null) {
					shading.set(computeLight(reflectResult,reflectRay,maxDepth-1));
				}
				color.add(new Color3f(shading.x*result.material.specular.x,shading.y*result.material.specular.y,shading.z*result.material.specular.z));
			}
			
		}
    	//color.add(new Color3f(shading.x*result.material.specular.x,shading.y*result.material.specular.y,shading.z*result.material.specular.z));
    	return color;
    }
    
    /**
     * Generate a ray through pixel (i,j).
     * 
     * @param i The pixel row.
     * @param j The pixel column.
     * @param offset The offset from the center of the pixel, in the range [-0.5,+0.5] for each coordinate. 
     * @param cam The camera.
     * @param ray Contains the generated ray.
     */
	public static void generateRay(final int i, final int j, final double[] offset, final Camera cam, Ray ray) {
		Vector3d w=new Vector3d(cam.from.x-cam.to.x,cam.from.y-cam.to.y,cam.from.z-cam.to.z);
    	w.normalize();
    	Vector3d Camu=new Vector3d();
    	Camu.cross(cam.up,w);
    	Camu.normalize();
    	Vector3d Camv=new Vector3d();
    	Camv.cross(Camu,w);
    	Camv.normalize();
    	
    	double d=cam.from.distance(cam.to);
    	double t=Math.tan(Math.toRadians(cam.fovy/2))*d;
    	double b=-t;
    	double r=(cam.imageSize.getWidth()/cam.imageSize.getHeight())*t;
    	double l=-r;
    	double u=l+(r-l)*(i+offset[0])/cam.imageSize.getWidth();
    	double v=b+(t-b)*(j+offset[1])/cam.imageSize.getHeight();
    	
    	Point3d eyePoint=cam.from;
    	w.scale(-d);
    	Camu.scale(u);
    	Camv.scale(v);
    	Vector3d viewDirection=new Vector3d();
    	Vector3d sum=new Vector3d(w);
    	sum.add(Camu);
    	sum.add(Camv);
    	viewDirection.set(sum);
    	viewDirection.normalize();
    	ray.set(eyePoint, viewDirection);
	}

	/**
	 * Shoot a shadow ray in the scene and get the result.
	 * 
	 * @param result Intersection result from raytracing. 
	 * @param light The light to check for visibility.
	 * @param root The scene node.
	 * @param shadowResult Contains the result of a shadow ray test.
	 * @param shadowRay Contains the shadow ray used to test for visibility.
	 * 
	 * @return True if a point is in shadow, false otherwise. 
	 */
	public static boolean inShadow(final IntersectResult result, final Light light,List<Intersectable> root , IntersectResult shadowResult, Ray shadowRay) {
		double epsilon=0.00001;
		Vector3d lightD = new Vector3d();
    	lightD.sub(light.from,result.p);
    	double t=lightD.length();
    	lightD.normalize();
		Point3d p=new Point3d();
		p.scaleAdd(epsilon, lightD,result.p);
		shadowRay.set(p,lightD);
		for(Intersectable a:root) {
		a.intersect(shadowRay, shadowResult);
		}
	
		// TODO: Objective 5: check for shdows and use it in your lighting computation
		
		return shadowResult.t<t && shadowResult.t>1e-10;
	}  
	

	
}
