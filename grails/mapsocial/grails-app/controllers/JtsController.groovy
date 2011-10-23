
import com.vividsolutions.jts.geom.*
import com.vividsolutions.jts.io.*
import com.vividsolutions.jts.operation.overlay.snap.*
import grails.converters.JSON

import grails.plugins.springsecurity.Secured


@Secured(['IS_AUTHENTICATED_FULLY'])
class JtsController {
	def exec = {
		def pm = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
		def fact = new GeometryFactory(pm);
		def wktRdr = new WKTReader(fact);	
		
		def text = request.reader.text
		
		if(params.operation) {			
			def geometries = text.split("\\*")
			Geometry A = selfSnap(wktRdr.read(geometries[0]))
			Geometry B = null
			Geometry C = null
			if (geometries.length==2)
				B = selfSnap(wktRdr.read(geometries[1]))
			
			if ("area".equalsIgnoreCase(params.operation))
				C = A;
			else if ("intersection".equalsIgnoreCase(params.operation))
				C = A.intersection(B);
			else if ("union".equalsIgnoreCase(params.operation))
				C = A.union(B);
			else if ("buffer".equalsIgnoreCase(params.operation)) {
				// defaults to 25
				C = A.buffer(25);
			} else if (params.operation.startsWith("buffer")) {
				// parametric buffer
				def distance=(String)params.operation.substring(6)
				C = A.buffer(Double.parseDouble(distance));
			} else {
				render text: "${params.operation} not supported.", status: 400
				return false
			}
			
			render(contentType: "text/json") {
				geom(C.toText())
				area(C.getArea())
			}
		} else {
			render text: "Please supply an operation to be performed.", status: 400
			return false
		}
	}

	def selfSnap(Geometry g)
	{
		double snapTol = GeometrySnapper.computeOverlaySnapTolerance(g);
		GeometrySnapper snapper = new GeometrySnapper(g);
		Geometry snapped = snapper.snapTo(g, snapTol);
		// need to "clean" snapped geometry - use buffer(0) as a simple way to do this
		Geometry fix = snapped.buffer(0);
		return fix;
	}
}
