dataSource {
        pooled = true
        driverClassName = "org.postgresql.Driver"
        username = "social"
        password = "social"
        dialect = org.hibernate.dialect.PostgreSQLDialect
}
hibernate {
    show_sql=true
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
        development {
                dataSource {
                        dbCreate = "" // one of 'create', 'create-drop','update'
                        url = "jdbc:postgresql:social"
                }
        }
        test {
                dataSource {
                        dbCreate = ""
                        username = "social_test"
                        url = "jdbc:postgresql:social_test"
                }
                hibernate {
					show_sql=false
				}
        }
        production {
                dataSource {
                        dbCreate = ""
                        url = "jdbc:postgresql:social"
                }
				hibernate {
					show_sql=false
				}
        }
}

