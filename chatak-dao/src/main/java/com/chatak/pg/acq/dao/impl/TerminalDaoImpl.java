package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import com.chatak.pg.acq.dao.TerminalDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGTerminal;
import com.chatak.pg.acq.dao.model.QPGTerminal;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.acq.dao.repository.TerminalRepository;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.user.bean.AddTerminalRequest;
import com.chatak.pg.user.bean.AddTerminalResponse;
import com.chatak.pg.user.bean.DeleteTerminalRequest;
import com.chatak.pg.user.bean.DeleteTerminalResponse;
import com.chatak.pg.user.bean.GetTerminalListRequest;
import com.chatak.pg.user.bean.GetTerminalListResponse;
import com.chatak.pg.user.bean.UpdateTerminalRequest;
import com.chatak.pg.user.bean.UpdateTerminalResponse;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 08-Dec-2014 3:17:58 pm
 * @version 1.0
 */
@Repository("terminalDao")
public class TerminalDaoImpl implements TerminalDao {

  private Logger logger = Logger.getLogger(TerminalDaoImpl.class);

  @Autowired
  private TerminalRepository terminalRepository;

  @Autowired
  private MerchantRepository merchantRepository;

  @PersistenceContext
  private EntityManager entityManager;

  /**
   * Method to Validate merchant and terminal Id of a request
   * 
   * @param merchantId
   * @param terminalid
   * @return
   * @throws DataAccessException
   */
  @Override
  public boolean validateTermial(Long inputMerchantId, String inputTerminalId) {

    logger.info("TerminalDaoImpl | validateTermial | Entering");

    boolean flag = false;

    List<PGTerminal> terminals = terminalRepository
        .findByTerminalIdAndMerchantId(Long.valueOf(inputTerminalId), inputMerchantId);

    if (null != terminals && !terminals.isEmpty()) {
      flag = true;
    }
    logger.info("TerminalDaoImpl | validateTermial | Exiting");

    return flag;
  }

  /**
   * Method to check given terminal id is active
   * 
   * @param merchantId
   * @param terminalid
   * @return
   */
  @Override
  public boolean isActiveTermial(Long merchantId, String terminalid) {
    boolean response = false;
    List<PGTerminal> terminals =
        terminalRepository.findByTerminalIdAndMerchantId(Long.valueOf(terminalid), merchantId);
    if (null != terminals && !terminals.isEmpty()) {
      response = PGConstants.STATUS_SUCCESS.equals(terminals.get(0).getStatus());
    }
    return response;
  }

  /**
   * Method to add new terminal
   * 
   * @param terminal
   * @return
   * @throws DataAccessException
   */
  @Override
  public AddTerminalResponse addTerminal(AddTerminalRequest terminalRequest) {
    logger.info("TerminalDaoImpl | addTerminal | Entering");
    AddTerminalResponse terminalResponse = new AddTerminalResponse();
    try {
      PGMerchant pgMerchant = merchantRepository.findById(terminalRequest.getMerchantId());
      if (pgMerchant == null) {
        terminalResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        terminalResponse.setErrorMessage(PGConstants.INVALID_MERCHANT_ID);
        return terminalResponse;
      }

      PGTerminal terminal = new PGTerminal();

      Long terminalId = generateTerminalId();
      terminal.setCreatedBy(terminalRequest.getCreatedBy());
      terminal.setCreatedDate(DateUtil.getCurrentTimestamp());
      terminal.setDescription(
          terminalRequest.getDescription() != null ? terminalRequest.getDescription() : null);
      terminal.setMerchantId(terminalRequest.getMerchantId());
      terminal.setPrice(terminalRequest.getPrice());
      terminal.setProductId(terminalRequest.getProductId());
      terminal.setStatus(PGConstants.STATUS_SUCCESS);
      terminal.setTerminalId(terminalId);
      terminal = terminalRepository.save(terminal);
      terminalResponse.setTerminal_id(terminal.getTerminalId());
      terminalResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      terminalResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
    } catch (Exception e) {
      logger.error("TerminalDaoImpl | addTerminal | Exception" + e);
      terminalResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      terminalResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }
    logger.info("TerminalDaoImpl | addTerminal | Exiting");
    return terminalResponse;
  }

  private Long generateTerminalId() {
    String terminalId = CommonUtil.generateRandNumeric(PGConstants.LENGTH_TERMINAL_ID);

    PGTerminal response = getTerminal(Long.valueOf(terminalId));

    if (response != null) {
      return generateTerminalId();
    }
    return Long.valueOf(terminalId);

  }

  /**
   * Method to add new terminal
   * 
   * @param terminal
   * @return
   * @throws DataAccessException
   */
  @Override
  public UpdateTerminalResponse updateTerminal(UpdateTerminalRequest terminalRequest) {
    logger.info("TerminalDaoImpl | updateTerminal | Entering");
    UpdateTerminalResponse terminalResponse = new UpdateTerminalResponse();
    try {
      PGMerchant pgMerchant = merchantRepository.findById(terminalRequest.getMerchantId());
      if (pgMerchant == null) {
        terminalResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        terminalResponse.setErrorMessage(PGConstants.INVALID_MERCHANT_ID);
        return terminalResponse;
      }

      PGTerminal pgTerminal = terminalRepository.findByTerminalId(terminalRequest.getTerminalId());
      if (null != pgTerminal) {
        Timestamp currentDate = DateUtil.getCurrentTimestamp();

        pgTerminal.setCreatedDate(pgTerminal.getCreatedDate());
        pgTerminal.setDescription(terminalRequest.getDescription());
        pgTerminal.setMerchantId(terminalRequest.getMerchantId());
        pgTerminal.setPrice(terminalRequest.getPrice());
        pgTerminal.setProductId(terminalRequest.getProductId());
        pgTerminal.setStatus(terminalRequest.getStatus());
        pgTerminal.setUpdatedBy(terminalRequest.getUpdatedBy());
        pgTerminal.setUpdatedDate(currentDate);

        terminalRepository.save(pgTerminal);
        terminalResponse.setTerminal_id(pgTerminal.getTerminalId());
        terminalResponse.setUpdated(true);
        terminalResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        terminalResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      } else {
        terminalResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        terminalResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
      }
    } catch (Exception e) {
      logger.error("TerminalDaoImpl | updateTerminal | Exception" + e);
      terminalResponse.setUpdated(false);
      terminalResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      terminalResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }
    logger.info("TerminalDaoImpl | updateTerminal | Exiting");
    return terminalResponse;
  }

  @Override
  public DeleteTerminalResponse deleteTerminal(DeleteTerminalRequest deleteTerminalRequest) {
    DeleteTerminalResponse deleteTerminalResponse = new DeleteTerminalResponse();
    logger.info("TerminalDaoImpl | updateTerminal | Entering");
    try {
      PGTerminal terminalDb =
          terminalRepository.findByTerminalId(deleteTerminalRequest.getTerminal_id());
      if (null != terminalDb) {
        terminalDb.setStatus(PGConstants.STATUS_DELETED);
        terminalRepository.save(terminalDb);
        deleteTerminalResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        deleteTerminalResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      } else {
        deleteTerminalResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        deleteTerminalResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
      }
    } catch (Exception e) {
      logger.error("ERROR:: TerminalDaoImpl:: deleteTerminal method", e);
      deleteTerminalResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      deleteTerminalResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }
    logger.info("TerminalDaoImpl | updateTerminal | Exiting");
    return deleteTerminalResponse;
  }

  @Override
  public boolean changeTerminalStatus(PGTerminal terminal, Integer status) {
    boolean response = false;
    logger.info("TerminalDaoImpl | changeTerminalStatus | Entering");
    List<PGTerminal> terminalDBs = terminalRepository
        .findByTerminalIdAndMerchantId(terminal.getTerminalId(), terminal.getMerchantId());
    if (null != terminalDBs && !terminalDBs.isEmpty()) {
      PGTerminal terminalDB = terminalDBs.get(0);
      terminalDB.setStatus(status);
      terminalRepository.save(terminalDB);
      response = true;
    }
    logger.info("TerminalDaoImpl | changeTerminalStatus | Exiting");
    return response;
  }

  @Override
  public PGTerminal getTerminal(Long terminalId) {
    return terminalRepository.findByTerminalId(terminalId);
  }

  @Override
  public PGTerminal getTerminalonMerchantCode(Long merchantId) {
    return terminalRepository.findByMerchantId(merchantId);
  }

  @Override
  public GetTerminalListResponse getTerminals(GetTerminalListRequest searchTerminalRequest) {
    GetTerminalListResponse response = new GetTerminalListResponse();
    List<PGTerminal> terminalList = null;
    try {
      int offset = 0;
      int limit = 0;
      Integer totalRecords = searchTerminalRequest.getNoOfRecords();
      if (searchTerminalRequest.getPageIndex() == null
          || searchTerminalRequest.getPageIndex() == 1) {
        totalRecords = getTotalNumberOfRecords(searchTerminalRequest);
        searchTerminalRequest.setNoOfRecords(totalRecords);
      }
      response.setNoOfRecords(totalRecords);
      if (searchTerminalRequest.getPageIndex() == null
          && searchTerminalRequest.getPageSize() == null) {
        offset = 0;
        limit = Constants.DEFAULT_PAGE_SIZE;
      } else {
        offset = (searchTerminalRequest.getPageIndex() - 1) * searchTerminalRequest.getPageSize();
        limit = searchTerminalRequest.getPageSize();
      }

      JPAQuery query = new JPAQuery(entityManager);
      List<Tuple> tupleList = query.from(QPGTerminal.pGTerminal)
          .where(isTerminalCodeEq(searchTerminalRequest.getTerminalId()),
              isStatusEq(searchTerminalRequest.getStatus()))
          .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
          .list(QPGTerminal.pGTerminal.terminalId, QPGTerminal.pGTerminal.status,
              QPGTerminal.pGTerminal.id, QPGTerminal.pGTerminal.merchantId);
      if (!CollectionUtils.isEmpty(tupleList)) {
        terminalList = new ArrayList<>();
        PGTerminal terminal = null;
        for (Tuple tuple : tupleList) {
          terminal = new PGTerminal();
          terminal.setId(tuple.get(QPGTerminal.pGTerminal.id));
          terminal.setTerminalId(tuple.get(QPGTerminal.pGTerminal.terminalId));
          terminal.setMerchantId(tuple.get(QPGTerminal.pGTerminal.merchantId));
          terminal.setStatus(tuple.get(QPGTerminal.pGTerminal.status));
          terminalList.add(terminal);
        }
      }
      if (terminalList != null && !terminalList.isEmpty()) {
        response.setTerminalList(terminalList);
        response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        response.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      } else {
        response.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        response.setErrorMessage(PGConstants.NO_RECORDS_FOUND);
      }
    } catch (Exception e) {
      logger.error("MerchantDaoImpl | getMerchantlist | Exception " + e);
      response.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      response
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }
    return response;
  }

  private int getTotalNumberOfRecords(GetTerminalListRequest searchTerminalRequest) {

    JPAQuery query = new JPAQuery(entityManager);
    List<Long> list = query.from(QPGTerminal.pGTerminal)
        .where(isTerminalCodeEq(searchTerminalRequest.getTerminalId()),
            isStatusEq(searchTerminalRequest.getStatus()))
        .list(QPGTerminal.pGTerminal.id);
    return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
  }

  private BooleanExpression isTerminalCodeEq(Long terminalId) {
    return terminalId != null
        ? QPGTerminal.pGTerminal.terminalId.like("%" + terminalId + "%") : null;
  }

  private BooleanExpression isStatusEq(Integer status) {
    return (status != null && !"-1".equals(status.toString()))
        ? QPGTerminal.pGTerminal.status.eq(status) : null;
  }

  private OrderSpecifier<Timestamp> orderByCreatedDateDesc() {
    return QPGTerminal.pGTerminal.createdDate.desc();
  }

  /**
   * @param terminalId
   * @return
   * @throws DataAccessException
   */
  @Override
  public List<Map<String, String>> getMerchantId(Long terminalId) {

    PGTerminal pgTerminal = terminalRepository.findByTerminalId(terminalId);
    Long merchantId = pgTerminal.getMerchantId();
    return merchantRepository.getMerchantMapByMerchantId(merchantId);
  }

}
