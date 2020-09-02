package rjw.net.baselibrary.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import rjw.net.baselibrary.R;
import rjw.net.baselibrary.utils.DensityUtil;


/**
 * @author zhd: 好好写
 * @date 2020/4/25 11:29
 * @desc
 */
@SuppressLint("AppCompatCustomView")
public class MyRadioButton extends RadioButton {
    private Drawable drawableLeft, drawableRight, drawableTop, drawableBottom;
    private int imgWDP, imgHDP;
    private int imgWPX, imgHPX;

    public MyRadioButton(Context context) {
        super(context);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyRadioButton);//获取我们定义的属性
        if (typedArray != null) {
            imgWDP = typedArray.getInteger(R.styleable.MyRadioButton_imgW, 50);
            imgHDP = typedArray.getInteger(R.styleable.MyRadioButton_imgH, 50);

            drawableLeft = typedArray.getDrawable(R.styleable.MyRadioButton_drawableLeft);
            drawableRight = typedArray.getDrawable(R.styleable.MyRadioButton_drawableRight);
            drawableTop = typedArray.getDrawable(R.styleable.MyRadioButton_drawableTop);
            drawableBottom = typedArray.getDrawable(R.styleable.MyRadioButton_drawableBotton);
            if (drawableLeft != null) {
                drawableLeft.setBounds(0, 0, DensityUtil.dip2px(context,imgWDP), DensityUtil.dip2px(context,imgHDP));
                setCompoundDrawables(drawableLeft, null, null, null);
            }
            if (drawableRight != null) {
                drawableRight.setBounds(0, 0, DensityUtil.dip2px(context,imgWDP), DensityUtil.dip2px(context,imgHDP));
                setCompoundDrawables(null, null, drawableRight, null);
            }
            if (drawableTop != null) {
                drawableTop.setBounds(0, 0, DensityUtil.dip2px(context,imgWDP), DensityUtil.dip2px(context,imgHDP));
                setCompoundDrawables(null, drawableTop, null, null);
            }
            if (drawableBottom != null) {
                drawableBottom.setBounds(0, 0, DensityUtil.dip2px(context,imgWDP), DensityUtil.dip2px(context,imgHDP));
                setCompoundDrawables(null, null, null, drawableBottom);
            }
        }
    }
}
