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
}
