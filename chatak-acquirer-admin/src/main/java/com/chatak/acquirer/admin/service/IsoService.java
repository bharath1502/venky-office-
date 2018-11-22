package com.chatak.acquirer.admin.service;

import java.util.List;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.acq.dao.model.Iso;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.CardProgramResponse;
import com.chatak.pg.user.bean.IsoRequest;
import com.chatak.pg.user.bean.IsoResponse;
import com.chatak.pg.user.bean.ProgramManagerRequest;

public interface IsoService {
	
	public CardProgramResponse fetchCardProgramByPm(Long id);

	public com.chatak.pg.user.bean.Response createIso(IsoRequest isoRequest) throws ChatakAdminException;

	public IsoResponse searchIso(IsoRequest isoRequest) throws ChatakAdminException;

	public IsoResponse getIsoById(IsoRequest isoRequest) throws ChatakAdminException;

	public IsoResponse updateIso(IsoRequest isoRequest) throws ChatakAdminException;

	public Response findISONameByAccountCurrency(String currencyId) throws ChatakAdminException;

	public CardProgramResponse fetchCardProgramByIso(Long id, String currencyId);

	public IsoResponse fetchCardProgramByIso(Long isoId) throws ChatakAdminException;

	public IsoResponse fetchProgramManagerByIsoCurrency(Long isoId, String currency) throws ChatakAdminException;

	public IsoResponse getAllIso(IsoRequest isoRequest) throws ChatakAdminException;

	public List<Long> findByPmId(Long pmId) throws ChatakAdminException;

	public Response findIsoNameByCurrencyAndId(Long id, String currencyId);
	
	public Response findIsoNameByProgramManagerId(Long pmId);

	public IsoResponse changeStatus(IsoRequest isoRequest) throws ChatakAdminException;

	public CardProgramResponse fetchIsoCardProgramByMerchantId(Long merchantId);

	public List<Iso> findIsoByIsoId(Long isoId);

	public List<IsoRequest> findIsoByProgramaManagerId(Long Pmid);

	public List<ProgramManagerRequest> findPmByIsoId(Long isoId);
	
	public Response findIsoNameAndIdByEntityId(Long pmId);
}
