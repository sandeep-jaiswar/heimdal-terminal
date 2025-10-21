package com.marketterminal.analytics.indicator;

import static org.junit.jupiter.api.Assertions.*;

import com.marketterminal.data.model.EodData;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BarSeries;

class TechnicalIndicatorServiceTest {

  private TechnicalIndicatorService service;
  private List<EodData> testData;

  @BeforeEach
  void setUp() {
    service = new TechnicalIndicatorService();
    testData = createTestData();
  }

  private List<EodData> createTestData() {
    List<EodData> data = new ArrayList<>();
    LocalDate startDate = LocalDate.of(2024, 1, 1);

    for (int i = 0; i < 50; i++) {
      EodData eod = new EodData();
      eod.setSymbol("TEST");
      eod.setDate(startDate.plusDays(i));
      eod.setOpen(new BigDecimal("100.00").add(new BigDecimal(i)));
      eod.setHigh(new BigDecimal("105.00").add(new BigDecimal(i)));
      eod.setLow(new BigDecimal("95.00").add(new BigDecimal(i)));
      eod.setClose(new BigDecimal("100.00").add(new BigDecimal(i)));
      eod.setVolume(1000000L + (i * 10000L));
      eod.setAdjClose(eod.getClose());
      data.add(eod);
    }

    return data;
  }

  @Test
  void testCreateBarSeries() {
    BarSeries series = service.createBarSeries("TEST", testData);

    assertNotNull(series);
    assertEquals("TEST", series.getName());
    assertEquals(50, series.getBarCount());
  }

  @Test
  void testCalculateSMA() {
    BarSeries series = service.createBarSeries("TEST", testData);
    BigDecimal[] sma = service.calculateSMA(series, 20);

    assertNotNull(sma);
    assertEquals(50, sma.length);
    assertTrue(sma[49].compareTo(BigDecimal.ZERO) > 0);
  }

  @Test
  void testCalculateEMA() {
    BarSeries series = service.createBarSeries("TEST", testData);
    BigDecimal[] ema = service.calculateEMA(series, 12);

    assertNotNull(ema);
    assertEquals(50, ema.length);
    assertTrue(ema[49].compareTo(BigDecimal.ZERO) > 0);
  }

  @Test
  void testCalculateRSI() {
    BarSeries series = service.createBarSeries("TEST", testData);
    BigDecimal[] rsi = service.calculateRSI(series, 14);

    assertNotNull(rsi);
    assertEquals(50, rsi.length);
    assertTrue(rsi[49].compareTo(BigDecimal.ZERO) >= 0);
    assertTrue(rsi[49].compareTo(new BigDecimal("100")) <= 0);
  }

  @Test
  void testCalculateMACD() {
    BarSeries series = service.createBarSeries("TEST", testData);
    TechnicalIndicatorService.MacdResult macd = service.calculateMACD(series);

    assertNotNull(macd);
    assertNotNull(macd.getMacdLine());
    assertNotNull(macd.getSignalLine());
    assertNotNull(macd.getHistogram());
    assertEquals(50, macd.getMacdLine().length);
    assertEquals(50, macd.getSignalLine().length);
    assertEquals(50, macd.getHistogram().length);
  }

  @Test
  void testCalculateBollingerBands() {
    BarSeries series = service.createBarSeries("TEST", testData);
    TechnicalIndicatorService.BollingerBandsResult bb = service.calculateBollingerBands(series, 20, 2.0);

    assertNotNull(bb);
    assertNotNull(bb.getUpperBand());
    assertNotNull(bb.getMiddleBand());
    assertNotNull(bb.getLowerBand());
    assertEquals(50, bb.getUpperBand().length);

    // Upper band should be greater than middle, middle greater than lower
    assertTrue(bb.getUpperBand()[49].compareTo(bb.getMiddleBand()[49]) > 0);
    assertTrue(bb.getMiddleBand()[49].compareTo(bb.getLowerBand()[49]) > 0);
  }
}
