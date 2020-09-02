package rjw.net.baselibrary.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import rjw.net.baselibrary.R;

/**
 * @author wfx: 一行代码,亿万生活.
 * @date 2020/4/1 15:23
 * @desc
 */
public class LoadingDialog {

    public static Dialog getInstance(Context context) {

        Dialog dialog = new Dialog(context, R.style.Theme_AppCompat_Dialog);
        dialog.setContentView(R.layout.dialog_loading);
//        dialog.setCancelable(true);
        return dialog;
    }

}
