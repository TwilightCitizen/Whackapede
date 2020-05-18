/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.gameElements;

public class MovingGameElement extends GameElement {
    // Speed of traversal.
    private int speed;

    // Direction along the X and Y axes.
    private int directionX;
    private int directionY;

    public MovingGameElement( int positionX, int positionY, int speed, int directionX, int directionY ) {
        super( positionX, positionY );

        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    // Position along the X and Y axes is read/write.
    public int getPositionX() { return super.getPositionX(); }
    public int getPositionY() { return super.getPositionY(); }
    public void setPositionX( int positionX ) { super.setPositionX( positionX ); }
    public void setPositionY( int positionY ) { super.setPositionY( positionY ); }

    // Speed of traversal is read/write.
    public int getSpeed() { return speed; }
    public void setSpeed( int speed ) { this.speed = speed; }

    // Direction along the X and Y axes is read/write.
    public int getDirectionX() { return directionX; }
    public int getDirectionY() { return directionY; }
    public void setDirectionX( int directionX ) { this.directionX = directionX; }
    public void setDirectionY( int directionY ) { this.directionY = directionY; }
}