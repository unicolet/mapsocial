// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

//grails.config.locations = [ "classpath:ds-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]
// The default codec used to encode data with ${}
grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"
grails.converters.encoding="UTF-8"

// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://www.changeme.com"
    }
}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    warn  'org.codehaus.groovy.grails.web.servlet',  //  controllers
	       'org.codehaus.groovy.grails.web.pages', //  GSP
	       'org.codehaus.groovy.grails.web.sitemesh', //  layouts
	       'org.codehaus.groovy.grails."web.mapping.filter', // URL mapping
	       'org.codehaus.groovy.grails."web.mapping', // URL mapping
	       'org.codehaus.groovy.grails.commons', // core / classloading
	       'org.codehaus.groovy.grails.plugins', // plugins
	       'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
	       'org.springframework',
	       'org.hibernate'

    warn   'org.mortbay.log'
}

environments {
	
	production {
		def catalinaBase = System.properties.getProperty('catalina.base')
		if (!catalinaBase) catalinaBase = '.'   // just in case
		def logDirectory = "${catalinaBase}/logs"

		log4j = {
			appenders {
				// set up a log file in the standard tomcat area; be sure to use .toString() with ${}
				rollingFile name:'tomcatLog', file:"${logDirectory}/${appName}.log".toString(), maxFileSize:'100KB'
				'null' name:'stacktrace'
			}

			root {
				// change the root logger to my tomcatLog file
				error 'tomcatLog'
				additivity = true
			}

			// example for sending stacktraces to my tomcatLog file
			error tomcatLog:'StackTrace'

			// set level for my messages; this uses the root logger (and thus the tomcatLog file)
			info 'grails.app'
		}
	}
	test {
		def catalinaBase = System.properties.getProperty('catalina.base')
		if (!catalinaBase) catalinaBase = '.'   // just in case
		def logDirectory = "${catalinaBase}/logs"

		log4j = {
			appenders {
				// set up a log file in the standard tomcat area; be sure to use .toString() with ${}
				rollingFile name:'tomcatLog', file:"${logDirectory}/${appName}.log".toString(), maxFileSize:'100KB'
				'null' name: 'stacktrace'
			}

			root {
				// change the root logger to my tomcatLog file
				error 'tomcatLog'
				additivity = true
			}

			// example for sending stacktraces to my tomcatLog file
			error tomcatLog:'StackTrace'

			// set level for my messages; this uses the root logger (and thus the tomcatLog file)
			debug 'grails.app'
		}
	}
	development {
		def catalinaBase = System.properties.getProperty('catalina.base')
		if (!catalinaBase) catalinaBase = '.'   // just in case
		def logDirectory = "${catalinaBase}/logs"

		log4j = {
			appenders {
				// set up a log file in the standard tomcat area; be sure to use .toString() with ${}
				rollingFile name:'tomcatLog', file:"${logDirectory}/${appName}.log".toString(), maxFileSize:'100KB'
				'null' name: 'stacktrace'
			}

			root {
				// change the root logger to my tomcatLog file
				error 'tomcatLog'
				additivity = true
			}

			// example for sending stacktraces to my tomcatLog file
			error tomcatLog:'StackTrace'

			// set level for my messages; this uses the root logger (and thus the tomcatLog file)
			debug 'grails.app'
		}
	}
}

     
// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'org.mappu.Person'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'org.mappu.PersonAuthority'
grails.plugins.springsecurity.authority.className = 'org.mappu.Authority'
//grails.plugins.springsecurity.dao.reflectionSaltSourceProperty = 'username'
//grails.plugins.springsecurity.password.algorithm='SHA-512'

if (appName) {
	def ENV_NAME = "GRAILS_CONFIG"   
	if(!grails.config.locations || !(grails.config.locations instanceof List)) {
		grails.config.locations = []
	}
	if(System.getenv(ENV_NAME)) {
		println "Including configuration file specified in environment: " + System.getenv(ENV_NAME) + "/${appName}-config.properties";
		grails.config.locations << "file:" + System.getenv(ENV_NAME) + "/${appName}-config.properties"
	} else if(System.getProperty(ENV_NAME)) {
            println "Including configuration file specified on command line: " + System.getProperty(ENV_NAME)+ "/${appName}-config.properties";
            grails.config.locations << "file:" + System.getProperty(ENV_NAME)+ "/${appName}-config.properties"
    } 
    else {
        println "No external configuration file defined."
    }
}
