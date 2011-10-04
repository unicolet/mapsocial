class Social {
    String id;
    Boolean starred=false;
    String tags;
	String username;

    static constraints = {
    	tags(nullable:true)
    }
    
    static mapping = {
    	id generator:"assigned",column:"id"
    }
}
