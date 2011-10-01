
import com.vividsolutions.jts.geom.*
import com.vividsolutions.jts.io.*
import com.vividsolutions.jts.operation.overlay.snap.*

class JtsController {
	def exec = {
		def pm = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
		def fact = new GeometryFactory(pm);
		def wktRdr = new WKTReader(fact);	
		
		def text = request.reader.text
		
		println "Operation: "+params.operation
		//println "Body: "+text
		
		def geometries = text.split("\\*")
		Geometry A = selfSnap(wktRdr.read(geometries[0]))
		Geometry B = null
		Geometry C = null
		if (geometries.length==2)
			B = selfSnap(wktRdr.read(geometries[1]))
		
		if ("intersection".equalsIgnoreCase(params.operation))
			C = A.intersection(B);
		else if ("union".equalsIgnoreCase(params.operation))
			C = A.union(B);
		else if ("buffer".equalsIgnoreCase(params.operation))
			C = A.buffer(20);
		else {
			render text: "${params.operation} not supported.", status: 404
			return false
		}	
		
		render C.toText()
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
