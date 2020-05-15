/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import android.graphics.Canvas;

class Segment {
    private Segment head = null;
    private Segment tail = null;

    private int positionX;
    private int positionY;

    private int speed;

    private int directionX;
    private int directionY;

    private boolean isAbove = true;

    private Turn turnReached;
    private Exit exitTaken;

    Segment( int positionX, int positionY, int speed, int directionX, int directionY ) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    boolean getIsHead() { return head == null; }

    boolean getIsTail() { return tail == null; }

    int getPositionX() { return positionX; }

    void setPositionX( int positionX ) { this.positionX = positionX; }

    int getPositionY() { return positionY; }

    void setPositionY( int positionY ) { this.positionY = positionY; }

    int getSpeed() { return speed; }

    void setSpeed( int speed ) { this.speed = speed; }

    int getDirectionX() { return directionX; }

    void setDirectionX( int directionX ) { this.directionX = directionX; }

    int getDirectionY() { return directionY; }

    void setDirectionY( int directionY ) { this.directionY = directionY; }

    boolean getIsAbove() { return isAbove; }

    void setIsAbove() { isAbove = true; }

    boolean getIsBelow() { return !isAbove; }

    void setIsBelow() { isAbove = false; }

    void toggleAboveBelow() { isAbove = !isAbove; }

    void setHead( Segment segment ) { head = segment; }

    Segment getHead() { return head; }

    void setTail( Segment segment ) { tail = segment; }

    Segment getTail() { return tail; }

    void setTurnReached( Turn turnReached ) { this.turnReached = turnReached; }

    Turn getTurnReached() { return turnReached; }

    void setExitTaken( Exit exitTaken ) { this.exitTaken = exitTaken; }

    Exit getExitTaken() { return exitTaken; }

    private Segment addTailLeft( int cellSize ) {
        Segment tail = new Segment( positionX - cellSize, positionY, speed, directionX, directionY );

        this.tail = tail;
        tail.head = this;
        tail.isAbove = this.isAbove;

        return tail;
    }

    void addTailsLeft( int tails, int cellSize ) {
        Segment tail = this;

        while( tails > 0 ) {
            tail = tail.addTailLeft( cellSize );
            tails--;
        }
    }
}

