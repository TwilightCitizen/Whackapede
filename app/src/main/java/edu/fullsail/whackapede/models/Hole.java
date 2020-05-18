/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import edu.fullsail.whackapede.gameElements.FixedGameElement;
import edu.fullsail.whackapede.gameElements.Position;

/*
Hole maintains the position along the X and Y axis of the game arena where holes should be drawn.
*/
class Hole extends FixedGameElement {
    Hole( Position position ) { super( position ); }
}
