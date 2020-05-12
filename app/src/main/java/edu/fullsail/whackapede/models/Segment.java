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

    private float currentXPercent;
    private float currentYPercent;

    private float lastXPercent;
    private float lastYPercent;

    private float velocityX;
    private float velocityY;

    private boolean isAbove = true;

    private float radiusPercent;

    public Segment( float currentXPercent, float currentYPercent, float radiusPercent ) {
        this.currentXPercent = currentXPercent;
        this.currentYPercent = currentYPercent;
        this.radiusPercent = radiusPercent;
    }

    public boolean getIsHead() {
        return head == null;
    }

    public boolean getIsTail() {
        return tail == null;
    }

    public float getCurrentXFor( Canvas canvas ) {
        return currentXPercent * canvas.getWidth();
    }

    public float getCurrentYFor( Canvas canvas ) {
        return currentYPercent * canvas.getWidth();
    }

    public float getRadiusFor( Canvas canvas ) {
        return radiusPercent * canvas.getWidth();
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

    /* public Segment addTail() {
        Segment tail = new Segment( currentX, currentY + radius * 2, radius );

        this.tail = tail;
        tail.head = this;
        tail.isAbove = this.isAbove;

        return tail;
    } */
}

