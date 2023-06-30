FROM mcr.microsoft.com/openjdk/jdk:17-ubuntu

RUN apt update && apt install -y \
    curl \
    git \
    openssh-server \
    zip \
 && apt clean \
 && rm -rf /var/lib/apt/lists/*

RUN curl -s "https://get.sdkman.io" | bash
RUN echo ". $HOME/.sdkman/bin/sdkman-init.sh; sdk install gradle" | bash
