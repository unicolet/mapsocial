package org.mappu
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class LayerQueryUIController {
	static scaffold = LayerQuery
	
	def index = {
		redirect(action: "list", params: params)
	}
}
