package org.mappu
import grails.converters.JSON

import grails.plugins.springsecurity.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class TipsController {
	def tip2map = {t ->
        [guid: t.id,
         tipTitle: t.title,
         tipText: t.text,
         tipImg: t.imageData!=null ? "/mapsocial/tips/img/${t.id}" : null ]
    }

    def index = { redirect(action: "showNext", params: params) }
	
	def showNext = {
		def all=UsageTip.findAllByLanguage( params.language?"en":params.language );
		if(all.size()!=0) {
			def tip = all[ Math.floor(Math.random()*all.size()) as Integer ]
			render(contentType: "text/json") {
				content(tip2map(tip))
			}
			return false;
		}
		render(contentType: "text/json") {
			content([guid: 0,
					tipTitle: "Not found",
					tipText: "Not found",
					tipImg: null])
		}
	}
	
	def img = {
		def media = UsageTip.get( params.id )
		if (media) {
				byte[] rawData = media.imageData
				response.contentType = media.mimeType
				response.outputStream << rawData
				response.outputStream.flush()
				return false
		}
		render(contentType:"text/plain", text: "Not found")
	}
}
