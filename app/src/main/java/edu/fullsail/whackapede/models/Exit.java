/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import java.util.ArrayList;

class Exit {
    private final double directionX;
    private final double directionY;

    private Exit( double directionX, double directionY ) {
        this.directionX = directionX;
        this.directionY = directionY;
    }

    double getDirectionX() {
        return directionX;
    }

    double getDirectionY() {
        return directionY;
    }

    static Exit getExitTop() {
        return new Exit(  0,  1 );
    }

    static Exit getExitBottom() {
        return new Exit(  0, -1 );
    }

    static Exit getExitLeft() {
        return new Exit( -1,  0 );
    }

    static Exit getExitRight() {
        return new Exit(  1,  0 );
    }

    static ArrayList< Exit > getFourWayExit() {
        ArrayList< Exit > exits = new ArrayList<>();

        exits.add( getExitTop() );
        exits.add( getExitBottom() );
        exits.add( getExitLeft() );
        exits.add( getExitRight() );

        return  exits;
    }

    static ArrayList< Exit > getThreeWayExitTop() {
        ArrayList< Exit > exits = new ArrayList<>();

        exits.add( getExitBottom() );
        exits.add( getExitLeft() );
        exits.add( getExitRight() );

        return  exits;
    }

    static ArrayList< Exit > getThreeWayExitBottom() {
        ArrayList< Exit > exits = new ArrayList<>();

        exits.add( getExitTop() );
        exits.add( getExitLeft() );
        exits.add( getExitRight() );

        return  exits;
    }

    static ArrayList< Exit > getThreeWayExitLeft() {
        ArrayList< Exit > exits = new ArrayList<>();

        exits.add( getExitTop() );
        exits.add( getExitBottom() );
        exits.add( getExitRight() );

        return  exits;
    }

    static ArrayList< Exit > getThreeWayExitRight() {
        ArrayList< Exit > exits = new ArrayList<>();

        exits.add( getExitTop() );
        exits.add( getExitBottom() );
        exits.add( getExitLeft() );

        return  exits;
    }

    static ArrayList< Exit > getTwoWayExitTopLeft() {
        ArrayList< Exit > exits = new ArrayList<>();

        exits.add( getExitBottom() );
        exits.add( getExitRight()  );

        return  exits;
    }

    static ArrayList< Exit > getTwoWayExitTopRight() {
        ArrayList< Exit > exits = new ArrayList<>();

        exits.add( getExitBottom() );
        exits.add( getExitLeft() );

        return  exits;
    }

    static ArrayList< Exit > getTwoWayExitBottomLeft() {
        ArrayList< Exit > exits = new ArrayList<>();

        exits.add( getExitTop() );
        exits.add( getExitRight()  );

        return  exits;
    }

    static ArrayList< Exit > getTwoWayExitBottomRight() {
        ArrayList< Exit > exits = new ArrayList<>();

        exits.add( getExitTop() );
        exits.add( getExitLeft() );

        return  exits;
    }
}