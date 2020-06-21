package com.ziploan.team.verification_module.borrowerdetails.ekitsign;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadedFile {

@SerializedName("file_name")
@Expose
private String fileName;
@SerializedName("file_url")
@Expose
private String fileUrl;

public String getFileName() {
return fileName;
}

public void setFileName(String fileName) {
this.fileName = fileName;
}

public String getFileUrl() {
return fileUrl;
}

public void setFileUrl(String fileUrl) {
this.fileUrl = fileUrl;
}

}