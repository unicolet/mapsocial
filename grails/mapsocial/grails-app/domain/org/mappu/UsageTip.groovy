package org.mappu

class UsageTip {
	String language
	String title
	String text
	byte[] imageData
	String mimeType

    static constraints = {
		language(size:2..2)
		title()
		text(widget:'textarea')
		mimeType(nullable:true,editable:false)
		imageData(nullable:true)
    }
	
	static mapping = {
		cache true
	}
}
