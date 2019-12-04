//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.PorterDuff;
//import android.graphics.PorterDuffXfermode;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.View;
//import java.io.Serializable;
//import java.util.ArrayList;
//
////DrawView.class
//
//public class DrawView extends View
//{
//
//    ArrayList<Point> list;
//    private Bitmap bitmap;
//    static final int RED_STATE = 0;
//    static final int BLUE_STATE = 1;
//    static final int YELLOW_STATE = 2;
//    static final int ERASER_STATE = 3;
//    Paint[] paintList = new Paint[4];
//    int colorState = RED_STATE;
//
//    public DrawView(Context context)
//    {
//        super(context);
//        init();
//    }
//
//    public DrawView(Context context, AttributeSet attrs, int defStyle)
//    {
//        super(context, attrs, defStyle);
//        init();
//    }
//
//    public DrawView(Context context, AttributeSet attrs)
//    {
//        super(context, attrs);
//        init();
//    }
//
//    public void init()
//    {
//        list = new ArrayList<Point>();
//        Paint redPaint = new Paint();
//        redPaint.setColor(Color.RED);
//        redPaint.setStrokeWidth(5);
//        redPaint.setAntiAlias(true);
//        Paint bluePaint = new Paint();
//        bluePaint.setColor(Color.BLUE);
//        bluePaint.setStrokeWidth(5);
//        bluePaint.setAntiAlias(true);
//        Paint yellowPaint = new Paint();
//        yellowPaint.setColor(Color.YELLOW);
//        yellowPaint.setStrokeWidth(5);
//        yellowPaint.setAntiAlias(true);
//        Paint eraserPaint = new Paint();
//        eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//        eraserPaint.setStrokeWidth(30);
//        eraserPaint.setAntiAlias(true);
//        paintList[0] = redPaint;
//        paintList[1] = bluePaint;
//        paintList[2] = yellowPaint;
//        paintList[3] = eraserPaint;
//
//    }
//
//    @Override
//
//    protected void onDraw(Canvas canvas)
//    {
//        super.onDraw(canvas);
//        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
//        canvas.drawBitmap(bitmap);
//
//        for (int i = 0; i < list.size(); i++)
//
//        {
//            if (list.get(i).Draw)
//            {
//                canvas.drawLine(list.get(i - 1).x, list.get(i - 1).y, list.get(i).x, list.get(i).y,
//                        paintList[list.get(i).colorState]);
//
//            }
//
//        }
//
//    }
//
//    @Override
//
//    public boolean onTouchEvent(MotionEvent event)
//    {
//
//        if (event.getAction() == MotionEvent.ACTION_DOWN)
//        {
//            list.add(new Point(event.getX(), event.getY(), false, colorState));
//            return true;
//        }
//
//        if (event.getAction() == MotionEvent.ACTION_MOVE)
//
//        {
//            list.add(new Point(event.getX(), event.getY(), true, colorState));
//            invalidate();
//            return true;
//        }
//        return false;
//
//    }
//
//}
//
//
////Point.class
//
//
//
//public class Point implements Serializable
//
//{
//
//    float x;
//    float y;
//    boolean Draw;
//    int colorState;
//
//    Point(float ax, float ay, boolean ad, int colorState)
//
//    {
//
//        this.x = ax;
//        this.y = ay;
//        this.Draw = ad;
//        this.colorState = colorState;
//
//    }
//
//}
//
