package com.marketterminal.analytics.screener;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a filter criterion for stock screening.
 */
public class ScreenerCriterion {
  private String field;
  private ComparisonOperator operator;
  private BigDecimal value;

  public ScreenerCriterion(String field, ComparisonOperator operator, BigDecimal value) {
    this.field = field;
    this.operator = operator;
    this.value = value;
  }

  public String getField() {
    return field;
  }

  public ComparisonOperator getOperator() {
    return operator;
  }

  public BigDecimal getValue() {
    return value;
  }

  public enum ComparisonOperator {
    GREATER_THAN,
    LESS_THAN,
    GREATER_THAN_OR_EQUAL,
    LESS_THAN_OR_EQUAL,
    EQUAL
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScreenerCriterion that = (ScreenerCriterion) o;
    return Objects.equals(field, that.field)
        && operator == that.operator
        && Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(field, operator, value);
  }

  @Override
  public String toString() {
    return "ScreenerCriterion{"
        + "field='"
        + field
        + '\''
        + ", operator="
        + operator
        + ", value="
        + value
        + '}';
  }
}
