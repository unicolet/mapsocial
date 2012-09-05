class Social {
	Long id;
    String socialId;
    Boolean starred=false;
    String tags;
	String username;
	Double x,y;

    static constraints = {
    	tags(nullable:true)
    }
    
    static mapping = {
		cache true
		version false
    }
    
    def this2map = {
        return [guid: this.socialId,
         tags: this.tags,
		 username: this.username,
         starred: this.starred,
		 x:this.x,
		 y:this.y]
    }
}
