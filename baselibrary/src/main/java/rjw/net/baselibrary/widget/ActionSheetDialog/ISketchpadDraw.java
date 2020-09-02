package rjw.net.baselibrary.widget.ActionSheetDialog;

import android.graphics.Canvas;

//ʵ�ֻ�ͼ�ӿ�
public interface ISketchpadDraw {

	void draw(Canvas canvas);
	boolean hasDraw();
	void cleanAll();
	void touchDown(float x, float y);
	void touchMove(float x, float y);
	void touchUp(float x, float y);
}
