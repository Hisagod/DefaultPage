package com.aib.other;

import com.lib.df.page.R;

public class DefaultPage {
    public static int loadRes = R.layout.default_view_load;
    public static int emptyRes = R.layout.default_view_empty;
    public static int errorRes = R.layout.default_view_error;

    public static void init(int loadRes, int emptyRes, int errorRes) {
        DefaultPage.loadRes = loadRes;
        DefaultPage.emptyRes = emptyRes;
        DefaultPage.errorRes = errorRes;
    }
}
