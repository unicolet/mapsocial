class Social {
    String id;
    Boolean starred=false;
    String tags;

    static constraints = {
    	tags(nullable:true)
    }
    
    static mapping = {
    	id generator:"assigned",column:"id"
    }
}
