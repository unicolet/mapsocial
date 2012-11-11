import grails.util.JSonBuilder
import grails.web.JSONBuilder
import groovy.xml.StreamingMarkupBuilder
import javax.servlet.http.HttpServletResponse
import grails.converters.*

/**
 *   Unit tests for SocialController
 */
class SocialControllerTests extends grails.test.ControllerUnitTestCase {
    
    protected void setUp() {
        super.setUp();
        controller.metaClass.getPrincipal = { return [username:'testcase'] }
        // got this from here:
        // http://www.lucasward.net/2011/03/grails-testing-issue-when-rendering-as.html
        controller.metaClass.render = {Map map, Closure c ->
            renderArgs.putAll(map)

            switch(map["contentType"]) {
            case null:
                break

            case "application/xml":
            case "text/xml":
                def b = new StreamingMarkupBuilder()
                if (map["encoding"]) b.encoding = map["encoding"]

                def writable = b.bind(c)
                delegate.response.outputStream << writable
                break

            case "text/json":
                // old builder
                def builder = new grails.util.JSonBuilder(delegate.response)
                builder.json(c)
                
                // new builder
                //new JSONBuilder().build(c).render(delegate.response)
                
                break
            default:
                println "Can't handle this contentType: "+map["contentType"]
                break
            }
        }
    }
    
    protected void tearDown() {
        super.tearDown();
        controller.metaClass.getPrincipal = null;
    }

    void testShowNonExistentSocial() {
        def tgtId=-1
        controller.params.id=tgtId
        controller.show()
        assertEquals "${tgtId} not found.", controller.response.contentAsString
    }
    
    void testShowAsList() {
        // w/o params show invokes list
        controller.show()

        assertEquals "json", "{\"content\":[]}", controller.response.contentAsString
        assertEquals "items found", [], JSON.parse(controller.response.contentAsString)?.content
        assertTrue "contentType", controller.response.contentType.contains("json")
    }

    void testList() {
        // w/o params show invokes list
        controller.list()

        assertEquals "json", "{\"content\":[]}", controller.response.contentAsString
        assertEquals "items found", [], JSON.parse(controller.response.contentAsString)?.content
        assertTrue "contentType", controller.response.contentType.contains("json")
    }
    
    void testListFeatureComments() {
        controller.params.id="topp:states:1"
        controller.comments()
        
        println controller.response.contentAsString
        def response = JSON.parse(controller.response.contentAsString)
        assertTrue "contentType", controller.response.contentType.contains("json")
        assertTrue "items not found", response?.content.size() > 0
        assertEquals "comment_text", "this is the comment text", response.content[0]['text']
        assertEquals "username", "demo", response.content[0]['username']
        assertEquals "created", "2012-11-11T01:02:30+01:00", response.content[0]['dateCreated']
        assertEquals "social", "topp:states:1", response.content[0]['social']
    }

    void testListFeatureWithoutComments() {
        controller.params.id="topp:states:0"
        controller.comments()
        
        println controller.response.contentAsString
        def response = JSON.parse(controller.response.contentAsString)
        assertTrue "items not found", response?.content.size() == 0
        assertTrue "contentType", controller.response.contentType.contains("json")
    }
}


