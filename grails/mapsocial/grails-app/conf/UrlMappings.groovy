class UrlMappings {
    static mappings = {
        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }
        "/"(view: "/index")
        "500"(view: '/error')
        "/social/tagSummary"(controller: "social") {
            action = [GET: "tagSummary"]
        }
        "/social/$id?"(controller: "social") {
            action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
        }
        "/social/$id/comments"(controller: "social") {
            action = [GET: "comments"]
        }
        "/comment/$id?"(controller: "comment") {
            action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
        }
        "/link/$id?"(controller: "link") {
            action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
        }
        "/jts/$operation"(controller: "jts") {
            action = [GET: "exec", POST: "exec"]
        }
        "/layerQuery/$id?"(controller: "layerQuery") {
            action = [GET: "show"]
        }
    }
}
