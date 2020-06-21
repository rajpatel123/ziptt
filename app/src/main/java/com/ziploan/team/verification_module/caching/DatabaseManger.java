package com.ziploan.team.verification_module.caching;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ziploan.team.App;
import com.ziploan.team.collection.db.BankNameTable;
import com.ziploan.team.collection.db.RecordVisitTable;
import com.ziploan.team.collection.db.SaveApplicationList;
import com.ziploan.team.collection.model.app_list.OverdueBreakup;
import com.ziploan.team.collection.model.app_list.Reference;
import com.ziploan.team.collection.model.app_list.Result;
import com.ziploan.team.collection.model.bank_names.Response;
import com.ziploan.team.collection.model.record_visit.Denominations;
import com.ziploan.team.collection.model.record_visit.RecordVisitRequestModel;
import com.ziploan.team.verification_module.borrowerdetails.BankInfoModel;
import com.ziploan.team.verification_module.borrowerdetails.FamilyReferences;
import com.ziploan.team.verification_module.borrowerdetails.ReferenceUser;
import com.ziploan.team.verification_module.borrowerdetails.ZiploanBorrowerDetails;
import com.ziploan.team.verification_module.borrowerdetails.ZiploanNewQuestion;
import com.ziploan.team.verification_module.borrowerdetails.ZiploanQuestion;
import com.ziploan.team.verification_module.borrowerdetails.ZiploanSiteInfo;
import com.ziploan.team.verification_module.borrowerdetails.ekitsign.EsignFieldTable;
import com.ziploan.team.verification_module.borrowerdetails.questions.ZiploanNewBorrowerDetails;
import com.ziploan.team.verification_module.borrowerslist.BorrowersUnverified;
import com.ziploan.team.verification_module.caching.tables.ApplicationVerificationDetailTable;
import com.ziploan.team.verification_module.caching.tables.ApplicationsTable;
import com.ziploan.team.verification_module.caching.tables.DocumentsTable;
import com.ziploan.team.verification_module.caching.tables.FiltersTable;
import com.ziploan.team.verification_module.services.OnSaveSuccess;
import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ziploan-Nitesh on 10/08/2016.
 */
public class DatabaseManger {

    private static DatabaseManger instance;

    public static DatabaseManger getInstance() {
        if (instance == null) {
            instance = new DatabaseManger();
        }
        return instance;
    }

    private DatabaseManger() {

    }

    public void saveApplicationList(final ArrayList<BorrowersUnverified> arrayList) {
        deleteApplications();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (BorrowersUnverified borrower : arrayList) {
                    saveApplication(borrower);
                }
            }
        }).start();
    }

    public void saveFiltersData(final String filters, final OnSaveSuccess onSaveSuccess) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject mainObject = null;
                try {
                    mainObject = new JSONObject(filters);
                    JSONObject filtersObject = mainObject.getJSONObject("filters");
                    JSONArray arrKeys = filtersObject.names();
                    for (int i = 0; i < arrKeys.length(); i++) {
                        String key = (String) arrKeys.get(i);
                        ContentValues values = new ContentValues();
                        values.put(FiltersTable.Cols.FILTER_KEY, key);
                        HashMap<String, String> items = new Gson().fromJson(filtersObject.get(key).toString(), HashMap.class);
                        Iterator mayKeysIterator = items.keySet().iterator();
                        while (mayKeysIterator.hasNext()) {
                            String filterKey = (String) mayKeysIterator.next();
                            values.put(FiltersTable.Cols.FILTER_ID, filterKey);
                            values.put(FiltersTable.Cols.FILTER_NAME, items.get(filterKey));
                            App.getDb().insertWithOnConflict(FiltersTable.NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                        }
                    }
                    onSaveSuccess.onSuccess();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public ArrayList<FilterKey> getAllAssetsFiltersKey() {
        ArrayList<FilterKey> filtersKey = new ArrayList<>();
        String sql = "SELECT " + FiltersTable.Cols.FILTER_KEY + " FROM " + FiltersTable.NAME + " GROUP BY " +
                FiltersTable.Cols.FILTER_KEY;
        Cursor cursor = App.getDb().rawQuery(sql, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    filtersKey.add(new FilterKey(cursor.getString(cursor.getColumnIndex(FiltersTable.Cols.FILTER_KEY))));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return filtersKey;
    }

    public HashMap<String, ArrayList<FilterItem>> getAllAssetsFiltersItemsByKey() {
        HashMap<String, ArrayList<FilterItem>> data = new HashMap<>();
        String sql = "SELECT * FROM " + FiltersTable.NAME;
        Cursor cursor = App.getDb().rawQuery(sql, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    String filterKey = cursor.getString(cursor.getColumnIndex(FiltersTable.Cols.FILTER_KEY));
                    if (data.containsKey(filterKey)) {
                        data.get(filterKey).add(getFilterItem(cursor.getString(cursor.getColumnIndex(FiltersTable.Cols.FILTER_ID)), cursor.getString(cursor.getColumnIndex(FiltersTable.Cols.FILTER_NAME))));
                    } else {
                        ArrayList<FilterItem> items = new ArrayList<>();
                        items.add(getFilterItem(cursor.getString(cursor.getColumnIndex(FiltersTable.Cols.FILTER_ID)), cursor.getString(cursor.getColumnIndex(FiltersTable.Cols.FILTER_NAME))));
                        data.put(filterKey, items);
                    }
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return data;
    }

    private FilterItem getFilterItem(String id, String name) {
        return new FilterItem(id, name);
    }

    private void saveApplication(BorrowersUnverified borrower) {
        ContentValues values = new ContentValues();
        values.put(ApplicationsTable.Cols.LOAN_REQUEST_ID, borrower.getLoan_request_id());
        values.put(ApplicationsTable.Cols.LOAN_APP_NO, borrower.getLoan_application_number());
        values.put(ApplicationsTable.Cols.FIRST_NAME, borrower.getFirst_name());
        values.put(ApplicationsTable.Cols.LAST_NAME, borrower.getLast_name());
        values.put(ApplicationsTable.Cols.BUSINESS_NAME, borrower.getBusiness_name());
        values.put(ApplicationsTable.Cols.BUSINESS_ADDRESS, borrower.getBusiness_address());
        values.put(ApplicationsTable.Cols.LOAN_REQUEST_STATUS, borrower.getLoan_request_status());
        values.put(ApplicationsTable.Cols.LOAN_REQUEST_STATUS_NAME, borrower.getLoan_request_status_name());
        values.put(ApplicationsTable.Cols.LOAN_REQUEST_ASSIGNED_DATE, borrower.getLoan_request_assigned_date());
        values.put(ApplicationsTable.Cols.MOBILE, borrower.getMobile());
        values.put(ApplicationsTable.Cols.EKYC_STATUS, borrower.getEkyc_status());
        App.getDb().insertWithOnConflict(ApplicationsTable.NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void saveBankNames(final List<Response> arrayList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Response result : arrayList) {
                    saveBankList(result);
                }
            }
        }).start();
    }

    private void saveBankList(Response result) {
        try {
            ContentValues values = new ContentValues();
            values.put(BankNameTable.Cols.BANK_NAME, result.getBankDisplayName());
            values.put(BankNameTable.Cols.BANK_CODE, result.getBankCode());
            App.getDb().insertWithOnConflict(BankNameTable.NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Response> getBankListFromDb() {
        ArrayList<Response> data = new ArrayList<>();
        String sql = "SELECT * FROM " + BankNameTable.NAME;
        Cursor cursor = null;
        try {
            cursor = App.getDb().rawQuery(sql, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    data.add(getBanksData(cursor));
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return data;
    }

    private Response getBanksData(Cursor cursor) {
        Response response = new Response();
        String bankName = cursor.getString(cursor.getColumnIndex(BankNameTable.Cols.BANK_NAME));
        String bankCode = cursor.getString(cursor.getColumnIndex(BankNameTable.Cols.BANK_CODE));
        response.setBankCode(bankCode);
        response.setBankDisplayName(bankName);
        return response;
    }


    public void saveCollectionApplicationList(final List<Result> arrayList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Result result : arrayList) {
                    saveCollectionApplication(result);
                }
            }
        }).start();
    }

    private void saveCollectionApplication(Result result) {
        ContentValues values = new ContentValues();
        values.put(SaveApplicationList.Cols.LOAN_REQUEST_ID, result.getIdentifier());
        values.put(SaveApplicationList.Cols.LOAN_APP_NO, result.getLoanApplicationNumber());
        values.put(SaveApplicationList.Cols.BUSINESS_ADDESS, result.getBusinessAddress());
        values.put(SaveApplicationList.Cols.BUSINESS_NAME, result.getBusinessName());
        values.put(SaveApplicationList.Cols.AMOUNT_OVERDUE, result.getAmountOverdue());
        values.put(SaveApplicationList.Cols.APPLICATION_NAME, result.getApplicantName());
        values.put(SaveApplicationList.Cols.BUSINESS_CITY, result.getmBusiness_city());
        values.put(SaveApplicationList.Cols.BUSINESS_PINCODE, result.getmBusiness_pincode());
        values.put(SaveApplicationList.Cols.BUSINESS_STATE, result.getmBusiness_state());
        values.put(SaveApplicationList.Cols.DELIQUENCY_BUCKET, result.getDeliquencyBucket());
        values.put(SaveApplicationList.Cols.EMI, result.getEmi());
        values.put(SaveApplicationList.Cols.MOBILE_NUMBER, result.getMobileNumber());
        values.put(SaveApplicationList.Cols.PORTFOLIO_AMOUNT, result.getPortfolioAmount());
        values.put(SaveApplicationList.Cols.FORECLOSURE_AMOUNT, result.getForeclosureAmount());
        values.put(SaveApplicationList.Cols.LOAN_AMOUNT, result.getLoanAmount());
        values.put(SaveApplicationList.Cols.RESIDENCE_ADDRESS, result.getResidenceAddress());
        values.put(SaveApplicationList.Cols.LAST_VIST_DATE, result.getLastVisitDate());
        values.put(SaveApplicationList.Cols.OVERDUE_BREAKUP, new Gson().toJson(result.getOverdueBreakup()));
        values.put(SaveApplicationList.Cols.LAST_VISIT_COORDINATES, new Gson().toJson(result.getLastVisitedCoordinates()));
        values.put(SaveApplicationList.Cols.APPLICANT_REFERENCE_NUMBER, new Gson().toJson(result.getReferences()));
        App.getDb().insertWithOnConflict(SaveApplicationList.NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public ArrayList<Result> getCollectionListDataFromDb() {
        ArrayList<Result> data = new ArrayList<>();
        String sql = "SELECT * FROM " + SaveApplicationList.NAME;
        Cursor cursor = null;
        try {
            cursor = App.getDb().rawQuery(sql, null);
            if (cursor != null && cursor.getCount() > 0) {
                Gson gson = new Gson();
                cursor.moveToFirst();
                do {
                    data.add(getCollectionListData(cursor, gson));
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return data;
    }

    public Result getCollectionListData(Cursor cursor, Gson gson) {
        Result result = new Result();

        String identifier = cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.LOAN_REQUEST_ID));
        String loan_request_id = cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.LOAN_APP_NO));
        String business_address = cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.BUSINESS_ADDESS));
        String BUSINESS_NAME = cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.BUSINESS_NAME));
        String amountOverdue = cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.AMOUNT_OVERDUE));

        Type type = new TypeToken<List<OverdueBreakup>>() {
        }.getType();
        List<OverdueBreakup> overdueBreakups = gson.fromJson(cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.OVERDUE_BREAKUP)), type);

        Type typeString = new TypeToken<List<String>>() {
        }.getType();

        List<String> LAST_VISTE_COORDINATES = gson.fromJson(cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.LAST_VISIT_COORDINATES)), typeString);

        Type typeReference = new TypeToken<List<Reference>>() {
        }.getType();

        List<Reference> savedReference = gson.fromJson(cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.APPLICANT_REFERENCE_NUMBER)), typeReference);

        String APPLICATION_NAME = cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.APPLICATION_NAME));
        String BUSINESS_CITY = cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.BUSINESS_CITY));
        String BUSINESS_PINCODE = cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.BUSINESS_PINCODE));
        String BUSINESS_STATE = cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.BUSINESS_STATE));
        String loanAmount = cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.LOAN_AMOUNT));
        String DELIQUENCY_BUCKET = cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.DELIQUENCY_BUCKET));

        String EMI = cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.EMI));
        String MOBILE_NUMBER = cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.MOBILE_NUMBER));
        String PORTFOLIO_AMOUNT = cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.PORTFOLIO_AMOUNT));
        String FORCLOSER_AMOUNT = cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.FORECLOSURE_AMOUNT));
        String RESIDENCE_ADDRESS = cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.RESIDENCE_ADDRESS));
        String LAST_VIST_DATE = cursor.getString(cursor.getColumnIndex(SaveApplicationList.Cols.LAST_VIST_DATE));

        result.setLoanApplicationNumber(loan_request_id);
        result.setIdentifier(identifier);
        result.setBusinessAddress(business_address);
        result.setAmountOverdue(amountOverdue);
        result.setOverdueBreakup(overdueBreakups);
        result.setApplicantName(APPLICATION_NAME);
        result.setmBusiness_city(BUSINESS_CITY);
        result.setmBusiness_pincode(BUSINESS_PINCODE);
        result.setmBusiness_state(BUSINESS_STATE);
//        result.setDateOfDisbursement(DATE_OF_DISBURSEMENT);
        result.setDeliquencyBucket(DELIQUENCY_BUCKET);
        result.setEmi(EMI);
        result.setMobileNumber(MOBILE_NUMBER);

        result.setPortfolioAmount(PORTFOLIO_AMOUNT);
        result.setResidenceAddress(RESIDENCE_ADDRESS);
        result.setLastVisitDate(LAST_VIST_DATE);
        result.setForeclosureAmount(FORCLOSER_AMOUNT);
        result.setLastVisitedCoordinates(LAST_VISTE_COORDINATES);
        result.setBusinessName(BUSINESS_NAME);
        result.setReferences(savedReference);
        result.setLoanAmount(loanAmount);
        return result;
    }

    public void deleteCollectionAppListData() {
        String sql = "DELETE FROM " + SaveApplicationList.NAME;
        Cursor cursor = null;
        try {
            cursor = App.getDb().rawQuery(sql, null);
            cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }


    public void saveRecordVisitData(final RecordVisitRequestModel visitRequestModel, final String reqId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                saveRecordVisit(visitRequestModel, reqId);
            }
        }).start();
    }

    private void saveRecordVisit(RecordVisitRequestModel visitRequestModel, String reqId) {
        ContentValues values = new ContentValues();
        values.put(RecordVisitTable.Cols.LOAN_REQUEST_ID, reqId);
        values.put(RecordVisitTable.Cols.LOAN_APP_NO, visitRequestModel.getmIdentifier());
        values.put(RecordVisitTable.Cols.AMOUNT, visitRequestModel.getAmount().toString());
        values.put(RecordVisitTable.Cols.BANK_NAME, visitRequestModel.getBankName());
        values.put(RecordVisitTable.Cols.COMMENT, visitRequestModel.getComment());
        values.put(RecordVisitTable.Cols.DENOMINATIONS, new Gson().toJson(visitRequestModel.getDenominations()));
        values.put(RecordVisitTable.Cols.FILE_URL, visitRequestModel.getmFileUrl());
        values.put(RecordVisitTable.Cols.IS_PERSON_MET, visitRequestModel.getIsPersonMet().toString());
        values.put(RecordVisitTable.Cols.MODE, visitRequestModel.getMode() != null ? visitRequestModel.getMode().toString() : "");
        values.put(RecordVisitTable.Cols.PERSON_MET, visitRequestModel.getPersonMet());
        values.put(RecordVisitTable.Cols.REFERENCE_NUMBER, visitRequestModel.getRefrenceNumber());
        values.put(RecordVisitTable.Cols.VALUE_DATE, visitRequestModel.getValueDate());
        App.getDb().insertWithOnConflict(RecordVisitTable.NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }


    public void saveDocumentData(String data, String zl_number) {
        ContentValues values = new ContentValues();
        values.put(DocumentsTable.Cols.ZL_DATA, data);
        values.put(DocumentsTable.Cols.ZL_NUMBER, zl_number);
        App.getDb().insertWithOnConflict(DocumentsTable.NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }


    public String getDocumentData(String zl_number) {
        String data = null;


        String sql = "SELECT * FROM " + DocumentsTable.NAME + " where " + DocumentsTable.Cols.ZL_NUMBER + "='" + zl_number + "'";

        Cursor cursor = App.getDb().rawQuery(sql, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    data = cursor.getString(cursor.getColumnIndex(DocumentsTable.Cols.ZL_DATA));

                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return data;
    }




    public void saveEsignFeildData(String data, String zl_number, String files) {
        ContentValues values = new ContentValues();
        values.put(EsignFieldTable.Cols.ZL_FEILD_DATA,data);
        values.put(EsignFieldTable.Cols.ZL_NUMBER, zl_number);
        values.put(EsignFieldTable.Cols.ZL_FILES, files);
        App.getDb().insertWithOnConflict(EsignFieldTable.NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }




    public String getEsignFeildData(String zl_number) {
        String data = null;

        String sql = "SELECT * FROM " + EsignFieldTable.NAME + " where " + EsignFieldTable.Cols.ZL_NUMBER + "='" + zl_number + "'";

        Cursor cursor = App.getDb().rawQuery(sql, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    data = cursor.getString(cursor.getColumnIndex(EsignFieldTable.Cols.ZL_FEILD_DATA));

                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return data;
    }



    public String getEsignFeildFiles(String zl_number) {
        String data = null;


        String sql = "SELECT * FROM " + EsignFieldTable.NAME + " where " + EsignFieldTable.Cols.ZL_NUMBER + "='" + zl_number + "'";

        Cursor cursor = App.getDb().rawQuery(sql, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    data = cursor.getString(cursor.getColumnIndex(EsignFieldTable.Cols.ZL_FILES));
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return data;
    }


    public void deleteRecordVisitData(String loanRequestId) {
        String sql = "DELETE FROM " + RecordVisitTable.NAME + " where " + RecordVisitTable.Cols.LOAN_REQUEST_ID + "='" + loanRequestId + "'";
        Cursor cursor = App.getDb().rawQuery(sql, null);
        try {
            cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }

    public void deleteAllRecordVisitData() {
        String sql = "DELETE FROM " + RecordVisitTable.NAME;
        Cursor cursor = App.getDb().rawQuery(sql, null);
        try {
            cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }


    public List<HashMap<String, RecordVisitRequestModel>> getRecordVisitDataFromDb() {
        List<HashMap<String, RecordVisitRequestModel>> data = new ArrayList<>();
        String sql = "SELECT * FROM " + RecordVisitTable.NAME;

        Cursor cursor = App.getDb().rawQuery(sql, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    data.add(getRecordVisitData(cursor));
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return data;
    }

    public HashMap<String, RecordVisitRequestModel> getRecordVisitData(Cursor cursor) {
        HashMap<String, RecordVisitRequestModel> data = new HashMap<>();
        RecordVisitRequestModel recordVisitRequestModel = new RecordVisitRequestModel();
        String loan_request_id = cursor.getString(cursor.getColumnIndex(RecordVisitTable.Cols.LOAN_REQUEST_ID));
        String identifier = cursor.getString(cursor.getColumnIndex(RecordVisitTable.Cols.LOAN_APP_NO));
        String amount = cursor.getString(cursor.getColumnIndex(RecordVisitTable.Cols.AMOUNT));
        String bank_name = cursor.getString(cursor.getColumnIndex(RecordVisitTable.Cols.BANK_NAME));
        String comment = cursor.getString(cursor.getColumnIndex(RecordVisitTable.Cols.COMMENT));
        Denominations denomination = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(RecordVisitTable.Cols.DENOMINATIONS)), Denominations.class);
        String file_uri = cursor.getString(cursor.getColumnIndex(RecordVisitTable.Cols.FILE_URL));
        String is_person_met = cursor.getString(cursor.getColumnIndex(RecordVisitTable.Cols.IS_PERSON_MET));
        String mode = cursor.getString(cursor.getColumnIndex(RecordVisitTable.Cols.MODE));
        String person_met = cursor.getString(cursor.getColumnIndex(RecordVisitTable.Cols.PERSON_MET));
        String reference_number = cursor.getString(cursor.getColumnIndex(RecordVisitTable.Cols.REFERENCE_NUMBER));
        String value_date = cursor.getString(cursor.getColumnIndex(RecordVisitTable.Cols.VALUE_DATE));

        recordVisitRequestModel.setAmount(Integer.parseInt(amount));
        recordVisitRequestModel.setmFileUrl(file_uri);
        recordVisitRequestModel.setValueDate(value_date);
        recordVisitRequestModel.setRefrenceNumber(reference_number);
        recordVisitRequestModel.setPersonMet(person_met);
        recordVisitRequestModel.setIsPersonMet(is_person_met.equalsIgnoreCase("true") ? true : false);
        recordVisitRequestModel.setDenominations(denomination);
        recordVisitRequestModel.setComment(comment);
        if (!TextUtils.isEmpty(mode))
            recordVisitRequestModel.setMode(Integer.parseInt(mode));

        recordVisitRequestModel.setBankName(bank_name);
        recordVisitRequestModel.setmIdentifier(identifier);
        data.put(loan_request_id, recordVisitRequestModel);
        return data;
    }


    public void saveApplicationDetails(ZiploanBorrowerDetails details, boolean isFinal) {
        ZiploanSiteInfo siteInfo = details.getSite_info();
        ArrayList<ZiploanQuestion> questions = details.getQuestions();

        ContentValues values = new ContentValues();
        values.put(ApplicationVerificationDetailTable.Cols.LOAN_REQUEST_ID, details.getLoan_request_id());
        values.put(ApplicationVerificationDetailTable.Cols.REFERENCE, new Gson().toJson(siteInfo.getArrReferenceUsers()));
        values.put(ApplicationVerificationDetailTable.Cols.REFERENCE_family, new Gson().toJson(siteInfo.getArrFamilyReferenceUsers()));
        values.put(ApplicationVerificationDetailTable.Cols.BANK_INFO, new Gson().toJson(details.getLoan_details()));
        // values.put(ApplicationVerificationDetailTable.Cols.BUSINESS_RESIDENCE_SEPERATE, siteInfo.getBusiness_place_seperate_residence_place());
        values.put(ApplicationVerificationDetailTable.Cols.BUSINESS_PLACE_PHOTO, new Gson().toJson(siteInfo.getBusiness_place_photo_url()));
        values.put(ApplicationVerificationDetailTable.Cols.BUSINESS_LONG_SHOT_PHOTO, new Gson().toJson(siteInfo.getLong_shot_photos()));
        values.put(ApplicationVerificationDetailTable.Cols.BUSINESS_ID_PROOF_PHOTO, new Gson().toJson(siteInfo.getId_proof_photos()));
        values.put(ApplicationVerificationDetailTable.Cols.QUESTIONS, new Gson().toJson(questions));
        values.put(ApplicationVerificationDetailTable.Cols.STOCK_INVENTORY_AMOUNT, siteInfo.getStock_inventory_amount());
        values.put(ApplicationVerificationDetailTable.Cols.ASSET_MACHINERY_AMOUNT, siteInfo.getFixed_asset_machinery_amount());
        values.put(ApplicationVerificationDetailTable.Cols.EMPLOYEE_NO, siteInfo.getNo_employees());
        values.put(ApplicationVerificationDetailTable.Cols.CUSTOM_ENTITY_TYPE, siteInfo.getPerson_email());
        values.put(ApplicationVerificationDetailTable.Cols.CUSTOM_BUSINESS_ADD_TYPE, "");
        values.put(ApplicationVerificationDetailTable.Cols.CUSTOM_RESIDENCE_ADD_TYPE, "");
        values.put(ApplicationVerificationDetailTable.Cols.LAT, details.getLat());
        values.put(ApplicationVerificationDetailTable.Cols.LNG, details.getLng());
        values.put(ApplicationVerificationDetailTable.Cols.TEXT_DESIGNATION, siteInfo.getText_designation());
        values.put(ApplicationVerificationDetailTable.Cols.IS_FINAL, isFinal ? "1" : "0");

        values.put(ApplicationVerificationDetailTable.Cols.app_not_installed_reason, siteInfo.getApp_not_installed_reason());
        values.put(ApplicationVerificationDetailTable.Cols.RAW_MATERIAL, siteInfo.getRaw_material());
        values.put(ApplicationVerificationDetailTable.Cols.rent_amount, siteInfo.getRent_amount());
        values.put(ApplicationVerificationDetailTable.Cols.investment_in_business, siteInfo.getInvestment_in_business());
        values.put(ApplicationVerificationDetailTable.Cols.no_of_machines, siteInfo.getNo_of_machines());
        values.put(ApplicationVerificationDetailTable.Cols.actual_no_of_employees, siteInfo.getActual_no_of_employees());
        values.put(ApplicationVerificationDetailTable.Cols.person_met, siteInfo.getPerson_met());
        values.put(ApplicationVerificationDetailTable.Cols.nature_of_work, siteInfo.getNature_of_work());
        values.put(ApplicationVerificationDetailTable.Cols.TEXT_nature_of_work, siteInfo.getText_nature_of_work());
        values.put(ApplicationVerificationDetailTable.Cols.designation_in_office, siteInfo.getDesignation_in_office());
        values.put(ApplicationVerificationDetailTable.Cols.sign_board_observed, siteInfo.getSign_board_observed());
        values.put(ApplicationVerificationDetailTable.Cols.business_category, siteInfo.getBusiness_category());

        values.put(ApplicationVerificationDetailTable.Cols.STABILITY, siteInfo.getBusiness_stability());
        values.put(ApplicationVerificationDetailTable.Cols.PLACE_CHANGE_REASON, siteInfo.getBusiness_place_change_reason());
        values.put(ApplicationVerificationDetailTable.Cols.LANDMARK, siteInfo.getLandmark());
        values.put(ApplicationVerificationDetailTable.Cols.BUSINESS_ADDRESS_DASHBOARD, siteInfo.getBusiness_address_same_as_dashboard());
        values.put(ApplicationVerificationDetailTable.Cols.BUSINESS_ADDRESS_DASHBOARD_REASON, siteInfo.getBusiness_address_same_as_dashboard_reason());
        values.put(ApplicationVerificationDetailTable.Cols.locality_business_place, siteInfo.getLocality_business_place());
        values.put(ApplicationVerificationDetailTable.Cols.education_qualification, siteInfo.getEducation_qualification());

        try {
            App.getDb().insertWithOnConflict(ApplicationVerificationDetailTable.NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public void saveNewApplicationDetails(ZiploanNewBorrowerDetails details, boolean isFinal) {
        ZiploanSiteInfo siteInfo = details.getSite_info();
        ArrayList<ZiploanNewQuestion> questions = details.getQuestions();
        try {
            ContentValues values = new ContentValues();
            values.put(ApplicationVerificationDetailTable.Cols.LOAN_REQUEST_ID, details.getLoan_request_id());
            values.put(ApplicationVerificationDetailTable.Cols.REFERENCE, new Gson().toJson(siteInfo.getArrReferenceUsers()));
            values.put(ApplicationVerificationDetailTable.Cols.REFERENCE_family, new Gson().toJson(siteInfo.getArrFamilyReferenceUsers()));
            //values.put(ApplicationVerificationDetailTable.Cols.BUSINESS_RESIDENCE_SEPERATE, siteInfo.getBusiness_place_seperate_residence_place());
            values.put(ApplicationVerificationDetailTable.Cols.BUSINESS_PLACE_PHOTO, new Gson().toJson(siteInfo.getBusiness_place_photo_url()));
            values.put(ApplicationVerificationDetailTable.Cols.BUSINESS_LONG_SHOT_PHOTO, new Gson().toJson(siteInfo.getLong_shot_photos()));
            values.put(ApplicationVerificationDetailTable.Cols.BUSINESS_ID_PROOF_PHOTO, new Gson().toJson(siteInfo.getId_proof_photos()));
            values.put(ApplicationVerificationDetailTable.Cols.QUESTIONS, new Gson().toJson(questions));
            values.put(ApplicationVerificationDetailTable.Cols.BANK_INFO, new Gson().toJson(details.getLoan_details()));
            values.put(ApplicationVerificationDetailTable.Cols.STOCK_INVENTORY_AMOUNT, siteInfo.getStock_inventory_amount());
            values.put(ApplicationVerificationDetailTable.Cols.ASSET_MACHINERY_AMOUNT, siteInfo.getFixed_asset_machinery_amount());
            values.put(ApplicationVerificationDetailTable.Cols.EMPLOYEE_NO, siteInfo.getNo_employees());
            values.put(ApplicationVerificationDetailTable.Cols.CUSTOM_ENTITY_TYPE, siteInfo.getPerson_email());
            values.put(ApplicationVerificationDetailTable.Cols.CUSTOM_BUSINESS_ADD_TYPE, "");
            values.put(ApplicationVerificationDetailTable.Cols.CUSTOM_RESIDENCE_ADD_TYPE, "");
            values.put(ApplicationVerificationDetailTable.Cols.LAT, details.getLat());
            values.put(ApplicationVerificationDetailTable.Cols.LNG, details.getLng());
            values.put(ApplicationVerificationDetailTable.Cols.IS_FINAL, isFinal ? "1" : "0");

            values.put(ApplicationVerificationDetailTable.Cols.app_not_installed_reason, siteInfo.getApp_not_installed_reason());
            values.put(ApplicationVerificationDetailTable.Cols.RAW_MATERIAL, siteInfo.getRaw_material());
            values.put(ApplicationVerificationDetailTable.Cols.rent_amount, siteInfo.getRent_amount());
            values.put(ApplicationVerificationDetailTable.Cols.investment_in_business, siteInfo.getInvestment_in_business());
            values.put(ApplicationVerificationDetailTable.Cols.no_of_machines, siteInfo.getNo_of_machines());
            values.put(ApplicationVerificationDetailTable.Cols.actual_no_of_employees, siteInfo.getActual_no_of_employees());
            values.put(ApplicationVerificationDetailTable.Cols.person_met, siteInfo.getPerson_met());
            values.put(ApplicationVerificationDetailTable.Cols.nature_of_work, siteInfo.getNature_of_work());
            values.put(ApplicationVerificationDetailTable.Cols.TEXT_nature_of_work, siteInfo.getText_nature_of_work());
            values.put(ApplicationVerificationDetailTable.Cols.designation_in_office, siteInfo.getDesignation_in_office());
            values.put(ApplicationVerificationDetailTable.Cols.sign_board_observed, siteInfo.getSign_board_observed());
            values.put(ApplicationVerificationDetailTable.Cols.business_category, siteInfo.getBusiness_category());

            values.put(ApplicationVerificationDetailTable.Cols.STABILITY, siteInfo.getBusiness_stability());
            values.put(ApplicationVerificationDetailTable.Cols.PLACE_CHANGE_REASON, siteInfo.getBusiness_place_change_reason());
            values.put(ApplicationVerificationDetailTable.Cols.LANDMARK, siteInfo.getLandmark());
            values.put(ApplicationVerificationDetailTable.Cols.BUSINESS_ADDRESS_DASHBOARD, siteInfo.getBusiness_address_same_as_dashboard());
            values.put(ApplicationVerificationDetailTable.Cols.BUSINESS_ADDRESS_DASHBOARD_REASON, siteInfo.getBusiness_address_same_as_dashboard_reason());
            values.put(ApplicationVerificationDetailTable.Cols.locality_business_place, siteInfo.getLocality_business_place());
            values.put(ApplicationVerificationDetailTable.Cols.education_qualification, siteInfo.getEducation_qualification());


            try {
                App.getDb().insertWithOnConflict(ApplicationVerificationDetailTable.NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteApplicationInfoByLoanRequestId(String loan_request_id) {
        String sql = "DELETE FROM " + ApplicationVerificationDetailTable.NAME + " WHERE " + ApplicationVerificationDetailTable.Cols.LOAN_REQUEST_ID + "= '" + loan_request_id + "'";
        Cursor cursor = App.getDb().rawQuery(sql, null);
        try {
            cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }

    public void deleteApplications() {
        String sql = "DELETE FROM " + ApplicationsTable.NAME;
        Cursor cursor = App.getDb().rawQuery(sql, null);
        try {
            cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }


    public void updateVerificationApplicationStatus(String loanRequestId) {
        String sql = "UPDATE " + ApplicationVerificationDetailTable.NAME + " SET " + ApplicationVerificationDetailTable.Cols.IS_FINAL + "='0'" + " WHERE " + ApplicationVerificationDetailTable.Cols.LOAN_REQUEST_ID + "='" + loanRequestId + "'";
        Cursor cursor = App.getDb().rawQuery(sql, null);
        try {
            cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }


    public ArrayList<BorrowersUnverified> getApplicationList() {
        ArrayList<BorrowersUnverified> arrBorrowers = new ArrayList<>();
        String sql = "SELECT * FROM " + ApplicationsTable.NAME + " ORDER BY " + ApplicationsTable.Cols.LOAN_REQUEST_ASSIGNED_DATE + " DESC";
        Cursor cursor = App.getDb().rawQuery(sql, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    String loan_request_id = cursor.getString(cursor.getColumnIndex(ApplicationsTable.Cols.LOAN_REQUEST_ID));
                    String app_no = cursor.getString(cursor.getColumnIndex(ApplicationsTable.Cols.LOAN_APP_NO));
                    String first_name = cursor.getString(cursor.getColumnIndex(ApplicationsTable.Cols.FIRST_NAME));
                    String last_name = cursor.getString(cursor.getColumnIndex(ApplicationsTable.Cols.LAST_NAME));
                    String business_name = cursor.getString(cursor.getColumnIndex(ApplicationsTable.Cols.BUSINESS_NAME));
                    String businesss_address = cursor.getString(cursor.getColumnIndex(ApplicationsTable.Cols.BUSINESS_ADDRESS));
                    String loan_request_status = cursor.getString(cursor.getColumnIndex(ApplicationsTable.Cols.LOAN_REQUEST_STATUS));
                    String loan_request_status_name = cursor.getString(cursor.getColumnIndex(ApplicationsTable.Cols.LOAN_REQUEST_STATUS_NAME));
                    String loan_request_date = cursor.getString(cursor.getColumnIndex(ApplicationsTable.Cols.LOAN_REQUEST_ASSIGNED_DATE));
                    String mobile = cursor.getString(cursor.getColumnIndex(ApplicationsTable.Cols.MOBILE));
                    String ekyc_status = cursor.getString(cursor.getColumnIndex(ApplicationsTable.Cols.EKYC_STATUS));
                    BorrowersUnverified borrower = new BorrowersUnverified();
                    borrower.setLoan_request_id(loan_request_id);
                    borrower.setLoan_application_number(app_no);
                    borrower.setFirst_name(first_name);
                    borrower.setLast_name(last_name);
                    borrower.setBusiness_name(business_name);
                    borrower.setBusiness_address(businesss_address);
                    borrower.setLoan_request_status(TextUtils.isEmpty(loan_request_status) ? "0" : loan_request_status);
                    borrower.setLoan_request_status_name(loan_request_status_name);
                    borrower.setLoan_request_assigned_date(loan_request_date);
                    borrower.setMobile(mobile);
                    borrower.setEkyc_status(Integer.parseInt(!TextUtils.isEmpty(ekyc_status) ? ekyc_status : "0"));
                    arrBorrowers.add(borrower);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return arrBorrowers;
    }

    public ZiploanBorrowerDetails getBorrowerApplicationDetails(BorrowersUnverified borrower) {
        ZiploanBorrowerDetails details = null;
        String sql = "SELECT * FROM " + ApplicationVerificationDetailTable.NAME + " WHERE " + ApplicationVerificationDetailTable.Cols.LOAN_REQUEST_ID + "=?";
        Cursor cursor = App.getDb().rawQuery(sql, new String[]{borrower.getLoan_request_id()});
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                details = getZiploanBorrowerDetailsFromCursor(cursor);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return details;
    }

    public ArrayList<ZiploanBorrowerDetails> getBorrowerApplicationDetailsListFinalSubmitted() {
        ArrayList<ZiploanBorrowerDetails> arrDetails = new ArrayList<>();
        String sql = "SELECT * FROM " + ApplicationVerificationDetailTable.NAME + " WHERE " + ApplicationVerificationDetailTable.Cols.IS_FINAL + "=?";
        Cursor cursor = App.getDb().rawQuery(sql, new String[]{"1"});
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    arrDetails.add(getZiploanBorrowerDetailsFromCursor(cursor));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return arrDetails;
    }

    public ArrayList<ZiploanNewBorrowerDetails> getNewBorrowerApplicationDetailsListFinalSubmitted() {
        ArrayList<ZiploanNewBorrowerDetails> arrDetails = new ArrayList<>();
        String sql = "SELECT * FROM " + ApplicationVerificationDetailTable.NAME + " WHERE " + ApplicationVerificationDetailTable.Cols.IS_FINAL + "=?";
        Cursor cursor = App.getDb().rawQuery(sql, new String[]{"1"});
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    arrDetails.add(getNewZiploanBorrowerDetailsFromCursor(cursor));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return arrDetails;
    }


    @NonNull
    private ZiploanBorrowerDetails getZiploanBorrowerDetailsFromCursor(Cursor cursor) {

        String loan_request_id = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.LOAN_REQUEST_ID));
        String reference = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.REFERENCE));
        String reference_family = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.REFERENCE_family));
        String bank_info = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.BANK_INFO));
        String business_residence_seperate = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.BUSINESS_RESIDENCE_SEPERATE));
        String business_place_photo = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.BUSINESS_PLACE_PHOTO));
        String business_long_shot_photo = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.BUSINESS_LONG_SHOT_PHOTO));
        String business_id_proof_photo = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.BUSINESS_ID_PROOF_PHOTO));
        String questions = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.QUESTIONS));
        String inventory_amount = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.STOCK_INVENTORY_AMOUNT));
        String machinery_amount = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.ASSET_MACHINERY_AMOUNT));

        String text_nature = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.nature_of_work));
        String text_nature_details = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.TEXT_nature_of_work));
        String text_designation = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.TEXT_DESIGNATION));
        String employee_no = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.EMPLOYEE_NO));
        String lat = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.LAT));
        String lng = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.LNG));
        String isFinal = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.IS_FINAL));
        ZiploanSiteInfo siteInfo = new ZiploanSiteInfo();
        siteInfo.setArrReferenceUsers((ArrayList<ReferenceUser>) new Gson().fromJson(reference,
                new TypeToken<List<ReferenceUser>>() {}.getType()));

        siteInfo.setArrFamilyReferenceUsers((ArrayList<FamilyReferences>) new Gson().fromJson(reference_family,
                new TypeToken<List<FamilyReferences>>() {}.getType()));
        // siteInfo.setBusiness_place_seperate_residence_place(Integer.parseInt(business_residence_seperate));
        siteInfo.setStock_inventory_amount(inventory_amount);
        siteInfo.setFixed_asset_machinery_amount(machinery_amount);
        siteInfo.setText_nature_of_work(text_nature_details);
        siteInfo.setNature_of_work(text_nature);
        siteInfo.setRaw_material(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.RAW_MATERIAL)));
        siteInfo.setRent_amount(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.rent_amount))));
        siteInfo.setInvestment_in_business(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.investment_in_business)));
        siteInfo.setNo_of_machines(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.no_of_machines)));
        siteInfo.setActual_no_of_employees(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.actual_no_of_employees)));
        siteInfo.setPerson_met(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.person_met)));
        siteInfo.setDesignation_in_office(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.designation_in_office)));
        siteInfo.setSign_board_observed(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.sign_board_observed)));
        siteInfo.setApp_not_installed_reason(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.app_not_installed_reason)));
        siteInfo.setBusiness_category(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.business_category)));
        siteInfo.setPerson_email(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.CUSTOM_ENTITY_TYPE)));
        siteInfo.setText_designation(text_designation);

        siteInfo.setBusiness_stability(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.STABILITY)));
        siteInfo.setBusiness_place_change_reason(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.PLACE_CHANGE_REASON)));
        siteInfo.setLandmark(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.LANDMARK)));
        siteInfo.setBusiness_address_same_as_dashboard(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.BUSINESS_ADDRESS_DASHBOARD)));
        siteInfo.setBusiness_address_same_as_dashboard_reason(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.BUSINESS_ADDRESS_DASHBOARD_REASON)));
        siteInfo.setLocality_business_place(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.locality_business_place)));
        siteInfo.setEducation_qualification(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.education_qualification)));

        siteInfo.setNo_employees(Integer.parseInt(employee_no));
        siteInfo.setBusiness_place_photo_url((List<ZiploanPhoto>) new Gson().fromJson(business_place_photo, new TypeToken<List<ZiploanPhoto>>() {
        }.getType()));
        ArrayList<ZiploanQuestion> arrQuestions = new Gson().fromJson(questions, new TypeToken<List<ZiploanQuestion>>() {
        }.getType());
        siteInfo.setLong_shot_photos((List<ZiploanPhoto>) new Gson().fromJson(business_long_shot_photo, new TypeToken<List<ZiploanPhoto>>() {
        }.getType()));
        siteInfo.setId_proof_photos((List<ZiploanPhoto>) new Gson().fromJson(business_id_proof_photo, new TypeToken<List<ZiploanPhoto>>() {
        }.getType()));
        ZiploanBorrowerDetails details = new ZiploanBorrowerDetails();
        details.setLoan_request_id(loan_request_id);
        details.setIs_final(isFinal.equalsIgnoreCase("1") ? true : false);
        details.setSite_info(siteInfo);
        details.setQuestions(arrQuestions);
        details.setLat(lat);
        details.setLng(lng);
        details.setLoan_details((ArrayList<BankInfoModel>) new Gson().fromJson(bank_info, new TypeToken<List<BankInfoModel>>() {
        }.getType()));
        return details;
    }

    @NonNull
    private ZiploanNewBorrowerDetails getNewZiploanBorrowerDetailsFromCursor(Cursor cursor) {

        String loan_request_id = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.LOAN_REQUEST_ID));
        String reference = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.REFERENCE));
        String referenceFamily= cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.REFERENCE_family));
        String bank_info = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.BANK_INFO));
        String business_residence_seperate = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.BUSINESS_RESIDENCE_SEPERATE));
        String business_place_photo = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.BUSINESS_PLACE_PHOTO));
        String business_long_shot_photo = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.BUSINESS_LONG_SHOT_PHOTO));
        String business_id_proof_photo = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.BUSINESS_ID_PROOF_PHOTO));
        String questions = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.QUESTIONS));
        String inventory_amount = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.STOCK_INVENTORY_AMOUNT));
        String machinery_amount = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.ASSET_MACHINERY_AMOUNT));
        String employee_no = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.EMPLOYEE_NO));
        String lat = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.LAT));
        String lng = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.LNG));
        String isFinal = cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.IS_FINAL));
        ZiploanSiteInfo siteInfo = new ZiploanSiteInfo();
        siteInfo.setArrReferenceUsers((ArrayList<ReferenceUser>) new Gson().fromJson(reference, new TypeToken<List<ReferenceUser>>() {
        }.getType()));

        siteInfo.setArrFamilyReferenceUsers((ArrayList<FamilyReferences>) new Gson().fromJson(referenceFamily, new TypeToken<List<FamilyReferences>>() {
        }.getType()));
        //siteInfo.setBusiness_place_seperate_residence_place(Integer.parseInt(business_residence_seperate));
        siteInfo.setStock_inventory_amount(inventory_amount);
        siteInfo.setFixed_asset_machinery_amount(machinery_amount);

        siteInfo.setRaw_material(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.RAW_MATERIAL)));
        siteInfo.setRent_amount(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.rent_amount))));
        siteInfo.setInvestment_in_business(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.investment_in_business)));
        siteInfo.setNo_of_machines(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.no_of_machines)));
        siteInfo.setActual_no_of_employees(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.actual_no_of_employees)));
        siteInfo.setPerson_met(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.person_met)));
        siteInfo.setNature_of_work(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.nature_of_work)));
        siteInfo.setText_nature_of_work(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.TEXT_nature_of_work)));
        siteInfo.setDesignation_in_office(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.designation_in_office)));
        siteInfo.setSign_board_observed(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.sign_board_observed)));
        siteInfo.setApp_not_installed_reason(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.app_not_installed_reason)));
        siteInfo.setBusiness_category(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.business_category)));
        siteInfo.setPerson_email(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.CUSTOM_ENTITY_TYPE)));

        siteInfo.setBusiness_stability(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.STABILITY)));
        siteInfo.setBusiness_place_change_reason(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.PLACE_CHANGE_REASON)));
        siteInfo.setLandmark(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.LANDMARK)));
        siteInfo.setBusiness_address_same_as_dashboard(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.BUSINESS_ADDRESS_DASHBOARD)));
        siteInfo.setBusiness_address_same_as_dashboard_reason(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.BUSINESS_ADDRESS_DASHBOARD_REASON)));
        siteInfo.setLocality_business_place(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.locality_business_place)));
        siteInfo.setEducation_qualification(cursor.getString(cursor.getColumnIndex(ApplicationVerificationDetailTable.Cols.education_qualification)));


        siteInfo.setNo_employees(Integer.parseInt(employee_no));
        siteInfo.setBusiness_place_photo_url((List<ZiploanPhoto>) new Gson().fromJson(business_place_photo, new TypeToken<List<ZiploanPhoto>>() {
        }.getType()));
        siteInfo.setLong_shot_photos((List<ZiploanPhoto>) new Gson().fromJson(business_long_shot_photo, new TypeToken<List<ZiploanPhoto>>() {
        }.getType()));
        siteInfo.setId_proof_photos((List<ZiploanPhoto>) new Gson().fromJson(business_id_proof_photo, new TypeToken<List<ZiploanPhoto>>() {
        }.getType()));
        ArrayList<ZiploanNewQuestion> arrQuestions = new Gson().fromJson(questions, new TypeToken<List<ZiploanNewQuestion>>() {
        }.getType());

        ZiploanNewBorrowerDetails details = new ZiploanNewBorrowerDetails();
        details.setLoan_request_id(loan_request_id);
        details.setIs_final(isFinal.equalsIgnoreCase("1") ? true : false);
        details.setSite_info(siteInfo);
        details.setQuestions(arrQuestions);
        details.setLoan_details((List<BankInfoModel>) new Gson().fromJson(bank_info, new TypeToken<List<BankInfoModel>>() {
        }.getType()));
        details.setLat(lat);
        details.setLng(lng);
        return details;
    }

}