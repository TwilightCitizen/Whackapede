/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.gameElements;

public class GameElement {
    // Positions along the X and Y axes.
    private int positionX;
    private int positionY;

    GameElement( int positionX, int positionY ) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    // Position along the X and Y axes is read/write.
    int getPositionX() { return positionX; }
    int getPositionY() { return positionY; }
    void setPositionX( int positionX ) { this.positionX = positionX; }
    void setPositionY( int positionY ) { this.positionY = positionY; }

    // Determine this Game Element coincides with another one based on a new position.
    public boolean coincidesPerfectly( GameElement gameElement, int nextX, int nextY ) {
        return this.positionX == nextX && this.positionY == nextY;
    }

    // Determine this Game Element intersects the path of another one passing from top to bottom.
    public boolean passesTopToBottom( GameElement gameElement, int nextX, int nextY ) {
        return gameElement.getPositionX() == this.positionX && this.positionX == nextX &&
               gameElement.getPositionY() <  this.positionY && this.positionY <  nextY;
    }

    // Determine this Game Element intersects the path of another one passing from bottom to top.
    public boolean passesBottomToTop( GameElement gameElement, int nextX, int nextY ) {
        return gameElement.getPositionX() == this.positionX && this.positionX == nextX &&
               gameElement.getPositionY() >  this.positionY && this.positionY >  nextY;
    }

    // Determine this Game Element intersects the path of another one passing from left to right.
    public boolean passesLeftToRight( GameElement gameElement, int nextX, int nextY ) {
        return gameElement.getPositionY() == this.positionY && this.positionY == nextY &&
               gameElement.getPositionX() <  this.positionX && this.positionX <  nextX;
    }

    // Determine this Game Element intersects the path of another one passing from right to left.
    public boolean passesRightToLeft( GameElement gameElement, int nextX, int nextY ) {
        return gameElement.getPositionY() == this.positionY && this.positionY == nextY &&
               gameElement.getPositionX() >  this.positionX && this.positionX >  nextX;
    }

    // Determine this Game Element intersects the path of another one based on a new position.
    public boolean intersectsPathOf( GameElement gameElement, int nextX, int nextY ) {
        return coincidesPerfectly( gameElement, nextX, nextY ) ||
                passesTopToBottom( gameElement, nextX, nextY ) ||
                passesBottomToTop( gameElement, nextX, nextY ) ||
                passesLeftToRight( gameElement, nextX, nextY ) ||
                passesRightToLeft( gameElement, nextX, nextY );
    }
}