package com.behdavar.backservices.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("auth.service")
public class AuthServiceProperties {

    private Jpa jpa = new Jpa();

    private Login login = new Login();

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Jpa getJpa() {
        return jpa;
    }

    public void setJpa(Jpa jpa) {
        this.jpa = jpa;
    }

    public static class Jpa {

        private DataSource dataSource = new DataSource();
        private Hibernate hibernate = new Hibernate();

        public DataSource getDataSource() {
            return dataSource;
        }

        public void setDataSource(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        public Hibernate getHibernate() {
            return hibernate;
        }

        public void setHibernate(Hibernate hibernate) {
            this.hibernate = hibernate;
        }

        public static class DataSource {
            private String url;
            private String username;
            private String password;
            private Driver driver = new Driver();
            private Hikari hikari = new Hikari();

            public static class Driver {
                private String className;

                public String getClassName() {
                    return className;
                }

                public void setClassName(String className) {
                    this.className = className;
                }
            }

            public static class Hikari {
                private String maximumPoolSize;

                public String getMaximumPoolSize() {
                    return maximumPoolSize;
                }

                public void setMaximumPoolSize(String maximumPoolSize) {
                    this.maximumPoolSize = maximumPoolSize;
                }
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public Driver getDriver() {
                return driver;
            }

            public void setDriver(Driver driver) {
                this.driver = driver;
            }

            public Hikari getHikari() {
                return hikari;
            }

            public void setHikari(Hikari hikari) {
                this.hikari = hikari;
            }
        }

        public static class Hibernate {
            private String orderInserts;
            private String orderUpdates;
            private String dialect;
            private String showSql;
            private Hbm2ddl hbm2ddl = new Hbm2ddl();
            private Jdbc jdbc = new Jdbc();
            private Cache cache = new Cache();

            public static class Hbm2ddl {
                private String auto;

                public String getAuto() {
                    return auto;
                }

                public void setAuto(String auto) {
                    this.auto = auto;
                }
            }

            public static class Jdbc {
                private Integer batchSize;

                public Integer getBatchSize() {
                    return batchSize;
                }

                public void setBatchSize(Integer batchSize) {
                    this.batchSize = batchSize;
                }
            }

            public static class Cache {
                private Boolean useSecondLevelCache;
                private Region region = new Region();

                public Boolean getUseSecondLevelCache() {
                    return useSecondLevelCache;
                }

                public void setUseSecondLevelCache(Boolean useSecondLevelCache) {
                    this.useSecondLevelCache = useSecondLevelCache;
                }

                public Region getRegion() {
                    return region;
                }

                public void setRegion(Region region) {
                    this.region = region;
                }

                public static class Region {
                    private String factoryClass;

                    public String getFactoryClass() {
                        return factoryClass;
                    }

                    public void setFactoryClass(String factoryClass) {
                        this.factoryClass = factoryClass;
                    }
                }
            }

            public String getOrderInserts() {
                return orderInserts;
            }

            public void setOrderInserts(String orderInserts) {
                this.orderInserts = orderInserts;
            }

            public String getOrderUpdates() {
                return orderUpdates;
            }

            public void setOrderUpdates(String orderUpdates) {
                this.orderUpdates = orderUpdates;
            }

            public String getDialect() {
                return dialect;
            }

            public void setDialect(String dialect) {
                this.dialect = dialect;
            }

            public String getShowSql() {
                return showSql;
            }

            public void setShowSql(String showSql) {
                this.showSql = showSql;
            }

            public Hbm2ddl getHbm2ddl() {
                return hbm2ddl;
            }

            public void setHbm2ddl(Hbm2ddl hbm2ddl) {
                this.hbm2ddl = hbm2ddl;
            }

            public Jdbc getJdbc() {
                return jdbc;
            }

            public void setJdbc(Jdbc jdbc) {
                this.jdbc = jdbc;
            }

            public Cache getCache() {
                return cache;
            }

            public void setCache(Cache cache) {
                this.cache = cache;
            }
        }
    }

    public static class Login {
        private Login.Page page = new Login.Page();

        public Page getPage() {
            return page;
        }

        public void setPage(Page page) {
            this.page = page;
        }

        public static class Page {
            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
