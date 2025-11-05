# Base image: Maven + JDK 17 (Debian Bookworm)
FROM maven:3.9.9-eclipse-temurin-24

ENV DEBIAN_FRONTEND=noninteractive
WORKDIR /usr/src/app

# Install required tools and browsers
RUN apt-get update && \
    apt-get install -y \
        wget curl gnupg ca-certificates apt-transport-https software-properties-common \
        bzip2 \
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

# Print installed versions
RUN echo "✅ Installed browsers:" && \
    google-chrome --version && \
    microsoft-edge --version && \
    firefox --version && \
    mvn -v

CMD ["/bin/bash"]
