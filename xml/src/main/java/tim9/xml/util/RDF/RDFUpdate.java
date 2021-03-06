package tim9.xml.util.RDF;

import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

import tim9.xml.util.AuthenticationUtilities;
import tim9.xml.util.AuthenticationUtilities.ConnectionPropertiesFusekiJena;

public class RDFUpdate {

	public static void remove(String SPARQL_NAMED_GRAPH_URI) throws Exception
    {
        ConnectionPropertiesFusekiJena conn = AuthenticationUtilities.loadPropertiesFusekiJena();
        String spargDelete = SparqlUtil.dropGraph(conn.dataEndpoint +  SPARQL_NAMED_GRAPH_URI);
        
		// UpdateRequest represents a unit of execution
		UpdateRequest update = UpdateFactory.create(spargDelete);

		UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);
		processor.execute();
    }
}
