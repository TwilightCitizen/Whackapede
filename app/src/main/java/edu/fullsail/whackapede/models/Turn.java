/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import java.util.ArrayList;
import java.util.Random;

/*
Turn maintains the position along the X and Y axis of the game arena where Segments can turn.  It
also maintains an array of Exits from the turn available to a Segment traversing it, and provides
a random Exit from its available Exits, excluding any Exit that would cause the traversing Segment
to go in reverse.  Turns are not ordinarily drawn to screen, but can be for troubleshooting.
*/
class Turn {
    // Positions along the X and Y axes.
    private final int positionX;
    private final int positionY;

    // Exits available at the Turn.
    private final ArrayList< Exit > exits;

    Turn( int positionXPercent, int currentYPercent, ArrayList< Exit > exits ) {
        this.positionX = positionXPercent;
        this.positionY = currentYPercent;
        this.exits = exits;
    }

    // Positions are read-only.  Holes remain stationary.
    int getPositionX() { return positionX; }
    int getPositionY() { return positionY; }

    // Provide a random Exit from the Turn excluding any that would have the given Segment go in reverse.
    Exit getRandomExit( Segment segment ) {
        Random rand = new Random();
        Exit excludedExit = Exit.getExitReverseOf( segment.getDirectionX(), segment.getDirectionY() );
        ArrayList< Exit > includedExits = new ArrayList<>( exits );

        includedExits.remove( excludedExit );

        return includedExits.get( rand.nextInt( includedExits.size() ) );
    }

    // Get the count of Exits at the Turn.  Primarily used when drawing Exits to the screen.
    int getExitCount() { return exits.size(); }
}
