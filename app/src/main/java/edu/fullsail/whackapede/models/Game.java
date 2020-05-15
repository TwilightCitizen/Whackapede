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
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Locale;

import static edu.fullsail.whackapede.R.color.*;

public class Game {
    private boolean isPaused = true;

    private boolean drawingEnvironmentIsInitialized = false;

    private int colorEarth;
    private int colorGrass;
    private int colorTrans;
    private int colorAbove;
    private int colorBelow;
    private int colorFourWay;
    private int colorThreeWay;
    private int colorTwoWay;

    private Paint paintEarth;
    private Paint paintHole;
    private Paint paintLayer;
    private Paint paintSegment;
    private Paint paintTurn;

    private Bitmap bitmapLayer;
    private Canvas canvasLayer;

    private final float cellWidthPercent = 1 / 7f;
    private final float radiusHolePercent = cellWidthPercent * 0.5f;

    private final ArrayList< Segment > centipedes = new ArrayList<>();
    private final ArrayList< Hole > holes = new ArrayList<>();
    private final ArrayList< Turn > turns = new ArrayList<>();

    public Game() {
        setupHoles();
        setupTurns();
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

    private void setupTurns() {
        setupInteriorFourWayTurns();
        setupTopThreeWayTurns();
        setupBottomThreeWayTurns();
        setupLeftThreeWayTurns();
        setupRightThreeWayTurns();
        setupCornerTwoWayTurns();
    }

    private void setupInteriorFourWayTurns() {
        int[] fourWayTurnsAcross = new int[] { 1, 2, 3, 4, 5 };
        int[] fourWayTurnsDown   = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        for( int fourWayTurnAcross : fourWayTurnsAcross )
            for( int fourWayTurnDown : fourWayTurnsDown )
                turns.add( new Turn(
                    cellWidthPercent * fourWayTurnAcross + radiusHolePercent,
                    cellWidthPercent * fourWayTurnDown   + radiusHolePercent,
                    radiusHolePercent * 0.75f, Exit.fourWayExit
                ) );
    }

    private void setupTopThreeWayTurns() {
        int[] threeWayTurnsTopAcross = new int[] { 1, 2, 3, 4, 5 };
        int   threeWayTurnsTopDown   = 0;

        for( int threeWayTurnTopAcross : threeWayTurnsTopAcross )
            turns.add( new Turn(
                cellWidthPercent * threeWayTurnTopAcross + radiusHolePercent,
                cellWidthPercent * threeWayTurnsTopDown  + radiusHolePercent,
                radiusHolePercent * 0.75f, Exit.threeWayExitTop
            ) );
    }

    private void setupBottomThreeWayTurns() {
        int[] threeWayTurnsBottomAcross = new int[] { 1, 2, 3, 4, 5 };
        int   threeWayTurnsBottomDown   = 10;

        for( int threeWayTurnBottomAcross : threeWayTurnsBottomAcross )
            turns.add( new Turn(
                cellWidthPercent * threeWayTurnBottomAcross + radiusHolePercent,
                cellWidthPercent * threeWayTurnsBottomDown  + radiusHolePercent,
                radiusHolePercent * 0.75f, Exit.threeWayExitBottom
            ) );
    }

    private void setupLeftThreeWayTurns() {
        int   threeWayTurnsLeftAcross = 0;
        int[] threeWayTurnsLeftDown   = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        for( int threeWayTurnLeftDown : threeWayTurnsLeftDown )
            turns.add( new Turn(
                cellWidthPercent * threeWayTurnsLeftAcross + radiusHolePercent,
                cellWidthPercent * threeWayTurnLeftDown    + radiusHolePercent,
                radiusHolePercent * 0.75f, Exit.threeWayExitLeft
            ) );
    }

    private void setupRightThreeWayTurns() {
        int   threeWayTurnsRightAcross = 6;
        int[] threeWayTurnsRightDown   = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        for( int threeWayTurnRightDown : threeWayTurnsRightDown )
            turns.add( new Turn(
                cellWidthPercent * threeWayTurnsRightAcross + radiusHolePercent,
                cellWidthPercent * threeWayTurnRightDown    + radiusHolePercent,
                radiusHolePercent * 0.75f, Exit.threeWayExitRight
            ) );
    }

    private void setupCornerTwoWayTurns() {
        turns.add( new Turn(
            cellWidthPercent * 0 + radiusHolePercent,
            cellWidthPercent * 0 + radiusHolePercent,
            radiusHolePercent * 0.75f, Exit.twoWayExitTopLeft
        ) );

        turns.add( new Turn(
            cellWidthPercent * 6 + radiusHolePercent,
            cellWidthPercent * 0 + radiusHolePercent,
            radiusHolePercent * 0.75f, Exit.twoWayExitTopRight
        ) );

        turns.add( new Turn(
            cellWidthPercent * 0 + radiusHolePercent,
            cellWidthPercent * 10 + radiusHolePercent,
            radiusHolePercent * 0.75f, Exit.twoWayExitBottomLeft
        ) );

        turns.add( new Turn(
            cellWidthPercent * 6 + radiusHolePercent,
            cellWidthPercent * 10 + radiusHolePercent,
            radiusHolePercent * 0.75f, Exit.twoWayExitBottomRight
        ) );
    }

    private void setupCentipedes() {
        float radiusSegmentPercent = radiusHolePercent * 0.75f;
        float segmentSpeedPercent = cellWidthPercent * 5f;

        Segment segment = new Segment(
            cellWidthPercent * -1  + radiusHolePercent,
            cellWidthPercent *  3  + radiusHolePercent,
            radiusSegmentPercent, segmentSpeedPercent, 1, 0
        );

        // segment.addTailsLeft( 9 );
        centipedes.add( segment );

        segment = new Segment(
            cellWidthPercent * -2  + radiusHolePercent,
            cellWidthPercent *  3  + radiusHolePercent,
            radiusSegmentPercent, segmentSpeedPercent, 1, 0
        );

        centipedes.add( segment );
    }

    public void drawToCanvas( Context context, Canvas canvas ) {
        initializeDrawingEnvironment( context, canvas );
        drawEarthLayerToCanvas( canvas );
        drawBelowLayerToCanvas( canvas );
        drawGrassLayerToCanvas( canvas );
        drawAboveLayerToCanvas( canvas );

        // Temporarily draw turns for visualization during development.
        // drawTurnLayerToCanvas( canvas );
    }

    private void initializeDrawingEnvironment( Context context, Canvas canvas ) {
        if( drawingEnvironmentIsInitialized ) return;

        initializeColors( context );
        initializePaints();

        bitmapLayer = Bitmap.createBitmap(
            canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888
        );

        canvasLayer = new Canvas( bitmapLayer );
    }

    private void initializePaints() {
        paintLayer = new Paint();
        paintEarth = new Paint();
        paintHole = new Paint();
        paintSegment = new Paint();
        paintTurn = new Paint();

        paintEarth.setColor( colorEarth );
        paintHole.setAntiAlias( true );
        paintHole.setXfermode( new PorterDuffXfermode( PorterDuff.Mode.CLEAR ) );
        paintSegment.setAntiAlias( true );
        paintTurn.setAntiAlias( true );
    }

    private void initializeColors( Context context ) {
        colorEarth = ContextCompat.getColor( context, earthBrown );
        colorGrass = ContextCompat.getColor( context, grassGreen );
        colorTrans = ContextCompat.getColor( context, grassTrans );
        colorAbove = ContextCompat.getColor( context, dayBlue  );
        colorBelow = ContextCompat.getColor( context, nightBlue );
        colorFourWay  = ContextCompat.getColor( context, fourWay );
        colorThreeWay = ContextCompat.getColor( context, threeWay );
        colorTwoWay   = ContextCompat.getColor( context, twoWay );
    }

    private void drawEarthLayerToCanvas( Canvas canvas ) {
        bitmapLayer.eraseColor( Color.TRANSPARENT );
        canvasLayer.drawColor( colorGrass );

        for( Hole hole : holes ) canvasLayer.drawCircle(
            hole.getCurrentXFor( canvas ), hole.getCurrentYFor( canvas ),
            hole.getRadiusFor( canvas ), paintEarth
        );

        canvas.drawBitmap( bitmapLayer, 0, 0, paintLayer );
    }

    private void drawGrassLayerToCanvas( Canvas canvas ) {
        bitmapLayer.eraseColor( Color.TRANSPARENT );
        canvasLayer.drawColor( colorTrans );

        for( Hole hole : holes ) canvasLayer.drawCircle(
            hole.getCurrentXFor( canvas ), hole.getCurrentYFor( canvas ),
            hole.getRadiusFor( canvas ), paintHole
        );

        canvas.drawBitmap( bitmapLayer, 0, 0, paintLayer );
    }

    private void drawAboveLayerToCanvas( Canvas canvas ) {
        bitmapLayer.eraseColor( Color.TRANSPARENT );
        paintSegment.setColor( colorAbove );

        for( Segment centipede : centipedes ) {
            Segment segment = centipede;

            while( segment != null ) {
                if( segment.getIsAbove() ) canvasLayer.drawCircle(
                    segment.getCurrentXFor( canvas ), segment.getCurrentYFor( canvas ),
                    segment.getRadiusFor(  canvas ), paintSegment
                );

                segment = segment.getTail();
            }
        }

        canvas.drawBitmap( bitmapLayer, 0, 0, paintLayer );
    }

    private void drawBelowLayerToCanvas( Canvas canvas ) {
        bitmapLayer.eraseColor( Color.TRANSPARENT );
        paintSegment.setColor( colorBelow );

        for( Segment centipede : centipedes ) {
            Segment segment = centipede;

            while( segment != null ) {
                if( segment.getIsBelow() ) canvasLayer.drawCircle(
                    segment.getCurrentXFor( canvas ), segment.getCurrentYFor( canvas ),
                    segment.getRadiusFor(  canvas ), paintSegment
                );

                segment = segment.getTail();
            }
        }

        canvas.drawBitmap( bitmapLayer, 0, 0, paintLayer );
    }

    private void drawTurnLayerToCanvas( Canvas canvas ) {
        bitmapLayer.eraseColor( Color.TRANSPARENT );

        for( Turn turn : turns ) {
            if( turn.getExits().size() == 4 )
                paintTurn.setColor( colorFourWay );
            else if( turn.getExits().size() == 3 )
                paintTurn.setColor( colorThreeWay );
            else if( turn.getExits().size() == 2 )
                paintTurn.setColor( colorTwoWay );

            canvasLayer.drawCircle(
                turn.getCurrentXFor( canvas ), turn.getCurrentYFor( canvas ),
                turn.getRadiusFor( canvas ), paintTurn
            );
        }

        canvas.drawBitmap( bitmapLayer, 0, 0, paintLayer );
    }

    public void animateOver( float elapsedTimeMillis ) {
        if( isPaused ) return;

        float interval = elapsedTimeMillis / 1000f;

        for( Segment centipede : centipedes ) {
            Segment segment = centipede;
            
            while( segment != null ) {
                float segmentNextXPercent =
                    segment.getPositionXPercent() + centipede.getSpeedPercent() *
                    segment.getDirectionX() * interval;

                float segmentNextYPercent =
                    segment.getPositionYPercent() + segment.getSpeedPercent() *
                    segment.getDirectionY() * interval;

                animateThroughHoles( segment, segmentNextXPercent, segmentNextYPercent );
                animateThroughTurns( segment, segmentNextXPercent, segmentNextYPercent );

                segment = segment.getTail();
            }
        }
    }

    private void animateThroughTurns(
        Segment segment, float segmentNextXPercent, float segmentNextYPercent
    ) {
        for( Turn turn : turns ) {
            boolean segmentPerfectlyInTurn =
                turn.getPositionXPercent() == segmentNextXPercent && turn.getPositionYPercent() == segmentNextYPercent ;

            boolean segmentPassedTurnTopToBottom =
                segment.getPositionXPercent() == turn.getPositionXPercent() && turn.getPositionXPercent() == segmentNextXPercent &&
                segment.getPositionYPercent() <  turn.getPositionYPercent() && turn.getPositionYPercent() <  segmentNextYPercent;

            boolean segmentPassedTurnBottomToTop =
                segment.getPositionXPercent() == turn.getPositionXPercent() && turn.getPositionXPercent() == segmentNextXPercent &&
                segment.getPositionYPercent() >  turn.getPositionYPercent() && turn.getPositionYPercent() >  segmentNextYPercent;

            boolean segmentPassedLeftToRight =
                segment.getPositionYPercent() == turn.getPositionYPercent() && turn.getPositionYPercent() == segmentNextYPercent &&
                segment.getPositionXPercent() <  turn.getPositionXPercent() && turn.getPositionXPercent() <  segmentNextXPercent;

            boolean segmentPassedRightToLeft =
                segment.getPositionYPercent() == turn.getPositionYPercent() && turn.getPositionYPercent() == segmentNextYPercent &&
                segment.getPositionXPercent() >  turn.getPositionXPercent() && turn.getPositionXPercent() >  segmentNextXPercent;

            boolean segmentReachedTurn =
                segmentPerfectlyInTurn || segmentPassedTurnTopToBottom || segmentPassedTurnBottomToTop || segmentPassedLeftToRight || segmentPassedRightToLeft;

            if( !segmentReachedTurn ) continue;

            if( segment.getTurnReached() == turn ) continue;

            segment.setTurnReached( turn );

            if( segment.getIsHead() )
                segment.setExitTaken( turn.getRandomExit( segment ) );
            else
                segment.setExitTaken( segment.getHead().getExitTaken() );

            segment.setDirectionX( segment.getExitTaken().getDirectionX() );
            segment.setDirectionY( segment.getExitTaken().getDirectionY() );

            if( segmentPerfectlyInTurn ) {
                segment.setPositionXPercent( segmentNextXPercent );
                segment.setPositionYPercent( segmentNextYPercent );

                return;
            }

            float segmentTravelAfterTurn = 0;

            if( segmentPassedTurnTopToBottom ) {
                segmentTravelAfterTurn = segmentNextYPercent - turn.getPositionYPercent();
            } else if( segmentPassedTurnBottomToTop ) {
                segmentTravelAfterTurn = turn.getPositionYPercent() - segmentNextYPercent;
            } else if( segmentPassedLeftToRight ) {
                segmentTravelAfterTurn = segmentNextXPercent - turn.getPositionXPercent();
            } else if( segmentPassedRightToLeft ) {
                segmentTravelAfterTurn = turn.getPositionXPercent() - segmentNextXPercent;
            }

            if( segment.getExitTaken().equals( Exit.exitTop ) ) {
                segmentNextXPercent = turn.getPositionXPercent();
                segmentNextYPercent = segmentNextYPercent - segmentTravelAfterTurn;
            } else if( segment.getExitTaken().equals( Exit.exitBottom ) ) {
                segmentNextXPercent = turn.getPositionXPercent();
                segmentNextYPercent = segmentNextYPercent + segmentTravelAfterTurn;
            } else if( segment.getExitTaken().equals( Exit.exitLeft ) ) {
                segmentNextXPercent = segmentNextXPercent - segmentTravelAfterTurn;
                segmentNextYPercent = turn.getPositionYPercent();
            } else if(  segment.getExitTaken().equals( Exit.exitRight ) ) {
                segmentNextXPercent = segmentNextXPercent + segmentTravelAfterTurn;
                segmentNextYPercent = turn.getPositionYPercent();
            }

            segment.setPositionXPercent( segmentNextXPercent );
            segment.setPositionYPercent( segmentNextYPercent );

            return;
        }

        segment.setPositionXPercent( segmentNextXPercent );
        segment.setPositionYPercent( segmentNextYPercent );
    }

    private void animateThroughHoles(
        Segment segment, float segmentNextXPercent, float segmentNextYPercent
    ) {
        for( Hole hole : holes ) {
            boolean segmentPerfectlyInHole =
                hole.getPositionXPercent() == segmentNextXPercent && hole.getPositionYPercent() == segmentNextYPercent ;

            boolean segmentPassedHoleTopToBottom =
                segment.getPositionXPercent() == hole.getPositionXPercent() && hole.getPositionXPercent() == segmentNextXPercent &&
                segment.getPositionYPercent() <  hole.getPositionYPercent() && hole.getPositionYPercent() <  segmentNextYPercent;

            boolean segmentPassedHoleBottomToTop =
                segment.getPositionXPercent() == hole.getPositionXPercent() && hole.getPositionXPercent() == segmentNextXPercent &&
                segment.getPositionYPercent() >  hole.getPositionYPercent() && hole.getPositionYPercent() >  segmentNextYPercent;

            boolean segmentPassedLeftToRight =
                segment.getPositionYPercent() == hole.getPositionYPercent() && hole.getPositionYPercent() == segmentNextYPercent &&
                segment.getPositionXPercent() <  hole.getPositionXPercent() && hole.getPositionXPercent() <  segmentNextXPercent;

            boolean segmentPassedRightToLeft =
                segment.getPositionYPercent() == hole.getPositionYPercent() && hole.getPositionYPercent() == segmentNextYPercent &&
                segment.getPositionXPercent() >  hole.getPositionXPercent() && hole.getPositionXPercent() >  segmentNextXPercent;

            boolean segmentReachedHole =
                    segmentPerfectlyInHole || segmentPassedHoleTopToBottom || segmentPassedHoleBottomToTop || segmentPassedLeftToRight || segmentPassedRightToLeft;

            if( segmentReachedHole ) segment.toggleAboveBelow();
        }
    }

    public void toggleState() {
        isPaused = !isPaused;
    }

    public boolean isPaused() {
        return isPaused;
    }
}
