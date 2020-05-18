/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

public class FixedGameElement extends GameElement {
    FixedGameElement( int positionX, int positionY ) {
        super( positionX, positionY );
    }

    // Position along the X and Y axes is read-only.
    int getPositionX() { return super.getPositionX(); }
    int getPositionY() { return super.getPositionY(); }
}
