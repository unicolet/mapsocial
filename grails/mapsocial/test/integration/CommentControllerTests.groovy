import grails.util.JSonBuilder
import grails.web.JSONBuilder
import groovy.xml.StreamingMarkupBuilder
import javax.servlet.http.HttpServletResponse
import grails.converters.*

/**
 *   Unit tests for CommentController
 */
class CommentControllerTests extends grails.test.ControllerUnitTestCase {
    
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
                //def builder = new grails.util.JSonBuilder(delegate.response)
                //builder.json(c)
                
                // new builder
                new JSONBuilder().build(c).render(delegate.response)
                
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

    void testList() {
        
        controller.list()

        assertTrue "items not found", JSON.parse(controller.response.contentAsString)?.content.size() > 0
        assertTrue "contentType", controller.response.contentType.contains("json")
    }
}


