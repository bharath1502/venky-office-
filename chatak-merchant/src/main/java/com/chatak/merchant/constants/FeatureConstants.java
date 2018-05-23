package com.chatak.merchant.constants;

import com.chatak.pg.util.Properties;

public interface FeatureConstants {
	
	public static final String MERCHANT_SERVICE_DASHBOARD_FEATURE_ID =Properties.getProperty("merchant.service.dashboard.feature.id");
	
	public static final String MERCHANT_SERVICE_SUB_MERCHANT_FEATURE_ID = Properties.getProperty("merchant.services.subMerchant.feature.id");
	
	public static final String MERCHANT_SERVICE_SUB_MERCHANT_CREATE_FEATURE_ID = Properties.getProperty("merchant.services.subMerchant.create.feature.id");
	
	public static final String MERCHANT_SERVICE_SUB_MERCHANT_VIEW_FEATURE_ID = Properties.getProperty("merchant.services.subMerchant.view.feature.id");
	
	public static final String MERCHANT_SERVICE_SUB_MERCHANT_DELETE_FEATURE_ID = Properties.getProperty("merchant.services.subMerchant.delete.feature.id");
	
	public static final String MERCHANT_SERVICE_VIRTUAL_FEATURE_ID =Properties.getProperty("merchant.services.virtualTerminal.feature.id");
	
	public static final String MERCHANT_SERVICE_FRAUD_FEATURE_ID =Properties.getProperty("merchant.services.fraud.feature.id");
	
	public static final String MERCHANT_SERVICE_FRAUD_BASIC_FEATURE_ID =Properties.getProperty("merchant.services.fraud.basic.feature.id");
	
	public static final String MERCHANT_SERVICE_FRAUD_ADVANCED_FEATURE_ID = Properties.getProperty("merchant.services.fraud.advanced.feature.id");
	
	public static final String MERCHANT_SERVICE_RECURRING_FEATURE_ID = Properties.getProperty("merchant.services.recurring.feature.id");
	
	public static final String MERCHANT_SERVICE_RECURRING_CREATE_FEATURE_ID = Properties.getProperty("merchant.services.recurring.create.feature.id");
	
	public static final String MERCHANT_SERVICE_RECURRING_EDIT_FEATURE_ID = Properties.getProperty("merchant.services.recurring.edit.feature.id");
	
	public static final String MERCHANT_SERVICE_RECURRING_DELETE_FEATURE_ID = Properties.getProperty("merchant.services.recurring.delete.feature.id");
	
	public static final String MERCHANT_SERVICE_TRANSACTION_FEATURE_ID = Properties.getProperty("merchant.services.transaction.feature.id");
	
	public static final String MERCHANT_SERVICE_TRANSFER_FEATURE_ID = Properties.getProperty("merchant.services.transfer.feature.id");
	
	public static final String MERCHANT_SERVICE_TRANSFER_TO_BANK_FEATURE_ID = Properties.getProperty("merchant.services.transfer.toBankAccount.feature.id");
	   
	public static final String MERCHANT_SERVICE_TRANSFER_BY_CHECK_FEATURE_ID = Properties.getProperty("merchant.services.transfer.byCheck.feature.id");
	   
    public static final String MERCHANT_SERVICE_REPORTS_FEATURE_ID = Properties.getProperty("merchant.services.reports.feature.id");
	   
	public static final String MERCHANT_SERVICE_REPORTS_EFT_FEATURE_ID = Properties.getProperty("merchant.services.reports.eftTransfer.feature.id");
	   
	public static final String MERCHANT_SERVICE_REPORTS_TRANSACTION_FEATURE_ID  = Properties.getProperty("merchant.services.reports.merchant.transaction.revenue.feature.id");
	   
	public static final String MERCHANT_SERVICE_ADJUSTMENTS_FEATURE_ID = Properties.getProperty("merchant.services.adjustments.feature.id");
	
	public static final String MERCHANT_SERVICE_ADJUSTMENTS_ACCOUNT_FEATURE_ID = Properties.getProperty("merchant.services.adjustments.account.transfer.feature.id");

	public static final String MERCHANT_SERVICE_FUNDING_REPORT_FEATURE_ID =Properties.getProperty("merchant.services.funding.report.feature.id");

    public static final String MERCHANT_SERVICE_SCHEDULAR_REPORT_FEATURE_ID =Properties.getProperty("merchant.services.schedule.report.feature.id");
    
    public static final String MERCHANT_SERVICE_BATCH_REPORT_FEATURE_ID =Properties.getProperty("merchant.services.batchReport.feature.id");
}
