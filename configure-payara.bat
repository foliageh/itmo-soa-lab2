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

echo === Конфигурация завершена ===
pause
