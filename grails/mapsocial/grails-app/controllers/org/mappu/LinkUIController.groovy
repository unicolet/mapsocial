package org.mappu
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class LinkUIController {
	static scaffold = Link

    def index = {
		redirect(action: "list", params: params)
	}
}
