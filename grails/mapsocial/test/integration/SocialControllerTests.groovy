import grails.util.JSonBuilder
import grails.web.JSONBuilder
import groovy.xml.StreamingMarkupBuilder
import javax.servlet.http.HttpServletResponse
import grails.converters.*

/*
 * Disclaimer: due to the overwhelming number of quirks with the Grails
 * framework these tests do not (sic) reflect what is actually sent back on the wire.
 * 
 * The following exceptions apply:
 * - status is ignored, so it seems to be always 200 in the tests
 * - contentType here is application/json, but the controller actually sends text/json 
 * - json is rendered uncorrectly as empty arrays are rendered as objects 
 *
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

    void testShowNonExistentSocial() {
        def tgtId=-1
        controller.params.id=tgtId
        controller.show()
        assertEquals "${tgtId} not found.", controller.response.contentAsString
        assertEquals 200, controller.response.status
    }
    
    void testShowAsList() {
        // w/o params show invokes list
        controller.show()
        println "response: "+controller.response.contentAsString
        assertEquals "items found", [:], JSON.parse(controller.response.contentAsString)?.content
        assertEquals "contentType", "application/json;charset=UTF-8", controller.response.contentType
        assertEquals "status", 200, controller.response.status
    }

    void testList() {
        // w/o params show invokes list
        controller.list()
        println "response:" + controller.response
        assertEquals "items found", [:], JSON.parse(controller.response.contentAsString)?.content
        assertEquals "contentType", "application/json;charset=UTF-8", controller.response.contentType
        assertEquals "status", 200, controller.response.status
    }
}


