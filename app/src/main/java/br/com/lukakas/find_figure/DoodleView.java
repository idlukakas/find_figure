package br.com.lukakas.find_figure;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.lukakas.find_figure.R;

public class DoodleView extends View {

    private static final int RADIUS = 200;

    private TextView chooseTextView;

    private Bitmap bitmap;

    private Canvas canvasBitmap;

    private Paint paintScreen;

    private Paint paintLine;

    private int correct;

    private int sqr;

    private int circle;

    private int triangle;

    private int rect;

    private Rect sqrObj;

    private Rect rectObj;

    private int circleX;

    private int circleY;

    List<LatLng> trian;

    private int score;

    private TextView scoreTextView;

    private int round;

    public DoodleView (Context context, AttributeSet set){
        super (context, set);
        paintScreen = new Paint();
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setColor(Color.argb(255,255,5,5));
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(10);
        paintLine.setStrokeCap(Paint.Cap.ROUND);
        score = 0;
        round = 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvasBitmap = new Canvas(bitmap);
        bitmap.eraseColor(Color.WHITE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if(round < 5) {
            scoreTextView = (((Activity) getContext()).findViewById(R.id.scoreTextView));
            chooseTextView = (((Activity) getContext()).findViewById(R.id.chooseTextView));
            // Fills the bitmap's pixels with the specified Color
            bitmap.eraseColor(Color.WHITE);
            Random r = new Random();
            // Retorna um número entre 0-4 para definir a posição do quadrado
            sqr = r.nextInt(4);
            // Seta a próxima posição depois do quadrado retornando para a posição 0
            // caso for maior ou igual a posição 3
            circle = next(sqr);
            triangle = next(circle);
            rect = next(triangle);
            // Escolhe qual dos 4 vai ser o certo
            correct = r.nextInt(4);
            // Desenhas as figuras no espaço do quadro de desenho
            // passando as suas posições definidas anteriormente
            canvas.drawBitmap(bitmap, 0, 0, paintScreen);
            drawSquare(canvasBitmap, paintLine, sqr);
            drawCircle(canvasBitmap, paintLine, circle);
            drawTriangle(canvasBitmap, paintLine, triangle, 400);
            drawRect(canvasBitmap, paintLine, rect);
            // Qual texto vai ser exibido de acordo com a escolha do certo
            if (correct == sqr) {
                chooseTextView.setText(R.string.clickSqr);
            } else if (correct == circle) {
                chooseTextView.setText(R.string.clickCir);
            } else if (correct == rect) {
                chooseTextView.setText(R.string.clickRect);
            } else if (correct == triangle) {
                chooseTextView.setText(R.string.clickTri);
            }
            round++;
        } else {
            Toast.makeText(getContext(), "Final " + scoreTextView.getText(), Toast.LENGTH_SHORT).show();
            score = 0;
            scoreTextView.setText("Score: " + score);
            round = 0;
            invalidate();
        }
    }

    private void drawSquare(Canvas canvasBitmap, Paint paintLine, int pos) {
        switch (pos) {
            case 0:
                sqrObj = new Rect(getWidth()/4-150, getHeight()/4-200, getWidth()/2-30, getHeight()/2-200);
                canvasBitmap.drawRect(sqrObj, paintLine);
                break;
            case 1:
                sqrObj = new Rect(getWidth()/4*3-225, getHeight()/4-300, getWidth()/4*3+150, getHeight()/2-300);
                canvasBitmap.drawRect(sqrObj, paintLine);
                break;
            case 2:
                sqrObj = new Rect(getWidth()/4-180, getHeight()/2+300, getWidth()/2-60, getHeight()/4*3+300);
                canvasBitmap.drawRect(sqrObj, paintLine);
                break;
            case 3:
                sqrObj = new Rect(getWidth()/4*3-220, getHeight()/2+300, getWidth()/4*3+180, getHeight()/4*3+300);
                canvasBitmap.drawRect(sqrObj, paintLine);
                break;
        }
    }

    private void drawCircle(Canvas canvasBitmap, Paint paintLine, int pos) {
        switch (pos) {
            case 0:
                circleX = getWidth()/4;
                circleY = getHeight()/4;
                canvasBitmap.drawCircle(circleX, circleY, RADIUS, paintLine);
                break;
            case 1:
                circleX = getWidth()/4*3;
                circleY = getHeight()/4;
                canvasBitmap.drawCircle(circleX, circleY, RADIUS, paintLine);
                break;
            case 2:
                circleX = getWidth()/4;
                circleY = getHeight()/4*3;
                canvasBitmap.drawCircle(circleX, circleY, RADIUS, paintLine);
                break;
            case 3:
                circleX = getWidth()/4*3;
                circleY = getHeight()/4*3;
                canvasBitmap.drawCircle(circleX, circleY, RADIUS, paintLine);
                break;
        }
    }

    private void drawRect(Canvas canvasBitmap, Paint paintLine, int pos) {
        switch (pos) {
            case 0:
                rectObj = new Rect(getWidth()/4-150, getHeight()/4-300, getWidth()/2-210, getHeight()/2-300);
                canvasBitmap.drawRect(rectObj, paintLine);
                break;
            case 1:
                rectObj = new Rect(getWidth()/4*3-100, getHeight()/4-200, getWidth()/4*3+150, getHeight()/2-200);
                canvasBitmap.drawRect(rectObj, paintLine);
                break;
            case 2:
                rectObj = new Rect(getWidth()/4-150, getHeight()/2+300, getWidth()/2-210, getHeight()/4*3+300);
                canvasBitmap.drawRect(rectObj, paintLine);
                break;
            case 3:
                rectObj = new Rect(getWidth()/4*3-125, getHeight()/2+300, getWidth()/4*3+125, getHeight()/4*3+300);
                canvasBitmap.drawRect(rectObj, paintLine);
                break;
        }
    }

    public void drawTriangle(Canvas canvas, Paint paint, int pos, int width) {
        int x = 0;
        int y = 0;
        switch (pos) {
            case 0:
                x = getWidth()/4;
                y = getHeight()/4;
                break;
            case 1:
                x = getWidth()/4*3;
                y = getHeight()/4;
                break;
            case 2:
                x = getWidth()/4;
                y = getHeight()/4*3;
                break;
            case 3:
                x = getWidth()/4*3;
                y = getHeight()/4*3;
                break;
        }

        int halfWidth = width / 2;

        trian = new ArrayList<>();
        trian.add(new LatLng(x/100, (y-halfWidth)/100));
        trian.add(new LatLng((x -halfWidth)/100, (y + halfWidth)/100));
        trian.add(new LatLng((x + halfWidth)/100, (y + halfWidth)/100));

        Path path = new Path();
        path.moveTo(x, y - halfWidth); // Top
        path.lineTo(x - halfWidth, y + halfWidth); // Bottom left
        path.lineTo(x + halfWidth, y + halfWidth); // Bottom right
        path.lineTo(x, y - halfWidth); // Back to Top
        path.close();

        canvas.drawPath(path, paint);
    }

    private int next(int i) {
        if (i >= 3)
            return 0;
        return i+1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(checkIfPoint(event)){
            addPoint();
        } else {
            decreasePoint();
        }
        invalidate();
        return false;
    }

    private void decreasePoint() {
        if(score != 0) {
            score--;
        }
        scoreTextView.setText("Score: " + score);
    }

    private boolean checkIfPoint(MotionEvent event) {
        // Verifica primeiro qual a figura que foi pedida
        // Passa o evento do touch para verificar no que tocou é a figura requisitada
        if(correct==sqr) {
            if(itsMySqr(event)) {
                return true;
            }
            return false;
        } else if(correct==circle) {
            if(itsMyCir(event)) {
                return true;
            }
        } else if(correct==rect) {
            if(itsMyrRect(event)) {
                return true;
            }
        } else if(correct==triangle) {
            if(itsMyTri(event)) {
                return true;
            }
        }
        return false;
    }

    private boolean itsMyTri(MotionEvent event) {
        float x = event.getX()/100;
        float y = event.getY()/100;
        LatLng point = new LatLng(x, y);
        boolean contains = PolyUtil.containsLocation(point.latitude, point.longitude, trian, true);
        if (contains) {
            return true;
        }
        return false;
    }

    private boolean itsMyCir(MotionEvent event) {
        if(Math.sqrt(Math.pow(event.getX() - circleX, 2) + Math.pow(event.getY() - circleY, 2)) <= RADIUS)
            return true;
        return false;
    }

    private boolean itsMyrRect(MotionEvent event) {
        // Returns true if (x,y) is inside the rectangle.
        if(rectObj.contains((int)event.getX(), (int)event.getY())){
            return true;
        }
        return false;
    }

    private boolean itsMySqr(MotionEvent event) {
        // Returns true if (x,y) is inside the square.
        if(sqrObj.contains((int)event.getX(), (int)event.getY())){
            return true;
        }
        return false;
    }

    private void addPoint() {
        score++;
        scoreTextView.setText("Score: " + score);
    }
}
