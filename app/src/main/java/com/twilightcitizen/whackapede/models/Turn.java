/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.models;

import java.util.ArrayList;
import java.util.Random;

import com.twilightcitizen.whackapede.gameElements.FixedGameElement;
import com.twilightcitizen.whackapede.gameElements.Position;

/*
Turn maintains the position along the X and Y axis of the game arena where Segments can turn.  It
also maintains an array of Exits from the turn available to a Segment traversing it, and provides
a random Exit from its available Exits, excluding any Exit that would cause the traversing Segment
to go in reverse.  Turns are not ordinarily drawn to screen, but can be for troubleshooting.
*/
class Turn extends FixedGameElement {
    // Exits available at the Turn.
    private final ArrayList< Exit > exits;

    Turn( Position position, ArrayList< Exit > exits ) {
        super( position );

        this.exits = exits;
    }

    // Provide a random Exit from the Turn excluding any that would have the given Segment go in reverse.
    Exit getRandomExit( Segment segment ) {
        Random rand = new Random();
        Exit excludedExit = Exit.getExitReverseOf( segment.getDirection() );
        ArrayList< Exit > includedExits = new ArrayList<>( exits );

        includedExits.remove( excludedExit );

        return includedExits.get( rand.nextInt( includedExits.size() ) );
    }
}
