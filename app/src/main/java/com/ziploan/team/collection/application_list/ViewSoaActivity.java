package com.ziploan.team.collection.application_list;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ziploan.team.R;
import com.ziploan.team.verification_module.base.BaseActivity;

public class ViewSoaActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.view_soalayout;
    }

    public static void start(Activity mContext, Bundle bundle) {
        Intent intent = new Intent(mContext, ViewSoaActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.view_soa_documents));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {

    }
}
