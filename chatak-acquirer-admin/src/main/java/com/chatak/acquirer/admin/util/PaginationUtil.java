/**
 * 
 */
package com.chatak.acquirer.admin.util;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.util.Constants;

/**
 * @author
 */
public final class PaginationUtil {

  private static Logger logger = Logger.getLogger(PaginationUtil.class);

  private PaginationUtil() {

  }

  public static ModelAndView getPagenationModel(ModelAndView modelAndView, int totalRecords) {
    try {

      int MAX_COUNT = Constants.MAX_TRANSACTION_ENTITY_DISPLAY_SIZE;
      if (!StringUtil.isNull(modelAndView.getModel().get(Constants.PAGE_SIZE))) {
        MAX_COUNT = (Integer) modelAndView.getModel().get(Constants.PAGE_SIZE);
      }

      int pageCounter =
          (totalRecords / MAX_COUNT) + ((totalRecords % MAX_COUNT > 0) ? Constants.ONE : 0);

      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_TOTAL_RECORDS_PAGE_NUM, totalRecords);
      modelAndView.addObject(Constants.PORTAL_PAGES_SESSION_NAME, pageCounter);
      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_PAGE_NUMBER, Constants.ONE);
      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_BEGIN_PAGE_NUM, Constants.ONE);

      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_END_PAGE_NUM,
          (pageCounter > Constants.MAX_ENTITY_DISPLAY_SIZE) ? Constants.MAX_ENTITY_DISPLAY_SIZE
              : pageCounter);
    } catch (Exception exp) {
      logger.error("error:method1::PaginationUtil::getPaginationList", exp);
    }

    return modelAndView;
  }

  public static ModelAndView getPagenationModelSuccessive(ModelAndView modelAndView,
      int currentPage, int totalRecords) {
    try {
      int MAX_COUNT = Constants.MAX_TRANSACTION_ENTITY_DISPLAY_SIZE;
      if (!StringUtil.isNull(modelAndView.getModel().get(Constants.PAGE_SIZE))) {
        MAX_COUNT = (Integer) modelAndView.getModel().get(Constants.PAGE_SIZE);
      }
      int pageCounter =
          (totalRecords / MAX_COUNT) + ((totalRecords % MAX_COUNT > 0) ? Constants.ONE : 0);

      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_TOTAL_RECORDS_PAGE_NUM, totalRecords);
      modelAndView.addObject(Constants.PORTAL_PAGES_SESSION_NAME, pageCounter);
      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_PAGE_NUMBER, currentPage);
      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_BEGIN_PAGE_NUM, Constants.ONE);

      int end = Constants.MAX_ENTITY_DISPLAY_SIZE;
      int begin = Constants.ONE;

      if (currentPage > Constants.MAX_ENTITY_DISPLAY_SIZE) {
        begin = currentPage - (Constants.FOUR + Constants.ONE);
        end = currentPage + Constants.FOUR;

        if (pageCounter < end) {
          end = pageCounter;
          begin = end - Constants.MAX_ENTITY_DISPLAY_SIZE + Constants.ONE;
        }

        begin = (begin + Constants.MAX_ENTITY_DISPLAY_SIZE > pageCounter)
            ? pageCounter - (Constants.MAX_ENTITY_DISPLAY_SIZE - Constants.ONE) : begin;
      } else {
        end = (pageCounter < end) ? pageCounter : end;
      }

      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_BEGIN_PAGE_NUM, begin);
      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_END_PAGE_NUM, end);
    } catch (Exception e) {
      logger.error("error:method1::PaginationUtil::getPagenationModelSuccessive", e);
    }

    return modelAndView;
  }

  public static int getMaxDisplayCount() {
    return Constants.MAX_TRANSACTION_ENTITY_DISPLAY_SIZE;
  }

  public static ModelAndView getPagenationModelSuccessive(ModelAndView modelAndView,
      int currentPage, int totalRecords, int maxPageSize) {
    logger.info("Entering::PrepaidAdminPaginationUtil::getPagenationModelSuccessive method");
    try {
      final int MAX_COUNT = maxPageSize;
      int pageCounter =
          (totalRecords / MAX_COUNT) + ((totalRecords % MAX_COUNT > 0) ? Constants.ONE : 0);

      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_TOTAL_RECORDS_PAGE_NUM, totalRecords);
      modelAndView.addObject(Constants.PORTAL_PAGES_SESSION_NAME, pageCounter);
      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_PAGE_NUMBER, currentPage);
      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_BEGIN_PAGE_NUM, Constants.ONE);

      int begin = Constants.ONE;
      int end = Constants.MAX_ENTITIES_PAGINATION_DISPLAY_SIZE;

      if (currentPage <= pageCounter && currentPage > (Constants.FIVE)) {
        begin = currentPage - (Constants.FOUR + Constants.ONE);
        end = currentPage + Constants.FOUR;

        if (end < Constants.MAX_ENTITIES_PAGINATION_DISPLAY_SIZE) {
          end = pageCounter;
        } else if (end > pageCounter) {
          begin = end - Constants.MAX_ENTITIES_PAGINATION_DISPLAY_SIZE + Constants.ONE;
          end = pageCounter;
        }
        if (!(end <= Constants.MAX_ENTITIES_PAGINATION_DISPLAY_SIZE)) {
          begin = (begin + Constants.MAX_ENTITIES_PAGINATION_DISPLAY_SIZE > pageCounter)
              ? pageCounter - (Constants.MAX_ENTITIES_PAGINATION_DISPLAY_SIZE - Constants.ONE)
              : begin;
        }
      } else {
        end = (end > pageCounter) ? pageCounter : end;
      }

      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_BEGIN_PAGE_NUM, begin);
      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_END_PAGE_NUM, end);
    } catch (Exception e) {
      logger.error("error:method1::PrepaidAdminPaginationUtil::getPagenationModelSuccessive", e);
    }

    return modelAndView;
  }
  
  public static ModelAndView getPagenationModel(ModelAndView modelAndView, Integer totalRecords,
      int maxPageSize) {
    logger.info("Entering::PrepaidAdminPaginationUtil::getPagenationModel method");
    try {
      if (totalRecords == null || Constants.ZERO.equals(totalRecords)) {
        modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_TOTAL_RECORDS_PAGE_NUM,
            Constants.ZERO);
      } else {

        final int MAX_COUNT = maxPageSize;
        int pageCounter =
            (totalRecords / MAX_COUNT) + ((totalRecords % MAX_COUNT > 0) ? Constants.ONE : 0);

        modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_TOTAL_RECORDS_PAGE_NUM,
            totalRecords);
        modelAndView.addObject(Constants.PORTAL_PAGES_SESSION_NAME, pageCounter);
        modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_PAGE_NUMBER, Constants.ONE);
        modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_BEGIN_PAGE_NUM, Constants.ONE);

        modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_END_PAGE_NUM,
            (pageCounter > Constants.MAX_ENTITIES_PAGINATION_DISPLAY_SIZE)
                ? Constants.MAX_ENTITIES_PAGINATION_DISPLAY_SIZE : pageCounter);
      }

    } catch (Exception e) {
      logger.error("error:method1::PrepaidAdminPaginationUtil::getPaginationList", e);
    }

    return modelAndView;
  }
}
