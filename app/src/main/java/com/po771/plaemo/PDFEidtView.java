package com.po771.plaemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PDFEidtView extends View {

    public boolean changed = false;

    boolean pen_state = true; //pen: true, eraser: false

    String FileName;
    String directory;

    Canvas mCanvas;
    Bitmap mBitmap;
    Paint mPaint;
    Paint ePaint; //eraserPaint

    float lastX;
    float lastY;

    Path mPath = new Path();

    float mCurveEndX;
    float mCurveEndY;

    int mInvalidateExtraBorder = 10;

    static final float TOUCH_TOLERANCE = 8;

    public PDFEidtView(Context context) {
        super(context);
        init(context);
    }

    public PDFEidtView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PDFEidtView(Context context, @Nullable AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3.0F);

        ePaint = new Paint();
        ePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        ePaint.setStyle(Paint.Style.STROKE);
        ePaint.setStrokeJoin(Paint.Join.ROUND);
        ePaint.setStrokeCap(Paint.Cap.ROUND);
        ePaint.setStrokeWidth(20.0F);
        ePaint.setAntiAlias(true);

        this.lastX = -1;
        this.lastY = -1;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Canvas canvas = new Canvas();
        Bitmap img = null;
        try
        {
            String fileName = get_FileName();
            directory = get_Directory();
            File f = new File(directory, fileName+".png");
            if(f.exists()==true) {
            //파일이 있을시
                img = BitmapFactory.decodeStream(new FileInputStream(f)).copy(Bitmap.Config.ARGB_8888, true);
                canvas.setBitmap(img);
                canvas.drawColor(Color.TRANSPARENT); // 배경색을 투명색으로 지정
            } else {
            //파일이 없을시
                img = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                canvas.setBitmap(img);
                canvas.drawColor(Color.TRANSPARENT); // 배경색을 투명색으로 지정
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }finally {

        }
        mBitmap = img;
        mCanvas = canvas;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                changed = true;
                Rect rect = touchUp(event, false);
                if (rect != null) {
                    invalidate(rect);
                }
                mPath.rewind();

                return true;
            case MotionEvent.ACTION_DOWN:
                rect = touchDown(event);
                if (rect != null) {
                    invalidate(rect);
                }

                return true;
            case MotionEvent.ACTION_MOVE:
                rect = touchMove(event);
                if (rect != null) {
                    invalidate(rect);
                }
                return true;

        }
        return false;
    }

    private Rect touchMove(MotionEvent event) {
        Rect rect=processMove(event);
        return rect;
    }

    private Rect processMove(MotionEvent event) {
        final float x=event.getX();
        final float y=event.getY();

        final float dx=Math.abs(x-lastX);
        final float dy=Math.abs(y-lastY);

        Rect mInvalidateRect=new Rect();

        if(dx>=TOUCH_TOLERANCE || dy>=TOUCH_TOLERANCE){
            final int border=mInvalidateExtraBorder;

            mInvalidateRect.set((int)mCurveEndX-border,(int)mCurveEndY-border,(int)mCurveEndX+border,(int)mCurveEndY+border);

            float cx=mCurveEndX=(x+lastX)/2;
            float cy=mCurveEndY=(y+lastY)/2;

            mPath.quadTo(lastX,lastY,cx,cy);

            mInvalidateRect.union((int)lastX-border,(int)lastY-border,(int)lastX+border,(int)lastY+border);
            mInvalidateRect.union((int)cx-border,(int)cy-border,(int)cx,(int)cy+border);

            lastX=x;
            lastY=y;
            if(pen_state == true) mCanvas.drawPath(mPath,mPaint);
            else mCanvas.drawPath(mPath, ePaint);

        }

        return mInvalidateRect;
    }

    private Rect touchDown(MotionEvent event) {
        float x=event.getX();
        float y=event.getY();

        lastX=x;
        lastY=y;

        Rect mInvalidateRect=new Rect();
        mPath.moveTo(x,y);

        final int border=mInvalidateExtraBorder;
        mInvalidateRect.set((int)x-border,(int)y-border,(int)x+border,(int)y+border);
        mCurveEndX=x;
        mCurveEndY=y;

        if(pen_state == true) mCanvas.drawPath(mPath,mPaint);
        else mCanvas.drawPath(mPath, ePaint);

        return mInvalidateRect;
    }
    public void setStrokeWidth(int width){ // 굵기를 지정하는 함수입니다.
        if(pen_state == true) mPaint.setStrokeWidth(width);
        else ePaint.setStrokeWidth(width);
    }

    private Rect touchUp(MotionEvent event, boolean b) {
        Rect rect=processMove(event);
        return rect;
    }

    public void setColor(int color){ // 붓의 색깔을 결정하는 함수
        mPaint.setColor(color);
    }
    public void setCap(int cap){ // 선의 끝나는 지점의 장식 설정
        switch(cap){
            case 0:
                mPaint.setStrokeCap(Paint.Cap.BUTT); // 정해진 위치에서 끝남.
                break;
            case 1:
                mPaint.setStrokeCap(Paint.Cap.ROUND); // 둥근 모양으로 끝이 장식됨.
                break;
            case 2:
                mPaint.setStrokeCap(Paint.Cap.SQUARE); // 사각형 모양, 해당 좌표보다 조금 더 길게 그려짐.
                break;
        }
    }

    public void SetPenState(int pen){
        switch (pen){
            case 1: // 팬
                pen_state = true;
                break;
            case 2: // 지우개
                pen_state = false;
                break;
            default:
                pen_state = true;
                break;
        }
    }

    public String get_FileName() {
        return FileName;
    }

    public void set_FileName(String FileName) {
        this.FileName = FileName;
    }

    public String get_Directory() {
        return directory;
    }

    public void set_Directory( String directory) {
        this.directory = directory;
    }

}

