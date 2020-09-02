package rjw.net.baselibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rjw.net.baselibrary.widget.ActionSheetDialog.SketchpadView;

public class BitmapUtil {

    public static Bitmap duplicateBitmap(Bitmap bmpSrc) {
        if (null == bmpSrc) {
            return null;
        }

        int bmpSrcWidth = bmpSrc.getWidth() + 15;
        int bmpSrcHeight = bmpSrc.getHeight() + 15;

        Bitmap bmpDest = Bitmap.createBitmap(SketchpadView.BITMAP_WIDTH, SketchpadView.BITMAP_HEIGHT, Bitmap.Config.ARGB_8888);
        if (null != bmpDest) {
            Canvas canvas = new Canvas(bmpDest);
            final Rect rect = new Rect(0, 0, bmpSrcWidth, bmpSrcHeight);
            canvas.drawColor(Color.TRANSPARENT);
            canvas.drawBitmap(bmpSrc, rect, rect, null);
        }

        return bmpDest;
    }

    public static Bitmap byteArrayToBitmap(byte[] array) {
        if (null == array) {
            return null;
        }

        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

    public static byte[] bitampToByteArray(Bitmap bitmap) {
        byte[] array = null;
        try {
            if (null != bitmap) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                array = os.toByteArray();
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return array;
    }

    public static void saveBitmapToSDCard(Bitmap bmp, String strPath) {
        if (null != bmp && null != strPath && !strPath.equalsIgnoreCase("")) {
            try {
                File file = new File(strPath);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = BitmapUtil.bitampToByteArray(bmp);
                fos.write(buffer);
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap loadBitmapFromSDCard(String strPath) {
        File file = new File(strPath);

        try {
            FileInputStream fis = new FileInputStream(file);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = 2;   //width��hight��Ϊԭ���Ķ���һ
            Bitmap btp = BitmapFactory.decodeStream(fis, null, options);
            return btp;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap returnBitMap(final String url) {
        Bitmap bitmap = null;
        URL imageurl = null;
        try {
            imageurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
