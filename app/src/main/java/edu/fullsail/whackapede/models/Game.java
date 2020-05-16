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
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import static edu.fullsail.whackapede.R.color.*;

public class Game {
    private boolean gameIsPaused;

    private boolean boardIsInitialized;

    private boolean drawingToolsAreInitialized;

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

    private int cellSize;
    private int radiusHole;
    private int radiusSegment ;
    private int radiusTurn ;
    private int segmentSpeed;

    private final ArrayList< Segment > centipedes = new ArrayList<>();
    private final ArrayList< Hole > holes = new ArrayList<>();
    private final ArrayList< Turn > turns = new ArrayList<>();
    private final ArrayList< MotionEvent > touchEvents = new ArrayList<>();

    private int score;
    private double remainingTimeMillis;

    public Game() {
        gameIsPaused = true;
        boardIsInitialized = false;
        drawingToolsAreInitialized = false;
    }

    public int getScore() {
        return score;
    }

    public double getRemainingTimeMillis() {
        return remainingTimeMillis;
    }

    public void initializeBoard( int canvasWidth ) {
        if( boardIsInitialized ) return;

        double cellWidthPercent = 1 / 15d;
        this.cellSize = (int) ( canvasWidth * cellWidthPercent );
        this.radiusHole = (int) ( canvasWidth * cellWidthPercent / 2 );
        this.radiusSegment = radiusHole;
        this.radiusTurn = radiusHole / 2;
        this.segmentSpeed = cellSize;
        this.score = 0;
        this.remainingTimeMillis = 0;

        setupHoles();
        setupTurns();
        setupCentipedes();

        boardIsInitialized = true;
    }

    private void setupHoles() {
        int[] holesAcross = new int[] { 3, 7, 11 };
        int[] holesDown   = new int[] { 3, 7, 11, 15, 19 };

        for( int holeAcross : holesAcross ) for( int holeDown : holesDown )
            holes.add( new Hole( cellSize * holeAcross, cellSize * holeDown ) );
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
        int[] fourWayTurnsAcross = new int[] { 3, 5, 7, 9, 11 };
        int[] fourWayTurnsDown   = new int[] { 3, 5, 7, 9, 11, 13, 15, 17, 19 };

        for( int fourWayTurnAcross : fourWayTurnsAcross )
            for( int fourWayTurnDown : fourWayTurnsDown ) turns.add( new Turn(
                cellSize * fourWayTurnAcross, cellSize * fourWayTurnDown, Exit.fourWayExit
            ) );
    }

    private void setupTopThreeWayTurns() {
        int[] threeWayTurnsTopAcross = new int[] { 3, 5, 7, 9, 11 };
        int   threeWayTurnsTopDown   = 1;

        for( int threeWayTurnTopAcross : threeWayTurnsTopAcross ) turns.add( new Turn(
            cellSize * threeWayTurnTopAcross, cellSize * threeWayTurnsTopDown, Exit.threeWayExitTop
        ) );
    }

    private void setupBottomThreeWayTurns() {
        int[] threeWayTurnsBottomAcross = new int[] { 3, 5, 7, 9, 11 };
        int   threeWayTurnsBottomDown   = 21;

        for( int threeWayTurnBottomAcross : threeWayTurnsBottomAcross ) turns.add( new Turn(
            cellSize * threeWayTurnBottomAcross, cellSize * threeWayTurnsBottomDown, Exit.threeWayExitBottom
        ) );
    }

    private void setupLeftThreeWayTurns() {
        int   threeWayTurnsLeftAcross = 1;
        int[] threeWayTurnsLeftDown   = new int[] { 3, 5, 7, 9, 11, 13, 15, 17, 19 };

        for( int threeWayTurnLeftDown : threeWayTurnsLeftDown ) turns.add( new Turn(
            cellSize * threeWayTurnsLeftAcross, cellSize * threeWayTurnLeftDown, Exit.threeWayExitLeft
        ) );
    }

    private void setupRightThreeWayTurns() {
        int   threeWayTurnsRightAcross = 13;
        int[] threeWayTurnsRightDown   = new int[] { 3, 5, 7, 9, 11, 13, 15, 17, 19 };

        for( int threeWayTurnRightDown : threeWayTurnsRightDown ) turns.add( new Turn(
            cellSize * threeWayTurnsRightAcross, cellSize * threeWayTurnRightDown, Exit.threeWayExitRight
        ) );
    }

    private void setupCornerTwoWayTurns() {
        turns.add( new Turn( cellSize, cellSize, Exit.twoWayExitTopLeft ) );
        turns.add( new Turn( cellSize * 13, cellSize, Exit.twoWayExitTopRight ) );
        turns.add( new Turn( cellSize, cellSize * 21, Exit.twoWayExitBottomLeft ) );
        turns.add( new Turn( cellSize * 13, cellSize * 21, Exit.twoWayExitBottomRight ) );
    }

    private void setupCentipedes() {
        Segment segment = new Segment( cellSize * -1 , cellSize *  3, segmentSpeed, 1, 0 );

        segment.addTailsLeft( 9, cellSize );
        centipedes.add( segment );
    }

    private void drawToCanvas( Context context, Canvas canvas ) {
        initializeDrawingTools( context, canvas );
        drawEarthLayerToCanvas( canvas );
        drawBelowLayerToCanvas( canvas );
        drawGrassLayerToCanvas( canvas );
        drawAboveLayerToCanvas( canvas );

        // Temporarily draw turns for visualization during development.
        // drawTurnLayerToCanvas( canvas );
    }

    private void initializeDrawingTools( Context context, Canvas canvas ) {
        if( drawingToolsAreInitialized ) return;

        initializeColors( context );
        initializePaints();

        bitmapLayer = Bitmap.createBitmap( canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888 );
        canvasLayer = new Canvas( bitmapLayer );
        drawingToolsAreInitialized = true;
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
            (float) hole.getPositionX() + radiusHole, (float) hole.getPositionY() + radiusHole,
            (float) radiusHole, paintEarth
        );

        canvas.drawBitmap( bitmapLayer, 0, 0, paintLayer );
    }

    private void drawGrassLayerToCanvas( Canvas canvas ) {
        bitmapLayer.eraseColor( Color.TRANSPARENT );
        canvasLayer.drawColor( colorTrans );

        for( Hole hole : holes ) canvasLayer.drawCircle(
            (float) hole.getPositionX() + radiusHole, (float) hole.getPositionY() + radiusHole,
            (float) radiusHole, paintHole
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
                    (float) segment.getPositionX() + radiusSegment,
                    (float) segment.getPositionY() + radiusSegment,
                    (float) radiusSegment, paintSegment
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
                    (float) segment.getPositionX() + radiusSegment,
                    (float) segment.getPositionY() + radiusSegment,
                    (float) radiusSegment, paintSegment
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
                (float) turn.getPositionX() + radiusHole,
                (float) turn.getPositionY() + radiusHole,
                (float) radiusTurn, paintTurn
            );
        }

        canvas.drawBitmap( bitmapLayer, 0, 0, paintLayer );
    }

    public void loop( Context context, Canvas canvas, double elapsedTimeMillis  ) {
        attackCentipedes();
        animateCentipedes( elapsedTimeMillis );
        drawToCanvas( context, canvas );
    }

    public void addTouchEvent( MotionEvent touchEvent ) {
        touchEvents.add( touchEvent );
    }

    private void attackCentipedes() {
        if( gameIsPaused ) return;

        ArrayList< Segment > segmentsKilled = new ArrayList<>();
        ArrayList< Segment > headsToRemove = new ArrayList<>();
        ArrayList< Segment > headsToAdd = new ArrayList<>();

        for( MotionEvent touchEvent : touchEvents ) {
            for( Segment centipede : centipedes ) {
                Segment segment = centipede;

                while( segment != null ) {
                    boolean touchOnSegmentX =
                        touchEvent.getX() <= segment.getPositionX() + cellSize &&
                        touchEvent.getX() >= segment.getPositionX();

                    boolean touchOnSegmentY =
                        touchEvent.getY() <= segment.getPositionY() + cellSize &&
                        touchEvent.getY() >= segment.getPositionY();

                    boolean touchOnSegment =
                        touchOnSegmentX && touchOnSegmentY  && segment.getIsAbove();

                    if( touchOnSegment ) segmentsKilled.add( segment );

                    segment = segment.getTail();
                }
            }
        }

        touchEvents.clear();

        for( Segment centipede : centipedes ) {
            Segment segment = centipede;

            while( segment != null ) {
                segment.setSpeed( segment.getSpeed() + segmentSpeed * segmentsKilled.size() );

                segment = segment.getTail();
            }
        }

        for( Segment segment : segmentsKilled ) {
            if( segment.getIsHead() ) {
                if( segment.getTail() != null ) {
                    headsToAdd.add( segment.getTail() );
                    segment.getTail().removeHead();
                    segment.removeTail();
                }

                headsToRemove.add( segment );
            } else if ( segment.getIsTail() ) {
                segment.getHead().removeTail();
                segment.removeHead();
            } else {
                headsToAdd.add( segment.getTail() );
                segment.getHead().removeTail();
                segment.removeHead();
                segment.getTail().removeHead();
                segment.removeTail();
            }
        }

        centipedes.removeAll( headsToRemove );
        centipedes.addAll( headsToAdd );

        score += segmentsKilled.size();
    }

    private void animateCentipedes( double elapsedTimeMillis ) {
        if( gameIsPaused ) return;

        double interval = elapsedTimeMillis / 1000d;

        for( Segment centipede : centipedes ) {
            int change = (int) ( centipede.getSpeed() * interval );

            Segment segment = centipede;
            
            while( segment != null ) {
                int segmentNextX = segment.getPositionX() + change * segment.getDirectionX();
                int segmentNextY = segment.getPositionY() + change * segment.getDirectionY();

                animateThroughHoles( segment, segmentNextX, segmentNextY );
                animateThroughTurns( segment, segmentNextX, segmentNextY );

                segment = segment.getTail();
            }
        }
    }

    private void animateThroughTurns( Segment segment, int segmentNextX, int segmentNextY ) {
        for( Turn turn : turns ) {
            boolean segmentPerfectlyInTurn =
                turn.getPositionX() == segmentNextX && turn.getPositionY() == segmentNextY ;

            boolean segmentPassedTurnTopToBottom =
                segment.getPositionX() == turn.getPositionX() && turn.getPositionX() == segmentNextX &&
                segment.getPositionY() <  turn.getPositionY() && turn.getPositionY() <  segmentNextY;

            boolean segmentPassedTurnBottomToTop =
                segment.getPositionX() == turn.getPositionX() && turn.getPositionX() == segmentNextX &&
                segment.getPositionY() >  turn.getPositionY() && turn.getPositionY() >  segmentNextY;

            boolean segmentPassedLeftToRight =
                segment.getPositionY() == turn.getPositionY() && turn.getPositionY() == segmentNextY &&
                segment.getPositionX() <  turn.getPositionX() && turn.getPositionX() <  segmentNextX;

            boolean segmentPassedRightToLeft =
                segment.getPositionY() == turn.getPositionY() && turn.getPositionY() == segmentNextY &&
                segment.getPositionX() >  turn.getPositionX() && turn.getPositionX() >  segmentNextX;

            boolean segmentReachedTurn = segmentPerfectlyInTurn ||
                segmentPassedTurnTopToBottom || segmentPassedTurnBottomToTop ||
                segmentPassedLeftToRight || segmentPassedRightToLeft;

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
                segment.setPositionX( segmentNextX );
                segment.setPositionY( segmentNextY );

                return;
            }

            if( segment.getIsHead() ) {
                int segmentTravelAfterTurn = 0;

                if( segmentPassedTurnTopToBottom ) {
                    segmentTravelAfterTurn = segmentNextY - turn.getPositionY();
                } else if( segmentPassedTurnBottomToTop ) {
                    segmentTravelAfterTurn = turn.getPositionY() - segmentNextY;
                } else if( segmentPassedLeftToRight ) {
                    segmentTravelAfterTurn = segmentNextX - turn.getPositionX();
                } else if( segmentPassedRightToLeft ) {
                    segmentTravelAfterTurn = turn.getPositionX() - segmentNextX;
                }

                if( segment.getExitTaken().equals( Exit.exitTop ) ) {
                    segmentNextX = turn.getPositionX();
                    segmentNextY = segmentNextY - segmentTravelAfterTurn;
                } else if( segment.getExitTaken().equals( Exit.exitBottom ) ) {
                    segmentNextX = turn.getPositionX();
                    segmentNextY = segmentNextY + segmentTravelAfterTurn;
                } else if( segment.getExitTaken().equals( Exit.exitLeft ) ) {
                    segmentNextX = segmentNextX - segmentTravelAfterTurn;
                    segmentNextY = turn.getPositionY();
                } else if( segment.getExitTaken().equals( Exit.exitRight ) ) {
                    segmentNextX = segmentNextX + segmentTravelAfterTurn;
                    segmentNextY = turn.getPositionY();
                }
            } else {
                if( segment.getExitTaken().equals( Exit.exitTop ) ) {
                    segmentNextX = turn.getPositionX();
                    segmentNextY = segment.getHead().getPositionY() + cellSize;
                } else if( segment.getExitTaken().equals( Exit.exitBottom ) ) {
                    segmentNextX = turn.getPositionX();
                    segmentNextY = segment.getHead().getPositionY() - cellSize;
                } else if( segment.getExitTaken().equals( Exit.exitLeft ) ) {
                    segmentNextX = segment.getHead().getPositionX() + cellSize;
                    segmentNextY = turn.getPositionY();
                } else if( segment.getExitTaken().equals( Exit.exitRight ) ) {
                    segmentNextX = segment.getHead().getPositionX() - cellSize;
                    segmentNextY = turn.getPositionY();
                }
            }

            segment.setPositionX( segmentNextX );
            segment.setPositionY( segmentNextY );

            return;
        }

        segment.setPositionX( segmentNextX );
        segment.setPositionY( segmentNextY );
    }

    private void animateThroughHoles( Segment segment, double segmentNextX, double segmentNextY ) {
        for( Hole hole : holes ) {
            boolean segmentPerfectlyInHole =
                hole.getPositionX() == segmentNextX && hole.getPositionY() == segmentNextY ;

            boolean segmentPassedHoleTopToBottom =
                segment.getPositionX() == hole.getPositionX() && hole.getPositionX() == segmentNextX &&
                segment.getPositionY() <  hole.getPositionY() && hole.getPositionY() <  segmentNextY;

            boolean segmentPassedHoleBottomToTop =
                segment.getPositionX() == hole.getPositionX() && hole.getPositionX() == segmentNextX &&
                segment.getPositionY() >  hole.getPositionY() && hole.getPositionY() >  segmentNextY;

            boolean segmentPassedLeftToRight =
                segment.getPositionY() == hole.getPositionY() && hole.getPositionY() == segmentNextY &&
                segment.getPositionX() <  hole.getPositionX() && hole.getPositionX() <  segmentNextX;

            boolean segmentPassedRightToLeft =
                segment.getPositionY() == hole.getPositionY() && hole.getPositionY() == segmentNextY &&
                segment.getPositionX() >  hole.getPositionX() && hole.getPositionX() >  segmentNextX;

            boolean segmentReachedHole = segmentPerfectlyInHole ||
                segmentPassedHoleTopToBottom || segmentPassedHoleBottomToTop ||
                segmentPassedLeftToRight || segmentPassedRightToLeft;

            if( segmentReachedHole ) segment.toggleAboveBelow();
        }
    }

    public void toggleState() {
        gameIsPaused = !gameIsPaused;
    }

    public boolean isGameIsPaused() {
        return gameIsPaused;
    }
}
