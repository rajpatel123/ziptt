
package com.ziploan.team.collection.model.kyc_response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Response {

    @SerializedName("file_data_applicant")
    private List<FileDataApplicant> mFileDataApplicant;

    public List<FileDataApplicant> getFileDataApplicant() {
        return mFileDataApplicant;
    }

    public void setFileDataApplicant(List<FileDataApplicant> fileDataApplicant) {
        mFileDataApplicant = fileDataApplicant;
    }

}
