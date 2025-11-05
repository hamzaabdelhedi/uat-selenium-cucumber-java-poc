# Dockerfile for Maven + Browsers
# This image contains Maven 3.9.10, Java 24, Chrome, Firefox, and Edge

FROM eclipse-temurin:24-jdk

# Set environment variables
ENV MAVEN_VERSION=3.9.10
ENV MAVEN_HOME=/opt/maven
ENV PATH="${MAVEN_HOME}/bin:${PATH}"

# Install necessary dependencies
RUN apt-get update && apt-get install -y \
    wget \
    curl \
    gnupg \
    unzip \
    xvfb \
    libxi6 \
    libgconf-2-4 \
    libnss3 \
    libxss1 \
    libasound2 \
    libatk-bridge2.0-0 \
    libgtk-3-0 \
    fonts-liberation \
    libappindicator3-1 \
    xdg-utils \
    apt-transport-https \
    software-properties-common \
    && rm -rf /var/lib/apt/lists/*

# Install Maven
RUN wget https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz -P /tmp \
    && tar xf /tmp/apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /opt \
    && ln -s /opt/apache-maven-${MAVEN_VERSION} /opt/maven \
    && rm /tmp/apache-maven-${MAVEN_VERSION}-bin.tar.gz

# Install Google Chrome
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

# Install Firefox
RUN apt-get update && apt-get install -y firefox-esr \
    && rm -rf /var/lib/apt/lists/*

# Install Microsoft Edge
RUN curl https://packages.microsoft.com/keys/microsoft.asc | gpg --dearmor > /etc/apt/trusted.gpg.d/microsoft.gpg \
    && echo "deb [arch=amd64] https://packages.microsoft.com/repos/edge stable main" > /etc/apt/sources.list.d/microsoft-edge.list \
    && apt-get update \
    && apt-get install -y microsoft-edge-stable \
    && rm -rf /var/lib/apt/lists/*

# Verify installations
RUN mvn --version && \
    google-chrome --version && \
    firefox --version && \
    microsoft-edge --version

# Set working directory
WORKDIR /workspace

# Create a non-root user for running tests
RUN useradd -m -u 1000 testuser && \
    chown -R testuser:testuser /workspace

USER testuser

CMD ["/bin/bash"]