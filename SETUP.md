# Setup Guide for Heimdal Terminal

This guide will help you set up and run Heimdal Terminal on your local machine.

## Prerequisites

### Required Software

1. **Java Development Kit (JDK) 21 or higher**
   ```bash
   # Check your Java version
   java -version
   # Should output: openjdk version "21" or higher
   ```

   Download from: [Adoptium Temurin](https://adoptium.net/)

2. **ClickHouse Database**
   
   We recommend using Docker for the easiest setup.

## Installation Steps

### Step 1: Clone the Repository

```bash
git clone https://github.com/sandeep-jaiswar/heimdal-terminal.git
cd heimdal-terminal
```

### Step 2: Set Up ClickHouse Database

#### Option A: Using Docker (Recommended)

```bash
# Start ClickHouse server
docker run -d \
  --name clickhouse-server \
  -p 8123:8123 \
  -p 9000:9000 \
  --ulimit nofile=262144:262144 \
  clickhouse/clickhouse-server

# Verify it's running
docker ps | grep clickhouse
```

#### Option B: Native Installation

**On Ubuntu/Debian:**
```bash
sudo apt-get install -y apt-transport-https ca-certificates dirmngr
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 8919F6BD2B48D754

echo "deb https://packages.clickhouse.com/deb stable main" | sudo tee \
    /etc/apt/sources.list.d/clickhouse.list
sudo apt-get update

sudo apt-get install -y clickhouse-server clickhouse-client
sudo service clickhouse-server start
```

**On macOS:**
```bash
brew install clickhouse
brew services start clickhouse
```

**On Windows:**
Download from [ClickHouse official website](https://clickhouse.com/docs/en/install)

### Step 3: Initialize the Database

The application will automatically create the schema on first run, but you can manually initialize it:

```bash
# Connect to ClickHouse
clickhouse-client

# Run the schema script
clickhouse-client < heimdal-data/src/main/resources/db/schema.sql
```

### Step 4: Seed Sample Data (Optional)

Add some sample NSE stocks to get started:

```bash
./gradlew :heimdal-data:run -PmainClass=com.marketterminal.data.service.DataSeeder
```

### Step 5: Build the Application

```bash
# Build all modules
./gradlew clean build

# This will:
# - Compile all Java code
# - Run unit tests
# - Create distributable JARs
```

### Step 6: Run the Application

```bash
# Option 1: Using Gradle
./gradlew :heimdal-app:run

# Option 2: Using the distribution
cd heimdal-app/build/install/heimdal-app/bin
./heimdal-app  # On Linux/Mac
heimdal-app.bat  # On Windows
```

## Configuration

### Database Configuration

Edit `heimdal-app/src/main/resources/application.yml`:

```yaml
database:
  host: localhost        # ClickHouse host
  port: 8123            # HTTP port (default 8123)
  database: market_data # Database name
  username: default     # Username
  password: ""          # Password (empty for local)
  maxConnections: 10    # Connection pool size

dataLoader:
  autoLoad: false       # Auto-load data on startup
  batchSize: 1000       # Records per batch
  retryAttempts: 3      # Retry failed loads
```

### Logging Configuration

Logging is configured via Logback. Default level is INFO.

To change log level, create `logback.xml` in `src/main/resources`:

```xml
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="DEBUG">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```

## Loading Historical Data

### Using Yahoo Finance

The application includes a Yahoo Finance loader. To fetch data for stocks:

1. Ensure stocks are added to the database (use DataSeeder)
2. Run the data loader:

```bash
# This will be available in a future update
# For now, data loading happens through the UI
```

### Data Sources

- **Yahoo Finance**: Historical OHLCV data (free, public)
  - NSE stocks: Add `.NS` suffix (e.g., `RELIANCE.NS`)
  - BSE stocks: Add `.BO` suffix (e.g., `RELIANCE.BO`)

- **NSE India**: Daily bhavcopy CSV files
  - Visit: https://www.nseindia.com/all-reports

- **BSE India**: Daily bhavcopy CSV files
  - Visit: https://www.bseindia.com/markets/equity/EQReports/BhavCopy.aspx

## Running Tests

```bash
# Run all tests
./gradlew test

# Run tests for a specific module
./gradlew :heimdal-data:test
./gradlew :heimdal-analytics:test

# Run tests with detailed output
./gradlew test --info

# Generate test coverage report (if configured)
./gradlew test jacocoTestReport
```

## Troubleshooting

### ClickHouse Connection Issues

**Problem**: Application cannot connect to ClickHouse

**Solution**:
```bash
# Check if ClickHouse is running
docker ps | grep clickhouse  # For Docker
sudo service clickhouse-server status  # For native install

# Check if port 8123 is accessible
curl http://localhost:8123
# Should return: "Ok."

# Check ClickHouse logs
docker logs clickhouse-server  # For Docker
tail -f /var/log/clickhouse-server/clickhouse-server.log  # For native
```

### Java Version Issues

**Problem**: Build fails with unsupported class file version

**Solution**:
```bash
# Check Java version
java -version

# Set JAVA_HOME if needed
export JAVA_HOME=/path/to/jdk-21
export PATH=$JAVA_HOME/bin:$PATH
```

### JavaFX Issues

**Problem**: JavaFX classes not found

**Solution**: The project uses the JavaFX plugin which automatically downloads JavaFX.
If issues persist:
```bash
# Clean and rebuild
./gradlew clean build --refresh-dependencies
```

### Port Already in Use

**Problem**: Port 8123 already in use

**Solution**:
```bash
# Find process using port 8123
sudo lsof -i :8123  # On Linux/Mac
netstat -ano | findstr :8123  # On Windows

# Stop ClickHouse or change port in application.yml
```

## Next Steps

1. **Add More Stocks**: Use the DataSeeder or manually add stocks through SQL
2. **Load Historical Data**: Use the Data menu in the application
3. **Create Screeners**: Go to Screener tab and add filters
4. **Track Portfolio**: Add stocks to watchlist in Portfolio tab
5. **View Charts**: Search for stocks in Stock Chart tab

## Development

### Building from Source

```bash
# Build without tests
./gradlew build -x test

# Build specific module
./gradlew :heimdal-data:build

# Clean build
./gradlew clean build
```

### Code Style

```bash
# Check code style (currently disabled due to compatibility)
./gradlew spotlessCheck

# Apply code formatting
./gradlew spotlessApply
```

## Getting Help

- **Documentation**: See README.md for general information
- **Issues**: Report bugs on [GitHub Issues](https://github.com/sandeep-jaiswar/heimdal-terminal/issues)
- **Discussions**: Use GitHub Discussions for questions

## Security Notes

- Database credentials in `application.yml` should be kept secure
- For production use, change default ClickHouse password
- Do not commit sensitive data or credentials to version control

---

**Happy Trading & Analysis! 📊**
