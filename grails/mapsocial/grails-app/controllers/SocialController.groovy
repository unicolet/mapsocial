import grails.converters.JSON
import java.util.Date
import java.text.SimpleDateFormat
import grails.plugins.springsecurity.Secured
import groovy.sql.Sql

@Secured(['IS_AUTHENTICATED_FULLY'])
class SocialController {
	def dataSource
	
	def getDateAsISO8601String(date) {
		SimpleDateFormat ISO8601FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		String result = ISO8601FORMAT.format(date);
		result = result.substring(0, result.length()-2) + ":" + result.substring(result.length()-2);
		return result;
  	}
 
    def social2map = {t ->
        [guid: t.socialId,
         tags: t.tags,
		 username: t.username,
         starred: t.starred,
		 x:t.x,
		 y:t.y]
    }
	
	def tag2map = {t ->
		[guid: t.tag,
		 tag: t.tag,
		 occurrences: t.occurrences,
		 visible: false]
	}
    
    def comment2map = {t ->
        [guid: t.id,
         text: t.text,
         social: t.social,
		 username: t.username,
         dateCreated: getDateAsISO8601String(t.dateCreated),
         lastUpdated: getDateAsISO8601String(t.lastUpdated) ]
    }
 
    def index = {
    	list()
    }
    
    def list = {
        def socials = Social.findAllByUsername(getPrincipal().username)
 
        render(contentType: "text/json") {
            content {
            	array {
            		socials.each {social(social2map(it))}
            	}
            }
        }
    }
 
    def show = {
    	println "Requested social "+params.id
        if (params.id) {
            def social = Social.findBySocialIdAndUsername(params.id as String, getPrincipal().username, [cache:true])
 
            if (social) {
                render(contentType: "text/json") {
                    content(social2map(social))
                }
            }
            else {
                render text: "${params.id} not found.", status: 404
            }
        }
        else {
            list()
        }
    }
 
    def delete = {
        def social = Social.findBySocialIdAndUsername(params.id as String, getPrincipal().username)
 
        if (social) {
            social.delete()
            render "" //would normally return 204 No Content here, but sc-server barfs on 0 bytes.
        }
        else {
            render text: "Social ${params.id} not found.", status: 404
        }
    }
 
    def save = { id = null ->                                                                                                              
        def payload = JSON.parse(request.reader.text)
        def social = null
        if(params.id) {
        	social = Social.findBySocialIdAndUsername(params.id as String, getPrincipal().username)
        }
        if (!social) { 
        	social = new Social()
        	social.socialId = params.id
			social.username=getPrincipal().username
        }
 
        social.tags = payload.tags
        social.starred = payload.starred
		if(payload.x && payload.y) {
	        social.x = payload.x
	        social.y = payload.y
		}
        println "Saving Social id="+social.socialId+" starred="+social.starred
        if (social.save()) {
            render(contentType: "text/json") {
				content(social2map(social))
			}
        } else {
        	social.errors.each {
        		println it
        	}
            render text: "Social could not be saved.", status: 500
        }
    }
 
    def update = {
        save(params.id)
    }
    
    def comments = {
    	println "Retrieving comments for: ${params.id}"
    	def comments = Comment.findAll("from Comment as c where c.social=? order by c.dateCreated asc",[params.id],[cache:true])
    	if (comments) {
    		render(contentType: "text/json") {
				content {
					comments.each { comment(comment2map(it)) }
				}
			}
    	} else {
    		render(contentType: "text/json") {
				content {}
			}
    	}
    }
	
	/**
	 * Return tags summary by reading the tags table.
	 * Tags tables are kept in-sync by a stored procedure.
	 */
	def tagSummary = {
		def sql=Sql.newInstance(dataSource)
		render(contentType: "text/json") {
			content {
				sql.eachRow( 'select * from tags order by occurrences desc' ) {
					tag ( tag2map(it) )
				}
			}
		}
	}
}
