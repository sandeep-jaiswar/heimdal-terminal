# 📊 Heimdal Terminal — Bloomberg-style Terminal for Indian Stock Market

[![Build and Test](https://github.com/sandeep-jaiswar/heimdal-terminal/actions/workflows/build.yml/badge.svg)](https://github.com/sandeep-jaiswar/heimdal-terminal/actions/workflows/build.yml)

**Heimdal Terminal** is an open-source, offline Bloomberg-style financial research terminal for the Indian stock market (NSE/BSE).

---

## 🚀 Key Features

✅ **EOD Market Data** - Fetch historical data from Yahoo Finance  
✅ **ClickHouse Database** - Fast, scalable local data storage  
✅ **JavaFX UI** - Modern desktop interface with 5 core panels  
✅ **Technical Indicators** - SMA, EMA, RSI, MACD, Bollinger Bands (TA4J)  
✅ **Stock Screener** - Filter by price, volume, and technical criteria  
✅ **Portfolio Tracker** - Watchlist and offline holdings simulation  
✅ **Corporate Actions** - Track dividends, splits, and bonuses  
✅ **80%+ Test Coverage** - Comprehensive JUnit 5 tests  
✅ **CI/CD Ready** - GitHub Actions workflow  

---

## 🧰 Tech Stack

| Component | Technology |
|-----------|------------|
| UI | JavaFX 21+, FXML |
| Backend | Java 21, Modular Architecture |
| Database | ClickHouse |
| Analytics | TA4J Technical Analysis |
| Build | Gradle 9.1.0 |
| Testing | JUnit 5, TestContainers |
| CI/CD | GitHub Actions |

---

## ⚙️ Quick Start

### Prerequisites
- Java 21+
- ClickHouse server (Docker or native)

### 1. Clone
```bash
git clone https://github.com/sandeep-jaiswar/heimdal-terminal.git
cd heimdal-terminal
```

### 2. Start ClickHouse
```bash
docker run -d --name clickhouse-server -p 8123:8123 -p 9000:9000 clickhouse/clickhouse-server
```

### 3. Build & Run
```bash
./gradlew clean build
./gradlew :heimdal-app:run
```

---

## 📦 Architecture

```
heimdal-terminal/
├── heimdal-data/         # Data layer: models, repos, loaders
├── heimdal-analytics/    # Analytics: indicators, screeners
├── heimdal-ui/          # JavaFX UI controllers & views
└── heimdal-app/         # Main application entry point
```

---

## 🧪 Testing

```bash
./gradlew test              # Run all tests
./gradlew build             # Build with tests
```

---

## 📝 Configuration

Edit `heimdal-app/src/main/resources/application.yml`:

```yaml
database:
  host: localhost
  port: 8123
  database: market_data
```

---

## 🤝 Contributing

1. Fork the repo
2. Create feature branch
3. Add tests
4. Submit PR

---

## ⚖️ License

MIT License - Free for research and educational use

---

## ⚠️ Disclaimer

For educational purposes only. Not affiliated with NSE, BSE, or Bloomberg.
