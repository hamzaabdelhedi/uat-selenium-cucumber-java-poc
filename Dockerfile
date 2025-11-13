# Base image: Maven + JDK 17 (Debian Bookworm)
FROM maven:3.9.9-eclipse-temurin-24
ENV DEBIAN_FRONTEND=noninteractive
WORKDIR /usr/src/app

# Environment variables for WebDriver versions (update as needed)
ENV CHROMEDRIVER_VERSION=131.0.6778.204
ENV GECKODRIVER_VERSION=0.35.0
ENV EDGEDRIVER_VERSION=131.0.2903.112

# Install required tools and browsers
RUN apt-get update && \
    apt-get install -y \
        wget curl gnupg ca-certificates apt-transport-https software-properties-common \
        bzip2 unzip \
    && mkdir -p /etc/apt/keyrings && \
    \
    # ----------------------------------------------------
    # 🧭 Google Chrome
    # ----------------------------------------------------
    wget -q -O- https://dl.google.com/linux/linux_signing_key.pub | gpg --dearmor -o /etc/apt/keyrings/google-chrome.gpg && \
    echo "deb [arch=amd64 signed-by=/etc/apt/keyrings/google-chrome.gpg] http://dl.google.com/linux/chrome/deb/ stable main" \
        > /etc/apt/sources.list.d/google-chrome.list && \
    \
    # ----------------------------------------------------
    # 🪟 Microsoft Edge
    # ----------------------------------------------------
    wget -q -O- https://packages.microsoft.com/keys/microsoft.asc | gpg --dearmor -o /etc/apt/keyrings/microsoft-edge.gpg && \
    echo "deb [arch=amd64 signed-by=/etc/apt/keyrings/microsoft-edge.gpg] https://packages.microsoft.com/repos/edge stable main" \
        > /etc/apt/sources.list.d/microsoft-edge.list && \
    \
    # ----------------------------------------------------
    # Install Chrome and Edge
    # ----------------------------------------------------
    apt-get update && \
    apt-get install -y google-chrome-stable microsoft-edge-stable && \
    \
    # ----------------------------------------------------
    # 🦊 Install Firefox (from Mozilla directly)
    # ----------------------------------------------------
    wget -O /tmp/firefox.tar.xz "https://download.mozilla.org/?product=firefox-latest&os=linux64&lang=en-US" && \
    tar xvf /tmp/firefox.tar.xz -C /opt/ && \
    ln -s /opt/firefox/firefox /usr/local/bin/firefox && \
    rm /tmp/firefox.tar.xz && \
    \
    # ----------------------------------------------------
    # Cleanup
    # ----------------------------------------------------
    apt-get clean && rm -rf /var/lib/apt/lists/* /var/cache/apt/*

# ----------------------------------------------------
# 🚗 Install ChromeDriver
# ----------------------------------------------------
RUN wget -q https://storage.googleapis.com/chrome-for-testing-public/${CHROMEDRIVER_VERSION}/linux64/chromedriver-linux64.zip -O /tmp/chromedriver.zip && \
    unzip -q /tmp/chromedriver.zip -d /tmp && \
    mv /tmp/chromedriver-linux64/chromedriver /usr/bin/chromedriver && \
    chmod +x /usr/bin/chromedriver && \
    rm -rf /tmp/chromedriver* && \
    chromedriver --version

# ----------------------------------------------------
# 🦎 Install GeckoDriver (Firefox)
# ----------------------------------------------------
RUN wget -q https://github.com/mozilla/geckodriver/releases/download/v${GECKODRIVER_VERSION}/geckodriver-v${GECKODRIVER_VERSION}-linux64.tar.gz -O /tmp/geckodriver.tar.gz && \
    tar -xzf /tmp/geckodriver.tar.gz -C /usr/bin && \
    chmod +x /usr/bin/geckodriver && \
    rm /tmp/geckodriver.tar.gz && \
    geckodriver --version

# ----------------------------------------------------
# 🌊 Install EdgeDriver
# ----------------------------------------------------
RUN wget -q https://msedgedriver.microsoft.com/${EDGEDRIVER_VERSION}/edgedriver_linux64.zip -O /tmp/edgedriver.zip && \
    unzip -q /tmp/edgedriver.zip -d /tmp && \
    mv /tmp/msedgedriver /usr/bin/msedgedriver && \
    chmod +x /usr/bin/msedgedriver && \
    rm -rf /tmp/edgedriver* && \
    msedgedriver --version

# Print installed versions
RUN echo "✅ Installed browsers:" && \
    google-chrome --version && \
    microsoft-edge --version && \
    firefox --version && \
    echo "\n✅ Installed WebDrivers:" && \
    chromedriver --version && \
    geckodriver --version && \
    msedgedriver --version && \
    echo "\n✅ Maven:" && \
    mvn -v

CMD ["/bin/bash"]