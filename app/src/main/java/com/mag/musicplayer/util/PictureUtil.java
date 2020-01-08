package com.mag.musicplayer.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;

import java.io.InputStream;
import java.util.List;

public class PictureUtil {

    public static Bitmap getScaleBitmap(String path, int destWith, int destHeight) {
        Matrix matrix = new Matrix();
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (bitmap != null) {
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, destWith, destHeight, false);
            return Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } else return null;
    }

    public static Bitmap getScaleBitmap(InputStream inputStream, int destWith, int destHeight) {
        Matrix matrix = new Matrix();
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        if (bitmap != null) {
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, destWith, destHeight, false);
            return Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } else return null;
    }

    public static Bitmap getScaleBitmap(String path, Activity activity) {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        int destWith = point.x;
        int destHeight = point.y;
        return getScaleBitmap(path, destWith, destHeight);
    }

    public static void grantCameraPermission(Uri photoUri, Activity activity, Intent intent) {

        List<ResolveInfo> cameraActivities = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ac : cameraActivities) {
            activity.grantUriPermission(ac.activityInfo.packageName,
                    photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }

    }

}
