package com.ferra.taga;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.aviran.cookiebar2.CookieBar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Global {

    public static void toggleKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            if (inputMethodManager.isActive()) {
                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
            } else {
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY); // show
            }
    }

    public static void showKeyboard(final EditText editText, final Context context) {
        editText.requestFocus();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (keyboard != null)
                    keyboard.showSoftInput(editText, 0);
                editText.requestFocus();
            }
        };
        editText.postDelayed(runnable, 50);
    }

    public static void successMessage(final Activity activity, Integer integer) {
        CookieBar.build(activity)
                .setTitle(R.string.successTitle)
                .setBackgroundColor(R.color.success_color)
                .setMessage(integer)
                .setCookiePosition(CookieBar.TOP)
                .show();
    }

    public static void warningMessage(final Activity activity, Integer integer) {
        CookieBar.build(activity)
                .setTitle(R.string.warningTitle)
                .setBackgroundColor(R.color.warningColor)
                .setMessage(integer)
                .setCookiePosition(CookieBar.TOP)
                .show();
    }

    public static void errorMessage(final Activity activity, Integer integer) {
        CookieBar.build(activity)
                .setTitle(R.string.errorTitle)
                .setBackgroundColor(R.color.errorColor)
                .setMessage(integer)
                .setCookiePosition(CookieBar.TOP)
                .show();
    }

    public static void successMessage(final Activity activity, String string) {
        CookieBar.build(activity)
                .setTitle(R.string.successTitle)
                .setBackgroundColor(R.color.success_color)
                .setMessage(string)
                .setCookiePosition(CookieBar.TOP)
                .show();
    }

    public static void warningMessage(final Activity activity, String string) {
        CookieBar.build(activity)
                .setTitle(R.string.warningTitle)
                .setBackgroundColor(R.color.warningColor)
                .setMessage(string)
                .setCookiePosition(CookieBar.TOP)
                .show();
    }

    public static void errorMessage(final Activity activity, String string) {
        CookieBar.build(activity)
                .setTitle(R.string.errorTitle)
                .setBackgroundColor(R.color.errorColor)
                .setMessage(string)
                .setCookiePosition(CookieBar.TOP)
                .show();
    }

    public static void setSystemBarColor(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public static void setBottomNavigationBarColorColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.setNavigationBarColor(ContextCompat.getColor(activity, color));
        }
    }

    public static void setSystemBarColor(Activity activity, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activity.getResources().getColor(color));
        }
    }

    public static void setSystemBarColorDialog(Context act, Dialog dialog, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = dialog.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(color));
        }
    }

    public static void setSystemBarLight(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = act.findViewById(android.R.id.content);
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }

    public static void setSystemBarLightDialog(Dialog dialog) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = dialog.findViewById(android.R.id.content);
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }

    public static void clearSystemBarLight(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = act.getWindow();
            window.setStatusBarColor(ContextCompat.getColor(act, R.color.colorPrimaryDark));
        }
    }

    /**
     * Making notification bar transparent
     */
    public static void setSystemBarTransparent(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
