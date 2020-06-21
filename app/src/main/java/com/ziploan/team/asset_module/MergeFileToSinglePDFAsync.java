package com.ziploan.team.asset_module;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.ziploan.team.utils.ImageUtils;
import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MergeFileToSinglePDFAsync extends AsyncTask<ArrayList<ZiploanPhoto>,String,String> {

    private final ResponseListener delegate;
    private final Context mContext;
    private final String mLoanRequestId;

    public MergeFileToSinglePDFAsync(Context context, String loan_request_id, ResponseListener reponseListener) {
        mContext = context;
        this.mLoanRequestId = loan_request_id;
        delegate = reponseListener;
    }

    @Override
    protected String doInBackground(ArrayList<ZiploanPhoto>... photos) {
        try{
            ArrayList<ZiploanPhoto> details = photos[0];
            return getPDFFilesPath(details,mLoanRequestId);

        }catch (Exception e){
        }
        return null;
    }

    @Override
    protected void onPostExecute(String pdfPath) {
        super.onPostExecute(pdfPath);
        if(pdfPath!=null)
            delegate.onResponse(pdfPath);
        else
            delegate.onError("Creating pdf file failed, Please try again");
    }

    private String getPDFFilesPath(List<ZiploanPhoto> arrPath, String loan_request_id) {
        String destination = getPDFFileName(loan_request_id);
        try {
            createPdf(destination, arrPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return destination;
    }

    /**
     * This method will return a file name for given {@param loan_request_id} and photo type index {@param index}
     * @param loan_request_id
     * @return
     */
    private String getPDFFileName(String loan_request_id) {
        String dir = Environment.getExternalStorageDirectory() + "/ZiploanTeamPDFs";
        File firDir = new File(dir);
        if(!firDir.exists()){
            firDir.mkdirs();
        }
        String filename = "BusinessPlacePhotos"+loan_request_id+"_"+Calendar.getInstance().getTimeInMillis()+".pdf";

        File file = new File(firDir,filename);
        return file.getPath();
    }

    /**
     * Will compress image and merge all images in a pdf reurn the path of pdf
     * @param dest
     * @throws IOException
     * @throws DocumentException
     */
    public void createPdf(String dest, List<ZiploanPhoto> imageArray) throws IOException, DocumentException {
        String newFilename = ImageUtils.compressImage(mContext,imageArray.get(0).getPhotoPath());
        Image img = Image.getInstance(newFilename);
        Document document = new Document(img);
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        for (int i=0;i<imageArray.size();i++){
            String filename = ImageUtils.compressImage(mContext,imageArray.get(i).getPhotoPath());
            img = Image.getInstance(filename);
            document.setPageSize(img);
            document.newPage();
            img.setAbsolutePosition(0, 0);
            document.add(img);
        }
        document.close();
    }

    public interface ResponseListener {
        void onResponse(String pdfPath);
        void onError(String errMsg);
    }
}