/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import android.graphics.Canvas;

public class Segment {
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

    private double radiusPercent;

    public Segment(
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

    public double getCurrentXFor( Canvas canvas ) {
        return currentXPercent * canvas.getWidth();
    }

    public double getCurrentYFor( Canvas canvas ) {
        return currentYPercent * canvas.getWidth();
    }

    public double getRadiusFor( Canvas canvas ) {
        return radiusPercent * canvas.getWidth();
    }

    public double getCurrentXPercent() {
        return currentXPercent;
    }

    public void setCurrentXPercent( double currentXPercent ) {
        this.currentXPercent = currentXPercent;
    }

    public double getCurrentYPercent() {
        return currentYPercent;
    }

    public void setCurrentYPercent( double currentYPercent ) {
        this.currentYPercent = currentYPercent;
    }

    public double getPreviousXPercent() {
        return previousXPercent;
    }

    public void setPreviousXPercent( double previousXPercent ) {
        this.previousXPercent = previousXPercent;
    }

    public double getPreviousYPercent() {
        return previousYPercent;
    }

    public void setPreviousYPercent( double previousYPercent ) {
        this.previousYPercent = previousYPercent;
    }

    public double getSpeedPercent() {
        return speedPercent;
    }

    public void setSpeedPercent( double speedPercent ) {
        this.speedPercent = speedPercent;
    }

    public double getDirectionX() {
        return directionX;
    }

    public void setDirectionX( double directionX ) {
        this.directionX = directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    public void setDirectionY( double directionY ) {
        this.directionY = directionY;
    }

    public boolean getIsAbove() {
        return isAbove;
    }

    public void setIsAbove() {
        isAbove = true;
    }

    public boolean getIsBelow() {
        return !isAbove;
    }

    public void setIsBelow() {
        isAbove = false;
    }

    public void setHead( Segment segment ) {
        head = segment;
    }

    public Segment getHead() {
        return head;
    }

    public void setTail( Segment segment ) {
        tail = segment;
    }

    public Segment getTail() {
        return tail;
    }

    public Segment addTailBelow() {
        Segment tail = new Segment(
            currentXPercent, currentYPercent + radiusPercent * 2,
            radiusPercent, speedPercent, directionX, directionY
        );

        this.tail = tail;
        tail.head = this;
        tail.isAbove = this.isAbove;

        return tail;
    }

    public void addTailsBelow( int tails ) {
        Segment tail = this;

        while( tails > 0 ) {
            tail = tail.addTailBelow();
            tails--;
        }
    }
}

