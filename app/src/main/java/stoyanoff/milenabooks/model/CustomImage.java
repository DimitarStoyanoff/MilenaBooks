package stoyanoff.milenabooks.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Stoyanoff on 1/12/2016.
 */

public class CustomImage {


    @SerializedName("FileName")
    private String fileName;

    @SerializedName("URL")
    private String fileURL;

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
