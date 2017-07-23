package com.microprocessoremulator;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

public class GlobalFont {
    public static void replaceDefaultFont(Context context, String fontBeingReplaced, String fontInAsset) {
        Typeface customFont = Typeface.createFromAsset(context.getAssets(), fontInAsset);
        replaceFont(fontBeingReplaced, customFont);
    }

    private static void replaceFont(String fontBeingReplaced, Typeface customFont) {
        try {
            Field myField = Typeface.class.getDeclaredField(fontBeingReplaced);
            myField.setAccessible(true);
            myField.set(null, customFont);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
