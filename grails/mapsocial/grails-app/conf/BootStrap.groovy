import org.mappu.*
import org.codehaus.groovy.grails.commons.ApplicationHolder
import liquibase.*
import liquibase.util.NetUtil
import liquibase.database.DatabaseFactory;
import grails.util.Environment

class BootStrap {
    
     def dataSource

     def init = { servletContext ->
         
        /****************************************
         *
         * Update database schema.
         *
         ****************************************/
        println "Liquibase: checking schema update, context="+Environment.current.toString()
        FileOpener clFO = new ClassLoaderFileOpener();
        FileOpener fsFO = new FileSystemFileOpener();

        String changelog = ApplicationHolder.application.parentContext.servletContext.getRealPath("/WEB-INF/changelog.xml")
        def changelogFile = new File(changelog)
        if( ! changelogFile.exists() ) {
            changelog="grails-app/migrations/changelog.xml"
        }
        
        Liquibase liquibase = new Liquibase(
            changelog,
            new CompositeFileOpener(clFO,fsFO),
            DatabaseFactory.getInstance().findCorrectDatabaseImplementation(dataSource.getConnection())
        );

        liquibase.update(Environment.current.toString());
        println "Liquibase: checking schema update completeted"
        
        /****************************************
         *
         * Done.
         *
         ****************************************/

        /****************************************
         *
         * Define admin user.
         *
         ****************************************/
        def user_admin = Person.findByUsername("admin")
        if (!user_admin) {
                println "Creating admin user and groups"
                def role_user = new Authority(authority:"ROLE_USER", description:"Users").save(flush:true)
                def role_superuser = new Authority(authority:"ROLE_ADMIN", description:"Administrators").save(flush:true)
                // do initial setup
                user_admin = new Person(
                   username:"admin",
                   password:"admin01",
                   enabled:true,
                   accountExpired:false,
                   accountLocked:false,
                   passwordExpired:false,
                   email: "admin@example.org",
                   realName: "The Admin"
                   ).save(flush:true)
                if (!user_admin) {
                        println "admin user creation failed"
                } else {
                        PersonAuthority.create(user_admin, role_superuser)
                        PersonAuthority.create(user_admin, role_user)
                }
        } else {
                println "admin user exists"
        }

        /****************************************
         *
         * Insert application tips.
         *
         ****************************************/
        def filePath = "maps_resources/tips.txt"
        
        def text = ApplicationHolder.application.parentContext.getResource("classpath:$filePath").inputStream.text
        text.eachLine {
            if(!it.startsWith("#")) { // skip comments
                def f = it.split("\\|")
                if(f.length>=3) {
                    if( ! UsageTip.findByTitleAndLanguage(f[1] as String,f[0] as String) ) {
                        def tip=new UsageTip()
                        tip.language=f[0]
                        tip.title=f[1]
                        tip.text=f[2]
                        if(f.length==4) {
                            tip.imageData=ApplicationHolder.application.parentContext.getResource("classpath:maps_resources/"+f[3]).getFile().getBytes()
                            tip.mimeType="image/png"
                        }
                        tip.save(flush:true);
                        println "tip ${f[1]} added"
                    } else {
                        println "tip ${f[1]} already exists"
                    }
                } else {
                    println "not 4 elements (${f.length}): line ${it}"
                }
            }
        }

     }
     def destroy = {
     }
} 
