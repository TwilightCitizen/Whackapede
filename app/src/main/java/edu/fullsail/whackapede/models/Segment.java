/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

public class Segment {
    private Segment head = null;
    private Segment tail = null;

    private float currentX;
    private float currentY;

    private float lastX;
    private float lastY;

    private float velocityX;
    private float velocityY;

    private boolean isAbove = true;

    private float radius;

    public Segment( float currentX, float currentY, float radius ) {
        this.currentX = currentX;
        this.currentY = currentY;
        this.radius = radius;
    }

    public boolean getIsHead() {
        return head == null;
    }

    public boolean getIsTail() {
        return tail == null;
    }

    public float getCurrentX() {
        return currentX;
    }

    public float getCurrentY() {
        return currentY;
    }

    public float getRadius() {
        return radius;
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
        Segment tail = new Segment( currentX, currentY + radius * 2, radius );

        this.tail = tail;
        tail.head = this;
        tail.isAbove = this.isAbove;

        return tail;
    }
}
