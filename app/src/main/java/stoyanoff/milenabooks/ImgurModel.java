package stoyanoff.milenabooks;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Stoyanoff on 2/12/2016.
 */

public class ImgurModel {

    @SerializedName("data")
    private CustomImage customImage;

    @SerializedName("success")
    private Boolean success;

    public CustomImage getCustomImage() {
        return customImage;
    }

    public void setCustomImage(CustomImage customImage) {
        this.customImage = customImage;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }


}
