/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import java.util.ArrayList;

public class Game {
    private ArrayList< SegmentNew > centipedes = new ArrayList<>();

    public Game() {
        /* float radiusHole = cellSize / 2;
        float radiusSegment = radiusHole * 0.75f;

        Segment segment = new Segment( cellSize * 2 + radiusHole, cellSize * 2 + radiusHole, radiusSegment );

        segment.addTail().addTail().addTail().addTail().addTail().addTail().addTail().addTail().addTail();

        centipedes.add( segment );

        segment = new Segment( cellSize * 3 + radiusHole, cellSize * 2 + radiusHole, radiusSegment );

        segment.setIsBelow();
        segment.addTail().addTail().addTail().addTail().addTail().addTail().addTail().addTail().addTail();

        centipedes.add( segment ); */

        float cellWidthPercent = cellWidthFor( 1 );
        float radiusHolePercent = cellWidthPercent * 0.5f;
        float radiusSegmentPercent = radiusHolePercent * 0.75f;

        SegmentNew segmentNew = new SegmentNew(
            cellWidthPercent * 2 + radiusHolePercent,
            cellWidthPercent * 2 + radiusHolePercent,
            radiusSegmentPercent
        );

        centipedes.add( segmentNew );

        segmentNew = new SegmentNew(
            cellWidthPercent * 3 + radiusHolePercent,
            cellWidthPercent * 3 + radiusHolePercent,
            radiusSegmentPercent
        );

        segmentNew.setIsBelow();

        centipedes.add( segmentNew );
    }

    public ArrayList< SegmentNew > getCentipedes() {
        return centipedes;
    }

    public static float cellWidthFor( float canvasWidth ) {
        return canvasWidth / 7;
    }

    public static float cellHeightFor( float canvasHeight ) {
        return canvasHeight / 11;
    }
}
