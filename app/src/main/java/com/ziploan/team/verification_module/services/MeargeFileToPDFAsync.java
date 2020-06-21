//package com.ziploan.team.verification_module.services;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.os.Environment;
//
//import com.google.gson.Gson;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.ziploan.team.utils.AppConstant;
//import com.ziploan.team.utils.ImageUtils;
//import com.ziploan.team.verification_module.borrowerdetails.ZiploanBorrowerDetails;
//import com.ziploan.team.verification_module.caching.DatabaseManger;
//import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class MeargeFileToPDFAsync extends AsyncTask<ZiploanBorrowerDetails,String,Boolean> {
//
//    private final ReponseListener delegate;
//    private final Context mContext;
//
//    public MeargeFileToPDFAsync(Context context,ReponseListener reponseListener) {
//        mContext = context;
//        delegate = reponseListener;
//    }
//
//    @Override
//    protected Boolean doInBackground(ZiploanBorrowerDetails... detailses) {
//        try{
//            ZiploanBorrowerDetails details = detailses[0];
//            if(!details.is_final()){
//                HashMap<Integer,List<ZiploanPhoto>> hashMap = getArrayBundle(details);
//                for (Map.Entry entry:hashMap.entrySet()){
//
//                    String pdfPath = getPDFFilesPath((List<ZiploanPhoto>) entry.getValue(),(int)entry.getKey(),details.getLoan_request_id());
//                    updateDetailsWithNewPath(pdfPath,(int)entry.getKey(),details);
//                }
//                DatabaseManger.getInstance().deleteApplicationInfoByLoanRequestId(details.getLoan_request_id());
//                DatabaseManger.getInstance().saveApplicationDetails(details,true);
//                System.out.println("data updated="+new Gson().toJson(details));
//            }
//        }catch (Exception e){
//            return false;
//        }
//        return true;
//    }
//
//
//
//    @Override
//    protected void onPostExecute(Boolean isSuccess) {
//        super.onPostExecute(isSuccess);
//        delegate.onResponse(isSuccess);
//    }
//
//    /**
//     * This method will update the local path with new path {@param pdfPath}
//     * for corresponding key index{@param index}
//     * @param pdfPath
//     * @param index
//     * @param details
//     */
//    private void updateDetailsWithNewPath(String pdfPath, int index, ZiploanBorrowerDetails details) {
//        List<ZiploanPhoto> arrUrl = new ArrayList<>();
//        switch (index){
//            case AppConstant.BUSINESS_PLACE_PHOTO:
//                arrUrl.add(new ZiploanPhoto(pdfPath));
//                details.getSite_info().setBusiness_place_photo_url(arrUrl);
//                break;
//        }
//    }
//
//    /**
//     * This Mehtod extracts all images from Ziploan Borrower details and put in map, Key as index and
//     * value as array of images for that photo typoe index
//     * @param details
//     * @return
//     */
//    private HashMap<Integer,List<ZiploanPhoto>> getArrayBundle(ZiploanBorrowerDetails details) {
//        HashMap<Integer,List<ZiploanPhoto>> hashMap = new HashMap<>();
//        if(details.getSite_info()!=null && details.getSite_info().getBusiness_place_photo_url()!=null && details.getSite_info().getBusiness_place_photo_url().size()>0){
//            hashMap.put(AppConstant.BUSINESS_PLACE_PHOTO,details.getSite_info().getBusiness_place_photo_url());
//        }
//        return hashMap;
//    }
//
//
//    private String getPDFFilesPath(List<ZiploanPhoto> arrPath, int index, String loan_request_id) {
//        String destination = getPDFFileNameByIndex(loan_request_id,index);
//        try {
//            createPdf(destination, arrPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//
//        return destination;
//    }
//
//    /**
//     * This method will return a file name for given {@param loan_request_id} and photo type index {@param index}
//     * @param loan_request_id
//     * @param index
//     * @return
//     */
//    private String getPDFFileNameByIndex(String loan_request_id,int index) {
//        String dir = Environment.getExternalStorageDirectory() + "/ZiploanTeamPDFs";
//        File firDir = new File(dir);
//        if(!firDir.exists()){
//            firDir.mkdirs();
//        }
//        String filename = null;
//        switch (index){
//            case AppConstant.BUSINESS_PLACE_PHOTO:
//                filename = loan_request_id+"_BusinessPlacePhotos.pdf";
//                break;
//        }
//
//        File file = new File(firDir,filename);
//        return file.getPath();
//    }
//
//    /**
//     * Will compress image and merge all images in a pdf reurn the path of pdf
//     * @param dest
//     * @throws IOException
//     * @throws DocumentException
//     */
//    public void createPdf(String dest, List<ZiploanPhoto> imageArray) throws IOException, DocumentException {
//        String newFilename = ImageUtils.compressImage(mContext,imageArray.get(0).getPhotoPath());
//        Image img = Image.getInstance(newFilename);
//        Document document = new Document(img);
//        PdfWriter.getInstance(document, new FileOutputStream(dest));
//        document.open();
//        for (int i=0;i<imageArray.size();i++){
//            String filename = ImageUtils.compressImage(mContext,imageArray.get(i).getPhotoPath());
//            img = Image.getInstance(filename);
//            document.setPageSize(img);
//            document.newPage();
//            img.setAbsolutePosition(0, 0);
//            document.add(img);
//        }
//        document.close();
//    }
//
//    public interface ReponseListener{
//        void onResponse(boolean isSuccess);
//    }
//}