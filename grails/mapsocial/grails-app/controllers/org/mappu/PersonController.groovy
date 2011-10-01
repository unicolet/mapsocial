package org.mappu

class PersonController {
    def scaffold = org.mappu.Person

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }
}
