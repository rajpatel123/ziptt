package com.ziploan.team.webapi;

import com.ziploan.team.asset_module.change_request.BusinessAddressChangeRequest;
import com.ziploan.team.asset_module.change_request.MobileChangeRequest;
import com.ziploan.team.asset_module.change_request.RepaymentBankChangeRequest;
import com.ziploan.team.asset_module.change_request.ResidenceAddressChangeRequest;
import com.ziploan.team.asset_module.change_request.TopUpRequest;
import com.ziploan.team.asset_module.ews.EwsResponse;
import com.ziploan.team.asset_module.ews.res.NewewsResponses;
import com.ziploan.team.asset_module.rv.AllRvResponse;
import com.ziploan.team.asset_module.visits.PastVisit;
import com.ziploan.team.collection.model.app_list.ApplicationListModel;
import com.ziploan.team.collection.model.bank_names.BankNameModel;
import com.ziploan.team.collection.model.config.AppConfigModel;
import com.ziploan.team.collection.model.filter.FilterModel;
import com.ziploan.team.collection.model.kyc_response.KycResponse;
import com.ziploan.team.collection.model.past_vist.PastVisitModel;
import com.ziploan.team.collection.model.record_visit.PastVisitResponse;
import com.ziploan.team.collection.model.record_visit.RecordVisitRequestModel;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.verification_module.borrowerdetails.ekitsign.ESignDocuments;
import com.ziploan.team.verification_module.borrowerdetails.ekitsign.esignfeildrequest.ESignFeildRequest;
import com.ziploan.team.verification_module.borrowerdetails.loginmethod.GenerateOTPResponse;
import com.ziploan.team.verification_module.borrowerdetails.loginmethod.LoginMethodTypeRequest;
import com.ziploan.team.verification_module.borrowerdetails.loginmethod.LoginMethodTypeResponse;
import com.ziploan.team.verification_module.borrowerdetails.questions.NewQuestionModel;
import com.ziploan.team.verification_module.borrowerslist.BorrowersUnverified;
import com.ziploan.team.verification_module.verifyekyc.GenerateAadhaarEKYCRequest;
import com.ziploan.team.verification_module.verifyekyc.GenerateBiometricAadhaarEKYCRequest;
import com.ziploan.team.verification_module.verifyekyc.OTPInitiateRequest;
import com.ziploan.team.webapi.QuestionAnswers.QuestionAnswersModel;
import com.ziploan.team.webapi.cibil.CibilResponse;
import com.ziploan.team.webapi.model.category.CategoryResponse;
import com.ziploan.team.webapi.model.sub_cat_.SubCatResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by ZIploan-Nitesh on 02/02/2017.
 */
public interface APIService {

    @POST("sourcing/v3/authentication/login/")
    Call<ApiResponse> loginUser(@Body LoginRequest request);

    @GET("Business_Category_List/")
    Call<BusinessCategoryResponse> getBusinessCategories();

    @GET("get_app_installation_status/")
    Call<BorrowersUnverified> getAppInstallStatus(@Query("loan_request_id") String loanAccountId);

    @GET("Verification_Manager_Loan_Request_List/")
    Call<ApiResponse> fetchUnverifiedBorrowers(@Query("verification_manager_id") String verification_manager_id);

    @GET("verification_manager_loan_data_retrieval/")
    Call<LoanRequestDataResponse> getLoanRequestData(@Query("loan_request_id") String loan_request_id);

    @POST("initiate_otp_aadhaar_ekyc/")
    Call<ApiResponse> initiateOTPForAadhaar(@Body OTPInitiateRequest request);

    @POST("generate_aadhaar_ekyc_response/")
    Call<ApiResponse> generateAadhaarEKYCResponse(@Body GenerateAadhaarEKYCRequest request);

    @POST("generate_biometric_aadhaar_ekyc_response/")
    Call<ApiResponse> generateBiometricAadhaarEKYCResponse(@Body GenerateBiometricAadhaarEKYCRequest request);

    @POST("Loan_Request_Verification_Details/")
    Call<ApiResponse> postApplicationDetails(@Body VerificationDetailsPostRequest request,
                                              @Query("version") int version);

    /****************ASSETS MODULE*****************/
    @GET("get_filters/asset/")
    Call<ResponseBody> getFilters();

    @GET("get_loan_accounts/asset/")
    Call<ApiLoanAccountsResponse> getLoanAccounts(@QueryMap Map<String, String> options);

    @GET("get_visits/past_visits")
    Call<ArrayList<HashMap<String, PastVisit>>> pastVisits(@Query("loan_request_id") String loan_request_id);

    @POST("post_details/log_asset_visit/")
    Call<ApiResponse> recordAssetVisit(@Body RecordNewVisitPojo visit);

    @POST("create_service_request/")
    Call<ApiResponse> changeRequestBusinessAddress(@Body BusinessAddressChangeRequest request);

    @POST("create_service_request/")
    Call<ApiResponse> changePriResidenceAddress(@Body ResidenceAddressChangeRequest request);

    @POST("create_service_request/")
    Call<ApiResponse> changeBankDetail(@Body RepaymentBankChangeRequest request);

    @POST("create_service_request/")
    Call<ApiResponse> changeMobile(@Body MobileChangeRequest request);

    @POST("create_service_request/")
    Call<ApiResponse> topUpRequst(@Body TopUpRequest request);

    @GET("get_filters/asset_manager_tracking/")
    Call<ApiResponse> getAssetManagers();

    @GET("get_visits/asset_manager_tracking/")
    Call<ApiResponse> getAssetManagerDetails(@Query("asset_managers") String asset_maanger_id);

    @Multipart
    @POST("upload_file/")
    Call<FileUploadResponse> uploadFile(@PartMap Map<String, RequestBody> params);

    @GET("file_deletion/")
    Call<FileUploadResponse> deleteFile(@QueryMap Map<String, String> queryMap);



    @GET("file_deletion/")
    Call<FileUploadResponse> deleteDocuments(@Query("loan_request_id") String req_id,
                                             @Query("file_url") String file_url,@Query("bucket_id") int bucket_id);

    @GET("mail_soa/")
    Call<ApiResponse> emailSOA(@Query("loan_application_number") String loan_application_number, @Query("email") String email);

    @GET("cibil_and_bank_details/")
    Call<CibilResponse> getBankInfo(@Query("loan_request_id") String loan_request_id);

    @GET("lms/get_ews_rule_data/")
    Call<NewewsResponses> getNewEws(@Query("loan_application_number") String loan_request_id);

    @GET("get_verification_questionnaire/")
    Call<QuestionAnswersModel> getQuestionAnswers(@Query("version") int version);

    @GET("get_verification_questionnaire/")
    Call<NewQuestionModel> getNewQuestionAnswers(@Query("version") int version);

    @GET("media/")
    Call<QuestionAnswersModel> media(@Query("media_id") String id);

    //////////////////////////////Collections/////////////////////////////////////////////

    @GET("get_loan_accounts/asset/")
    Call<ApplicationListModel> getCollectionApplicationList(@Query("records") int record, @Query("page") int page,@Header(AppConstant.Key.VIEW) String header);

    @GET("get_loan_accounts/asset/")
    Call<ApplicationListModel> searchApplication(@Query("q") String query, @Query("page") int page, @Header(AppConstant.Key.VIEW) String header);


    @GET("processingapi/collection/v2/collection_visits/{id}")
    Call<PastVisitModel> getPastVisits(@Path("id") String id,@Header(AppConstant.Key.VIEW) String header);

    @PATCH("processingapi/collection/v2/collection_visits/{id}/")
    Call<PastVisitResponse> postPastVisits(@Path("id") String id, @Body RecordVisitRequestModel recordVisitRequestModel,@Header(AppConstant.Key.VIEW) String header);

    @GET("processingapi/common/v2/kyc_details/{id}/")
    Call<KycResponse> getkycdocs(@Path("id") String id,@Header(AppConstant.Key.VIEW) String header);

    @GET("processingapi/common/v2/kyc_details/{id}/")
    Call<KycResponse> getOtherKyc(@Path("id") String id,@Header(AppConstant.Key.VIEW) String header);

//    @GET("customerapis/v1/master_bank_names/")
//    Call<BankNameModel> getBankNames(@Header(AppConstant.Key.VIEW) String header

//    @GET("https://webservices-qa.ziploan.in/customerapis/v1/master_bank_names/")
//    Call<BankNameModel> getBankNames(@Header(AppConstant.Key.VIEW) String header);
    @GET("customerapis/v1/master_bank_names/")
    Call<BankNameModel> getBankNames(@Header(AppConstant.Key.VIEW) String header);

    @GET
    Call<FilterModel> getCollectionFilter(@Url String url);

    @GET("get_team_app_configuration/")
    Call<AppConfigModel> getConfig();




    @GET("get_verification_documents/")
    Call<AllRvResponse> getRvResponse(@Query("loan_request_id")  String id);

    @GET("Business_Category_List/")
    Call<SubCatResponse> getBusinessSubCatList(@Query("Business_Category_Data")  String id);



    @POST("sourcing/v3/authentication/login_type/")
    Call<LoginMethodTypeResponse> getLoginMethodType(@Body LoginMethodTypeRequest loginRequest);


    @GET("sourcing/v3/authentication/generate_otp/")
    Call<GenerateOTPResponse> generate_otp(@Query("email") String email);


    //E-Kit Signing apis
    @GET("processingapi/common/v2/esign_form/{ZL_ID}/documents/")
    Call<ESignDocuments> getEsignDocuments(@Header("view") String view , @Path("ZL_ID") String zl_Id);


 //E-Kit Signing apis

    @GET("processingapi/common/v2/esign_form/{ZL_ID}/fields/")
    Call<ResponseBody> getEsignFeilds( @Path("ZL_ID") String zl_Id);

     @PATCH("processingapi/common/v2/esign_form/{ZL_ID}/fields/")
    Call<ResponseBody> patchEsignFeilds( @Path("ZL_ID") String zl_Id,@Body ESignFeildRequest jsonObject);


}
