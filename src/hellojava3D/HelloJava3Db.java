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

// HelloJava3Db renders a single, rotated cube.

public class HelloJava3Db extends Applet {
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

		objRoot.addChild(objRotate);
		objRotate.addChild(new ColorCube(0.4));
		// Let Java 3D perform optimizations on this scene graph.
		objRoot.compile();

		return objRoot;
	} // end of CreateSceneGraph method of HelloJava3Db

	// Create a simple scene and attach it to the virtual universe

	public HelloJava3Db() {
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
	} // end of HelloJava3Db (constructor)

	// The following allows this to be run as an application
	// as well as an applet

	public static void main(String[] args) {
		Frame frame = new MainFrame(new HelloJava3Db(), 256, 256);
	} // end of main (method of HelloJava3Db)

} // end of class HelloJava3Db
