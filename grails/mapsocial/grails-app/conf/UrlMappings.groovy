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
		"/social/tags"(controller: "social") {
			action = [GET: "tags"]
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
		"/tips/next"(controller: "tips") {
			action = [GET: "showNext"]
		}
		"/tips/img/$id"(controller: "tips") {
			action = [GET: "img"]
		}
    }
}
