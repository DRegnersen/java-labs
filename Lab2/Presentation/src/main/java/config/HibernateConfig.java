package config;

import config.builder.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.PostgreSQL9Dialect;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.sql.DataSource;
import java.net.URL;
import java.util.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HibernateConfig {
    @NonNull
    private String host;
    @NonNull
    private Integer port;
    @NonNull
    private String dbName;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private Strategy strategy;
    @NonNull
    private List<Class<?>> managedClasses;
    @NonNull
    private Boolean isSqlShowed;

    public EntityManagerFactory createEntityManagerFactory() {
        return new HibernatePersistenceProvider().createContainerEntityManagerFactory(
                getPersistenceUnitInfo(),
                getHibernateProperties());
    }

    public static HostBuilder getBuilder() {
        return new HibernateConfigBuilder();
    }

    private Map<String, Object> getHibernateProperties() {
        return Map.of(AvailableSettings.JPA_JDBC_URL, "jdbc:postgresql://%s:%d/%s".formatted(host, port, dbName),
                AvailableSettings.DIALECT, PostgreSQL9Dialect.class,
                AvailableSettings.HBM2DDL_AUTO, strategy.toString(),
                AvailableSettings.USER, username,
                AvailableSettings.PASS, password,
                AvailableSettings.SHOW_SQL, isSqlShowed);
    }

    private PersistenceUnitInfo getPersistenceUnitInfo() {
        return new HibernatePersistenceUnitInfo(managedClasses);
    }

    private static class HibernateConfigBuilder implements HostBuilder, PortBuilder, DbNameBuilder, UsernameBuilder,
            PasswordBuilder, StrategyBuilder, ManagedClassesBuilder, SqlShowingValueBuilder, TerminalBuilder {
        private String host;
        private Integer port;
        private String dbName;
        private String username;
        private String password;
        private Strategy strategy;
        private List<Class<?>> managedClasses;
        private Boolean isSqlShowed;

        @Override
        public PortBuilder withHost(String host) {
            this.host = host;
            return this;
        }

        @Override
        public DbNameBuilder withPort(Integer port) {
            this.port = port;
            return this;
        }

        @Override
        public UsernameBuilder withDbName(String dbName) {
            this.dbName = dbName;
            return this;
        }

        @Override
        public PasswordBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        @Override
        public StrategyBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        @Override
        public ManagedClassesBuilder withStrategy(Strategy strategy) {
            this.strategy = strategy;
            return this;
        }

        @Override
        public SqlShowingValueBuilder withManagedClasses(Class<?>... managedClasses) {
            this.managedClasses = Arrays.stream(managedClasses).toList();
            return this;
        }

        @Override
        public TerminalBuilder withSqlShowingValue(boolean isSqlShowed) {
            this.isSqlShowed = isSqlShowed;
            return this;
        }

        @Override
        public HibernateConfig build() {
            return new HibernateConfig(host, port, dbName, username, password, strategy, managedClasses, isSqlShowed);
        }
    }

    @RequiredArgsConstructor
    private static class HibernatePersistenceUnitInfo implements PersistenceUnitInfo {
        @NonNull
        private List<Class<?>> managedClasses;

        @Override
        public String getPersistenceUnitName() {
            return "config";
        }

        @Override
        public String getPersistenceProviderClassName() {
            return HibernatePersistenceProvider.class.toString();
        }

        @Override
        public PersistenceUnitTransactionType getTransactionType() {
            return PersistenceUnitTransactionType.RESOURCE_LOCAL;
        }

        @Override
        public DataSource getJtaDataSource() {
            return null;
        }

        @Override
        public DataSource getNonJtaDataSource() {
            return null;
        }

        @Override
        public List<String> getMappingFileNames() {
            return Collections.emptyList();
        }

        @Override
        public List<URL> getJarFileUrls() {
            return Collections.emptyList();
        }

        @Override
        public URL getPersistenceUnitRootUrl() {
            return null;
        }

        @Override
        public List<String> getManagedClassNames() {
            return managedClasses.stream().map(Class::getName).toList();
        }

        @Override
        public boolean excludeUnlistedClasses() {
            return false;
        }

        @Override
        public SharedCacheMode getSharedCacheMode() {
            return null;
        }

        @Override
        public ValidationMode getValidationMode() {
            return null;
        }

        @Override
        public Properties getProperties() {
            return null;
        }

        @Override
        public String getPersistenceXMLSchemaVersion() {
            return null;
        }

        @Override
        public ClassLoader getClassLoader() {
            return null;
        }

        @Override
        public void addTransformer(ClassTransformer transformer) {
        }

        @Override
        public ClassLoader getNewTempClassLoader() {
            return null;
        }
    }
}