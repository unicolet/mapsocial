import org.mappu.*

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

     }
     def destroy = {
     }
} 
