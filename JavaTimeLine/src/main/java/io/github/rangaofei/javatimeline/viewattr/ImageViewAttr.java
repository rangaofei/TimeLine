package io.github.rangaofei.javatimeline.viewattr;

public class ImageViewAttr {
    private String imageViewId;
    private String imageSrc;
    private boolean isKey;

    public ImageViewAttr(String imageViewId, String imageSrc, boolean isKey) {
        this.imageViewId = imageViewId;
        this.imageSrc = imageSrc;
        this.isKey = isKey;
    }

    public String getImageViewId() {
        return imageViewId;
    }

    public void setImageViewId(String imageViewId) {
        this.imageViewId = imageViewId;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean key) {
        isKey = key;
    }
}
