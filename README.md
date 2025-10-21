# 📊 Heimdal Terminal — A Bloomberg-style JavaFX Application for Indian Markets

**Heimdal Terminal** is an open-source, local, Bloomberg-style financial research terminal designed for the **Indian stock market**.

Unlike commercial data terminals, it runs **entirely offline**, fetching **End-of-Day (EOD)** market data from **public and free sources** like Yahoo Finance and NSE/BSE daily reports.  
It’s perfect for **investors, analysts, and quants** who want to perform deep research, screening, and visualization — without paying for live feeds.

---

## 🚀 Key Features
- 🧾 **EOD Market Data**: Automatically fetch daily NSE/BSE OHLCV data.
- 💾 **Local Database (SQLite/ClickHouse)**: Store all data offline.
- 📈 **Interactive JavaFX Charts**: Candlestick, line, and bar charts with TA indicators.
- 📊 **Stock Screeners**: Build custom filters on valuation & technical parameters.
- 📂 **Corporate Actions**: Dividends, splits, bonuses (from exchange sources).
- 🔍 **Technical Analysis (TA4J)**: SMA, EMA, RSI, MACD, Bollinger, etc.
- 🧮 **Backtesting & Strategy Research** (future module).
- 🖥️ **Modular Workspace**: Dockable panels, dark mode, and custom layouts.

---

## 🧰 Tech Stack
| Layer | Technology |
|--------|-------------|
| **Frontend (UI)** | JavaFX 21+, FXML, ControlsFX |
| **Backend / Services** | Plain Java 17+, optional Spring Boot |
| **Database** | SQLite (default), ClickHouse (optional) |
| **Data Loader** | Python (`yfinance`) or Java (`HttpClient`) |
| **Analytics** | TA4J (Java Technical Analysis Library) |
| **Build System** | Gradle / Maven |

---

## 📦 Project Structure
heimdal-terminal/
├── data-loader/ # Python/Java scripts to fetch EOD data
├── src/main/java/com/marketterminal/
│ ├── ui/ # JavaFX UI (FXML + Controllers)
│ ├── data/ # Database access & models
│ ├── analytics/ # TA4J analytics, indicators, screeners
│ ├── utils/ # Helpers, DbUtils, config loaders
│ └── MainApp.java # Entry point
├── resources/
│ ├── db/ # Schema, seed data
│ └── fxml/ # FXML layouts
├── README.md
└── .github/COPILOT_INSTRUCTION.md


---

## ⚙️ Getting Started

### 1️⃣ Clone the repository
```bash
git clone https://github.com/<your-username>/bharat-terminal.git
cd bharat-terminal

(Optional) Setup virtual environment for data loader
cd data-loader
pip install yfinance pandas sqlite3
python eod_loader.py

3️⃣ Build the application
./gradlew clean build

4️⃣ Run the JavaFX application
./gradlew run

📅 Data Sources
Source	Type	Access
Yahoo Finance	Historical OHLCV	yfinance library
NSE India	Daily bhavcopy (CSV)	https://www.nseindia.com/all-reports

BSE India	Daily bhavcopy (CSV)	https://www.bseindia.com/markets/equity/EQReports/BhavCopy.aspx

All data is publicly available and used strictly for personal research.

🧪 Planned Modules

 Market Dashboard (Indices + Gainers/Losers)

 Stock Charts with Indicators

 Screener Engine (filter builder)

 Corporate Actions viewer

 Portfolio simulation

 Strategy backtester

 Report exports (Excel/PDF)

🧑‍💻 Contribution Guidelines

We welcome contributions!

Use Java 17+ and clean modular design.

Document code and add JavaDoc for public APIs.

All PRs must pass gradle build and code style checks.

Do not include any proprietary or live feed integration.

⚖️ License

This project is released under the MIT License — use freely for personal or academic research.

💬 Credits

Created by engineers passionate about financial data, open analytics, and bringing institutional-grade tools to individual researchers.

⚠️ Disclaimer:
This project is not affiliated with or endorsed by NSE, BSE, or Bloomberg.
All data used is publicly available EOD data, and the terminal is for research and educational purposes only.
