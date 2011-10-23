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
}
