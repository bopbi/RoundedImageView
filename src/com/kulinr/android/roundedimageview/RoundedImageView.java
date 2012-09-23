package com.kulinr.android.roundedimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

public class RoundedImageView extends View {

	private Bitmap image;
	private Drawable placeHolder;
	private Bitmap framedPhoto;
	private Context context;

	public RoundedImageView(Context context) {
		super(context);
		this.context = context;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int measuredWidth = getDefaultSize(getSuggestedMinimumWidth(),
				widthMeasureSpec);
		int measuredHeight = getDefaultSize(getSuggestedMinimumHeight(),
				heightMeasureSpec);

		int min = Math.min(measuredWidth, measuredHeight);
		
		setMeasuredDimension(min, min);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (image == null && placeHolder == null)
			return;

		if (framedPhoto == null) {
			createFramedPhoto(Math.min(getWidth(), getHeight()));
		}

		canvas.drawBitmap(framedPhoto, 0, 0, null);
	}

	private void createFramedPhoto(int size) {
		Drawable imageDrawable = (image != null) ? new BitmapDrawable(
				context.getResources(), image) : placeHolder;

		Bitmap output = Bitmap
				.createBitmap(size, size, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(output);

		RectF outerRect = new RectF(0, 0, size, size);
		float outerRadius = size / 18f;

		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.RED);
		canvas.drawRoundRect(outerRect, outerRadius, outerRadius, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		imageDrawable.setBounds(0, 0, size, size);

		canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
		imageDrawable.draw(canvas);
		canvas.restore();
		
		framedPhoto = output;
	}
}
