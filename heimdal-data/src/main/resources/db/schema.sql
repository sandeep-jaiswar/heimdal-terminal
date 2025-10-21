-- ClickHouse schema for Heimdal Terminal

-- Create database if not exists
CREATE DATABASE IF NOT EXISTS market_data;

-- Stocks table
CREATE TABLE IF NOT EXISTS market_data.stocks (
    symbol String,
    name String,
    exchange String,
    sector String,
    industry String,
    active UInt8,
    created_at DateTime DEFAULT now()
) ENGINE = MergeTree()
ORDER BY (exchange, symbol);

-- EOD data table (partitioned by month for performance)
CREATE TABLE IF NOT EXISTS market_data.eod_data (
    symbol String,
    date Date,
    open Decimal(18, 4),
    high Decimal(18, 4),
    low Decimal(18, 4),
    close Decimal(18, 4),
    volume UInt64,
    adj_close Decimal(18, 4),
    created_at DateTime DEFAULT now()
) ENGINE = MergeTree()
PARTITION BY toYYYYMM(date)
ORDER BY (symbol, date);

-- Corporate actions table
CREATE TABLE IF NOT EXISTS market_data.corporate_actions (
    symbol String,
    ex_date Date,
    action_type String,
    value Decimal(18, 4),
    description String,
    created_at DateTime DEFAULT now()
) ENGINE = MergeTree()
ORDER BY (symbol, ex_date, action_type);

-- Indices table for market indices like NIFTY50, BANKNIFTY
CREATE TABLE IF NOT EXISTS market_data.indices (
    symbol String,
    date Date,
    open Decimal(18, 4),
    high Decimal(18, 4),
    low Decimal(18, 4),
    close Decimal(18, 4),
    volume UInt64,
    created_at DateTime DEFAULT now()
) ENGINE = MergeTree()
PARTITION BY toYYYYMM(date)
ORDER BY (symbol, date);
