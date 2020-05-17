/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

/*
Hole maintains the position along the X and Y axis of the game arena where holes should be drawn.
*/
class Hole {
    // Positions along the X and Y axes.
    private final int positionX;
    private final int positionY;

    Hole( int positionX, int positionY ) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    // Positions are read-only.  Holes remain stationary.
    int getPositionX() { return positionX; }
    int getPositionY() { return positionY; }
}
