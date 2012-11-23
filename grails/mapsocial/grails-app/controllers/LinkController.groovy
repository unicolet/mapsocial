import grails.converters.JSON

import java.util.Date
import java.text.SimpleDateFormat
import grails.plugins.springsecurity.Secured
import org.mappu.Link

@Secured(['IS_AUTHENTICATED_FULLY'])
class LinkController {
 
    def index = {
    	list()
    }
    
    def list = {
    	def links = null
    	if (params.isEmpty()) {
    		links = Link.list()
        } else {
        	def c = Link.createCriteria()
        	links = c.list {
        		eq("enabled", true)
        		or{
        			if(params.featureId) eq("featureId",params.featureId)
        			if(params.layer) eq("layer",params.layer)
        			if(params.layerGroup) eq("layerGroup",params.layerGroup)
        		} // end of or
				cache true
        	}
        }
 
        render(contentType: "text/json") {
            content {
                links.each { lnk( it.this2map() ) } // apparently Groovy needs the lnk wrapper
            }
        }
    }
 
    def show = {
        if (params.id) {
            def link = Link.get(params.id as Long)
 
            if (link) {
                render(contentType: "text/json") {
                    content( link.this2map() )
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
        println "Deleting link "+params.id
        def link = Link.get(params.id as Long)
 
        if (link) {
            link.delete()
            render "" //would normally return 204 No Content here, but sc-server barfs on 0 bytes.
        }
        else {
            render text: "Link ${params.id} not found.", status: 404
        }
    }
 
    def save = { id = null ->     
    	println "Before save, id="+params.id
        def payload = JSON.parse(request.reader.text)
        def link = null
        if(params.id && !params.id.equalsIgnoreCase("undefined")) {
        	link = Link.get(params.id as Long)
        }
        if (!link) { 
        	link = new Link()
        }
 
        link.properties = payload
        if (link.save()) {
            render(contentType: "text/json") {
				content( link.this2map() )
			}
        } else {
        	link.errors.each {
        		println it
        	}
            render text: "Link could not be saved.", status: 500
        }
    }
 
    def update = {
        save(params.id)
    }
}
