import grails.converters.JSON
import java.util.Date
import java.text.SimpleDateFormat

import grails.plugins.springsecurity.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class CommentController {
	
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
         starred: t.starred]
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
        def comments = Comment.list()
 
        render(contentType: "text/json") {
            content {
            	array {
            		comments.each {comment(comment2map(it))}
            	}
            }
        }
    }
 
    def show = {
    	println "Requested comment "+params.id
        if (params.id) {
            def comment = Comment.get(params.id as Long)
 
            if (comment) {
                render(contentType: "text/json") {
                    content(comment2map(comment))
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
        println "Deleting comment "+params.id
        def comment = Comment.get(params.id as Long)
 
        if (comment) {
            comment.delete()
            render "" //would normally return 204 No Content here, but sc-server barfs on 0 bytes.
        }
        else {
            render text: "Comment ${params.id} not found.", status: 404
        }
    }
 
    def save = { id = null ->     
    	println "Before save, id="+params.id
        def payload = JSON.parse(request.reader.text)
        def comment = null
        if(params.id && !params.id.equalsIgnoreCase("undefined")) {
        	comment = Comment.get(params.id as Long)
        }
        if (!comment) { 
        	comment = new Comment()
			comment.username=getPrincipal().username
        }
 
        comment.properties = payload
        println "Saving Comment with social="+comment.social
        if (comment.save()) {
        	println "Saved Comment with social="+comment.social+" id="+comment.id
            render(contentType: "text/json") {
				content(comment2map(comment))
			}
        } else {
        	comment.errors.each {
        		println it
        	}
            render text: "Comment could not be saved.", status: 500
        }
    }
 
    def update = {
        save(params.id)
    }
}
