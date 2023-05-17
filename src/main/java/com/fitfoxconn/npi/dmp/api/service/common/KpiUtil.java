package com.fitfoxconn.npi.dmp.api.service.common;

import com.fitfoxconn.npi.dmp.api.common.constant.PrecisionConstant;
import com.fitfoxconn.npi.dmp.api.entity.ods.TjOeeEntity;
import java.math.BigDecimal;
import java.math.RoundingMode;

/** KPI相關Util工具 */
public class KpiUtil {

  /** 計算達成率 */
  public static BigDecimal getAchievementRate(TjOeeEntity tjOee) {
    BigDecimal result = BigDecimal.ZERO;
    if(tjOee.getProductionTarget() > 0) {
      result = BigDecimal.valueOf((double) tjOee.getGoodCount() / tjOee.getProductionTarget())
          .setScale(PrecisionConstant.ACHIEVEMENT_RATE, RoundingMode.HALF_UP);
    }
    return result;
  }

}
