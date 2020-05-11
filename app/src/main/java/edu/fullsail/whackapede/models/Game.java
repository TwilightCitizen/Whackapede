/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import java.util.ArrayList;

public class Game {
    private ArrayList< Segment > centipedes = new ArrayList<>();

    public Game( float width ) {
        float cellSize = width / 7;
        float radiusHole = cellSize / 2;
        float radiusSegment = radiusHole * 0.75f;

        Segment segment = new Segment( cellSize * 2 + radiusHole, cellSize * 2 + radiusHole, radiusSegment );

        segment.addTail().addTail().addTail().addTail().addTail().addTail().addTail().addTail().addTail();

        centipedes.add( segment );

        segment = new Segment( cellSize * 3 + radiusHole, cellSize * 2 + radiusHole, radiusSegment );

        segment.setIsBelow();
        segment.addTail().addTail().addTail().addTail().addTail().addTail().addTail().addTail().addTail();

        centipedes.add( segment );
    }

    public ArrayList< Segment > getCentipedes() {
        return centipedes;
    }
}
