import sun.awt.windows.ThemeReader;

import grails.converters.JSON
import java.util.Date
import java.text.SimpleDateFormat
import grails.plugins.springsecurity.Secured
import groovy.sql.Sql

@Secured(['IS_AUTHENTICATED_FULLY'])
class SocialController {
	def dataSource
	
	def tag2map = {t ->
		[guid: t.tag,
		 tag: t.tag,
		 occurrences: t.occurrences,
		 visible: false]
	}
	
	def socialtag2map = {t->
		[id: t.id,
		x: t.x,
		y:t.y]
	}
 
    def index = {
    	list()
    }
    
    def list = {
        def socials = Social.findAllByUsername(getPrincipal().username)

        render(contentType: "text/json") {
            content {
                socials.each { scl( it.this2map() ) }
            }
        }
    }
 
    def show = {
    	println "Requested social "+params.id
        if (params.id) {
            def social = Social.findBySocialIdAndUsername(params.id as String, getPrincipal().username, [cache:true])
 
            if (social) {
                render(contentType: "text/json") {
                    content( social.this2map() )
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
        social.starred = ( payload.starred ? true : false )
		if(payload.x && payload.y) {
	        social.x = payload.x
	        social.y = payload.y
		}
        println "Saving Social id="+social.socialId+" starred="+social.starred
        if (social.save()) {
            render(contentType: "text/json") {
				content(social.this2map())
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
					comments.each { comment(it.this2map()) }
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
				sql.eachRow( 'select * from tags where occurrences>0 order by occurrences desc' ) {
					tag ( tag2map(it) )
				}
			}
		}
	}
	
	/**
	 * returns a JSON collection of tags geometries
	 */
	def tags = {
	    if(!params.bbox || !params.tags) {
	        render text: "Missing bbox or tags parameters (both required)", status: 400
	    } else {
            def bbox=params.bbox.split(",")
            
            def queryParams = (params.tags.split(",") as List)
            def sql=Sql.newInstance(dataSource)
            def query='select distinct s.id,s.x,s.y from social_tags as st, social as s where st.social_id=s.id '+
                'and st.tag in ('+( queryParams.collect{"?"}.join(',') ) +') '+
                'and s.x <= ? and s.x >= ? and s.y <= ? and s.y >= ?'
            queryParams << (bbox[2].toDouble())
            queryParams << (bbox[0].toDouble())
            queryParams << (bbox[3].toDouble())
            queryParams << (bbox[1].toDouble()) 
            render(contentType: "text/json") {
                content {
                    sql.eachRow( query, queryParams as List ) {
                        tag ( socialtag2map(it) )
                    }
                }
            }
		}
	}
}
