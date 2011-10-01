import grails.converters.JSON
import java.util.Date
import java.text.SimpleDateFormat

class LinkController {
	
	def getDateAsISO8601String(date) {
		SimpleDateFormat ISO8601FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		String result = ISO8601FORMAT.format(date);
		result = result.substring(0, result.length()-2) + ":" + result.substring(result.length()-2);
		return result;
  	}
 
    def link2map = {t ->
        [guid: t.id,
         layerGroup: t.layerGroup,
         layer: t.layer,
         featureId: t.featureId,
         enabled: t.enabled,
         url: t.url,
         description: t.description,
         title: t.title]
    }
 
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
        	}
        }
        println links
 
        render(contentType: "text/json") {
            content {
				links.each {lnk(link2map(it))}
            }
        }
    }
 
    def show = {
    	println "Requested link "+params.id
        if (params.id) {
            def link = Link.get(params.id as Long)
 
            if (link) {
                render(contentType: "text/json") {
                    content(link2map(link))
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
        println "Saving Link with social="+link.social
        if (link.save()) {
        	println "Saved Link with social="+link.social+" id="+link.id
            render(contentType: "text/json") {
				content(link2map(link))
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
