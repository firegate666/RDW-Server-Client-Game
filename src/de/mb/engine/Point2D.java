package de.mb.engine;
/*********************************************************
 * Beginning Java 5 Game Programming
 * by Jonathan S. Harbour
 * Point2D Class
 **********************************************************/
class Point2D extends Object {
    private double x, y;

    //int constructor
    Point2D(int x, int y) {
        setX(x);
        setY(y);
    }
    //float constructor
    Point2D(float x, float y) {
        setX(x);
        setY(y);
    }
    //double constructor
    Point2D(double x, double y) {
        setX(x);
        setY(y);
    }

    //X property
    double X() { return x; }
    public void setX(double x) { this.x = x; }
    public void setX(float x) { this.x = (double) x; }
    public void setX(int x) { this.x = (double) x; }

    //Y property
    double Y() { return y; }
    public void setY(double y) { this.y = y; }
    public void setY(float y) { this.y = (double) y; }
    public void setY(int y) { this.y = (double) y; }
}


