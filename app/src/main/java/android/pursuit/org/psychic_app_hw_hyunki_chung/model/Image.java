package android.pursuit.org.psychic_app_hw_hyunki_chung.model;

public class Image {
    private String image_type;
    private String image_url;

    public Image(String image_type, String image_url){
        this.image_type = image_type;
        this.image_url = image_url;
    }

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
