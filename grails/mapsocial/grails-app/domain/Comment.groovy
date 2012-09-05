class Comment {
    String text;
    Date dateCreated;
    Date lastUpdated;
    // this is a feature_id really used to look up the comments
    String social;
	String username;

    static constraints = {
    }
	
	static mapping = {
		cache true
	}
	
	def this2map = {
	    return [guid: this.id,
             text: this.text,
             social: this.social,
             username: this.username,
             dateCreated: Utils.getDateAsISO8601String(this.dateCreated),
             lastUpdated: Utils.getDateAsISO8601String(this.lastUpdated) ]
    }
}
