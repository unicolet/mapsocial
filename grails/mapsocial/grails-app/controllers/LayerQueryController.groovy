import grails.plugins.springsecurity.Secured
import org.mappu.LayerQuery

@Secured(['IS_AUTHENTICATED_FULLY'])
class LayerQueryController {

	def lq2map = {t ->
        [guid: t.id,
         name: t.name,
         description: t.description,
         layer: t.layer,
         filterString: t.filterString]
    }
	
    def index = {
    	list()
    }
    
    def list = {
        def queries = LayerQuery.list([cache:true])
 
        render(contentType: "text/json") {
            content {
				queries.each {layerQuery(lq2map(it))}
            }
        }
    }
}
