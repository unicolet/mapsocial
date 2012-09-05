package org.mappu
class Link {
	String layerGroup
	String layer
	String featureId
	String url
	String description
	String title
	Boolean enabled=true
	
    static constraints = {
    	layerGroup(nullable:true)
    	featureId(nullable:true)
    	layer(nullable:true)
    }
    
    public String toString() {
    	return "[ Link { id:${id},enabled:${enabled},layer:${layer},layerGroup:${layerGroup},url:${url}}]"
    }
	
	static mapping = {
		cache true
	}
	
	def this2map = {
        return [guid: this.id,
         layerGroup: this.layerGroup,
         layer: this.layer,
         featureId: this.featureId,
         enabled: this.enabled,
         url: this.url,
         description: this.description,
         title: this.title]
    }
}
