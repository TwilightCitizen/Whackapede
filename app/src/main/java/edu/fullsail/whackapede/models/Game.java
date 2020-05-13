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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import edu.fullsail.whackapede.R;

public class Game {
    private boolean isPaused = true;

    private final double cellWidthPercent = 1 / 7d;
    private final double radiusHolePercent = cellWidthPercent * 0.5d;

    private final ArrayList< Segment > centipedes = new ArrayList<>();
    private final ArrayList< Hole > holes = new ArrayList<>();
    private final ArrayList< Turnstile > turnstiles = new ArrayList<>();

    public Game() {
        setupHoles();
        setupTurnstiles();
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

    private void setupTurnstiles() {
        setupInteriorFourWayTurnstiles();
        setupTopThreeWayTurnstiles();
        setupBottomThreeWayTurnstiles();
        setupLeftThreeWayTurnstiles();
        setupRightThreeWayTurnstiles();
        setupCornerTwoWayTurnstiles();
    }

    private void setupInteriorFourWayTurnstiles() {
        int[] fourWayTurnstilesAcross = new int[] { 1, 2, 3, 4, 5 };
        int[] fourWayTurnstilesDown   = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        for( int fourWayTurnstileAcross : fourWayTurnstilesAcross )
            for( int fourWayTurnstileDown : fourWayTurnstilesDown )
                turnstiles.add( new Turnstile(
                    cellWidthPercent * fourWayTurnstileAcross + radiusHolePercent,
                    cellWidthPercent * fourWayTurnstileDown   + radiusHolePercent,
                    radiusHolePercent / 2, Exit.getFourWayExit()
                ) );
    }

    private void setupTopThreeWayTurnstiles() {
        int[] threeWayTurnstilesTopAcross = new int[] { 1, 2, 3, 4, 5 };
        int   threeWayTurnstilesTopDown   = 0;

        for( int threeWayTurnstileTopAcross : threeWayTurnstilesTopAcross )
            turnstiles.add( new Turnstile(
                cellWidthPercent * threeWayTurnstileTopAcross + radiusHolePercent,
                cellWidthPercent * threeWayTurnstilesTopDown  + radiusHolePercent,
                radiusHolePercent / 2, Exit.getThreeWayExitTop()
            ) );
    }

    private void setupBottomThreeWayTurnstiles() {
        int[] threeWayTurnstilesBottomAcross = new int[] { 1, 2, 3, 4, 5 };
        int   threeWayTurnstilesBottomDown   = 10;

        for( int threeWayTurnstileBottomAcross : threeWayTurnstilesBottomAcross )
            turnstiles.add( new Turnstile(
                cellWidthPercent * threeWayTurnstileBottomAcross + radiusHolePercent,
                cellWidthPercent * threeWayTurnstilesBottomDown  + radiusHolePercent,
                radiusHolePercent / 2, Exit.getThreeWayExitBottom()
            ) );
    }

    private void setupLeftThreeWayTurnstiles() {
        int   threeWayTurnstilesLeftAcross = 0;
        int[] threeWayTurnstilesLeftDown   = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        for( int threeWayTurnstileLeftDown : threeWayTurnstilesLeftDown )
            turnstiles.add( new Turnstile(
                cellWidthPercent * threeWayTurnstilesLeftAcross + radiusHolePercent,
                cellWidthPercent * threeWayTurnstileLeftDown    + radiusHolePercent,
                radiusHolePercent / 2, Exit.getThreeWayExitLeft()
            ) );
    }

    private void setupRightThreeWayTurnstiles() {
        int   threeWayTurnstilesRightAcross = 6;
        int[] threeWayTurnstilesRightDown   = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        for( int threeWayTurnstileRightDown : threeWayTurnstilesRightDown )
            turnstiles.add( new Turnstile(
                cellWidthPercent * threeWayTurnstilesRightAcross + radiusHolePercent,
                cellWidthPercent * threeWayTurnstileRightDown    + radiusHolePercent,
                radiusHolePercent / 2, Exit.getThreeWayExitRight()
            ) );
    }

    private void setupCornerTwoWayTurnstiles() {
        turnstiles.add( new Turnstile(
            cellWidthPercent * 0 + radiusHolePercent,
            cellWidthPercent * 0 + radiusHolePercent,
            radiusHolePercent / 2, Exit.getTwoWayExitTopLeft()
        ) );

        turnstiles.add( new Turnstile(
            cellWidthPercent * 6 + radiusHolePercent,
            cellWidthPercent * 0 + radiusHolePercent,
            radiusHolePercent / 2, Exit.getTwoWayExitTopRight()
        ) );

        turnstiles.add( new Turnstile(
            cellWidthPercent * 0 + radiusHolePercent,
            cellWidthPercent * 10 + radiusHolePercent,
            radiusHolePercent / 2, Exit.getTwoWayExitBottomLeft()
        ) );

        turnstiles.add( new Turnstile(
            cellWidthPercent * 6 + radiusHolePercent,
            cellWidthPercent * 10 + radiusHolePercent,
            radiusHolePercent / 2, Exit.getTwoWayExitBottomRight()
        ) );
    }

    private void setupCentipedes() {
        double radiusSegmentPercent = radiusHolePercent * 0.75d;
        double segmentSpeedPercent = cellWidthPercent * 2d;

        Segment segment = new Segment(
            cellWidthPercent * 3 + radiusHolePercent, cellWidthPercent * 0 + radiusHolePercent,
            radiusSegmentPercent, segmentSpeedPercent, 0, 1
        );

        segment.addTailsTop( 9 );
        centipedes.add( segment );
    }

    public void drawToCanvas( Context context, Canvas canvas ) {
        drawEarthLayerToCanvas( context, canvas );
        drawBelowLayerToCanvas( context, canvas );
        drawGrassLayerToCanvas( context, canvas );
        drawAboveLayerToCanvas( context, canvas );

        // Temporarily draw turnstiles for visualization during development.
        drawTurnstileLayerToCanvas( context, canvas );
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

    private void drawTurnstileLayerToCanvas( Context context, Canvas canvas ) {
        int colorFourWay  = ContextCompat.getColor( context, R.color.fourWay );
        int colorThreeWay = ContextCompat.getColor( context, R.color.threeWay );
        int colorTwoWay   = ContextCompat.getColor( context, R.color.twoWay );

        Bitmap bitmapTurnstile = Bitmap.createBitmap( canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888 );
        Canvas canvasTurnstile = new Canvas( bitmapTurnstile );

        Paint paintTurnstile = new Paint();

        paintTurnstile.setAntiAlias( true );

        for( Turnstile turnstile : turnstiles ) {
            if( turnstile.getExits().size() == 4 )
                paintTurnstile.setColor( colorFourWay );
            else if( turnstile.getExits().size() == 3 )
                paintTurnstile.setColor( colorThreeWay );
            else if( turnstile.getExits().size() == 2 )
                paintTurnstile.setColor( colorTwoWay );

            canvasTurnstile.drawCircle(
                (float) turnstile.getCurrentXFor( canvas ), (float) turnstile.getCurrentYFor( canvas ),
                (float) turnstile.getRadiusFor( canvas ), paintTurnstile
            );
        }

        canvas.drawBitmap( bitmapTurnstile, 0, 0, new Paint() );
    }

    public void animateOver( double elapsedTimeMillis ) {
        if( isPaused ) return;

        double interval = elapsedTimeMillis / 1000;

        for( Segment centipede : centipedes ) {
            Segment segment = centipede;

            while( segment != null ) {
                double segmentNextXPercent = segment.getCurrentXPercent() + segment.getSpeedPercent() * segment.getDirectionX() * interval;
                double segmentNextYPercent = segment.getCurrentYPercent() + segment.getSpeedPercent() * segment.getDirectionY() * interval;

                animateSegmentThroughHoles( segment, segmentNextXPercent, segmentNextYPercent );
                animateSegmentThroughTurnstiles( segment, segmentNextXPercent, segmentNextYPercent );

                segment = segment.getTail();
            }
        }
    }

    private void animateSegmentThroughTurnstiles(
        Segment segment, double segmentNextXPercent, double segmentNextYPercent
    ) {
        for( Turnstile turnstile : turnstiles ) {
            boolean segmentPerfectlyInTurnstile =
                turnstile.getCurrentXPercent() == segmentNextXPercent &&
                turnstile.getCurrentYPercent() == segmentNextYPercent;

            boolean segmentPassedTurnstileVertically =
                turnstile.getCurrentXPercent() == segmentNextXPercent && ( (
                    turnstile.getCurrentYPercent() < segmentNextYPercent &&
                    turnstile.getCurrentYPercent() > segment.getCurrentYPercent()
                ) || (
                    turnstile.getCurrentYPercent() > segmentNextYPercent &&
                    turnstile.getCurrentYPercent() < segment.getCurrentYPercent()
                ) );

            boolean segmentPassedTurnstileHorizontally =
                turnstile.getCurrentYPercent() == segmentNextYPercent && ( (
                    turnstile.getCurrentXPercent() < segmentNextXPercent &&
                    turnstile.getCurrentXPercent() > segment.getCurrentXPercent()
                ) || (
                    turnstile.getCurrentXPercent() > segmentNextXPercent &&
                    turnstile.getCurrentXPercent() < segment.getCurrentXPercent()
                ) );

            if( segmentPerfectlyInTurnstile ||
                segmentPassedTurnstileVertically ||
                segmentPassedTurnstileHorizontally
            ) {
                if( segment.getIsHead() )
                    segment.setExitTaken( turnstile.getRandomExit() );
                else
                    segment.setExitTaken( segment.getHead().getExitTaken() );
            } else {
                segment.setCurrentXPercent( segmentNextXPercent );
                segment.setCurrentYPercent( segmentNextYPercent );

                return;
            }

            segment.setDirectionX( segment.getExitTaken().getDirectionX() );
            segment.setDirectionY( segment.getExitTaken().getDirectionY() );

            double segmentTotalTravel;
            double segmentTravelToTurnstile ;
            double segmentTravelAfterTurnstile = 0;

            if( segmentPerfectlyInTurnstile ) {
                segment.setCurrentXPercent( segmentNextXPercent );
                segment.setCurrentYPercent( segmentNextYPercent );

                return;
            } else if( segmentPassedTurnstileVertically ) {
                segmentTotalTravel          = segment.getCurrentYPercent() - segmentNextYPercent;
                segmentTravelToTurnstile    = segment.getCurrentYPercent() - turnstile.getCurrentYPercent();
                segmentTravelAfterTurnstile = segmentTotalTravel - segmentTravelToTurnstile;
            } else if( segmentPassedTurnstileHorizontally ) {
                segmentTotalTravel          = segment.getCurrentXPercent() - segmentNextXPercent;
                segmentTravelToTurnstile    = segment.getCurrentXPercent() - turnstile.getCurrentXPercent();
                segmentTravelAfterTurnstile = segmentTotalTravel - segmentTravelToTurnstile;
            }

            if( segment.getExitTaken().equals( Exit.getExitTop() ) ||
                segment.getExitTaken().equals( Exit.getExitBottom() )
            ) {
                segmentNextXPercent = turnstile.getCurrentXPercent();
                segmentNextYPercent = segmentNextYPercent + segmentTravelAfterTurnstile;
            } else if( segment.getExitTaken().equals( Exit.getExitLeft() ) ||
                       segment.getExitTaken().equals( Exit.getExitRight() )
            ) {
                segmentNextXPercent = segmentNextXPercent + segmentTravelAfterTurnstile;;
                segmentNextYPercent = turnstile.getCurrentYPercent();
            }

            segment.setCurrentXPercent( segmentNextXPercent );
            segment.setCurrentYPercent( segmentNextYPercent );
        }
    }

    private void animateSegmentThroughHoles(
        Segment segment, double segmentNextXPercent, double segmentNextYPercent
    ) {
        for( Hole hole : holes ) {
            boolean segmentPerfectlyInHole =
                hole.getCurrentXPercent() == segmentNextXPercent &&
                hole.getCurrentYPercent() == segmentNextYPercent;

            boolean segmentPassedHoleVertically =
                hole.getCurrentXPercent() == segmentNextXPercent && ( (
                    hole.getCurrentYPercent() < segmentNextYPercent &&
                    hole.getCurrentYPercent() > segment.getCurrentYPercent()
                ) || (
                    hole.getCurrentYPercent() > segmentNextYPercent &&
                    hole.getCurrentYPercent() < segment.getCurrentYPercent()
                ) );

            boolean segmentPassedHoleHorizontally =
                hole.getCurrentYPercent() == segmentNextYPercent && ( (
                    hole.getCurrentXPercent() < segmentNextXPercent &&
                    hole.getCurrentXPercent() > segment.getCurrentXPercent()
                ) || (
                    hole.getCurrentXPercent() > segmentNextXPercent &&
                    hole.getCurrentXPercent() < segment.getCurrentXPercent()
                ) );

            if( segmentPerfectlyInHole ||
                segmentPassedHoleVertically ||
                segmentPassedHoleHorizontally
            ) segment.toggleAboveBelow();
        }
    }

    public void toggleState() {
        isPaused = !isPaused;
    }

    public boolean isPaused() {
        return isPaused;
    }
}
