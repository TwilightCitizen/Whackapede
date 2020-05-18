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
    private Direction direction;

    protected MovingGameElement( Position position, int speed, Direction direction ) {
        super( position );

        this.speed = speed;
        this.direction = direction;
    }

    // Position along the X and Y axes is read/write.
    public Position getPosition() { return super.getPosition(); }
    public void setPosition( Position position ) { super.setPosition( position ); }

    // Speed of traversal is read/write.
    public int getSpeed() { return speed; }
    public void setSpeed( int speed ) { this.speed = speed; }

    // Direction along the X and Y axes is read/write.
    public Direction getDirection() { return direction; }
    public void setDirection( Direction direction ) { this.direction = direction; }
}