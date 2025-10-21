# 🤖 GitHub Copilot Instruction

## Project Context
This repository contains a **JavaFX-based financial analytics terminal** that provides **Bloomberg-like functionality** focused on the **Indian stock market (NSE/BSE)**.

The terminal:
- Runs **locally** with **no external dependencies** or licensed data feeds.
- Uses **EOD (End-of-Day)** market data fetched from **free and public sources** such as Yahoo Finance or NSE/BSE daily CSVs.
- Stores all data in a **local SQLite or ClickHouse** database.
- Provides visualizations, technical indicators, screeners, and analytics via **JavaFX UI**.

---

## Copilot Behavior Guidelines

### 💡 Code Style
- Follow **clean, modular, and documented** Java 17+ code.
- Use **JavaFX FXML** for UI layout; keep controllers lightweight.
- Backend logic goes into `services/` or `utils/` packages.
- Favor **composition over inheritance**.
- Use **descriptive class and method names**; avoid abbreviations.

### 🧩 Structure
- **Frontend (JavaFX)** in `src/main/java/com/marketterminal/ui`
- **Data access layer** in `src/main/java/com/marketterminal/data`
- **Analytics layer** in `src/main/java/com/marketterminal/analytics`
- **EOD loader (Python or Java)** in `data-loader/`
- **Config & database schema** in `/resources/db`

### ⚙️ Libraries & Tools
- Use `TA4J` for indicators and technical analysis.
- Use `SQLite` for the local database.
- Use `HttpClient` (Java 11+) or `Jsoup` for web fetching.
- Use `SLF4J` for logging.
- Use `Gradle` for builds.

### 🧠 Copilot Should Prefer
- Code that is **offline-safe** and **does not require APIs** with keys.
- **Utility-driven** functions (load EOD CSV, calculate SMA, render charts).
- Readable, testable classes with proper encapsulation.
- JavaDoc for all public methods.

### 🚫 Copilot Should Avoid
- Suggesting any **real-time market feed integration**.
- Using **proprietary APIs** or **commercial data sources**.
- Introducing **frameworks heavier than necessary** (Spring Boot optional for now).
- Generating credentials, API keys, or accessing web scraping beyond allowed public NSE/BSE data.

---

## Example Prompt for Copilot
> Implement a `YahooFinanceLoader` class that fetches EOD data for NSE tickers using `yfinance` or public CSVs and stores it in SQLite.

> Create a JavaFX chart component that visualizes OHLC candles with SMA(20) overlay.

> Add a `ScreenerService` that queries SQLite and filters stocks where RSI < 30 and Volume > average(20).

---

## Quality Gate
All code contributions must:
1. Compile on **Java 17+**
2. Pass `gradle build` with no warnings.
3. Follow Checkstyle / Spotless formatting.
4. Be **deterministic**, **offline-capable**, and **testable**.

---

**Purpose:**  
Keep Copilot aligned with the project’s offline, analysis-focused, open-source goal — **a research terminal, not a trading product**.
