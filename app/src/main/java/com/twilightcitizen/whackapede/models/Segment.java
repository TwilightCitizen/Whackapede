/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.models;

import com.twilightcitizen.whackapede.gameElements.Direction;
import com.twilightcitizen.whackapede.gameElements.MovingGameElement;
import com.twilightcitizen.whackapede.gameElements.Position;

/*
Segment various data necessary for the proper placement of centipede segments on the game arena,
including position and direction along the X and Y axis, speed of traversal along them, position
in the above- or below-ground layers, Turns reached on their heading and Exits taken from them,
and their head and tail Segments, if any.
*/
class Segment extends MovingGameElement {
    // Head and tail Segments.
    private Segment head = null;
    private Segment tail = null;

    // Position on above- or below-ground layers.
    private boolean isAbove = true;

    // Turn reached during traversal and Exit taken from it.
    private Turn turnReached;
    private Exit exitTaken;

    Segment( Position position, int speed, Direction direction ) {
        super( position, speed, direction );
    }

    // Status as head or tail is determined by whether or not the Segment has a head or tail Segment.
    boolean getIsHead() { return head == null; }
    boolean getIsTail() { return tail == null; }

    // Head and tail are nearly read-only, removable but not directly settable to another Segment.
    Segment getHead() { return head; }
    Segment getTail() { return tail; }
    void removeHead() { head = null; }
    void removeTail() { tail = null; }

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

    // Add a tail Segment, placed adjacent to the Segment.
    private Segment addTailLeft( int cellSize, int sideX, int sideY ) {
        Segment tail = new Segment(
            new Position(
                super.getPosition().getX() + cellSize * sideX,
                super.getPosition().getY() + cellSize * sideY
            ),

            super.getSpeed(), super.getDirection()
        );

        this.tail = tail;
        tail.head = this;
        tail.isAbove = this.isAbove;

        return tail;
    }

    // Add X number of tail Segments adjacent to the Segment, each one the next tail of the last.
    @SuppressWarnings( "SameParameterValue" ) void addTails( int tails, int cellSize, int sideX, int sideY ) {
        Segment tail = this;

        while( tails > 0 ) {
            tail = tail.addTailLeft( cellSize, sideX, sideY );
            tails--;
        }
    }
}

