package org.mappu
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class UsageTipController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [usageTipInstanceList: UsageTip.list(params), usageTipInstanceTotal: UsageTip.count()]
    }

    def create = {
        def usageTipInstance = new UsageTip()
        usageTipInstance.properties = params
        return [usageTipInstance: usageTipInstance]
    }

    def save = {
        def usageTipInstance = new UsageTip(params)
		def file = request.getFile('imageData')
		if(file) {
			log.debug("Setting mime-type="+file.getContentType())
			usageTipInstance.setMimeType(file.getContentType())
		}
        if (usageTipInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'usageTip.label', default: 'UsageTip'), usageTipInstance.id])}"
            redirect(action: "show", id: usageTipInstance.id)
        }
        else {
            render(view: "create", model: [usageTipInstance: usageTipInstance])
        }
    }

    def show = {
        def usageTipInstance = UsageTip.get(params.id)
        if (!usageTipInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usageTip.label', default: 'UsageTip'), params.id])}"
            redirect(action: "list")
        }
        else {
            [usageTipInstance: usageTipInstance]
        }
    }

    def edit = {
        def usageTipInstance = UsageTip.get(params.id)
        if (!usageTipInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usageTip.label', default: 'UsageTip'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [usageTipInstance: usageTipInstance]
        }
    }

    def update = {
        def usageTipInstance = UsageTip.get(params.id)
        if (usageTipInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (usageTipInstance.version > version) {
                    
                    usageTipInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'usageTip.label', default: 'UsageTip')] as Object[], "Another user has updated this UsageTip while you were editing")
                    render(view: "edit", model: [usageTipInstance: usageTipInstance])
                    return
                }
            }
            usageTipInstance.properties = params
			def file = request.getFile('imageData')
			if(file) {
				log.debug("Setting mime-type="+file.getContentType())
				usageTipInstance.setMimeType(file.getContentType())
			}
	
            if (!usageTipInstance.hasErrors() && usageTipInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'usageTip.label', default: 'UsageTip'), usageTipInstance.id])}"
                redirect(action: "show", id: usageTipInstance.id)
            }
            else {
                render(view: "edit", model: [usageTipInstance: usageTipInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usageTip.label', default: 'UsageTip'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def usageTipInstance = UsageTip.get(params.id)
        if (usageTipInstance) {
            try {
                usageTipInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'usageTip.label', default: 'UsageTip'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'usageTip.label', default: 'UsageTip'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usageTip.label', default: 'UsageTip'), params.id])}"
            redirect(action: "list")
        }
    }
}
