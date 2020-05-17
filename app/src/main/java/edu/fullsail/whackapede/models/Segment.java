/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

/*
Segment various data necessary for the proper placement of centipede segments on the game arena,
including position and direction along the X and Y axis, speed of traversal along them, position
in the above- or below-ground layers, Turns reached on their heading and Exits taken from them,
and their head and tail Segments, if any.
*/
class Segment {
    // Head and tail Segments.
    private Segment head = null;
    private Segment tail = null;

    // Positions along the X and Y axes.
    private int positionX;
    private int positionY;

    // Speed of traversal.
    private int speed;

    // Direction along the X and Y axes.
    private int directionX;
    private int directionY;

    // Position on above- or below-ground layers.
    private boolean isAbove = true;

    // Turn reached during traversal and Exit taken from it.
    private Turn turnReached;
    private Exit exitTaken;

    Segment( int positionX, int positionY, int speed, int directionX, int directionY ) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    // Status as head or tail is determined by whether or not the Segment has a head or tail Segment.
    boolean getIsHead() { return head == null; }
    boolean getIsTail() { return tail == null; }

    // Head and tail are nearly read-only, removable but not directly settable to another Segment.
    Segment getHead() { return head; }
    Segment getTail() { return tail; }
    void removeHead() { head = null; }
    void removeTail() { tail = null; }

    // Position along the X and Y axes is read/write.
    int getPositionX() { return positionX; }
    int getPositionY() { return positionY; }
    void setPositionX( int positionX ) { this.positionX = positionX; }
    void setPositionY( int positionY ) { this.positionY = positionY; }

    // Speed of traversal is read/write.
    int getSpeed() { return speed; }
    void setSpeed( int speed ) { this.speed = speed; }

    // Direction along the X and Y axes is read/write.
    int getDirectionX() { return directionX; }
    int getDirectionY() { return directionY; }
    void setDirectionX( int directionX ) { this.directionX = directionX; }
    void setDirectionY( int directionY ) { this.directionY = directionY; }

    // Position on above- or below-ground layers is nearly read-only, toggled but not directly settable.
    boolean getIsAbove() { return isAbove; }
    boolean getIsBelow() { return !isAbove; }
    void toggleAboveBelow() { isAbove = !isAbove; }

    // Turn reached during traversal is read/write.
    void setTurnReached( Turn turnReached ) { this.turnReached = turnReached; }
    Turn getTurnReached() { return turnReached; }

    // Exit taken from reached turn is read/write.
    void setExitTaken( Exit exitTaken ) { this.exitTaken = exitTaken; }
    Exit getExitTaken() { return exitTaken; }

    // Add a tail Segment, placed left of the Segment.
    private Segment addTailLeft( int cellSize ) {
        Segment tail = new Segment( positionX - cellSize, positionY, speed, directionX, directionY );

        this.tail = tail;
        tail.head = this;
        tail.isAbove = this.isAbove;

        return tail;
    }

    // Add X number of tail Segments left of the Segment, each successive one the tail of the last.
    void addTailsLeft( int tails, int cellSize ) {
        Segment tail = this;

        while( tails > 0 ) {
            tail = tail.addTailLeft( cellSize );
            tails--;
        }
    }
}

