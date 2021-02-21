package com.example.finalbrowser.tool;

import java.util.ArrayList;
import com.example.finalbrowser.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("ClickableViewAccessibility")
public class DrawView extends View {
	int left, top, right, bottom;
	Point[] points = new Point[4];

	/**
	 * point1 and point 3 are of same group and same as point 2 and point4
	 */
	int groupId = -1;
	private ArrayList<ColorBall> colorballs = new ArrayList<ColorBall>();
	// array that holds the balls
	private int balID = 0;
	// variable to know what ball is being dragged
	Paint paint;
	Canvas canvas;

	Point point1, point3;
	Point point2, point4;

	public DrawView(Context context) {
		super(context);
		paint = new Paint();
		setFocusable(true); // necessary for getting the touch events
		canvas = new Canvas();
		/*
		 * point1 = new Point(); point1.x = 50; point1.y = 20;
		 * 
		 * point2 = new Point(); point2.x = 450; point2.y = 20;
		 * 
		 * point3 = new Point(); point3.x = 450; point3.y = 320;
		 * 
		 * point4 = new Point(); point4.x = 50; point4.y = 320;
		 * 
		 * // declare each ball with the ColorBall class colorballs.add(new
		 * ColorBall(context, R.drawable.btn_circle_pressed, point1));
		 * colorballs.add(new ColorBall(context, R.drawable.btn_circle_pressed,
		 * point2)); colorballs.add(new ColorBall(context,
		 * R.drawable.btn_circle_pressed, point3)); colorballs.add(new
		 * ColorBall(context, R.drawable.btn_circle_pressed, point4));
		 */
	}

	public DrawView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		setFocusable(true); // necessary for getting the touch events
		canvas = new Canvas();
	}

	// the method that draws the balls
	@Override
	protected void onDraw(Canvas canvas) {
		if (points[3] == null) // point4 null when user did not touch and move
								// on screen.
			return;

		left = points[0].x;
		top = points[0].y;
		right = points[0].x;
		bottom = points[0].y;
		for (int i = 1; i < points.length; i++) {
			left = left > points[i].x ? points[i].x : left;
			top = top > points[i].y ? points[i].y : top;
			right = right < points[i].x ? points[i].x : right;
			bottom = bottom < points[i].y ? points[i].y : bottom;
		}
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeWidth(5);

		// draw stroke
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.parseColor("#AADB1255"));
		paint.setStrokeWidth(2);
		canvas.drawRect(left + colorballs.get(0).getWidthOfBall() / 2, top
				+ colorballs.get(0).getWidthOfBall() / 2, right
				+ colorballs.get(2).getWidthOfBall() / 2, bottom
				+ colorballs.get(2).getWidthOfBall() / 2, paint);
		// fill the rectangle
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.parseColor("#20246BDD"));
		paint.setStrokeWidth(0);
		canvas.drawRect(left + colorballs.get(0).getWidthOfBall() / 2, top
				+ colorballs.get(0).getWidthOfBall() / 2, right
				+ colorballs.get(2).getWidthOfBall() / 2, bottom
				+ colorballs.get(2).getWidthOfBall() / 2, paint);

		// draw the corners
		//BitmapDrawable bitmap = new BitmapDrawable();
		// draw the balls on the canvas
		paint.setColor(Color.BLUE);
		paint.setTextSize(18);
		paint.setStrokeWidth(0);
		for (int i = 0; i < colorballs.size(); i++) {
			ColorBall ball = colorballs.get(i);
			canvas.drawBitmap(ball.getBitmap(), ball.getX(), ball.getY(), paint);

			canvas.drawText("" + (i + 1), ball.getX(), ball.getY(), paint);
		}
	}

	// events when touching the screen
	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();

		int X = (int) event.getX();
		int Y = (int) event.getY();

		switch (eventaction) {

		case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on
										// a ball
			if (points[0] == null) {
				// initialize rectangle.
				points[0] = new Point();
				points[0].x = X;
				points[0].y = Y;

				points[1] = new Point();
				points[1].x = X;
				points[1].y = Y + 30;

				points[2] = new Point();
				points[2].x = X + 30;
				points[2].y = Y + 30;

				points[3] = new Point();
				points[3].x = X + 30;
				points[3].y = Y;

				balID = 2;
				groupId = 1;
				// declare each ball with the ColorBall class
				for (Point pt : points) {
					colorballs.add(new ColorBall(getContext(),
							R.drawable.btn_circle_pressed, pt));
				}
			} else {
				// resize rectangle
				balID = -1;
				groupId = -1;
				for (int i = colorballs.size() - 1; i >= 0; i--) {
					ColorBall ball = colorballs.get(i);
					// check if inside the bounds of the ball (circle)
					// get the center for the ball
					int centerX = ball.getX() + ball.getWidthOfBall();
					int centerY = ball.getY() + ball.getHeightOfBall();
					paint.setColor(Color.CYAN);
					// calculate the radius from the touch to the center of the
					// ball
					double radCircle = Math
							.sqrt((double) (((centerX - X) * (centerX - X)) + (centerY - Y)
									* (centerY - Y)));

					if (radCircle < ball.getWidthOfBall()) {

						balID = ball.getID();
						if (balID == 1 || balID == 3) {
							groupId = 2;
						} else {
							groupId = 1;
						}
						invalidate();
						break;
					}
					invalidate();
				}
			}
			break;

		case MotionEvent.ACTION_MOVE: // touch drag with the ball

			if (balID > -1) {
				// move the balls the same as the finger
				colorballs.get(balID).setX(X);
				colorballs.get(balID).setY(Y);

				paint.setColor(Color.CYAN);
				if (groupId == 1) {
					colorballs.get(1).setX(colorballs.get(0).getX());
					colorballs.get(1).setY(colorballs.get(2).getY());
					colorballs.get(3).setX(colorballs.get(2).getX());
					colorballs.get(3).setY(colorballs.get(0).getY());
				} else {
					colorballs.get(0).setX(colorballs.get(1).getX());
					colorballs.get(0).setY(colorballs.get(3).getY());
					colorballs.get(2).setX(colorballs.get(3).getX());
					colorballs.get(2).setY(colorballs.get(1).getY());
				}

				invalidate();
			}

			break;

		case MotionEvent.ACTION_UP:
			// touch drop - just do things here after dropping

			break;
		}
		// redraw the canvas
		invalidate();
		return true;

	}

	public Rect get_rect() {
		int local_left, local_right, local_top, local_bottom;

		local_left = left + colorballs.get(0).getWidthOfBall() / 2;
		local_top = top + colorballs.get(0).getWidthOfBall() / 2;
		local_right = right + colorballs.get(2).getWidthOfBall() / 2;
		local_bottom = bottom + colorballs.get(2).getWidthOfBall() / 2;
		
		System.out.println("레프트탑라이트바텀 : " + local_left + " " + local_top + " "
				+ local_right + " " + local_bottom);
		return new Rect(local_left, local_top, local_right, local_bottom);

	}

	public static class ColorBall {

		Bitmap bitmap;
		Context mContext;
		Point point;
		int id;
		static int count = 0;

		public ColorBall(Context context, int resourceId, Point point) {
			this.id = count++;
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					resourceId);
			mContext = context;
			this.point = point;
		}

		public int getWidthOfBall() {
			return bitmap.getWidth();
		}

		public int getHeightOfBall() {
			return bitmap.getHeight();
		}

		public Bitmap getBitmap() {
			return bitmap;
		}

		public int getX() {
			return point.x;
		}

		public int getY() {
			return point.y;
		}

		public int getID() {
			return id;
		}

		public void setX(int x) {
			point.x = x;
		}

		public void setY(int y) {
			point.y = y;
		}
	}
}
