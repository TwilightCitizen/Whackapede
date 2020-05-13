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

    private double currentXPercent;
    private double currentYPercent;

    private double previousXPercent;
    private double previousYPercent;

    private double speedPercent;

    private double directionX;
    private double directionY;

    private boolean isAbove = true;

    private final double radiusPercent;

    Segment(
            double currentXPercent, double currentYPercent, double radiusPercent,
            double speedPercent, double directionX, double directionY
    ) {
        this.currentXPercent = currentXPercent;
        this.currentYPercent = currentYPercent;
        this.radiusPercent = radiusPercent;
        this.speedPercent = speedPercent;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public boolean getIsHead() {
        return head == null;
    }

    public boolean getIsTail() {
        return tail == null;
    }

    double getCurrentXFor( Canvas canvas ) {
        return currentXPercent * canvas.getWidth();
    }

    double getCurrentYFor( Canvas canvas ) {
        return currentYPercent * canvas.getWidth();
    }

    double getRadiusFor( Canvas canvas ) {
        return radiusPercent * canvas.getWidth();
    }

    double getCurrentXPercent() {
        return currentXPercent;
    }

    void setCurrentXPercent( double currentXPercent ) {
        this.currentXPercent = currentXPercent;
    }

    double getCurrentYPercent() {
        return currentYPercent;
    }

    void setCurrentYPercent( double currentYPercent ) {
        this.currentYPercent = currentYPercent;
    }

    double getPreviousXPercent() {
        return previousXPercent;
    }

    void setPreviousXPercent( double previousXPercent ) {
        this.previousXPercent = previousXPercent;
    }

    double getPreviousYPercent() {
        return previousYPercent;
    }

    void setPreviousYPercent( double previousYPercent ) {
        this.previousYPercent = previousYPercent;
    }

    double getSpeedPercent() {
        return speedPercent;
    }

    void setSpeedPercent( double speedPercent ) {
        this.speedPercent = speedPercent;
    }

    double getDirectionX() {
        return directionX;
    }

    void setDirectionX( double directionX ) {
        this.directionX = directionX;
    }

    double getDirectionY() {
        return directionY;
    }

    void setDirectionY( double directionY ) {
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

    private Segment addTailBelow() {
        Segment tail = new Segment(
            currentXPercent, currentYPercent + radiusPercent * 2,
            radiusPercent, speedPercent, directionX, directionY
        );

        this.tail = tail;
        tail.head = this;
        tail.isAbove = this.isAbove;

        return tail;
    }

    void addTailsBelow( int tails ) {
        Segment tail = this;

        while( tails > 0 ) {
            tail = tail.addTailBelow();
            tails--;
        }
    }
}

