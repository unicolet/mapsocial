import grails.test.*
import org.mappu.*

class DomainClassesTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testCommentToMapMethods() {
        def now=new Date()
        def commentText="the text"
        
        def comment=new Comment(
            id:1,
            text:commentText,
            dateCreated:now,
            lastUpdated:now,
            social:"a:b:c",
            username:"me")
        
        def map=comment.this2map()
        
        assertEquals 1, map.guid // id has been remapped to guid!
        assertEquals commentText, map.text
        assertEquals Utils.getDateAsISO8601String(now), map.dateCreated
        assertEquals Utils.getDateAsISO8601String(now), map.lastUpdated
    }
    
    void testSocialToMapMethods() {
        def now=new Date()
        def tags="the text"
        
        def social=new Social(
            socialId: 2,
            id:1,
            tags:tags,
            starred:false,
            x: 10,
            y: 20,
            username:"me")
        
        def map=social.this2map()
        
        assertEquals "2", map.guid // socialid is the id and has been remapped to guid!
        assertEquals tags, map.tags
        assertEquals 10, map.x
        assertEquals false, map.starred
    }
    
    void testLinkToMapMethods() {
        String descr="Description"
        String title="Title"
        
        def link=new Link(
            layerGroup: "G",
            layer: "L",
            featureId: null,
            url: "/url/{{mustache_expr}}/index.php",
            description: descr,
            'title': title,
            enabled: true)
	
        def map=link.this2map()
        
        assertEquals null, map.guid // socialid is the id and has been remapped to guid!
        assertEquals "G", map.layerGroup
        assertEquals true, map.enabled
        assertEquals "/url/{{mustache_expr}}/index.php", map.url
        assertEquals descr, map.description
    }
    
    void testTipToMapMethods() {
        def tip=new UsageTip(
            id:1,
            title:"title",
            text:"text",
            mimeType:null)
            
       def map=tip.this2map()
       
       assertEquals 1, map.guid
       assertEquals "title", map.tipTitle
       assertEquals "text", map.tipText
       assertEquals null, map.enabled // not included into the map
       assertEquals null, map.tipImg // no img
    }
}
