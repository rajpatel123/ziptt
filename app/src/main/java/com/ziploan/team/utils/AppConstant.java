package com.ziploan.team.utils;

/**
 * Created by ZIploan-Nitesh on 2/3/2017.
 */

public class AppConstant {

    public static final int CAMERA_REQUEST = 100;
    public static final int GALLERY_REQUEST = 101;
    public static final int FILTER_REQUEST = 102;
    public static final int ENTITY_PHOTO = 1;
    public static final int BUSINESS_PHOTO = 2;
    public static final int RESIDENCE_PHOTO = 3;
    public static final int PAN_FIRST_PHOTO = 4;
    public static final int PAN_SECOND_PHOTO = 5;
    public static final int BUSINESS_PLACE_PHOTO = 6;
    public static final int VERIFY_BY_OTP_REQUEST = 103;
    public static final int VERIFY_ENTER_OTP_REQUEST = 104;
    public static final int APPLICANT_LIST_REQUEST = 105;
    public static final String EXTRA_NOTIFICATION_TYPE = "extra_notification_type";
    public static final String UPLOAD_SUCCESS = "upload_success";
    public static final String UPLOAD_FAILED = "upload_failed";
    public static final String EXTRA_NOTIFY_TIME = "extra_notify_time";
    public static final String EXTRA_EKYC_VER_STATUS = "extra_ekyc_ver_status";
    public static final String FILTER_PARAMS = "filters_params";
    public static final int REQUEST_PERMISSION_SETTING_LOCATION = 107;

    public static class Fonts{
        public static final String FONT_ROBOTO_REGULAR = "font/roboto_regular.ttf";
        public static final String FONT_ROBOTO_MEDIUM = "font/roboto_medium.ttf";
        public static final String FONT_ROBOTO_BOLD = "font/roboto_bold.ttf";
    }

    public class Key {
        public static final String EXTRA_OBJECT = "extra_object";
        public static final String EXTRA_OBJECT1 = "extra_object1";
        public static final String EXTRA_STRING = "extra_string";
        public static final String EXTRA_CAMERA_INDEX = "extra_camera_index";
        public static final String EXTRA_FILE_PATH = "extra_file_path";
        public static final String EXTRA_SITE_INFO = "extra_site_info";
        public static final String EXTRA_QUESTIONS = "extra_questions";
        public static final String EXTRA_BANK_INFO = "extra_bank_info";
        public static final String EXTRA_DOCUMENT_INFO = "extra_document_info";
        public static final String IS_NEED_TO_DISABLE = "is_need_to_disable";
        public static final String APP_INTSALLED_STATUS = "app_installed_status";
        public static final String LOAN_REQUEST_ID = "loan_request_id";
        public static final String LOAN_REQUEST_DATA = "loan_request_data";
        public static final String EXTRA_EKYC_DETAILS = "extra_ekyc_details";
        public static final String ACCESS_ID = "access-id";
        public static final String VIEW = "view";
        public static final String VIEW_VALUE = "collection_app";
        public static final String ACCESS_TOKEN = "access-token";
        public static final String EXTRA_WEB_URL = "extra_web_url";
        public static final String EXTRA_POST_DATA = "extra_post_data";
        public static final String EXTRA_OBJECT_VISITED_DATA = "extra_object_visited_data";
        public static final String EXTRA_LIST = "extra_list";
        public static final String ZL_ID = "ZL_ID";
        public static final String LENDER_NAME ="lender_name" ;
    }

    public class LocalBroadcastType {
        public static final String APPLICATION_POST_STATUS = "application_post_status";
        public static final int APPLICATION_SUBMITTED_LOCALLY = 1;
        public static final int FILE_UPLOADING_STARTED = 2;
        public static final int FILE_UPLOADING_COMPLETED = 3;
        public static final int FILE_UPLOADING_FAILED = 4;
    }

    public class LogService {
        public static final String TAG_TASK_ONEOFF_LOG = "task_oneoff_log";
    }

    public class ImageSourceType{
        public static final int GALLERY = 1;
        public static final int CAMREA = 0;
    }

    public class UploadStatus {
        public static final int UPLOADING_STARTED = 0;
        public static final int UPLOADING_SUCCESS = 1;
        public static final int UPLOADING_FAILED = 2;
    }

    public class UserType {
        public static final String PHYSICAL_VERIFICATION = "5788ad4ab251b30ea1231dce";
        public static final String ASSET_MANAGEMENT = "59896796e4a5db0c08a4d3ef";
        public static final String COLLECTION = "";
    }

    public class FileUploadEnvironment{
        public static final String PRODUCTION = "prod";
        public static final String UAT = "uat";
    }


    public class FileUploadBankType{
        public static final int PRIMARY = 1;
        public static final int SECONDARY = 2;
    }

    public class MimeType {
        public static final int IMAGE = 1;
        public static final int PDF = 2;
        public static final int OTHER = -1;
    }

    public class FileUploadBucketId{
        public static final int BUSINESS_PLACE_PHOTO = 1;
        public static final int ITR = 2;
        public static final int BANKSTATEMENT = 3;
    }
    public class MediaType {
        public static final String IMAGE = "image/*";
        public static final String PDF = "application/pdf";
    }

    public class FileType {
        public static final String ASSET_BUSINESS_PLACE_PHOTO = "asset_verification_business_photos";
        public static final String PD_BUSINESS_PLACE_PHOTO = "BusinessPlacePhotos";
        public static final String COLLECTION = "collection_proof";
        public static final String PD_LONG_SHOT_PHOTO = "long_shot_photos";
        public static final String PD_ID_PROOF_PHOTO = "id_proof_photos";
    }
}