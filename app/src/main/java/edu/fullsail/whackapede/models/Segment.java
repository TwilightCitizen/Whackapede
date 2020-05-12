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

    private double lastXPercent;
    private double lastYPercent;

    private double velocityXPercent;
    private double velocityYPercent;

    private boolean isAbove = true;

    private double radiusPercent;

    public Segment(
        double currentXPercent, double currentYPercent, double radiusPercent,
        double velocityXPercent, double velocityYPercent
    ) {
        this.currentXPercent = currentXPercent;
        this.currentYPercent = currentYPercent;
        this.radiusPercent = radiusPercent;
        this.velocityXPercent = velocityXPercent;
        this.velocityYPercent = velocityYPercent;
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

    public void updatePosition( double interval ) {
        lastXPercent = currentXPercent;
        lastYPercent = currentYPercent;
        currentXPercent = currentXPercent + velocityXPercent * interval;
        currentYPercent = currentYPercent + velocityYPercent * interval;
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

    public Segment addTail() {
        Segment tail = new Segment(
            currentXPercent, currentYPercent + radiusPercent * 2,
            radiusPercent, velocityXPercent, velocityYPercent
        );

        this.tail = tail;
        tail.head = this;
        tail.isAbove = this.isAbove;

        return tail;
    }

    public void addTails( int tails ) {
        Segment tail = this;

        while( tails > 0 ) {
            tail = tail.addTail();
            tails--;
        }
    }
}

