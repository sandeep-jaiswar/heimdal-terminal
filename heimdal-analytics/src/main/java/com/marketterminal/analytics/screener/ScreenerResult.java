package com.marketterminal.analytics.screener;

import com.marketterminal.data.model.Stock;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a stock screening result with computed metrics.
 */
public class ScreenerResult {
  private Stock stock;
  private BigDecimal lastClose;
  private Long lastVolume;
  private BigDecimal rsi;
  private BigDecimal sma20;
  private BigDecimal sma50;
  private BigDecimal changePercent;

  public ScreenerResult(Stock stock) {
    this.stock = stock;
  }

  public Stock getStock() {
    return stock;
  }

  public void setStock(Stock stock) {
    this.stock = stock;
  }

  public BigDecimal getLastClose() {
    return lastClose;
  }

  public void setLastClose(BigDecimal lastClose) {
    this.lastClose = lastClose;
  }

  public Long getLastVolume() {
    return lastVolume;
  }

  public void setLastVolume(Long lastVolume) {
    this.lastVolume = lastVolume;
  }

  public BigDecimal getRsi() {
    return rsi;
  }

  public void setRsi(BigDecimal rsi) {
    this.rsi = rsi;
  }

  public BigDecimal getSma20() {
    return sma20;
  }

  public void setSma20(BigDecimal sma20) {
    this.sma20 = sma20;
  }

  public BigDecimal getSma50() {
    return sma50;
  }

  public void setSma50(BigDecimal sma50) {
    this.sma50 = sma50;
  }

  public BigDecimal getChangePercent() {
    return changePercent;
  }

  public void setChangePercent(BigDecimal changePercent) {
    this.changePercent = changePercent;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScreenerResult that = (ScreenerResult) o;
    return Objects.equals(stock, that.stock);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stock);
  }

  @Override
  public String toString() {
    return "ScreenerResult{"
        + "stock="
        + stock
        + ", lastClose="
        + lastClose
        + ", lastVolume="
        + lastVolume
        + ", rsi="
        + rsi
        + ", sma20="
        + sma20
        + ", sma50="
        + sma50
        + ", changePercent="
        + changePercent
        + '}';
  }
}
