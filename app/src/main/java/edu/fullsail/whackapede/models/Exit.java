/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import java.util.ArrayList;

public class Exit {
    private double directionX;
    private double directionY;

    public Exit( double directionX, double directionY ) {
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public double getDirectionX() {
        return directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    public static Exit getExitTop() {
        return new Exit(  0,  1 );
    }

    public static Exit getExitBottom() {
        return new Exit(  0, -1 );
    }

    public static Exit getExitLeft() {
        return new Exit( -1,  0 );
    }

    public static Exit getExitRight() {
        return new Exit(  1,  0 );
    }

    public static ArrayList< Exit > getFourWayExit() {
        ArrayList< Exit > exits = new ArrayList<>();

        exits.add( getExitTop() );
        exits.add( getExitBottom() );
        exits.add( getExitLeft() );
        exits.add( getExitRight() );

        return  exits;
    }

    public static ArrayList< Exit > getThreeWayExitTop() {
        ArrayList< Exit > exits = new ArrayList<>();

        exits.add( getExitBottom() );
        exits.add( getExitLeft() );
        exits.add( getExitRight() );

        return  exits;
    }

    public static ArrayList< Exit > getThreeWayExitBottom() {
        ArrayList< Exit > exits = new ArrayList<>();

        exits.add( getExitTop() );
        exits.add( getExitLeft() );
        exits.add( getExitRight() );

        return  exits;
    }

    public static ArrayList< Exit > getThreeWayExitLeft() {
        ArrayList< Exit > exits = new ArrayList<>();

        exits.add( getExitTop() );
        exits.add( getExitBottom() );
        exits.add( getExitRight() );

        return  exits;
    }

    public static ArrayList< Exit > getThreeWayExitRight() {
        ArrayList< Exit > exits = new ArrayList<>();

        exits.add( getExitTop() );
        exits.add( getExitBottom() );
        exits.add( getExitLeft() );

        return  exits;
    }

    public static ArrayList< Exit > getTwoWayExitTopLeft() {
        ArrayList< Exit > exits = new ArrayList<>();

        exits.add( getExitBottom() );
        exits.add( getExitRight()  );

        return  exits;
    }

    public static ArrayList< Exit > getTwoWayExitTopRight() {
        ArrayList< Exit > exits = new ArrayList<>();

        exits.add( getExitBottom() );
        exits.add( getExitLeft() );

        return  exits;
    }

    public static ArrayList< Exit > getTwoWayExitBottomLeft() {
        ArrayList< Exit > exits = new ArrayList<>();

        exits.add( getExitTop() );
        exits.add( getExitRight()  );

        return  exits;
    }

    public static ArrayList< Exit > getTwoWayExitBottomRight() {
        ArrayList< Exit > exits = new ArrayList<>();

        exits.add( getExitTop() );
        exits.add( getExitLeft() );

        return  exits;
    }
}