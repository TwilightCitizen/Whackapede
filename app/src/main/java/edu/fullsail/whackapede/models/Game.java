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
import java.util.Random;

import edu.fullsail.whackapede.gameElements.Direction;
import edu.fullsail.whackapede.gameElements.Position;
import edu.fullsail.whackapede.utilities.TimeUtility;

import static edu.fullsail.whackapede.R.color.*;

/*
Game maintains the state and logic of a game of Whack-A-Pede.  This includes the internal
representation of game elements, their drawing to a canvas for on-screen display, player scoring
and time remaining, running status, and initialization.
*/
public class Game {
    // Flags to indicate if the game is paused, if its board is initialized, and if drawing
    // tools are initialized.  Needed to manage game interactions and drawing to screen.
    private boolean gameIsPaused;
    private boolean boardIsInitialized;
    private boolean drawingToolsAreInitialized;

    // Colors in which game elements will be drawn to screen.
    private int colorEarth;
    private int colorGrass;
    private int colorTrans;
    private int colorAbove;
    private int colorBelow;
    private int colorFourWay;
    private int colorThreeWay;
    private int colorTwoWay;

    // Paints with which game elements will be drawn to screen.
    private Paint paintEarth;
    private Paint paintHole;
    private Paint paintLayer;
    private Paint paintSegment;
    private Paint paintTurn;

    // Bitmap and canvas for drawing game elements to layers to be overlaid to screen.
    private Bitmap bitmapLayer;
    private Canvas canvasLayer;

    // Key dimensions for size and placement of game elements on screen.
    private int cellSize;
    private int radiusHole;
    private int radiusSegment ;
    private int radiusTurn ;
    private int segmentSpeed;
    private double segmentAcceleration;

    // Collections of game elements.
    private final ArrayList< Segment > centipedes = new ArrayList<>();
    private final ArrayList< Hole > holes = new ArrayList<>();
    private final ArrayList< Turn > turns = new ArrayList<>();

    // Touch events received from the surface to which the game is drawn.
    private final ArrayList< MotionEvent > touchEvents = new ArrayList<>();

    // Player score and time remaining.
    private int score;
    private long roundTimeMillis;
    private long remainingTimeMillis;

    // New games start paused without game board or drawing tools initialized.
    public Game() {
        gameIsPaused = true;
        boardIsInitialized = false;
        drawingToolsAreInitialized = false;
    }

    // Player score and time remaining are read-only.
    public int getScore() { return score; }
    public long getRemainingTimeMillis() { return remainingTimeMillis; }

    // Game can be paused by player or by app when interrupted.
    public void pause() { gameIsPaused = true; }
    public void toggleState() { gameIsPaused = !gameIsPaused; }
    public boolean isGameIsPaused() { return gameIsPaused; }

    // Initialize the game board.
    public void initializeBoard( int canvasWidth ) {
        // Guard against unnecessary initialization.
        if( boardIsInitialized ) return;

        // Cell width percentage is 1/15th of the screen width.  Screen ratio is assumed to be 15:23.
        double cellWidthPercent = 1 / 15d;

        // Establish key dimensions based on cell width.
        this.cellSize = (int) ( canvasWidth * cellWidthPercent );
        this.radiusHole = (int) ( canvasWidth * cellWidthPercent / 2 );
        this.radiusSegment = radiusHole;
        this.radiusTurn = radiusHole / 2;
        this.segmentSpeed = cellSize * 5;
        this.segmentAcceleration = 1.03125;

        // Zero score and time remaining.
        this.score = 0;
        this.roundTimeMillis = 1000 * 60;
        this.remainingTimeMillis = roundTimeMillis;

        // Position game elements on the board.
        setupHoles();
        setupTurns();
        setupCentipede();

        // Flag initialization complete.
        boardIsInitialized = true;
    }

    // Set holes at fixed intervals throughout the game board.
    private void setupHoles() {
        // Holes are 3 across by 5 down with gaps between each other and intervening turns.
        int[] holesAcross = new int[] { 3, 7, 11 };
        int[] holesDown   = new int[] { 3, 7, 11, 15, 19 };

        for( int holeAcross : holesAcross ) for( int holeDown : holesDown )
            holes.add( new Hole( new Position( cellSize * holeAcross, cellSize * holeDown ) ) );
    }

    // Set turns at fixed intervals throughout the game board.
    private void setupTurns() {
        setupInteriorFourWayTurns();
        setupTopThreeWayTurns();
        setupBottomThreeWayTurns();
        setupLeftThreeWayTurns();
        setupRightThreeWayTurns();
        setupCornerTwoWayTurns();
    }

    // Set four way turns in the interior the game board at fixed intervals.  This excludes the
    // corners and edges, but also coincide with holes.
    private void setupInteriorFourWayTurns() {
        // Interior turns are 5 across by 9 down.
        int[] fourWayTurnsAcross = new int[] { 3, 5, 7, 9, 11 };
        int[] fourWayTurnsDown   = new int[] { 3, 5, 7, 9, 11, 13, 15, 17, 19 };

        for( int fourWayTurnAcross : fourWayTurnsAcross )
            for( int fourWayTurnDown : fourWayTurnsDown ) turns.add( new Turn(
                new Position( cellSize * fourWayTurnAcross, cellSize * fourWayTurnDown ),
                Exit.fourWayExit
            ) );
    }

    // Set three way turns along the top edge, excluding corners.
    private void setupTopThreeWayTurns() {
        // Top edge turns are 5 across by 1 down.
        int[] threeWayTurnsTopAcross = new int[] { 3, 5, 7, 9, 11 };
        int   threeWayTurnsTopDown   = 1;

        for( int threeWayTurnTopAcross : threeWayTurnsTopAcross ) turns.add( new Turn(
            new Position( cellSize * threeWayTurnTopAcross, cellSize * threeWayTurnsTopDown ),
            Exit.threeWayExitTop
        ) );
    }

    // Set three way turns along the bottom edge, excluding corners.
    private void setupBottomThreeWayTurns() {
        // Bottom edge turns are 5 across by 1 down.
        int[] threeWayTurnsBottomAcross = new int[] { 3, 5, 7, 9, 11 };
        int   threeWayTurnsBottomDown   = 21;

        for( int threeWayTurnBottomAcross : threeWayTurnsBottomAcross ) turns.add( new Turn(
            new Position( cellSize * threeWayTurnBottomAcross, cellSize * threeWayTurnsBottomDown ),
            Exit.threeWayExitBottom
        ) );
    }

    // Set three way turns along the left edge, excluding corners.
    private void setupLeftThreeWayTurns() {
        // Left edge turns are 1 across by 9 down.
        int   threeWayTurnsLeftAcross = 1;
        int[] threeWayTurnsLeftDown   = new int[] { 3, 5, 7, 9, 11, 13, 15, 17, 19 };

        for( int threeWayTurnLeftDown : threeWayTurnsLeftDown ) turns.add( new Turn(
            new Position( cellSize * threeWayTurnsLeftAcross, cellSize * threeWayTurnLeftDown ),
            Exit.threeWayExitLeft
        ) );
    }

    // Set three way turns along the right edge, excluding corners.
    private void setupRightThreeWayTurns() {
        // Right edge turns are 1 across by 9 down.
        int   threeWayTurnsRightAcross = 13;
        int[] threeWayTurnsRightDown   = new int[] { 3, 5, 7, 9, 11, 13, 15, 17, 19 };

        for( int threeWayTurnRightDown : threeWayTurnsRightDown ) turns.add( new Turn(
            new Position( cellSize * threeWayTurnsRightAcross, cellSize * threeWayTurnRightDown ),
            Exit.threeWayExitRight
        ) );
    }

    // Set two way turns at each corner, all of which are 1 across by 1 down.
    private void setupCornerTwoWayTurns() {
        turns.add( new Turn( new Position( cellSize, cellSize ), Exit.twoWayExitTopLeft ) );
        turns.add( new Turn( new Position( cellSize * 13, cellSize ), Exit.twoWayExitTopRight ) );
        turns.add( new Turn( new Position( cellSize, cellSize * 21 ), Exit.twoWayExitBottomLeft ) );
        turns.add( new Turn( new Position( cellSize * 13, cellSize * 21 ), Exit.twoWayExitBottomRight ) );
    }

    // Set a 10-segment centipede off screen, oriented to crawl on screen.
    private void setupCentipede() {
        // Starting centipedes from which one will be chosen.
        ArrayList< Segment > startingCentipedes = new ArrayList<>();

        // Centipedes can start just beyond any of the game arena's edges.
        int leftStart = -1, topStart = -1, rightStart = 16, bottomStart = 24;

        // Centipedes can start above or below ground.
        boolean[] flags = new boolean[] { true, false };

        // Keep centipedes in line with turns so they don't just walk off screen.
        int[] rows = new int[] { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21 };
        int[] cols = new int[] { 1, 3, 5, 7, 9, 11, 13 };

        // Whip up from fresh centipedes.
        for( boolean setBelow : flags ) {
            for( int row : rows ) {
                Segment segment = new Segment(
                    new Position( cellSize * leftStart, cellSize * row ),
                    segmentSpeed, Direction.right
                );

                if( setBelow ) segment.toggleAboveBelow();
                segment.addTails( 9, cellSize, -1, 0 );
                startingCentipedes.add( segment );
            }

            for( int row : rows ) {
                Segment segment = new Segment(
                    new Position( cellSize * rightStart, cellSize * row ),
                    segmentSpeed, Direction.left
                );

                if( setBelow ) segment.toggleAboveBelow();
                segment.addTails( 9, cellSize, 1, 0 );
                startingCentipedes.add( segment );
            }

            for( int col : cols ) {
                Segment segment = new Segment(
                    new Position( cellSize * col, cellSize * topStart ),
                    segmentSpeed, Direction.down
                );

                if( setBelow ) segment.toggleAboveBelow();
                segment.addTails( 9, cellSize, 0, -1 );
                startingCentipedes.add( segment );
            }

            for( int col : cols ) {
                Segment segment = new Segment(
                    new Position( cellSize * col, cellSize * bottomStart ),
                    segmentSpeed, Direction.up
                );

                if( setBelow ) segment.toggleAboveBelow();
                segment.addTails( 9, cellSize, 0, 1 );
                startingCentipedes.add( segment );
            }
        }

        // Get a random centipede from the batch.
        Random rand = new Random();
        Segment startingSegment = startingCentipedes.get( rand.nextInt( startingCentipedes.size() ) );

        // And add it to the mix.
        centipedes.add( startingSegment );
    }

    // Initialize drawing tools and draw all game layers to canvas, overlaying them on screen.
    private void drawToCanvas( Context context, Canvas canvas ) {
        initializeDrawingTools( context, canvas );
        drawEarthLayerToCanvas( canvas );
        drawBelowLayerToCanvas( canvas );
        drawGrassLayerToCanvas( canvas );
        drawAboveLayerToCanvas( canvas );

        // Temporarily draw turns for visualization during development.
        // drawTurnLayerToCanvas( canvas );
    }

    // Initialize colors, paints, bitmaps, and canvas used in drawing routines.
    private void initializeDrawingTools( Context context, Canvas canvas ) {
        // Guard against unnecessary initialization.
        if( drawingToolsAreInitialized ) return;

        initializeColors( context );
        initializePaints();

        bitmapLayer = Bitmap.createBitmap( canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888 );
        canvasLayer = new Canvas( bitmapLayer );

        // Flag drawing tools as initialized.
        drawingToolsAreInitialized = true;
    }

    // Initialize all the paints used to draw on screen elements.
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

    // Obtain all colors used in on screen elements from resources.
    private void initializeColors( Context context ) {
        colorEarth = ContextCompat.getColor( context, earth_brown );
        colorGrass = ContextCompat.getColor( context, grass_green );
        colorTrans = ContextCompat.getColor( context, grass_trans );
        colorAbove = ContextCompat.getColor( context, day_blue );
        colorBelow = ContextCompat.getColor( context, night_blue );
        colorFourWay  = ContextCompat.getColor( context, four_way );
        colorThreeWay = ContextCompat.getColor( context, three_way );
        colorTwoWay   = ContextCompat.getColor( context, two_way );
    }

    /*
    Draw the earth layer to canvas.  The earth layer paints the canvas green with brown circles
    where holes will appear through the grass layer above it.  Brown is not drawn for the whole
    layer because the grass layer uses a semi-transparent green to reveal centipedes below it and
    this would darken the appearance of the grass layer.
    */
    private void drawEarthLayerToCanvas( Canvas canvas ) {
        bitmapLayer.eraseColor( Color.TRANSPARENT );
        canvasLayer.drawColor( colorGrass );
        
        for( Hole hole : holes ) canvasLayer.drawCircle(
            (float) hole.getPosition().getX() + radiusHole,
            (float) hole.getPosition().getY() + radiusHole,
            (float) radiusHole, paintEarth
        );

        canvas.drawBitmap( bitmapLayer, 0, 0, paintLayer );
    }

    /*
    Draw the grass layer to canvas.  The grass layer paints the canvas a semi-transparent green
    with completely transparent circles where holes are placed.  This allows the brown holes of the
    earth layer and any underground centipede segments to show completely through the holes, while
    offering only a slight glimpse of where underground centipedes are elsewhere.
    */
    private void drawGrassLayerToCanvas( Canvas canvas ) {
        bitmapLayer.eraseColor( Color.TRANSPARENT );
        canvasLayer.drawColor( colorTrans );

        for( Hole hole : holes ) canvasLayer.drawCircle(
            (float) hole.getPosition().getX() + radiusHole,
            (float) hole.getPosition().getY() + radiusHole,
            (float) radiusHole, paintHole
        );

        canvas.drawBitmap( bitmapLayer, 0, 0, paintLayer );
    }

    /*
    Draw centipede segments above ground as day blue circles wherever they are placed.
    */
    private void drawAboveLayerToCanvas( Canvas canvas ) {
        bitmapLayer.eraseColor( Color.TRANSPARENT );
        paintSegment.setColor( colorAbove );

        for( Segment centipede : centipedes ) {
            Segment segment = centipede;

            while( segment != null ) {
                if( segment.getIsAbove() ) canvasLayer.drawCircle(
                    (float) segment.getPosition().getX() + radiusSegment,
                    (float) segment.getPosition().getY() + radiusSegment,
                    (float) radiusSegment, paintSegment
                );

                segment = segment.getTail();
            }
        }

        canvas.drawBitmap( bitmapLayer, 0, 0, paintLayer );
    }

    /*
    Draw centipede segments below ground as night blue circles wherever they are placed.
    */
    private void drawBelowLayerToCanvas( Canvas canvas ) {
        bitmapLayer.eraseColor( Color.TRANSPARENT );
        paintSegment.setColor( colorBelow );

        for( Segment centipede : centipedes ) {
            Segment segment = centipede;

            while( segment != null ) {
                if( segment.getIsBelow() ) canvasLayer.drawCircle(
                    (float) segment.getPosition().getX() + radiusSegment,
                    (float) segment.getPosition().getY() + radiusSegment,
                    (float) radiusSegment, paintSegment
                );

                segment = segment.getTail();
            }
        }

        canvas.drawBitmap( bitmapLayer, 0, 0, paintLayer );
    }

    /*
    Draw the turn layer to canvas for debugging/troubleshooting.  Turns are drawn as differently
    colored circles for the type of turn they are, wherever they are placed.
    */
    private void drawTurnLayerToCanvas( Canvas canvas ) {
        bitmapLayer.eraseColor( Color.TRANSPARENT );

        for( Turn turn : turns ) {
            if( turn.getExitCount() == 4 )
                paintTurn.setColor( colorFourWay );
            else if( turn.getExitCount() == 3 )
                paintTurn.setColor( colorThreeWay );
            else if( turn.getExitCount() == 2 )
                paintTurn.setColor( colorTwoWay );

            canvasLayer.drawCircle(
                (float) turn.getPosition().getX() + radiusHole,
                (float) turn.getPosition().getY() + radiusHole,
                (float) radiusTurn, paintTurn
            );
        }

        canvas.drawBitmap( bitmapLayer, 0, 0, paintLayer );
    }

    /*
    Loop the game logic over the provided elapsed time.  Process attacks on centipedes,
    animate them over the interval, and then draw everything to canvas.
    */
    public void loop( Context context, Canvas canvas, long elapsedTimeMillis  ) {
        checkForGameOver( elapsedTimeMillis );
        checkForNextRound();
        attackCentipedes();
        animateCentipedes( elapsedTimeMillis );
        drawToCanvas( context, canvas );
    }

    /*
    Check for remaining centipedes.  If there are none, create new, faster one, add it to the
    board and reset the clock.
    */
    private void checkForNextRound() {
        // Guard against starting next round when paused.
        if( gameIsPaused ) return;

        // Guard against starting new round when centipedes exist.
        if( !centipedes.isEmpty() ) return;

        remainingTimeMillis = roundTimeMillis;
        segmentSpeed *= segmentAcceleration;

        setupCentipede();
    }

    /*
    Check for time remaining.  If there is none, pause the game, zero the clock, and more will
    happen in the next sprint.
    */
    private void checkForGameOver( long elapsedTimeMillis ) {
        // Guard against ticking clock and ending game when paused.
        if( gameIsPaused ) return;

        // Tick down the clock.
        remainingTimeMillis -= elapsedTimeMillis;

        // Game is over if timer reaches zero.
        if( remainingTimeMillis <= 0 ) {
            // Prevent negative clock.
            remainingTimeMillis = 0;

            // TODO: Implement end game when user/guest play is determined.
            pause();
        }
    }

    /*
    Touch events coming in from the SurfaceView are collected for processing centipede attacks.
    */
    public void addTouchEvent( MotionEvent touchEvent ) { touchEvents.add( touchEvent ); }

    /*
    Process collected touch events as centipede attacks.  Remove touched segments, if any, from
    the game board, splitting centipedes where attacked if possible, and speeding them up.  Score
    points for the player for all successfully attacked segments.
    */
    private void attackCentipedes() {
        // Keep players honest.  No pause cheats here.
        if( gameIsPaused ) return;

        // Collect any segments killed, any heads to remove, and any new heads to add.
        ArrayList< Segment > segmentsKilled = new ArrayList<>();
        ArrayList< Segment > headsToRemove = new ArrayList<>();
        ArrayList< Segment > headsToAdd = new ArrayList<>();

        // Process each touch event for every segment of every centipede.
        for( MotionEvent touchEvent : touchEvents ) {
            for( Segment centipede : centipedes ) {
                Segment segment = centipede;

                while( segment != null ) {
                    boolean touchOnSegmentX =
                        touchEvent.getX() <= segment.getPosition().getX() + cellSize &&
                        touchEvent.getX() >= segment.getPosition().getX();

                    boolean touchOnSegmentY =
                        touchEvent.getY() <= segment.getPosition().getY() + cellSize &&
                        touchEvent.getY() >= segment.getPosition().getY();

                    // Touched segment falls on both axes of touch as is above ground.
                    boolean touchOnSegment =
                        touchOnSegmentX && touchOnSegmentY  && segment.getIsAbove();

                    // Add any touched segments to the killed segments collection.
                    if( touchOnSegment ) segmentsKilled.add( segment );

                    segment = segment.getTail();
                }
            }
        }

        // Clear out the touch events so already touched spots don't become centipede auto-killers.
        touchEvents.clear();

        // Speed up all the centipede segments.  The more segments killed, the faster.
        for( Segment centipede : centipedes ) {
            Segment segment = centipede;

            while( segment != null ) {
                segment.setSpeed( segmentSpeed +  (int) (
                    ( segmentSpeed * segmentAcceleration - segmentSpeed ) *
                    segmentsKilled.size()
                ) );

                segment = segment.getTail();
            }
        }

        /*
        Remove heads and tails that were touched.  If an inner segment was touched, remove it
        and split the centipede into two smaller ones.

        TODO - Look into shattering centipedes into all heads if a head or tail is touched.
        */
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

        // Add points for each segment killed to the player's score.
        score += segmentsKilled.size();
    }

    // Animate the centipedes over the elapsed time interval.
    private void animateCentipedes( long elapsedTimeMillis ) {
        // Guard against animating centipedes when the game is paused.
        if( gameIsPaused ) return;

        // Normalize the elapsed time as a fraction of 1 seconds.
        double interval = TimeUtility.getInstance().millisToIntervalOfSeconds( elapsedTimeMillis );

        // Animate each segment of each centipede for the interval through holes and around turns.
        for( Segment centipede : centipedes ) {
            // Change in position is centipede speed of traversal by time traversed.
            int change = (int) ( centipede.getSpeed() * interval );

            Segment segment = centipede;
            
            while( segment != null ) {
                /*
                Change in velocity is centipede change in position in a given direction.
                Position only ever changes exclusively along the X or Y axis, as direction
                for one of the axes is always zero.
                */
                Position nextPosition = new Position(
                    segment.getPosition().getX() + change * segment.getDirection().getX(),
                    segment.getPosition().getY() + change * segment.getDirection().getY()
                );

                animateThroughHoles( segment, nextPosition );
                animateThroughTurns( segment, nextPosition );

                segment = segment.getTail();
            }
        }
    }

    /*
    Animate the segment through any turn it has reached.  Accounting for changes in direction, change
    the segment's position on the X and Y axes.
    */
    private void animateThroughTurns( Segment segment, Position nextPosition ) {
        // Check the segment against all turns.
        for( Turn turn : turns ) {
            // Guard against animating the segment around a turn is has not reached.
            if( !turn.intersectsPathOf( segment, nextPosition ) ) continue;

            /*
            Guard against the segment being in the same turn it was last time.  There are no
            guarantees that each iteration through will occur in the same amount of time.  Without
            this guard in place, segments can stutter at a turn, wind up going in reverse, and
            their tails can lose coordination with them, resulting in cats and dogs playing with
            each other and other madness.
            */
            if( segment.getTurnReached() == turn ) continue;

            // Track the turn the segment took for any tails that need to follow it.
            segment.setTurnReached( turn );

            // Heads lead; tails follow.
            if( segment.getIsHead() )
                segment.setExitTaken( turn.getRandomExit( segment ) );
            else
                segment.setExitTaken( segment.getHead().getExitTaken() );

            // Set the segment's new direction based on the exit taken.
            segment.setDirection( segment.getExitTaken().getDirection() );

            // In rare cases, the segment is perfectly in the turn, requiring no math to carry on.
            if( turn.coincidesWith( nextPosition ) ) {
                segment.setPosition( nextPosition );

                return;
            }

            /*
            In most cases, the segment overshoots its turn.  This requires calculations to course
            correct it.  As before, heads lead; tails follow.
            */
            if( segment.getIsHead() ) {
                int travelAfterTurn = 0;

                // Depending on the segment's heading, calculate its total travel past the turn.
                if( turn.wasPassedTopToBottom( segment, nextPosition ) ) {
                    travelAfterTurn = nextPosition.getY() - turn.getPosition().getY();
                } else if( turn.wasPassedBottomToTop( segment, nextPosition ) ) {
                    travelAfterTurn = turn.getPosition().getY() - nextPosition.getY();
                } else if( turn.wasPassedLeftToRight( segment, nextPosition ) ) {
                    travelAfterTurn = nextPosition.getX() - turn.getPosition().getX();
                } else if( turn.wasPassedRightToLeft( segment, nextPosition ) ) {
                    travelAfterTurn = turn.getPosition().getX() - nextPosition.getX();
                }

                /*
                Depending on its exit, fix its position on either the X or Y axes to that of the
                turn itself, and fix its position on the other axes by the amount past the turn.
                */
                if( segment.getExitTaken().equals( Exit.exitTop ) ) {
                    nextPosition = new Position(
                        turn.getPosition().getX(), nextPosition.getY() - travelAfterTurn
                    );
                } else if( segment.getExitTaken().equals( Exit.exitBottom ) ) {
                    nextPosition = new Position(
                        turn.getPosition().getX(), nextPosition.getY() + travelAfterTurn
                    );
                } else if( segment.getExitTaken().equals( Exit.exitLeft ) ) {
                    nextPosition = new Position(
                        nextPosition.getX() - travelAfterTurn, turn.getPosition().getY()
                    );
                } else if( segment.getExitTaken().equals( Exit.exitRight ) ) {
                    nextPosition = new Position(
                        nextPosition.getX() + travelAfterTurn, turn.getPosition().getY()
                    );
                }
            } else {
                /*
                Tails turn to follow their heads.  Their position needs to be calculated off of the
                heads they follow, rather than their own position in relation to the turn.  In
                theory tails should animate correctly around turns if their trajectory is found the
                same way as heads.  In practice, imprecision creeps into the calculations over time,
                and the tails become more and more spaced out.  Not only does this cause centipedes
                to look bad on screen, but eventually, the spacing is so severe that tails are
                hitting different intersections than their heads hit before them.  Again, cats and
                dogs start playing together, and the game descends into utter madness.
                */
                if( segment.getExitTaken().equals( Exit.exitTop ) ) {
                    nextPosition = new Position(
                        turn.getPosition().getX(), segment.getHead().getPosition().getY() + cellSize
                    );
                } else if( segment.getExitTaken().equals( Exit.exitBottom ) ) {
                    nextPosition = new Position(
                        turn.getPosition().getX(), segment.getHead().getPosition().getY() - cellSize
                    );
                } else if( segment.getExitTaken().equals( Exit.exitLeft ) ) {
                    nextPosition = new Position(
                        segment.getHead().getPosition().getX() + cellSize, turn.getPosition().getY()
                    );
                } else if( segment.getExitTaken().equals( Exit.exitRight ) ) {
                    nextPosition = new Position(
                        segment.getHead().getPosition().getX() - cellSize, turn.getPosition().getY()
                    );
                }
            }

            // Set the segment's recalculated position along the X and Y axes.
            segment.setPosition( nextPosition );

            return;
        }

        // Segment hit no turns.  Set the segment's original position along the X and Y axes.
        segment.setPosition( nextPosition );
    }

    /*
    Animate the segment through any hole it has reached.  Segments always navigate any hole they
    encounter, either going down or coming back up.
    */
    private void animateThroughHoles( Segment segment, Position nextPosition ) {
        for( Hole hole : holes ) {

            // Go up or down the hole.
            if( hole.intersectsPathOf( segment, nextPosition ) ) segment.toggleAboveBelow();
        }
    }
}
