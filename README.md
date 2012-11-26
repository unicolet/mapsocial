Mapsocial is the name of the Grails backend for Mappu
=====================================================

Whats is Mappu? [Mappu is all this](http://unicolet.github.com/mappu/ "Mappu")

Mapsocial is the component that provides an (almost) RESTful interface to Mappu
so that it can do all the awesome stuff it does and a minimal
configuration UI. Nothing flashy here, sorry.

The gory details
----------------

Mapsocial requires Grails 1.3.6 to build.

Please note that if you just want it to use a different DataSource configuration
that can be easily achieved by:

1. defining a GRAILS_CONFIG variable which must be visible to the servlet container
   running Mapsocial. For tomcat you can easily do that dy adding this line to
   catalina.sh
```
export GRAILS_CONFIG=/a/directory/of/your/choice
```
2. create a file named mapsocial-config.properties in the directory defined at the previous item.
For an example of the content, see below
3. restart tomcat
4. deploy mapsocial if not already deployed


Sample configuration file
-------------------------

```
#
# Save in a file called mapsocial-config.properties in $GRAILS_CONFIG
# GRAILS_CONFIG is an env var visible to the servlet container
# pointing to the directory where the configs are stored
#

# default values provided as example
dataSource.driverClassName=org.postgresql.Driver
dataSource.url=jdbc:postgresql:social
dataSource.username=social
dataSource.password=social
dataSource.dialect=org.hibernate.dialect.PostgreSQLDialect
dataSource.pooled=true
```
