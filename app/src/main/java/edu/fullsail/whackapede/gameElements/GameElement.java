/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.gameElements;

public class GameElement {
    // Positions along the X and Y axes.
    private Position position;

    GameElement( Position position ) { this.position = position; }

    // Position along the X and Y axes is read/write.
    public Position getPosition() { return position; }
    void setPosition( Position position ) { this.position = position; }

    // Determine this Game Element coincides with another one based on a new position.
    public boolean coincidesWith( Position nextPosition ) {
        return this.position.getX() == nextPosition.getX() && this.position.getY() == nextPosition.getY();
    }

    // Determine this Game Element intersects the path of another one passing from top to bottom.
    public boolean wasPassedTopToBottom( GameElement gameElement, Position nextPosition ) {
        return gameElement.getPosition().getX() == this.position.getX() && this.position.getX() == nextPosition.getX() &&
               gameElement.getPosition().getY() <  this.position.getY() && this.position.getY() <  nextPosition.getY();
    }

    // Determine this Game Element intersects the path of another one passing from bottom to top.
    public boolean wasPassedBottomToTop( GameElement gameElement, Position nextPosition ) {
        return gameElement.getPosition().getX() == this.position.getX() && this.position.getX() == nextPosition.getX() &&
               gameElement.getPosition().getY() >  this.position.getY() && this.position.getY() >  nextPosition.getY();
    }

    // Determine this Game Element intersects the path of another one passing from left to right.
    public boolean wasPassedLeftToRight( GameElement gameElement, Position nextPosition ) {
        return gameElement.getPosition().getY() == this.position.getY() && this.position.getY() == nextPosition.getY() &&
               gameElement.getPosition().getX() <  this.position.getX() && this.position.getX() <  nextPosition.getX();
    }

    // Determine this Game Element intersects the path of another one passing from right to left.
    public boolean wasPassedRightToLeft( GameElement gameElement, Position nextPosition ) {
        return gameElement.getPosition().getY() == this.position.getY() && this.position.getY() == nextPosition.getY() &&
               gameElement.getPosition().getX() >  this.position.getX() && this.position.getX() >  nextPosition.getX();
    }

    // Determine this Game Element intersects the path of another one based on a new position.
    public boolean intersectsPathOf( GameElement gameElement, Position nextPosition ) {
        return coincidesWith( nextPosition ) ||
                wasPassedTopToBottom( gameElement, nextPosition ) ||
                wasPassedBottomToTop( gameElement, nextPosition ) ||
                wasPassedLeftToRight( gameElement, nextPosition ) ||
                wasPassedRightToLeft( gameElement, nextPosition );
    }
}