@echo off
REM === Конфигурация Payara JDBC PostgreSQL ===

set PAYARA_HOME=C:\payara7
if not "%~1"=="" set PAYARA_HOME=%~1
set ASADMIN=%PAYARA_HOME%\bin\asadmin.bat

set MAVEN_REPO=%USERPROFILE%\.m2\repository
if not "%~2"=="" set MAVEN_REPO=%~2
set POSTGRES_JAR=%MAVEN_REPO%\org\postgresql\postgresql\42.7.8\postgresql-42.7.8.jar

REM Добавляем библиотеку PostgreSQL
"%ASADMIN%" add-library "%POSTGRES_JAR%"

REM Создаем пул соединений
"%ASADMIN%" create-jdbc-connection-pool ^
 --datasourceClassname=org.postgresql.ds.PGSimpleDataSource ^
 --restype=javax.sql.DataSource postgres-pool

REM Настройки параметров пула
"%ASADMIN%" set resources.jdbc-connection-pool.postgres-pool.property.user=postgres
"%ASADMIN%" set resources.jdbc-connection-pool.postgres-pool.property.password=postgres
"%ASADMIN%" set resources.jdbc-connection-pool.postgres-pool.property.databaseName=shrew
"%ASADMIN%" set resources.jdbc-connection-pool.postgres-pool.property.serverName=localhost
"%ASADMIN%" set resources.jdbc-connection-pool.postgres-pool.property.portNumber=5432

REM Проверяем соединение
"%ASADMIN%" ping-connection-pool postgres-pool

REM Создаем JDBC ресурс
"%ASADMIN%" create-jdbc-resource --enabled=true --poolName=postgres-pool --target=domain datasources/PostgresDS
"%ASADMIN%" create-resource-ref --enabled=true --target=server datasources/PostgresDS

REM Генерируем сертификат (везде пароль ставить password)
REM cd C:\payara7\glassfish\domains\domain1\config
REM $env:OPENSSL_CONF="C:\Program Files\Git\usr\ssl\openssl.cnf"
REM openssl req -x509 -newkey rsa:4096 -keyout mycert.key -out mycert.crt -days 365 -subj "/C=RU/ST=SPB/L=SPB/O=ITMO/CN=localhost" -addext "subjectAltName=DNS:localhost,IP:127.0.0.1"
REM openssl pkcs12 -export -in mycert.crt -inkey mycert.key -out mycert.p12 -name mydomain_certificate -passout pass:password
REM *остановить payara*
REM "C:\payara7\bin\asadmin.bat" change-master-password --savemasterpassword=true domain1
REM REM Enter the current master password>changeit
REM REM Enter the new master password>password
REM keytool -importkeystore -destkeystore keystore.p12 -srckeystore mycert.p12 -srcstoretype PKCS12 -alias mydomain_certificate -deststorepass password -srcstorepass password
REM если mydomain_certificate уже существует: keytool -delete -alias mydomain_certificate -keystore cacerts.p12 -storepass password
REM keytool -importcert -trustcacerts -destkeystore cacerts.p12 -file mycert.crt -alias mydomain_certificate -storepass password
REM REM Trust this certificate? [no]:  yes
REM *запустить payara*
REM "C:\payara7\bin\asadmin.bat" set configs.config.server-config.network-config.protocols.protocol.http-listener-2.ssl.cert-nickname=mydomain_certificate

echo === Конфигурация завершена ===
pause
