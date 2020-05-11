/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import edu.fullsail.whackapede.R;
import edu.fullsail.whackapede.interfaces.CanvasDrawable;

public class LayerGrass implements CanvasDrawable {
    private Canvas canvas;
    private Bitmap bitmapGrass;
    private Paint paintGrass = new Paint();

    public LayerGrass( Context context, Canvas canvas ) {
        this.canvas = canvas;

        float cellSize = ( float ) canvas.getWidth() / 7;
        float radiusHole = cellSize / 2;

        ArrayList< Float > holeXs = new ArrayList<>();

        holeXs.add( cellSize * 1 );
        holeXs.add( cellSize * 3 );
        holeXs.add( cellSize * 5 );

        ArrayList< Float > holeYs = new ArrayList<>();

        holeYs.add( cellSize * 1 );
        holeYs.add( cellSize * 3 );
        holeYs.add( cellSize * 5 );
        holeYs.add( cellSize * 7 );
        holeYs.add( cellSize * 9 );

        int colorGrass = ContextCompat.getColor( context, R.color.grassGreen );
        bitmapGrass = Bitmap.createBitmap( canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888 );
        Canvas canvasGrass = new Canvas( bitmapGrass );

        canvasGrass.drawColor( colorGrass );

        Paint paintHole = new Paint();

        paintHole.setAntiAlias( true );
        paintHole.setXfermode( new PorterDuffXfermode( PorterDuff.Mode.CLEAR ) );

        for( float holeX : holeXs ) for ( float holeY : holeYs )
            canvasGrass.drawCircle( holeX + radiusHole, holeY + radiusHole, radiusHole, paintHole );
    }

    @Override public void drawToCanvas() {
        canvas.drawBitmap( bitmapGrass, 0, 0, paintGrass );
    }
}