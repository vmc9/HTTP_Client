FROM openjdk:15
COPY ./out/production/HTTP_Client /src
WORKDIR /src
ENTRYPOINT ["java", "Main"]