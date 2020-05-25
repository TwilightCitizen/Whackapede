/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.gameElements;

public class MovingGameElement extends GameElement {
    // Speed of traversal.
    private float speed;

    // Direction along the X and Y axes.
    private Direction direction;

    protected MovingGameElement( Position position, float speed, Direction direction ) {
        super( position );

        this.speed = speed;
        this.direction = direction;
    }

    // Speed of traversal is read/write.
    public float getSpeed() { return speed; }
    public void setSpeed( float speed ) { this.speed = speed; }

    // Direction along the X and Y axes is read/write.
    public Direction getDirection() { return direction; }
    public void setDirection( Direction direction ) { this.direction = direction; }
}