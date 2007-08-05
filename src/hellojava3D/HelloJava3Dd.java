package hellojava3D;


import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.*;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

// HelloJava3Dc renders a single, rotating cube.

public class HelloJava3Dd extends Applet {

	public BranchGroup createSceneGraph() {
		// Create the root of the branch graph
		BranchGroup objRoot = new BranchGroup();

		// rotate object has composited transformation matrix
		Transform3D rotate = new Transform3D();
		Transform3D tempRotate = new Transform3D();

		rotate.rotX(Math.PI / 4.0d);
		tempRotate.rotY(Math.PI / 5.0d);
		rotate.mul(tempRotate);

		TransformGroup objRotate = new TransformGroup(rotate);

		// Create the transform group node and initialize it to the
		// identity. Enable the TRANSFORM_WRITE capability so that
		// our behavior code can modify it at runtime. Add it to the
		// root of the subgraph.
		TransformGroup objSpin = new TransformGroup();
		objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		objRoot.addChild(objRotate);
		objRotate.addChild(objSpin);

		// Create a simple shape leaf node, add it to the scene graph.
		// ColorCube is a Convenience Utility class
		objSpin.addChild(new ColorCube(0.4));

		// Create a new Behavior object that will perform the desired
		// operation on the specified transform object and add it into
		// the scene graph.
		Transform3D yAxis = new Transform3D();
		yAxis.setTranslation(new Vector3f(0.0f, 1.0f, 0.0f));
		Alpha rotationAlpha = new Alpha(-1, 4000);

		RotationInterpolator rotator = new RotationInterpolator(rotationAlpha,
				objSpin, yAxis, 0.0f, (float) Math.PI * 2.0f);

		// a bounding sphere specifies a region a behavior is active
		// create a sphere centered at the origin with radius of 1
		BoundingSphere bounds = new BoundingSphere();
		rotator.setSchedulingBounds(bounds);
		objSpin.addChild(rotator);

		return objRoot;
	} // end of CreateSceneGraph method of HelloJava3Dd

	public HelloJava3Dd() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();

		Canvas3D canvas3D = new Canvas3D(config);
		add("Center", canvas3D);

		BranchGroup scene = createSceneGraph();
		scene.compile();

		// SimpleUniverse is a Convenience Utility class
		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

		// This will move the ViewPlatform back a bit so the
		// objects in the scene can be viewed.
		simpleU.getViewingPlatform().setNominalViewingTransform();

		simpleU.addBranchGraph(scene);
	} // end of HelloJava3Dd (constructor)

	// The following allows this to be run as an application
	// as well as an applet

	public static void main(String[] args) {
		Frame frame = new MainFrame(new HelloJava3Dd(), 256, 256);
	} // end of main (method of HelloJava3D)

} // end of class HelloJava3Dd
