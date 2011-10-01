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
}
