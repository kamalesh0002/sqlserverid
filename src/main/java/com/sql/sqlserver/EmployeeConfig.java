package com.sql.sqlserver;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.identity.AzureCliCredential;
import com.azure.identity.AzureCliCredentialBuilder;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;

import com.azure.core.credential.TokenRequestContext;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.springframework.beans.factory.annotation.Value;

import javax.sql.DataSource;

@Configuration
public class EmployeeConfig {
    
    @Value("${spring.datasource.url}")
    private String databaseUrl;
    
    @Bean
    public DataSource dataSource() {
     //   AzureCliCredential defaultCredential = new AzureCliCredentialBuilder().build();

        DefaultAzureCredential defaultCredential = new DefaultAzureCredentialBuilder().build();
        String token = defaultCredential.getToken(new TokenRequestContext().addScopes("https://database.windows.net/.default")).block().getToken();

        SQLServerDataSource dataSource = new SQLServerDataSource();

        dataSource.setPassword(token); 
        dataSource.setServerName("");
        dataSource.setDatabaseName("mynewdb"); 
        dataSource.setAuthentication("ActiveDirectoryDefault"); 
        return dataSource;
    }
}
