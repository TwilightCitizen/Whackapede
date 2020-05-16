/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

class Hole {
    private final int positionX;
    private final int positionY;

    Hole( int positionX, int positionY ) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    int getPositionX() { return positionX; }
    int getPositionY() { return positionY; }
}
