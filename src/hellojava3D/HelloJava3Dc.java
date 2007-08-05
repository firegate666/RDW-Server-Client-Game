package hellojava3D;


import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.GMatrix;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

// HelloJava3Dc renders a single, rotating cube.

public class HelloJava3Dc extends Applet {

	public BranchGroup createSceneGraph() {
		// Create the root of the branch graph
		BranchGroup objRoot = new BranchGroup();

		// Create the transform group node and initialize it to the
		// identity. Add it to the root of the subgraph.
		TransformGroup objSpin = new TransformGroup();
		objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objRoot.addChild(objSpin);

		// Create a simple shape leaf node, add it to the scene graph.
		// ColorCube is a Convenience Utility class
		objSpin.addChild(new ColorCube(0.4));

		// Create a new Behavior object that will perform the desired
		// operation on the specified transform object and add it into
		// the scene graph.
		Alpha rotationAlpha = new Alpha(-1, 5000);

		RotationInterpolator rotator = new RotationInterpolator(rotationAlpha,
				objSpin);

		// a bounding sphere specifies a region a behavior is active
		// create a sphere centered at the origin with radius of 100
		BoundingSphere bounds = new BoundingSphere();
		rotator.setSchedulingBounds(bounds);
		objSpin.addChild(rotator);

		return objRoot;
	} // end of CreateSceneGraph method

	public HelloJava3Dc() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();

		Canvas3D canvas3D = new Canvas3D(config);
		add("Center", canvas3D);

		BranchGroup scene = createSceneGraph();

		// SimpleUniverse is a Convenience Utility class
		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

		// This will move the ViewPlatform back a bit so the
		// objects in the scene can be viewed.
		simpleU.getViewingPlatform().setNominalViewingTransform();

		simpleU.addBranchGraph(scene);
	} // end of HelloJava3D (constructor)

	// The following allows this to be run as an application
	// as well as an applet

	public static void main(String[] args) {
		Frame frame = new MainFrame(new HelloJava3Dc(), 256, 256);
	} // end of main (method of HelloJava3D)

} // end of class HelloJava3Dc
