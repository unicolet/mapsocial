package org.mappu

class UsageTip {
	String language
	String title
	String text
	byte[] imageData
	String mimeType
	boolean enabled=true

    static constraints = {
		language(size:2..2)
		title()
		text(widget:'textarea',size:5..2000)
		mimeType(nullable:true,editable:false)
		imageData(nullable:true)
    }
	
	static mapping = {
		cache true
	}
	
	def this2map = {
        return [guid: this.id,
         tipTitle: this.title,
         tipText: this.text,
         tipImg: this.imageData!=null ? "/mapsocial/tips/img/${t.id}" : null ]
    }
}
