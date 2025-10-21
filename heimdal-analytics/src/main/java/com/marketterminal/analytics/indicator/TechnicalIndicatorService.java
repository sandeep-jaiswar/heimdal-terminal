package com.marketterminal.analytics.indicator;

import com.marketterminal.data.model.EodData;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.indicators.*;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.num.DecimalNum;

/**
 * Service for calculating technical indicators using TA4J.
 */
public class TechnicalIndicatorService {

  /**
   * Converts EOD data list to TA4J BarSeries.
   *
   * @param symbol Stock symbol
   * @param eodDataList List of EOD data
   * @return BarSeries for TA4J
   */
  public BarSeries createBarSeries(String symbol, List<EodData> eodDataList) {
    BarSeries series = new BaseBarSeriesBuilder().withName(symbol).build();

    for (EodData eod : eodDataList) {
      series.addBar(
          eod.getDate().atStartOfDay().atZone(java.time.ZoneId.systemDefault()),
          DecimalNum.valueOf(eod.getOpen()),
          DecimalNum.valueOf(eod.getHigh()),
          DecimalNum.valueOf(eod.getLow()),
          DecimalNum.valueOf(eod.getClose()),
          DecimalNum.valueOf(eod.getVolume()));
    }

    return series;
  }

  /**
   * Calculates Simple Moving Average (SMA).
   *
   * @param series BarSeries
   * @param period Period for SMA
   * @return SMA values as BigDecimal array
   */
  public BigDecimal[] calculateSMA(BarSeries series, int period) {
    ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
    SMAIndicator sma = new SMAIndicator(closePrice, period);

    BigDecimal[] result = new BigDecimal[series.getBarCount()];
    for (int i = 0; i < series.getBarCount(); i++) {
      result[i] = new BigDecimal(sma.getValue(i).doubleValue()).setScale(2, RoundingMode.HALF_UP);
    }
    return result;
  }

  /**
   * Calculates Exponential Moving Average (EMA).
   *
   * @param series BarSeries
   * @param period Period for EMA
   * @return EMA values as BigDecimal array
   */
  public BigDecimal[] calculateEMA(BarSeries series, int period) {
    ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
    EMAIndicator ema = new EMAIndicator(closePrice, period);

    BigDecimal[] result = new BigDecimal[series.getBarCount()];
    for (int i = 0; i < series.getBarCount(); i++) {
      result[i] = new BigDecimal(ema.getValue(i).doubleValue()).setScale(2, RoundingMode.HALF_UP);
    }
    return result;
  }

  /**
   * Calculates Relative Strength Index (RSI).
   *
   * @param series BarSeries
   * @param period Period for RSI (typically 14)
   * @return RSI values as BigDecimal array
   */
  public BigDecimal[] calculateRSI(BarSeries series, int period) {
    ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
    RSIIndicator rsi = new RSIIndicator(closePrice, period);

    BigDecimal[] result = new BigDecimal[series.getBarCount()];
    for (int i = 0; i < series.getBarCount(); i++) {
      result[i] = new BigDecimal(rsi.getValue(i).doubleValue()).setScale(2, RoundingMode.HALF_UP);
    }
    return result;
  }

  /**
   * Calculates MACD (Moving Average Convergence Divergence).
   *
   * @param series BarSeries
   * @return MACD result containing MACD line, signal line, and histogram
   */
  public MacdResult calculateMACD(BarSeries series) {
    ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
    MACDIndicator macd = new MACDIndicator(closePrice, 12, 26);
    EMAIndicator signal = new EMAIndicator(macd, 9);

    int barCount = series.getBarCount();
    BigDecimal[] macdLine = new BigDecimal[barCount];
    BigDecimal[] signalLine = new BigDecimal[barCount];
    BigDecimal[] histogram = new BigDecimal[barCount];

    for (int i = 0; i < barCount; i++) {
      macdLine[i] = new BigDecimal(macd.getValue(i).doubleValue()).setScale(2, RoundingMode.HALF_UP);
      signalLine[i] = new BigDecimal(signal.getValue(i).doubleValue()).setScale(2, RoundingMode.HALF_UP);
      histogram[i] =
          new BigDecimal(macd.getValue(i).minus(signal.getValue(i)).doubleValue())
              .setScale(2, RoundingMode.HALF_UP);
    }

    return new MacdResult(macdLine, signalLine, histogram);
  }

  /**
   * Calculates Bollinger Bands.
   *
   * @param series BarSeries
   * @param period Period for moving average (typically 20)
   * @param multiplier Standard deviation multiplier (typically 2)
   * @return Bollinger Bands result
   */
  public BollingerBandsResult calculateBollingerBands(
      BarSeries series, int period, double multiplier) {
    ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
    BollingerBandsMiddleIndicator middleBand = new BollingerBandsMiddleIndicator(new SMAIndicator(closePrice, period));
    StandardDeviationIndicator stdDev = new StandardDeviationIndicator(closePrice, period);
    BollingerBandsUpperIndicator upperBand =
        new BollingerBandsUpperIndicator(middleBand, stdDev, DecimalNum.valueOf(multiplier));
    BollingerBandsLowerIndicator lowerBand =
        new BollingerBandsLowerIndicator(middleBand, stdDev, DecimalNum.valueOf(multiplier));

    int barCount = series.getBarCount();
    BigDecimal[] upper = new BigDecimal[barCount];
    BigDecimal[] middle = new BigDecimal[barCount];
    BigDecimal[] lower = new BigDecimal[barCount];

    for (int i = 0; i < barCount; i++) {
      upper[i] = new BigDecimal(upperBand.getValue(i).doubleValue()).setScale(2, RoundingMode.HALF_UP);
      middle[i] = new BigDecimal(middleBand.getValue(i).doubleValue()).setScale(2, RoundingMode.HALF_UP);
      lower[i] = new BigDecimal(lowerBand.getValue(i).doubleValue()).setScale(2, RoundingMode.HALF_UP);
    }

    return new BollingerBandsResult(upper, middle, lower);
  }

  /**
   * Result class for MACD calculation.
   */
  public static class MacdResult {
    private final BigDecimal[] macdLine;
    private final BigDecimal[] signalLine;
    private final BigDecimal[] histogram;

    public MacdResult(BigDecimal[] macdLine, BigDecimal[] signalLine, BigDecimal[] histogram) {
      this.macdLine = macdLine;
      this.signalLine = signalLine;
      this.histogram = histogram;
    }

    public BigDecimal[] getMacdLine() {
      return macdLine;
    }

    public BigDecimal[] getSignalLine() {
      return signalLine;
    }

    public BigDecimal[] getHistogram() {
      return histogram;
    }
  }

  /**
   * Result class for Bollinger Bands calculation.
   */
  public static class BollingerBandsResult {
    private final BigDecimal[] upperBand;
    private final BigDecimal[] middleBand;
    private final BigDecimal[] lowerBand;

    public BollingerBandsResult(
        BigDecimal[] upperBand, BigDecimal[] middleBand, BigDecimal[] lowerBand) {
      this.upperBand = upperBand;
      this.middleBand = middleBand;
      this.lowerBand = lowerBand;
    }

    public BigDecimal[] getUpperBand() {
      return upperBand;
    }

    public BigDecimal[] getMiddleBand() {
      return middleBand;
    }

    public BigDecimal[] getLowerBand() {
      return lowerBand;
    }
  }
}
