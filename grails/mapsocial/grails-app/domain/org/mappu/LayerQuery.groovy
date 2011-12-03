package org.mappu
class LayerQuery {
	
	String name
	String description
	String filterString
	// in the group:layer format
	String layer

    static constraints = {
    }
	
	static mapping = {
		cache true
	}
}
