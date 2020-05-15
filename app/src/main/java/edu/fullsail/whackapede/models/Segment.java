/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import android.graphics.Canvas;

class Segment {
    private Segment head = null;
    private Segment tail = null;

    private float positionXPercent;
    private float positionYPercent;

    private float speedPercent;

    private float directionX;
    private float directionY;

    private boolean isAbove = true;

    private final float radiusPercent;

    private Exit exitTaken;

    Segment(
        float positionXPercent, float positionYPercent, float radiusPercent,
        float speedPercent, float directionX, float directionY
    ) {
        this.positionXPercent = positionXPercent;
        this.positionYPercent = positionYPercent;
        this.radiusPercent = radiusPercent;
        this.speedPercent = speedPercent;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    boolean getIsHead() {
        return head == null;
    }

    boolean getIsTail() {
        return tail == null;
    }

    float getCurrentXFor( Canvas canvas ) {
        return positionXPercent * canvas.getWidth();
    }

    float getCurrentYFor( Canvas canvas ) {
        return positionYPercent * canvas.getWidth();
    }

    float getRadiusFor( Canvas canvas ) {
        return radiusPercent * canvas.getWidth();
    }

    float getPositionXPercent() {
        return positionXPercent;
    }

    void setPositionXPercent( float positionXPercent ) {
        this.positionXPercent = positionXPercent;
    }

    float getPositionYPercent() {
        return positionYPercent;
    }

    void setPositionYPercent( float positionYPercent ) {
        this.positionYPercent = positionYPercent;
    }

    float getSpeedPercent() {
        return speedPercent;
    }

    void setSpeedPercent( float speedPercent ) {
        this.speedPercent = speedPercent;
    }

    float getDirectionX() {
        return directionX;
    }

    void setDirectionX( float directionX ) {
        this.directionX = directionX;
    }

    float getDirectionY() {
        return directionY;
    }

    void setDirectionY( float directionY ) {
        this.directionY = directionY;
    }

    boolean getIsAbove() {
        return isAbove;
    }

    void setIsAbove() {
        isAbove = true;
    }

    boolean getIsBelow() {
        return !isAbove;
    }

    void setIsBelow() {
        isAbove = false;
    }

    void toggleAboveBelow() {
        isAbove = !isAbove;
    }

    void setHead( Segment segment ) {
        head = segment;
    }

    Segment getHead() {
        return head;
    }

    void setTail( Segment segment ) {
        tail = segment;
    }

    Segment getTail() {
        return tail;
    }

    void setExitTaken( Exit exitTaken ) {
        this.exitTaken = exitTaken;
    }

    Exit getExitTaken() {
        return exitTaken;
    }

    private Segment addTailBottom() {
        Segment tail = new Segment(
            positionXPercent, positionYPercent + radiusPercent * 2,
            radiusPercent, speedPercent, directionX, directionY
        );

        this.tail = tail;
        tail.head = this;
        tail.isAbove = this.isAbove;

        return tail;
    }

    void addTailsBottom( int tails ) {
        Segment tail = this;

        while( tails > 0 ) {
            tail = tail.addTailBottom();
            tails--;
        }
    }

    private Segment addTailTop() {
        Segment tail = new Segment(
            positionXPercent, positionYPercent - radiusPercent * 2,
            radiusPercent, speedPercent, directionX, directionY
        );

        this.tail = tail;
        tail.head = this;
        tail.isAbove = this.isAbove;

        return tail;
    }

    void addTailsTop( int tails ) {
        Segment tail = this;

        while( tails > 0 ) {
            tail = tail.addTailTop();
            tails--;
        }
    }

    private Segment addTailLeft() {
        Segment tail = new Segment(
            positionXPercent - radiusPercent * 2, positionYPercent,
            radiusPercent, speedPercent, directionX, directionY
        );

        this.tail = tail;
        tail.head = this;
        tail.isAbove = this.isAbove;

        return tail;
    }

    void addTailsLeft( int tails ) {
        Segment tail = this;

        while( tails > 0 ) {
            tail = tail.addTailLeft();
            tails--;
        }
    }

    private Segment addTailRight() {
        Segment tail = new Segment(
            positionXPercent + radiusPercent * 2, positionYPercent,
            radiusPercent, speedPercent, directionX, directionY
        );

        this.tail = tail;
        tail.head = this;
        tail.isAbove = this.isAbove;

        return tail;
    }

    void addTailsRight( int tails ) {
        Segment tail = this;

        while( tails > 0 ) {
            tail = tail.addTailRight();
            tails--;
        }
    }
}

