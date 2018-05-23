package com.chatak.pg.constants;

import com.chatak.pg.util.Properties;

public interface FeatureConstants {
  
  public static final String VIEW_SUB_MERCHANT_TRANSACTIONS=Properties.getProperty("view.submerchant.transactions.id");
  public static final String PROCESS_SUB_MERCHANT_TRANSACTIONS=Properties.getProperty("process.submerchant.transactions.id");
  public static final String EXECUTE_SUB_MERCHANT_TRANSACTIONS=Properties.getProperty("execute.submerchant.transactions.id");
  public static final String REFUND_SUB_MERCHANT_TRANSACTIONS=Properties.getProperty("refund.submerchant.transactions.id");
  public static final String VOID_SUB_MERCHANT_TRANSACTIONS=Properties.getProperty("void.submerchant.transactions.id");
  public static final String MERCHANT_SERVICE_REPORTS_TRANSACTION_FEATURE_ID  = Properties.getProperty("merchant.services.transaction.feature.id");
  
}
