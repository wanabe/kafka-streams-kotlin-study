FROM gradle:6.5.0-jdk8

RUN apt-get update && apt-get install -y curl zip
RUN curl -s https://get.sdkman.io | bash
RUN bash -l -c 'source /root/.sdkman/bin/sdkman-init.sh && sdk install kotlin'

CMD ["bash"]
