/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import edu.fullsail.whackapede.R;

public class Game {
    private boolean isPaused = true;

    private double cellWidthPercent = 1 / 7d;
    private double radiusHolePercent = cellWidthPercent * 0.5d;
    private double radiusSegmentPercent = radiusHolePercent * 0.75d;
    private double segmentVelocityPercent = cellWidthPercent * 10d;

    private ArrayList< Segment > centipedes = new ArrayList<>();
    private ArrayList< Hole > holes = new ArrayList<>();

    public Game() {
        setupHoles();
        setupCentipedes();
    }

    private void setupHoles() {
        int[] holesAcross = new int[] { 1, 3, 5 };
        int[] holesDown   = new int[] { 1, 3, 5, 7, 9 };

        for( int holeAcross : holesAcross ) for( int holeDown : holesDown )
            holes.add( new Hole(
                cellWidthPercent * holeAcross + radiusHolePercent,
                cellWidthPercent * holeDown   + radiusHolePercent,
                radiusHolePercent
            ) );
    }

    private void setupCentipedes() {
        Segment segment = new Segment(
            cellWidthPercent * 2 + radiusHolePercent,
            cellWidthPercent * 2 + radiusHolePercent,
            radiusSegmentPercent,
            0, segmentVelocityPercent
        );

        segment.addTails( 9 );
        centipedes.add( segment );

        segment = new Segment(
            cellWidthPercent * 3 + radiusHolePercent,
            cellWidthPercent * 3 + radiusHolePercent,
            radiusSegmentPercent,
            0, segmentVelocityPercent * -1
        );

        segment.setIsBelow();
        segment.addTails( 9 );


        centipedes.add( segment );
    }

    public void drawToCanvas( Context context, Canvas canvas ) {
        drawEarthLayerToCanvas( context, canvas );
        drawBelowLayerToCanvas( context, canvas );
        drawGrassLayerToCanvas( context, canvas );
        drawAboveLayerToCanvas( context, canvas );
    }

    private void drawEarthLayerToCanvas( Context context, Canvas canvas ) {
        int colorEarth = ContextCompat.getColor( context, R.color.earthBrown );

        canvas.drawColor( colorEarth );
    }

    private void drawGrassLayerToCanvas( Context context, Canvas canvas ) {
        int colorGrass = ContextCompat.getColor( context, R.color.grassGreen );
        Bitmap bitmapGrass = Bitmap.createBitmap( canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888 );
        Canvas canvasGrass = new Canvas( bitmapGrass );

        canvasGrass.drawColor( colorGrass );

        Paint paintHole = new Paint();

        paintHole.setAntiAlias( true );
        paintHole.setXfermode( new PorterDuffXfermode( PorterDuff.Mode.CLEAR ) );

        for( Hole hole : holes ) canvasGrass.drawCircle(
            (float) hole.getCurrentXFor( canvas ), (float) hole.getCurrentYFor( canvas ),
            (float) hole.getRadiusFor( canvas ), paintHole
        );

        canvas.drawBitmap( bitmapGrass, 0, 0, new Paint() );
    }

    private void drawAboveLayerToCanvas( Context context, Canvas canvas ) {
        int colorAbove = ContextCompat.getColor( context, R.color.dayBlue  );
        Bitmap bitmapAbove = Bitmap.createBitmap( canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888 );
        Canvas canvasAbove = new Canvas( bitmapAbove );

        Paint paintSegment = new Paint();

        paintSegment.setAntiAlias( true );
        paintSegment.setColor( colorAbove );

        for( Segment centipede : centipedes ) {
            Segment segment = centipede;

            while( segment != null ) {
                if( segment.getIsAbove() ) canvasAbove.drawCircle(
                    (float) segment.getCurrentXFor( canvas ), (float) segment.getCurrentYFor( canvas ),
                    (float) segment.getRadiusFor(  canvas ), paintSegment
                );

                segment = segment.getTail();
            }
        }

        canvas.drawBitmap( bitmapAbove, 0, 0, new Paint() );
    }

    private void drawBelowLayerToCanvas( Context context, Canvas canvas ) {
        int colorBelow = ContextCompat.getColor( context, R.color.nightBlue );
        Bitmap bitmapBelow = Bitmap.createBitmap( canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888 );
        Canvas canvasAbove = new Canvas( bitmapBelow );

        Paint paintSegment = new Paint();

        paintSegment.setAntiAlias( true );
        paintSegment.setColor( colorBelow );

        for( Segment centipede : centipedes ) {
            Segment segment = centipede;

            while( segment != null ) {
                if( segment.getIsBelow() ) canvasAbove.drawCircle(
                    (float) segment.getCurrentXFor( canvas ), (float) segment.getCurrentYFor( canvas ),
                    (float) segment.getRadiusFor(  canvas ), paintSegment
                );

                segment = segment.getTail();
            }
        }

        canvas.drawBitmap( bitmapBelow, 0, 0, new Paint() );
    }

    public void updatePositions( double elapsedTimeMillis ) {
        if( isPaused ) return;

        double interval = elapsedTimeMillis / 1000;

        for( Segment centipede : centipedes ) {
            Segment segment = centipede;

            while( segment != null ) {
                segment.updatePosition( interval );

                segment = segment.getTail();
            }
        }
    }

    public void toggleState() {
        isPaused = !isPaused;
    }

    public boolean isPaused() {
        return isPaused;
    }
}
