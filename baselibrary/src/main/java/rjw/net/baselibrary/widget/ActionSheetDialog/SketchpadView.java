package rjw.net.baselibrary.widget.ActionSheetDialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import rjw.net.baselibrary.R;
import rjw.net.baselibrary.utils.BitmapUtil;


/**function:ʵ��ͼ�εĻ�������ʾ

 */
public class SketchpadView extends View{

	//���û��ʳ���
	public static final int STROKE_PEN = 12;       //����1
    public static int   flag =0;                    //����Ͱ����
	public static  int BITMAP_WIDTH = 650;		//������
	public static  int BITMAP_HEIGHT = 400;	//������
    
	private int m_strokeType = STROKE_PEN;   //���ʷ��
    private static int m_strokeColor = Color.RED;   //������ɫ
    private static int m_penSize = 5;         //���ʴ�С
    
    //ʵ���»���
    private boolean m_isEnableDraw = true;   //����Ƿ���Ի�
    private boolean m_isDirty = false;     //���
    private boolean m_isTouchUp = false;    //����Ƿ���굯��
    private boolean m_isSetForeBmp = false;   //����Ƿ�������ǰbitmap
    private int m_bkColor = Color.WHITE;    //����ɫ
     
    private int m_canvasWidth = 100;    //������
    private int m_canvasHeight = 100;    //������
    private boolean m_canClear = true;   //����Ƿ�����
    
    private Bitmap m_foreBitmap = null;     //������ʾ��bitmap
    private Bitmap m_tempForeBitmap = null; //���ڻ����bitmap
    private Bitmap m_bkBitmap = null;       //���ڱ��󻭵�bitmap
    private ISketchpadDraw m_curTool = null;   //��¼�����Ķ��󻭱���
    
    private Canvas m_canvas;     //����
    private Paint m_bitmapPaint = null;   //����
    
    int antiontemp = 0;//��ȡ�����������event
 	boolean myLoop = false;// ��ǹ������ʶ��
 	private Bitmap bgBitmap = null;
    ///////////////// paint and Bk//////////////////////////////
    //�����������
    public boolean isDirty(){
        return m_isDirty;   //
    }
    public void setDrawStrokeEnable(boolean isEnable){
        m_isEnableDraw = isEnable;  //ȷ���Ƿ�ɻ�ͼ
    }   
    public void setBkColor(int color){   //���ñ�����ɫ
    	if (m_bkColor != color){
    		m_bkColor = color;
    		invalidate();
    	}
    }
    public static void setStrokeSize(int size, int type){   //���û��ʵĴ�С����Ƥ����С
    	switch(type){
    	case STROKE_PEN:
    		m_penSize = size;
    		break;
    	}
    }

    public static void setStrokeColor(int color){   //���û�����ɫ
    	m_strokeColor = color;
    }

    public static int getStrokeSize(){   //�õ����ʵĴ�С
    	return m_penSize;
    }
    public static int getStrokeColor(){   //�õ����ʵĴ�С
    	return m_strokeColor;
    }
    ////////////////////////////////////////////////////////////
    public void clearAllStrokes(){   //�������
    	if (m_canClear){
    		// ���õ�ǰ��bitmap����Ϊ��
    		if (null != m_tempForeBitmap){
    			m_tempForeBitmap.recycle();
    			m_tempForeBitmap = null;
    		} 
    		// Create a new fore bitmap and set to canvas.
    		createStrokeBitmap(m_canvasWidth, m_canvasHeight);

    		invalidate();
    		m_isDirty = true;
    		m_canClear = false;
    	}
    }
   ///////////////////////bitmap/////////////////////
    /*����ʱ�Ե�ǰ��ͼ���ͼƬ���п���*/
   public Bitmap getCanvasSnapshot(){
	   setDrawingCacheEnabled(false);
	   setDrawingCacheEnabled(true);
	   buildDrawingCache(true);
	   Bitmap bmp = getDrawingCache(true);
	   if (null == bmp){
		   Log.d("leehong2", "getCanvasSnapshot getDrawingCache == null");
	   }
	   //return  bmp;
	   return BitmapUtil.duplicateBitmap(bmp);
   }
	public Bitmap initPic(){
		setDrawingCacheEnabled(true);
		buildDrawingCache(true);
		Bitmap bmp = getDrawingCache(true);
		if (null == bmp){
			Log.d("leehong2", "getCanvasSnapshot getDrawingCache == null");
		}
		//return  bmp;
		return BitmapUtil.duplicateBitmap(bmp);
	}
	/*��ͼ���ļ�ʱ�����õ�ǰ��ͼΪforeBitmap*/
    public void setForeBitmap(Bitmap foreBitmap){	
        if (foreBitmap != m_foreBitmap && null != foreBitmap){
        	// Recycle the bitmap.
            if (null != m_foreBitmap){
            	m_foreBitmap.recycle();
            }
            // Here create a new fore bitmap to avoid crashing when set bitmap to canvas. 
            m_foreBitmap = BitmapUtil.duplicateBitmap(foreBitmap);
            if (null != m_foreBitmap && null != m_canvas){
            	m_canvas.setBitmap(m_foreBitmap);
            }    
            invalidate();
        }
    }    
    public Bitmap getForeBitmap(){
        return m_bkBitmap;
    }
    public void setBkBitmap(Bitmap bmp){   //���ñ���bitmap 
        if (m_bkBitmap != bmp){
            //m_bkBitmap = bmp;
            m_bkBitmap = BitmapUtil.duplicateBitmap(bmp);
            invalidate();
        }
    }
    public Bitmap getBkBitmap(){
        return m_bkBitmap;
    }         
    protected void createStrokeBitmap(int w, int h){
        m_canvasWidth = w;
        m_canvasHeight = h;  
        Bitmap bitmap = Bitmap.createBitmap(m_canvasWidth, m_canvasHeight, Bitmap.Config.ARGB_8888);
        if (null != bitmap){
            m_foreBitmap = bitmap;
            // Set the fore bitmap to m_canvas to be as canvas of strokes.
            m_canvas.setBitmap(m_foreBitmap);
        }
    }
    protected void setTempForeBitmap(Bitmap tempForeBitmap){
        if (null != tempForeBitmap){
            if (null != m_foreBitmap){
                m_foreBitmap.recycle();
            }            
            m_foreBitmap = BitmapUtil.duplicateBitmap(tempForeBitmap);  
            if (null != m_foreBitmap && null != m_canvas) {
                m_canvas.setBitmap(m_foreBitmap);
                invalidate();
            }
        }
    }
    
    protected void setCanvasSize(int width, int height)
    {//���û�����С
        if (width > 0 && height > 0){
            if (m_canvasWidth != width || m_canvasHeight != height){
                m_canvasWidth = width;
                m_canvasHeight = height;
                createStrokeBitmap(m_canvasWidth, m_canvasHeight);
            }
        }
    }
    //��ʼ������   ����
	protected void initialize(){
		m_canvas = new Canvas();//ʵ����������������ͼ����
		m_bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);  //ʵ������������bitmap���û���canvas
	}
    //�������û��ʵ���ɫ�ʹ�С    �����޸�
    public void setStrokeType(int type){
    	m_strokeColor= SketchpadView.getStrokeColor();
	    m_penSize= SketchpadView.getStrokeSize();
    	switch(type){
    	case STROKE_PEN:
    		m_curTool = new PenuCtl(m_penSize, m_strokeColor);
    		Log.i("sada022", "penʵ����");
    		break;
    	}      
    	//���ڼ�¼������������
    	m_strokeType = type;
    }
/////////////////////////////////////////////////////////////////////
    //���췽������ �����
    public SketchpadView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initialize();
	}
    public SketchpadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		 bgBitmap = ((BitmapDrawable) (getResources()
					.getDrawable(R.drawable.pic1))).getBitmap();
		// TODO Auto-generated constructor stub
		initialize();
	}
	public SketchpadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initialize();
	}
///////////////////////////////////////////  
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		//canvas.drawBitmap(m_bkBitmap, 0, 0,null);
	//	canvas.drawColor(m_bkColor);
		// Draw background bitmap.
		if (null != m_bkBitmap){
			RectF dst = new RectF(getLeft(), getTop(), getRight(), getBottom());
			Rect  rst = new Rect(0, 0, m_bkBitmap.getWidth(), m_bkBitmap.getHeight());
			canvas.drawBitmap(m_bkBitmap, rst, dst, m_bitmapPaint);
		}
		if (null != m_foreBitmap){
			canvas.drawBitmap(m_foreBitmap, 0, 0, m_bitmapPaint);
		}
		if (null != m_curTool){
			if (!m_isTouchUp){   //���û�ͼ����
				m_curTool.draw(canvas);
			}
		}
	}
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		if (!m_isSetForeBmp){
			setCanvasSize(w, h);
		}
        Log.i("sada022", "Canvas");
		m_canvasWidth = w;
		m_canvasHeight = h;
		m_isSetForeBmp = false;
	}
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub	
		float  yx=event.getX();
		float  yy=event.getY();
		if (m_isEnableDraw)   //�ж��Ƿ�ɻ�ͼ
		{
			m_isTouchUp = false;  
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:			
				if(flag==1){
					seed_fill((int)yx,(int)yy,m_foreBitmap.getPixel((int)yx,(int)yy),m_strokeColor);
					invalidate();
					flag=0;
				}
				//����m_strokeType�����������ɶ����Ҽ�¼�²�������
				setStrokeType(m_strokeType);
				m_curTool.touchDown(event.getX(), event.getY());
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				m_curTool.touchMove(event.getX(), event.getY());
				invalidate();
				m_isDirty = true;
				m_canClear = true;
				break;
			case MotionEvent.ACTION_UP:
				m_isTouchUp = true;
				m_curTool.touchUp(event.getX(), event.getY());
				// Draw strokes on bitmap which is hold by m_canvas.
				m_curTool.draw(m_canvas);
				invalidate();
				m_isDirty = true;
				m_canClear = true;
				myLoop = false;
				break;
			}
		}
		return true;
	}
	//����ʵ�����ӵݹ飬��������Ͱ����   7/28
	public void seed_fill (  int x, int y, int t_color, int r_color){
		Log.i("liuwei","ִ�У�02");
		int MAX_ROW = 400;
		int MAX_COL = 650;
		int row_size = 400;
		int col_size = 650;
		if (x < 0 || x >= col_size || y < 0 || y >= row_size || m_foreBitmap.getPixel(x,y) == r_color) {
			return;
		}
		int queue[][]=new int[MAX_ROW*MAX_COL+1][2];
		int head = 0, end = 0;
		int tx, ty;
		/* Add node to the end of queue. */ 
		queue[end][0] = x;
		queue[end][1] = y;
		end++;
		while (head < end) {
			tx = queue[head][0]; 
			ty = queue[head][1];
			if (m_foreBitmap.getPixel(tx,ty) == t_color) { 
				m_foreBitmap.setPixel(tx,ty,r_color);
			}
			/* Remove the first element from queue. */ 
			head++;
		
			/* West */ 
			if (tx-1 >= 0 && m_foreBitmap.getPixel(tx-1,ty) == t_color) {
				m_foreBitmap.setPixel(tx-1,ty,r_color);
				queue[end][0] = tx-1;
				queue[end][1] = ty;
				end++;
			}
			else if(tx-1 >= 0&&m_foreBitmap.getPixel(tx-1,ty)!=t_color){
				m_foreBitmap.setPixel(tx-1,ty,r_color);
				
				
			}
			
			
			/* East */ 
			if (tx+1 < col_size && m_foreBitmap.getPixel(tx+1,ty) == t_color) {
				m_foreBitmap.setPixel(tx+1,ty,r_color);
				queue[end][0] = tx+1;
				queue[end][1] = ty;
				end++;
			}
			else if(tx+1 <col_size&&m_foreBitmap.getPixel(tx+1,ty)!=t_color){
				m_foreBitmap.setPixel(tx+1,ty,r_color);
				
				
			}
			/* North */ 
			if (ty-1 >= 0 && m_foreBitmap.getPixel(tx,ty-1) == t_color) {
				m_foreBitmap.setPixel(tx,ty-1,r_color);
				queue[end][0] = tx;
				queue[end][1] = ty-1;
				end++;
			}
			else if(ty-1 >= 0&&m_foreBitmap.getPixel(tx,ty-1)!=t_color){
				m_foreBitmap.setPixel(tx,ty-1,r_color);
				
				
			}
			/* South */ 
			if (ty+1 < row_size &&  m_foreBitmap.getPixel(tx,ty+1) == t_color) {
				m_foreBitmap.setPixel(tx,ty+1,r_color);
				queue[end][0] = tx;
				queue[end][1] = ty+1;
				end++;
			}
			else if(ty+1<row_size&&m_foreBitmap.getPixel(tx,ty+1)!=t_color){
				m_foreBitmap.setPixel(tx,ty+1,r_color);
				
				
			}
		}
		return; 
	}          
	
	
	
}
