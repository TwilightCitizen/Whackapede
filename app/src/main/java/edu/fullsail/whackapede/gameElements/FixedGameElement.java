/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.gameElements;

public class FixedGameElement extends GameElement {
    public FixedGameElement( int positionX, int positionY ) {
        super( positionX, positionY );
    }

    // Position along the X and Y axes is read-only.
    public int getPositionX() { return super.getPositionX(); }
    public int getPositionY() { return super.getPositionY(); }
}
