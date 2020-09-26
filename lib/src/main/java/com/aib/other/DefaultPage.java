package com.aib.other;

import androidx.annotation.LayoutRes;

import com.lib.df.page.R;

public class DefaultPage {
    public static int loadRes = R.layout.default_view_load;
    public static int emptyRes = R.layout.default_view_empty;
    public static int errorRes = R.layout.default_view_error;

    public static class Builder {
        private DefaultPage defaultPage;

        public Builder() {
            defaultPage = new DefaultPage();
        }

        public Builder load(@LayoutRes int loadRes) {
            DefaultPage.loadRes = loadRes;
            return this;
        }

        public Builder empty(@LayoutRes int emptyPage) {
            DefaultPage.emptyRes = emptyPage;
            return this;
        }

        public Builder error(@LayoutRes int errorPage) {
            DefaultPage.errorRes = errorPage;
            return this;
        }

        public DefaultPage build() {
            return defaultPage;
        }
    }
}
