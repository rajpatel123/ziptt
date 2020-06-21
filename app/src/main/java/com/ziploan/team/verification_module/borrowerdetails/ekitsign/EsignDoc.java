package com.ziploan.team.verification_module.borrowerdetails.ekitsign;

import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class EsignDoc {

    @SerializedName("file_type")
    @Expose
    private String fileType;
    @SerializedName("files")
    @Expose
    private List<String> files = null;

    public List<String> getFilenames() {
        return filenames;
    }

    public void setFilenames(List<String> filenames) {
        this.filenames = filenames;
    }

    @SerializedName("filenames")
    @Expose
    private List<String> filenames = null;

    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("section_name")
    @Expose
    private String sectionName;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

}
