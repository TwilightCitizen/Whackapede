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
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Locale;

import edu.fullsail.whackapede.R;

public class Game {
    private boolean isPaused = true;

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
        float segmentSpeedPercent = cellWidthPercent * 1f;

        Segment segment = new Segment(
            cellWidthPercent * -1  + radiusHolePercent,
            cellWidthPercent * 3  + radiusHolePercent,
            radiusSegmentPercent, segmentSpeedPercent, 1, 0
        );

        segment.addTailsLeft( 9 );
        centipedes.add( segment );
    }

    public void drawToCanvas( Context context, Canvas canvas ) {
        drawEarthLayerToCanvas( context, canvas );
        drawBelowLayerToCanvas( context, canvas );
        drawGrassLayerToCanvas( context, canvas );
        drawAboveLayerToCanvas( context, canvas );

        // Temporarily draw turns for visualization during development.
        // drawTurnLayerToCanvas( context, canvas );
    }

    private void drawEarthLayerToCanvas( Context context, Canvas canvas ) {
        int colorEarth = ContextCompat.getColor( context, R.color.earthBrown );

        canvas.drawColor( colorEarth );
    }

    private void drawGrassLayerToCanvas( Context context, Canvas canvas ) {
        int colorGrass = ContextCompat.getColor( context, R.color.grassGreen );

        Bitmap bitmapGrass = Bitmap.createBitmap(
            canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888
        );

        Canvas canvasGrass = new Canvas( bitmapGrass );

        canvasGrass.drawColor( ContextCompat.getColor( context, R.color.grassTrans ) ); // colorGrass );

        Paint paintHole = new Paint();

        paintHole.setAntiAlias( true );
        paintHole.setXfermode( new PorterDuffXfermode( PorterDuff.Mode.CLEAR ) );

        for( Hole hole : holes ) canvasGrass.drawCircle(
            hole.getCurrentXFor( canvas ), hole.getCurrentYFor( canvas ),
            hole.getRadiusFor( canvas ), paintHole
        );

        canvas.drawBitmap( bitmapGrass, 0, 0, new Paint() );
    }

    private void drawAboveLayerToCanvas( Context context, Canvas canvas ) {
        int colorAbove = ContextCompat.getColor( context, R.color.dayBlue  );

        Bitmap bitmapAbove = Bitmap.createBitmap(
            canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888
        );

        Canvas canvasAbove = new Canvas( bitmapAbove );
        Paint paintSegment = new Paint();

        paintSegment.setAntiAlias( true );
        paintSegment.setColor( colorAbove );

        for( Segment centipede : centipedes ) {
            Segment segment = centipede;

            while( segment != null ) {
                if( segment.getIsAbove() ) canvasAbove.drawCircle(
                    segment.getCurrentXFor( canvas ), segment.getCurrentYFor( canvas ),
                    segment.getRadiusFor(  canvas ), paintSegment
                );

                segment = segment.getTail();
            }
        }

        canvas.drawBitmap( bitmapAbove, 0, 0, new Paint() );
    }

    private void drawBelowLayerToCanvas( Context context, Canvas canvas ) {
        int colorBelow = ContextCompat.getColor( context, R.color.nightBlue );

        Bitmap bitmapBelow = Bitmap.createBitmap(
            canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888
        );

        Canvas canvasAbove = new Canvas( bitmapBelow );
        Paint paintSegment = new Paint();

        paintSegment.setAntiAlias( true );
        paintSegment.setColor( colorBelow );

        for( Segment centipede : centipedes ) {
            Segment segment = centipede;

            while( segment != null ) {
                if( segment.getIsBelow() ) canvasAbove.drawCircle(
                    segment.getCurrentXFor( canvas ), segment.getCurrentYFor( canvas ),
                    segment.getRadiusFor(  canvas ), paintSegment
                );

                segment = segment.getTail();
            }
        }

        canvas.drawBitmap( bitmapBelow, 0, 0, new Paint() );
    }

    private void drawTurnLayerToCanvas( Context context, Canvas canvas ) {
        int colorFourWay  = ContextCompat.getColor( context, R.color.fourWay );
        int colorThreeWay = ContextCompat.getColor( context, R.color.threeWay );
        int colorTwoWay   = ContextCompat.getColor( context, R.color.twoWay );

        Bitmap bitmapTurn = Bitmap.createBitmap(
            canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888
        );

        Canvas canvasTurn = new Canvas( bitmapTurn );
        Paint paintTurn = new Paint();

        paintTurn.setAntiAlias( true );

        for( Turn turn : turns ) {
            if( turn.getExits().size() == 4 )
                paintTurn.setColor( colorFourWay );
            else if( turn.getExits().size() == 3 )
                paintTurn.setColor( colorThreeWay );
            else if( turn.getExits().size() == 2 )
                paintTurn.setColor( colorTwoWay );

            canvasTurn.drawCircle(
                turn.getCurrentXFor( canvas ), turn.getCurrentYFor( canvas ),
                turn.getRadiusFor( canvas ), paintTurn
            );
        }

        canvas.drawBitmap( bitmapTurn, 0, 0, new Paint() );
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
            boolean turnXMatchesSegmentX =
                Float.compare( turn.getPositionXPercent(), segmentNextXPercent ) == 0;
            boolean turnYMatchesSegmentY =
                Float.compare( turn.getPositionYPercent(), segmentNextYPercent ) == 0;

            boolean segmentPerfectlyInTurn = turnXMatchesSegmentX && turnYMatchesSegmentY;

            boolean turnYBelowSegmentY =
                Float.compare( turn.getPositionYPercent(), segmentNextYPercent ) < 0;
            boolean turnYAboveNextY =
                Float.compare( turn.getPositionYPercent(), segment.getPositionYPercent() ) > 0;

            boolean segmentPassedTurnTopToBottom =
                    turnXMatchesSegmentX && turnYBelowSegmentY && turnYAboveNextY;

            boolean turnYAboveSegmentY =
                Float.compare( turn.getPositionYPercent(), segmentNextYPercent ) > 0;
            boolean turnYBelowNextY =
                Float.compare( turn.getPositionYPercent(), segment.getPositionYPercent() ) < 0;

            boolean segmentPassedTurnBottomToTop =
                turnXMatchesSegmentX && turnYAboveSegmentY && turnYBelowNextY;

            boolean turnXBelowSegmentX =
                Float.compare( turn.getPositionXPercent(), segmentNextXPercent ) < 0;
            boolean turnXAboveNextX =
                Float.compare( turn.getPositionXPercent(), segment.getPositionXPercent() ) > 0;

            boolean segmentPassedLeftToRight =
                turnYMatchesSegmentY && turnXBelowSegmentX && turnXAboveNextX;

            boolean turnXAboveSegmentX =
                Float.compare( turn.getPositionXPercent(), segmentNextXPercent ) > 0;
            boolean turnXBelowNextX  =
                Float.compare( turn.getPositionXPercent(), segment.getPositionXPercent() ) < 0;

            boolean segmentPassedRightToLeft =
                turnYMatchesSegmentY && turnXAboveSegmentX && turnXBelowNextX;

            boolean segmentPassedTurnVertically =
                segmentPassedTurnBottomToTop || segmentPassedTurnTopToBottom;

            boolean segmentPassedTurnHorizontally =
                segmentPassedLeftToRight || segmentPassedRightToLeft;

            boolean segmentReachedTurn =
                segmentPerfectlyInTurn ||
                segmentPassedTurnVertically ||
                segmentPassedTurnHorizontally;

            if( !segmentReachedTurn ) continue;

            Log.wtf( "GAME", "SEGMENT REACHED TURN" );
            Log.wtf( "GAME", String.format( Locale.getDefault(), "TURN HAS %d EXITS", turn.getExits().size() ) );

            if( segment.getIsHead() )
                segment.setExitTaken( turn.getRandomExit( segment ) );
            else
                segment.setExitTaken( segment.getHead().getExitTaken() );

            segment.setDirectionX( segment.getExitTaken().getDirectionX() );
            segment.setDirectionY( segment.getExitTaken().getDirectionY() );

            float segmentTotalTravel;
            float segmentTravelToTurn ;
            float segmentTravelAfterTurn = 0;

            if( segmentPerfectlyInTurn ) {
                segment.setPositionXPercent( segmentNextXPercent );
                segment.setPositionYPercent( segmentNextYPercent );

                Log.wtf( "GAME", "SEGMENT PERFECTLY IN TURN" );

                return;
            }

            if( segmentPassedTurnTopToBottom ) {
                segmentTotalTravel     = segment.getPositionYPercent() - segmentNextYPercent;
                segmentTravelToTurn    = segment.getPositionYPercent() - turn.getPositionYPercent();
                segmentTravelAfterTurn = segmentTotalTravel - segmentTravelToTurn;

                Log.wtf( "GAME", "SEGMENT PASSED TURN TOP TO BOTTOM" );
            } else if( segmentPassedTurnBottomToTop ) {
                segmentTotalTravel     = segment.getPositionYPercent() - segmentNextYPercent;
                segmentTravelToTurn    = turn.getPositionYPercent() - segment.getPositionYPercent() ;
                segmentTravelAfterTurn = segmentTotalTravel - segmentTravelToTurn;

                Log.wtf( "GAME", "SEGMENT PASSED TURN BOTTOM TO TOP" );
            } else if( segmentPassedLeftToRight ) {
                segmentTotalTravel     = segment.getPositionXPercent() - segmentNextXPercent;
                segmentTravelToTurn    = segment.getPositionXPercent() - turn.getPositionXPercent();
                segmentTravelAfterTurn = segmentTotalTravel - segmentTravelToTurn;

                Log.wtf( "GAME", "SEGMENT PASSED TURN LEFT TO RIGHT" );
            } else if( segmentPassedRightToLeft ) {
                segmentTotalTravel     = segment.getPositionXPercent() - segmentNextXPercent;
                segmentTravelToTurn    = turn.getPositionXPercent() - segment.getPositionXPercent();
                segmentTravelAfterTurn = segmentTotalTravel - segmentTravelToTurn;

                Log.wtf( "GAME", "SEGMENT PASSED TURN RIGHT TO LEFT" );
            }

            if( segment.getExitTaken().equals( Exit.exitTop ) ) {
                segmentNextXPercent = turn.getPositionXPercent();
                segmentNextYPercent = segmentNextYPercent + segmentTravelAfterTurn;

                Log.wtf( "GAME", "SEGMENT EXITED TOP" );
            } else if( segment.getExitTaken().equals( Exit.exitBottom ) ) {
                segmentNextXPercent = turn.getPositionXPercent();
                segmentNextYPercent = segmentNextYPercent + segmentTravelAfterTurn;

                Log.wtf( "GAME", "SEGMENT EXITED BOTTOM" );
            } else if( segment.getExitTaken().equals( Exit.exitLeft ) ) {
                segmentNextXPercent = segmentNextXPercent + segmentTravelAfterTurn;
                segmentNextYPercent = turn.getPositionYPercent();

                Log.wtf( "GAME", "SEGMENT EXITED LEFT" );
            } else if(  segment.getExitTaken().equals( Exit.exitRight ) ) {
                segmentNextXPercent = segmentNextXPercent + segmentTravelAfterTurn;
                segmentNextYPercent = turn.getPositionYPercent();

                Log.wtf( "GAME", "SEGMENT EXITED RIGHT" );
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
            boolean holeXMatchesSegmentX =
                Float.compare( hole.getPositionXPercent(), segmentNextXPercent ) == 0;
            boolean holeYMatchesSegmentY =
                Float.compare( hole.getPositionYPercent(), segmentNextYPercent ) == 0;

            boolean segmentPerfectlyInHole = holeXMatchesSegmentX && holeYMatchesSegmentY;

            boolean holeYBelowSegmentY =
                Float.compare( hole.getPositionYPercent(), segmentNextYPercent ) < 0;
            boolean holeYAboveNextY =
                Float.compare( hole.getPositionYPercent(), segment.getPositionYPercent() ) > 0;

            boolean segmentPassedHoleTopToBottom =
                holeXMatchesSegmentX && holeYBelowSegmentY && holeYAboveNextY;

            boolean holeYAboveSegmentY =
                Float.compare( hole.getPositionYPercent(), segmentNextYPercent ) > 0;
            boolean holeYBelowNextY =
                Float.compare( hole.getPositionYPercent(), segment.getPositionYPercent() ) < 0;

            boolean segmentPassedHoleBottomToTop =
                holeXMatchesSegmentX && holeYAboveSegmentY && holeYBelowNextY;

            boolean holeXBelowSegmentX =
                Float.compare( hole.getPositionXPercent(), segmentNextXPercent ) < 0;
            boolean holeXAboveNextX =
                Float.compare( hole.getPositionXPercent(), segment.getPositionXPercent() ) > 0;

            boolean segmentPassedLeftToRight =
                holeYMatchesSegmentY && holeXBelowSegmentX && holeXAboveNextX;

            boolean holeXAboveSegmentX =
                Float.compare( hole.getPositionXPercent(), segmentNextXPercent ) > 0;
            boolean holeXBelowNextX  =
                Float.compare( hole.getPositionXPercent(), segment.getPositionXPercent() ) < 0;

            boolean segmentPassedRightToLeft =
                holeYMatchesSegmentY && holeXAboveSegmentX && holeXBelowNextX;

            boolean segmentPassedHoleVertically =
                segmentPassedHoleBottomToTop || segmentPassedHoleTopToBottom;

            boolean segmentPassedHoleHorizontally =
                segmentPassedLeftToRight || segmentPassedRightToLeft;

            boolean segmentReachedHole =
                segmentPerfectlyInHole ||
                segmentPassedHoleVertically ||
                segmentPassedHoleHorizontally;

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
