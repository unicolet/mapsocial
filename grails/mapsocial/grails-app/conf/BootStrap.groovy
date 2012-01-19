import org.mappu.*
import org.codehaus.groovy.grails.commons.ApplicationHolder

class BootStrap {

     def init = { servletContext ->

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
				def tips=UsageTip.count()
				if(!tips) {
					def filePath = "maps_resources/tips.txt"
					
					def text = ApplicationHolder.application.parentContext.getResource("classpath:$filePath").inputStream.text
					text.eachLine {
						if(!it.startsWith("#")) { // skip comments
							def f = it.split("\\|")
							if(f.length>=3) {
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
								println "not 4 elements (${f.length}): line ${it}"
							}
						}
				   }
				} else {
					println "not adding tips, they already exist"
				}

     }
     def destroy = {
     }
} 
