/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import edu.fullsail.whackapede.gameElements.FixedGameElement;

/*
Hole maintains the position along the X and Y axis of the game arena where holes should be drawn.
*/
class Hole extends FixedGameElement {
    Hole( int positionX, int positionY ) { super( positionX, positionY ); }
}
