package com.foliageh.itmosoalab2.api;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@DataSourceDefinition(
        name = "java:global/datasources/PostgresDS",
        className = "org.postgresql.ds.PGSimpleDataSource",
        serverName = "localhost",
        portNumber = 5432,
        user = "postgres",
        password = "postgres",
        databaseName = "shrew"
)
public class ApplicationConfig extends Application {
}
