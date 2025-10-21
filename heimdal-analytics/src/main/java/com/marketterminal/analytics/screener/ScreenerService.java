package com.marketterminal.analytics.screener;

import com.marketterminal.analytics.indicator.TechnicalIndicatorService;
import com.marketterminal.data.model.EodData;
import com.marketterminal.data.model.Stock;
import com.marketterminal.data.repository.EodDataRepository;
import com.marketterminal.data.repository.StockRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BarSeries;

/**
 * Service for screening stocks based on various criteria.
 */
public class ScreenerService {
  private static final Logger logger = LoggerFactory.getLogger(ScreenerService.class);
  private final StockRepository stockRepository;
  private final EodDataRepository eodDataRepository;
  private final TechnicalIndicatorService indicatorService;

  public ScreenerService(
      StockRepository stockRepository,
      EodDataRepository eodDataRepository,
      TechnicalIndicatorService indicatorService) {
    this.stockRepository = stockRepository;
    this.eodDataRepository = eodDataRepository;
    this.indicatorService = indicatorService;
  }

  /**
   * Screens stocks based on provided criteria.
   *
   * @param criteria List of screening criteria
   * @return List of stocks matching the criteria
   */
  public List<ScreenerResult> screenStocks(List<ScreenerCriterion> criteria) {
    List<ScreenerResult> results = new ArrayList<>();

    try {
      List<Stock> activeStocks = stockRepository.findAllActive();
      LocalDate endDate = LocalDate.now();
      LocalDate startDate = endDate.minusDays(100);

      for (Stock stock : activeStocks) {
        try {
          List<EodData> eodDataList =
              eodDataRepository.findBySymbolAndDateRange(stock.getSymbol(), startDate, endDate);

          if (eodDataList.size() < 50) {
            continue;
          }

          ScreenerResult result = computeMetrics(stock, eodDataList);

          if (matchesCriteria(result, criteria)) {
            results.add(result);
          }
        } catch (Exception e) {
          logger.warn("Failed to screen stock {}", stock.getSymbol(), e);
        }
      }
    } catch (SQLException e) {
      logger.error("Failed to retrieve stocks for screening", e);
    }

    logger.info("Screened stocks: {} results out of active stocks", results.size());
    return results;
  }

  private ScreenerResult computeMetrics(Stock stock, List<EodData> eodDataList) {
    ScreenerResult result = new ScreenerResult(stock);

    BarSeries series = indicatorService.createBarSeries(stock.getSymbol(), eodDataList);

    EodData lastEod = eodDataList.get(eodDataList.size() - 1);
    result.setLastClose(lastEod.getClose());
    result.setLastVolume(lastEod.getVolume());

    if (eodDataList.size() >= 20) {
      BigDecimal[] sma20 = indicatorService.calculateSMA(series, 20);
      result.setSma20(sma20[sma20.length - 1]);
    }

    if (eodDataList.size() >= 50) {
      BigDecimal[] sma50 = indicatorService.calculateSMA(series, 50);
      result.setSma50(sma50[sma50.length - 1]);
    }

    if (eodDataList.size() >= 14) {
      BigDecimal[] rsi = indicatorService.calculateRSI(series, 14);
      result.setRsi(rsi[rsi.length - 1]);
    }

    if (eodDataList.size() >= 2) {
      EodData previousEod = eodDataList.get(eodDataList.size() - 2);
      BigDecimal change = lastEod.getClose().subtract(previousEod.getClose());
      BigDecimal changePercent =
          change.divide(previousEod.getClose(), 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
      result.setChangePercent(changePercent);
    }

    return result;
  }

  private boolean matchesCriteria(ScreenerResult result, List<ScreenerCriterion> criteria) {
    for (ScreenerCriterion criterion : criteria) {
      BigDecimal fieldValue = getFieldValue(result, criterion.getField());

      if (fieldValue == null) {
        return false;
      }

      if (!evaluateCriterion(fieldValue, criterion)) {
        return false;
      }
    }
    return true;
  }

  private BigDecimal getFieldValue(ScreenerResult result, String field) {
    return switch (field.toLowerCase()) {
      case "price", "close" -> result.getLastClose();
      case "volume" -> result.getLastVolume() != null ? new BigDecimal(result.getLastVolume()) : null;
      case "rsi" -> result.getRsi();
      case "sma20" -> result.getSma20();
      case "sma50" -> result.getSma50();
      case "change_percent" -> result.getChangePercent();
      default -> null;
    };
  }

  private boolean evaluateCriterion(BigDecimal value, ScreenerCriterion criterion) {
    int comparison = value.compareTo(criterion.getValue());

    return switch (criterion.getOperator()) {
      case GREATER_THAN -> comparison > 0;
      case LESS_THAN -> comparison < 0;
      case GREATER_THAN_OR_EQUAL -> comparison >= 0;
      case LESS_THAN_OR_EQUAL -> comparison <= 0;
      case EQUAL -> comparison == 0;
    };
  }
}
