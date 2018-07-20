package com.chatak.pay.service;

import com.chatak.pg.acq.dao.model.PGIssSettlementData;
import com.chatak.pg.model.SettlementTxnResponse;

public interface SettlementTxnService {

	public SettlementTxnResponse saveIssSettlementData(PGIssSettlementData issSettlementData);
}
