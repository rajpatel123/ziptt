package com.ziploan.team.verification_module.services;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.ziploan.team.collection.service.GetBankListJob;
import com.ziploan.team.collection.service.PostRecordVisitJob;

/**
 * Created by ZIploan-Nitesh on 2/24/2017.
 */
public class ZiploanTeamJobCreator implements JobCreator {

    @Override
    public Job create(String tag) {
        switch (tag) {
            case PostApplicationDataJob.TAG_TASK_ONEOFF_LOG:
                return new PostApplicationDataJob();
            case PostRecordVisitJob.RECOR_TAG:
                return new PostRecordVisitJob();
            case SyncFiltersJob.TAG_SYNC_FILTERS:
                return new SyncFiltersJob();
            case GetBankListJob.TAG:
                return new GetBankListJob();
            default:
                return null;
        }
    }
}
