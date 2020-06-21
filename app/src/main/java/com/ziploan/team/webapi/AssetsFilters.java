package com.ziploan.team.webapi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by ZIploan-Nitesh on 8/21/2017.
 */

public class AssetsFilters implements Serializable{
    @SerializedName("portfolio_types")
    private HashMap<String,String> portfolio_types;
    @SerializedName("risk_categories")
    private HashMap<String,String> risk_categories;
    @SerializedName("business_categories")
    private HashMap<String,String> business_categories;
    @SerializedName("delinquency_buckets")
    private HashMap<String,String> delinquency_buckets;
    @SerializedName("vintages")
    private HashMap<String,String> vintages;
    @SerializedName("sourcing_channels")
    private HashMap<String,String> sourcing_channels;
    @SerializedName("direct_sourcing_types")
    private HashMap<String,String> direct_sourcing_types;
    @SerializedName("partners")
    private HashMap<String,String> partners;
    @SerializedName("asset_managers")
    private HashMap<String,String> asset_managers;
    @SerializedName("cities")
    private HashMap<String,String> cities;
    @SerializedName("loan_tenors")
    private HashMap<String,String> loan_tenors;
    @SerializedName("interest_rates")
    private HashMap<String,String> interest_rates;
    @SerializedName("months_remaining")
    private HashMap<String,String> months_remaining;
    @SerializedName("entities")
    private HashMap<String,String> entities;
    @SerializedName("ews_types")
    private HashMap<String,String> ews_types;
    @SerializedName("last_visits")
    private HashMap<String,String> last_visits;
    @SerializedName("loan_amounts")
    private HashMap<String,String> loan_amounts;
    @SerializedName("gross_ITR_amount")
    private HashMap<String,String> gross_ITR_amount;

    public HashMap<String, String> getPortfolio_types() {
        return portfolio_types;
    }

    public void setPortfolio_types(HashMap<String, String> portfolio_types) {
        this.portfolio_types = portfolio_types;
    }

    public HashMap<String, String> getRisk_categories() {
        return risk_categories;
    }

    public void setRisk_categories(HashMap<String, String> risk_categories) {
        this.risk_categories = risk_categories;
    }

    public HashMap<String, String> getBusiness_categories() {
        return business_categories;
    }

    public void setBusiness_categories(HashMap<String, String> business_categories) {
        this.business_categories = business_categories;
    }

    public HashMap<String, String> getDelinquency_buckets() {
        return delinquency_buckets;
    }

    public void setDelinquency_buckets(HashMap<String, String> delinquency_buckets) {
        this.delinquency_buckets = delinquency_buckets;
    }

    public HashMap<String, String> getVintages() {
        return vintages;
    }

    public void setVintages(HashMap<String, String> vintages) {
        this.vintages = vintages;
    }

    public HashMap<String, String> getSourcing_channels() {
        return sourcing_channels;
    }

    public void setSourcing_channels(HashMap<String, String> sourcing_channels) {
        this.sourcing_channels = sourcing_channels;
    }

    public HashMap<String, String> getDirect_sourcing_types() {
        return direct_sourcing_types;
    }

    public void setDirect_sourcing_types(HashMap<String, String> direct_sourcing_types) {
        this.direct_sourcing_types = direct_sourcing_types;
    }

    public HashMap<String, String> getPartners() {
        return partners;
    }

    public void setPartners(HashMap<String, String> partners) {
        this.partners = partners;
    }

    public HashMap<String, String> getAsset_managers() {
        return asset_managers;
    }

    public void setAsset_managers(HashMap<String, String> asset_managers) {
        this.asset_managers = asset_managers;
    }

    public HashMap<String, String> getCities() {
        return cities;
    }

    public void setCities(HashMap<String, String> cities) {
        this.cities = cities;
    }

    public HashMap<String, String> getLoan_tenors() {
        return loan_tenors;
    }

    public void setLoan_tenors(HashMap<String, String> loan_tenors) {
        this.loan_tenors = loan_tenors;
    }

    public HashMap<String, String> getInterest_rates() {
        return interest_rates;
    }

    public void setInterest_rates(HashMap<String, String> interest_rates) {
        this.interest_rates = interest_rates;
    }

    public HashMap<String, String> getMonths_remaining() {
        return months_remaining;
    }

    public void setMonths_remaining(HashMap<String, String> months_remaining) {
        this.months_remaining = months_remaining;
    }

    public HashMap<String, String> getEntities() {
        return entities;
    }

    public void setEntities(HashMap<String, String> entities) {
        this.entities = entities;
    }

    public HashMap<String, String> getEws_types() {
        return ews_types;
    }

    public void setEws_types(HashMap<String, String> ews_types) {
        this.ews_types = ews_types;
    }

    public HashMap<String, String> getLast_visits() {
        return last_visits;
    }

    public void setLast_visits(HashMap<String, String> last_visits) {
        this.last_visits = last_visits;
    }

    public HashMap<String, String> getLoan_amounts() {
        return loan_amounts;
    }

    public void setLoan_amounts(HashMap<String, String> loan_amounts) {
        this.loan_amounts = loan_amounts;
    }

    public HashMap<String, String> getGross_ITR_amount() {
        return gross_ITR_amount;
    }

    public void setGross_ITR_amount(HashMap<String, String> gross_ITR_amount) {
        this.gross_ITR_amount = gross_ITR_amount;
    }
}
