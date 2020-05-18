/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

public class MovingGameElement extends GameElement {
    // Speed of traversal.
    private int speed;

    // Direction along the X and Y axes.
    private int directionX;
    private int directionY;

    MovingGameElement( int positionX, int positionY, int speed, int directionX, int directionY ) {
        super( positionX, positionY );

        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    // Position along the X and Y axes is read/write.
    int getPositionX() { return super.getPositionX(); }
    int getPositionY() { return super.getPositionY(); }
    void setPositionX( int positionX ) { super.setPositionX( positionX ); }
    void setPositionY( int positionY ) { super.setPositionY( positionY ); }

    // Speed of traversal is read/write.
    int getSpeed() { return speed; }
    void setSpeed( int speed ) { this.speed = speed; }

    // Direction along the X and Y axes is read/write.
    int getDirectionX() { return directionX; }
    int getDirectionY() { return directionY; }
    void setDirectionX( int directionX ) { this.directionX = directionX; }
    void setDirectionY( int directionY ) { this.directionY = directionY; }
}