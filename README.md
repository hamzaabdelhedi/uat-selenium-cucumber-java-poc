# UAT Automation Framework

A comprehensive User Acceptance Testing (UAT) framework built with Selenium WebDriver, Cucumber BDD, and Maven. This framework demonstrates automated testing of login functionality with support for multiple execution modes.

## 🚀 Features

- ✅ **Singleton WebDriver Management** - Efficient resource handling
- ✅ **Page Object Model (POM)** - Maintainable test structure
- ✅ **BDD with Cucumber & Gherkin** - Business-readable test scenarios
- ✅ **Multiple Browser Support** - Chrome, Firefox, Edge
- ✅ **Three Execution Modes** - Local, Remote (Selenium Grid), Docker
- ✅ **Automatic Driver Management** - Using Bonigarcia WebDriverManager
- ✅ **HTML Test Reports** - With screenshots on failure

## 📋 Prerequisites

- **Java 24** or higher
- **Maven 3.9.10** or higher
- **Browsers installed** (for local execution): Chrome, Firefox, or Edge
- **Docker** (optional, for Docker execution mode)
- **Selenium Grid** (optional, for remote execution mode)


## 🔧 Execution Modes

The framework supports three distinct execution modes, configured via Maven properties.

---

### 1️⃣ Local Execution Mode (Default)

**Description:** Runs tests on your local machine with browsers installed locally.

**How it works:**
- WebDriverManager automatically downloads the appropriate driver binary (chromedriver, geckodriver, etc.)
- Browser launches directly on your machine
- Fastest execution for local development
- No additional infrastructure required

**Prerequisites:**
- Browser must be installed on your machine
- No additional setup needed

**Command Examples:**

```bash
# Run with Chrome (default browser)
mvn clean verify

# Run with Firefox
mvn clean verify -Dbrowser=firefox

# Run with Edge
mvn clean verify -Dbrowser=edge

# Alternative: using mvn test
mvn clean test -Dbrowser=chrome
```

**Maven Properties:**
| Property | Value | Description |
|----------|-------|-------------|
| `browser` | `chrome` (default), `firefox`, `edge` | Browser to use |

**When to use:**
- ✅ Local development and debugging
- ✅ Quick test execution
- ✅ No need for distributed testing
- ✅ Simple setup

---

### 2️⃣ Remote Execution Mode (Selenium Grid)

**Description:** Runs tests on a remote Selenium Grid or standalone Selenium server.

**How it works:**
- Tests connect to a running Selenium Grid hub
- Browser launches on a remote Grid node (can be on different machine)
- WebDriverManager uses `.remoteAddress(url)` to connect to Grid
- Supports distributed and parallel test execution

**Prerequisites:**
- Running Selenium Grid or standalone server
- Network access to the Grid URL

**Setting up Selenium Grid (Example using Docker):**

```bash
# Option 1: Standalone Chrome
docker run -d -p 4444:4444 --name selenium-chrome \
  selenium/standalone-chrome:latest

# Option 2: Standalone Firefox
docker run -d -p 4444:4444 --name selenium-firefox \
  selenium/standalone-firefox:latest

# Option 3: Full Grid (Hub + Nodes)
docker-compose up -d  # Using selenium-grid docker-compose.yml
```

**Command Examples:**

```bash
# Run tests on remote Grid with Chrome
mvn clean verify -Dbrowser=chrome -Dremote.browser=http://localhost:4444

# Run tests on remote Grid with Firefox
mvn clean verify -Dbrowser=firefox -Dremote.browser=http://localhost:4444

# Run tests on cloud Grid (e.g., Selenium Grid in cloud)
mvn clean verify -Dbrowser=chrome -Dremote.browser=http://selenium-grid.company.com:4444

# Alternative: using mvn test
mvn clean test -Dbrowser=chrome -Dremote.browser=http://localhost:4444
```

**Maven Properties:**
| Property | Value | Description |
|----------|-------|-------------|
| `browser` | `chrome`, `firefox`, `edge` | Browser to use on remote Grid |
| `remote.browser` | URL (e.g., `http://localhost:4444`) | Selenium Grid hub URL |

**When to use:**
- ✅ Distributed test execution
- ✅ Running tests on different OS/browser combinations
- ✅ CI/CD pipeline integration
- ✅ Cloud-based testing (BrowserStack, Sauce Labs, etc.)
- ✅ Parallel test execution across multiple nodes

**Verifying Grid is running:**
```bash
# Check Grid status
curl http://localhost:4444/status

# Or open in browser
http://localhost:4444/ui
```

---

### 3️⃣ Docker-Provisioned Execution Mode

**Description:** Runs tests with browsers automatically provisioned in Docker containers.

**How it works:**
- WebDriverManager automatically downloads browser Docker images
- Browser runs in isolated Docker container
- WebDriverManager uses `.browserInDocker()` method
- No need to install browsers on host machine
- Container is automatically created and destroyed

**Prerequisites:**
- Docker installed and running
- Docker daemon accessible
- No browser installation needed on host

**Installing Docker:**
- **macOS/Windows:** [Docker Desktop](https://www.docker.com/products/docker-desktop)
- **Linux:** `sudo apt-get install docker.io` (Ubuntu/Debian)

**Command Examples:**

```bash
# Run tests with Chrome in Docker
mvn clean verify -Dbrowser=chrome -Dauto.provisioned.browser=true

# Run tests with Firefox in Docker
mvn clean verify -Dbrowser=firefox -Dauto.provisioned.browser=true

# Run tests with Edge in Docker
mvn clean verify -Dbrowser=edge -Dauto.provisioned.browser=true

# Alternative: using mvn test
mvn clean test -Dbrowser=chrome -Dauto.provisioned.browser=true
```

**Maven Properties:**
| Property | Value | Description |
|----------|-------|-------------|
| `browser` | `chrome`, `firefox`, `edge` | Browser to run in Docker |
| `auto.provisioned.browser` | `true` or `false` (default) | Enable Docker browser provisioning |

**When to use:**
- ✅ Clean, isolated test environment
- ✅ No browser installation on host machine
- ✅ Consistent test environment across team
- ✅ Easy CI/CD integration
- ✅ Testing on machines without browsers installed

**First Run (Automatic Docker Image Download):**
```bash
# First time: WebDriverManager downloads Docker image (~500MB-1GB)
mvn clean verify -Dbrowser=chrome -Dauto.provisioned.browser=true

# Subsequent runs: Uses cached Docker image (much faster)
mvn clean verify -Dbrowser=chrome -Dauto.provisioned.browser=true
```

**Checking Docker Images:**
```bash
# View downloaded browser images
docker images | grep selenium

# Example output:
# selenium/standalone-chrome    latest    abc123...   500MB
```

---

## 📄 License

This is a sample project for educational purposes.

---

## 🔗 Useful Links

- [Selenium WebDriver](https://www.selenium.dev/documentation/webdriver/)
- [Cucumber Documentation](https://cucumber.io/docs/cucumber/)
- [WebDriverManager](https://github.com/bonigarcia/webdrivermanager)
- [Selenium Grid](https://www.selenium.dev/documentation/grid/)
- [Practice Test Site](https://practicetestautomation.com/)
