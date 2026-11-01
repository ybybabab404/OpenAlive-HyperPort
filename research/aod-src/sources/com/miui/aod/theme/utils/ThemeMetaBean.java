package com.miui.aod.theme.utils;

/* JADX INFO: loaded from: classes.dex */
public class ThemeMetaBean {
    public DescriptionsBean descriptions;
    public TitlesBean titles;

    public static class AuthorsBean {
    }

    public static class BuiltInPreviewsBean {
    }

    public static class BuiltInThumbnailsBean {
    }

    public static class DescriptionsBean {
        public String fallback;
        public String zh_CN;

        public String getFallback() {
            return this.fallback;
        }

        public String getZh_CN() {
            return this.zh_CN;
        }
    }

    public static class DesignersBean {
    }

    public static class ExtraMetaBean {
    }

    public static class ParentResourcesBean {

        public static class ExtraMetaBeanX {
        }
    }

    public static class TitlesBean {
        public String fallback;
        public String zh_CN;

        public String getFallback() {
            return this.fallback;
        }

        public String getZh_CN() {
            return this.zh_CN;
        }
    }

    public DescriptionsBean getDescriptions() {
        return this.descriptions;
    }

    public TitlesBean getTitles() {
        return this.titles;
    }
}
