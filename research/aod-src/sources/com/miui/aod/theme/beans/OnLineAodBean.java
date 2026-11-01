package com.miui.aod.theme.beans;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class OnLineAodBean {
    private String cardType;
    private int cardTypeOrdinal;
    private String cardUuid;
    private int index;
    private int productCount;
    private int productCountPerCard;
    private List<ProductsBean> products;
    private String subTitle;
    private String subjectUuid;
    private String title;
    private String trackId;

    public static class ProductsBean {
        private String imageUrl;
        private boolean like;
        private int likeCount;
        private String name;
        private String productType;
        private String productUuid;
        private String trackId;
        private String uuid;

        public String getImageUrl() {
            return this.imageUrl;
        }

        public int getLikeCount() {
            return this.likeCount;
        }

        public String getName() {
            return this.name;
        }

        public String getProductType() {
            return this.productType;
        }

        public String getProductUuid() {
            return this.productUuid;
        }

        public String getTrackId() {
            return this.trackId;
        }

        public String getUuid() {
            return this.uuid;
        }

        public boolean isLike() {
            return this.like;
        }

        public void setImageUrl(String str) {
            this.imageUrl = str;
        }

        public void setLike(boolean z) {
            this.like = z;
        }

        public void setLikeCount(int i) {
            this.likeCount = i;
        }

        public void setName(String str) {
            this.name = str;
        }

        public void setProductType(String str) {
            this.productType = str;
        }

        public void setProductUuid(String str) {
            this.productUuid = str;
        }

        public void setTrackId(String str) {
            this.trackId = str;
        }

        public void setUuid(String str) {
            this.uuid = str;
        }
    }

    public String getCardType() {
        return this.cardType;
    }

    public int getCardTypeOrdinal() {
        return this.cardTypeOrdinal;
    }

    public String getCardUuid() {
        return this.cardUuid;
    }

    public int getIndex() {
        return this.index;
    }

    public int getProductCount() {
        return this.productCount;
    }

    public int getProductCountPerCard() {
        return this.productCountPerCard;
    }

    public List<ProductsBean> getProducts() {
        return this.products;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public String getSubjectUuid() {
        return this.subjectUuid;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTrackId() {
        return this.trackId;
    }

    public void setCardType(String str) {
        this.cardType = str;
    }

    public void setCardTypeOrdinal(int i) {
        this.cardTypeOrdinal = i;
    }

    public void setCardUuid(String str) {
        this.cardUuid = str;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public void setProductCount(int i) {
        this.productCount = i;
    }

    public void setProductCountPerCard(int i) {
        this.productCountPerCard = i;
    }

    public void setProducts(List<ProductsBean> list) {
        this.products = list;
    }

    public void setSubTitle(String str) {
        this.subTitle = str;
    }

    public void setSubjectUuid(String str) {
        this.subjectUuid = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setTrackId(String str) {
        this.trackId = str;
    }
}
