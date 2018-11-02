package com.djakapermana.company.Utils;

import android.graphics.Bitmap;

public class ImageHelper {
    public static Bitmap compressbitmap(Bitmap realImage, boolean filter) {
        final int maxSize = 1024;
        int width;
        int height;
        int inWidth = realImage.getWidth();
        int inHeight = realImage.getHeight();
        if (inWidth > inHeight) {
            width = maxSize;
            height = (inHeight * maxSize) / inWidth;
        } else {
            height = maxSize;
            width = (inWidth * maxSize) / inHeight;
        }
        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width, height, filter);
        return newBitmap;
    }
}
