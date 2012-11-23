import grails.util.JSonBuilder
import grails.web.JSONBuilder
import groovy.xml.StreamingMarkupBuilder
import javax.servlet.http.HttpServletResponse
import grails.converters.*

/**
 *   Unit tests for LinkController
 */
class LinkControllerTests extends grails.test.ControllerUnitTestCase {
    
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
    
    /**
     * This controller uses only the list method as all links are fetched at startup.
     * SC local queries are used from then on.
     *
     */
    void testListWithParams() {
        controller.params.layer="notpresent"
        // w/o params show invokes list
        controller.show()

        assertTrue "contentType", controller.response.contentType.contains("json")
        assertEquals "json", "{\"content\":[]}", controller.response.contentAsString
        assertEquals "items found", [], JSON.parse(controller.response.contentAsString)?.content
    }
    
    /**
     * This controller uses only the list method as all links are fetched at startup.
     * SC local queries are used from then on.
     *
     */
    void testListWithMatchingParams() {
        controller.params.layer="states"
        // w/o params show invokes list
        controller.show()

        assertTrue "contentType", controller.response.contentType.contains("json")
        assertEquals "json", "{\"content\":[{\"guid\":1,\"layerGroup\":\"top\",\"layer\":\"states\",\"featureId\":\"\",\"enabled\":true,\"url\":\"the_url\",\"description\":\"descr\",\"title\":\"title\"}]}", controller.response.contentAsString
        assertEquals "items found", [["guid":1,"layerGroup":"top","layer":"states","featureId":"","enabled":true,"url":"the_url","description":"descr","title":"title"]], JSON.parse(controller.response.contentAsString)?.content
    }


    void testList() {
        // w/o params show invokes list
        controller.show()

        assertTrue "contentType", controller.response.contentType.contains("json")
        assertEquals "json", "{\"content\":[{\"guid\":1,\"layerGroup\":\"top\",\"layer\":\"states\",\"featureId\":\"\",\"enabled\":true,\"url\":\"the_url\",\"description\":\"descr\",\"title\":\"title\"}]}", controller.response.contentAsString
        assertEquals "items found", [["guid":1,"layerGroup":"top","layer":"states","featureId":"","enabled":true,"url":"the_url","description":"descr","title":"title"]], JSON.parse(controller.response.contentAsString)?.content
    }
    
    void testShowLinkById() {
        controller.params.id=1
        // w/o params show invokes list
        controller.show()

        assertTrue "contentType", controller.response.contentType.contains("json")
        assertEquals "json", "{\"content\":{\"guid\":1,\"layerGroup\":\"top\",\"layer\":\"states\",\"featureId\":\"\",\"enabled\":true,\"url\":\"the_url\",\"description\":\"descr\",\"title\":\"title\"}}", controller.response.contentAsString
        assertEquals "items found", ["guid":1,"layerGroup":"top","layer":"states","featureId":"","enabled":true,"url":"the_url","description":"descr","title":"title"], JSON.parse(controller.response.contentAsString)?.content
    }

}


