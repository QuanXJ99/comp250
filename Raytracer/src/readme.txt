For objective 11, 

1.I did mirror reflection. You just need to add type="reflection"
after the material you want to reflect

2.I also did the depth of field blur, and you can enable it in Scene.class.
I have attached the file with my BoxStack to be blurred.

3.I implemented area lights. You can see the soft shadow 
in TwoSpherePlane_AreaLight.png.For light to be area light, you need to set 
type="area" for your light in .xml file.

4.I implemented quadric, and you can see the result in Quatrics.png

5.I tried to do multi-thread, but it only works for simple pictures 
like the plane and the sphere. For SceneNode objects, because of the race condition,
the value is overwritten, which causes the wrong color pixel assignment.

I did all shadings, including ambient, diffuse Lambertian, 
and Blinn-Phong specular

I used the uniform grid for the anti-aliasing, and I added jetter in render.class.
For jitter to work, you need to set jetter="true" in your .xml file.

For triangle meshes, I tried both the formula in the book and the formula on the 
slides, it turns out that they are running at the same approximate speed.

In my Novel_Scene, I added reflection to the ball.I also includes a mesh cow.

I also have the picture of mesh boy, which is shown in abstract_oject.png