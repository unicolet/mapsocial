class Social {
	Long id;
    String socialId;
    Boolean starred=false;
    String tags;
	String username;

    static constraints = {
    	tags(nullable:true)
		socialId(unique:true)
    }
    
    static mapping = {
		cache true
		version false
    }
}
