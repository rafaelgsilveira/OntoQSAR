package ontologia;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.impl.LiteralLabel;
import org.apache.jena.iri.impl.Main;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.SysRIOT;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDF;
import org.apache.log4j.PropertyConfigurator;

public class main {
	public static void main(String[] args){

		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
		FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
		Model model = FileManager.get().loadModel("C:/Users/matheus19/Downloads/englishOntology_populada.owl");
		
		//-----busca pela molecular_mass-----
		//INIBIDOR_MM_menos_500(model);
		//INIBIDOR_MM_maior_500(model);
		
		//-----busca pelos bbb-----
		//INIBIDOR_true_BBB(model);
		//INIBIDOR_false_BBB(model);
		
		//-----busca pelo lipinski-----
		//INIBIDOR_true_lipinski(model);
		//INIBIDOR_false_lipinski(model);
	}
	
static void INIBIDOR_MM_menos_500(Model model){
	
	//query in sparkle
	String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
			"PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
			"PREFIX dbp: <http://dbpedia.org/property/>"+
					
			"SELECT ?inhibitor ?chemical_descriptor ?subject ?value_molecularMass"+
			"WHERE {?subject  <http://www.semanticweb.org/matheus19/ontologies/2019/1/untitled-ontology-14#value_molecularMass> ?value_molecularMass."+
			"?chemical_descriptor <http://www.semanticweb.org/matheus19/ontologies/2019/1/untitled-ontology-14#sub_has_the> ?subject."+
			"?inhibitor <http://www.semanticweb.org/matheus19/ontologies/2019/1/untitled-ontology-14#has_the> ?chemical_descriptor."+
			"FILTER (?value_molecularMass < 500.0)}";
			
	
	Query query = QueryFactory.create(queryString);
	QueryExecution qexec = QueryExecutionFactory.create(query,model);
	
	try{
		ResultSet results = qexec.execSelect();
		//ResultSetFormatter.out(System.out, results, query) ;
		while(results.hasNext()){
			QuerySolution soln = results.nextSolution();
			
			//pega somente o inibidor
			RDFNode name = soln.getResource("inhibitor");
			Node a = name.asNode();
			String inibidor = a.toString();
			int aux=0;
			
			//faz um for pra imprimir somente o inibidor e não toda a URI
			for (int i = 0; i < inibidor.length(); i++) {
				if(inibidor.charAt(i)=='#')break;
				aux++;
			}
			
			aux++;
			
			for (int i = aux; i < inibidor.length(); i++) {
				System.out.print(inibidor.charAt(i));
			}
			System.out.println();		
		}
	}finally{
		qexec.close();
	}
}
static void INIBIDOR_MM_maior_500(Model model){
		
		//query in sparkle
		String queryString = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				"PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
				"PREFIX dbp: <http://dbpedia.org/property/>"+
						
				"SELECT ?inhibitor ?chemical_descriptor ?subject ?value_molecularMass"+
				"WHERE {?subject  <http://www.semanticweb.org/matheus19/ontologies/2019/1/untitled-ontology-14#value_molecularMass> ?value_molecularMass."+
				"?chemical_descriptor <http://www.semanticweb.org/matheus19/ontologies/2019/1/untitled-ontology-14#sub_has_the> ?subject."+
				"?inhibitor <http://www.semanticweb.org/matheus19/ontologies/2019/1/untitled-ontology-14#has_the> ?chemical_descriptor."+
				"FILTER (?value_molecularMass > 500.0)}";
				
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query,model);
		
		try{
			ResultSet results = qexec.execSelect();
			//ResultSetFormatter.out(System.out, results, query) ;
			while(results.hasNext()){
				QuerySolution soln = results.nextSolution();
				
				//pega somente o inibidor
				RDFNode name = soln.getResource("inhibitor");
				Node a = name.asNode();
				String inibidor = a.toString();
				int aux=0;
				
				//faz um for pra imprimir somente o inibidor e não toda a URI
				for (int i = 0; i < inibidor.length(); i++) {
					if(inibidor.charAt(i)=='#')break;
					aux++;
				}
				
				aux++;
				
				for (int i = aux; i < inibidor.length(); i++) {
					System.out.print(inibidor.charAt(i));
				}
				System.out.println();		
			}
		}finally{
			qexec.close();
		}
	}

static void INIBIDOR_true_BBB(Model model){
	//query in sparkle
	String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
			"PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
			
			"SELECT ?inhibitor ?chemical_descriptor ?subject ?value_bbb"+
			"WHERE {?subject <http://www.semanticweb.org/matheus19/ontologies/2019/1/untitled-ontology-14#value_bbb> ?value_bbb."+
			"?chemical_descriptor <http://www.semanticweb.org/matheus19/ontologies/2019/1/untitled-ontology-14#sub_has_the> ?subject."+
			"?inhibitor <http://www.semanticweb.org/matheus19/ontologies/2019/1/untitled-ontology-14#has_the> ?chemical_descriptor."+
			" FILTER (?value_bbb = true)}";
	
			Query query = QueryFactory.create(queryString);
			QueryExecution qexec = QueryExecutionFactory.create(query,model);
			
			try{
				ResultSet results = qexec.execSelect();
				//ResultSetFormatter.out(System.out, results, query) ;
				while(results.hasNext()){
					QuerySolution soln = results.nextSolution();
					
					//pega somente o inibidor
					RDFNode name = soln.getResource("inhibitor");
					Node a = name.asNode();
					String inibidor = a.toString();
					int aux=0;
					
					//faz um for pra imprimir somente o inibidor e não toda a URI
					for (int i = 0; i < inibidor.length(); i++) {
						if(inibidor.charAt(i)=='#')break;
						aux++;
					}
					
					aux++;
					
					for (int i = aux; i < inibidor.length(); i++) {
						System.out.print(inibidor.charAt(i));
					}
					System.out.println();		
				}
			}finally{
				qexec.close();
			}
		}
static void INIBIDOR_false_BBB(Model model){
	//query in sparkle
		String queryString = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				"PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
				
				"SELECT ?inhibitor ?chemical_descriptor ?subject ?value_bbb"+
				"WHERE {?subject <http://www.semanticweb.org/matheus19/ontologies/2019/1/untitled-ontology-14#value_bbb> ?value_bbb."+
				"?chemical_descriptor <http://www.semanticweb.org/matheus19/ontologies/2019/1/untitled-ontology-14#sub_has_the> ?subject."+
				"?inhibitor <http://www.semanticweb.org/matheus19/ontologies/2019/1/untitled-ontology-14#has_the> ?chemical_descriptor."+
				" FILTER (?value_bbb = false)}";
		
				Query query = QueryFactory.create(queryString);
				QueryExecution qexec = QueryExecutionFactory.create(query,model);
				
				try{
					ResultSet results = qexec.execSelect();
					//ResultSetFormatter.out(System.out, results, query) ;
					while(results.hasNext()){
						QuerySolution soln = results.nextSolution();
						
						//pega somente o inibidor
						RDFNode name = soln.getResource("inhibitor");
						Node a = name.asNode();
						String inibidor = a.toString();
						int aux=0;
						
						//faz um for pra imprimir somente o inibidor e não toda a URI
						for (int i = 0; i < inibidor.length(); i++) {
							if(inibidor.charAt(i)=='#')break;
							aux++;
						}
						
						aux++;
						
						for (int i = aux; i < inibidor.length(); i++) {
							System.out.print(inibidor.charAt(i));
						}
						System.out.println();		
					}
				}finally{
					qexec.close();
				}
			}

static void INIBIDOR_true_lipinski(Model model){
	
	//query in sparkle
	String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
			"PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
			
			"SELECT ?inhibitor ?chemical_descriptor ?subject ?lipinski"+
			"WHERE {?subject <http://www.semanticweb.org/matheus19/ontologies/2019/1/untitled-ontology-14#yes/no> ?lipinski."+
			"?chemical_descriptor <http://www.semanticweb.org/matheus19/ontologies/2019/1/untitled-ontology-14#sub_has_the> ?subject."+
			"?inhibitor <http://www.semanticweb.org/matheus19/ontologies/2019/1/untitled-ontology-14#has_the> ?chemical_descriptor."+
			"FILTER (?lipinski = true)}";
	
			Query query = QueryFactory.create(queryString);
			QueryExecution qexec = QueryExecutionFactory.create(query,model);
			
			try{
				ResultSet results = qexec.execSelect();
				//ResultSetFormatter.out(System.out, results, query) ;
				while(results.hasNext()){
					QuerySolution soln = results.nextSolution();
					
					//pega somente o inibidor
					RDFNode name = soln.getResource("inhibitor");
					Node a = name.asNode();
					String inibidor = a.toString();
					int aux=0;
					
					//faz um for pra imprimir somente o inibidor e não toda a URI
					for (int i = 0; i < inibidor.length(); i++) {
						if(inibidor.charAt(i)=='#')break;
						aux++;
					}
					
					aux++;
					
					for (int i = aux; i < inibidor.length(); i++) {
						System.out.print(inibidor.charAt(i));
					}
					System.out.println();		
				}
			}finally{
				qexec.close();
			}
	
}
static void INIBIDOR_false_lipinski(Model model){
	
	//query in sparkle
		String queryString = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				"PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
				
				"SELECT ?inhibitor ?chemical_descriptor ?subject ?lipinski"+
				"WHERE {?subject <http://www.semanticweb.org/matheus19/ontologies/2019/1/untitled-ontology-14#yes/no> ?lipinski."+
				"?chemical_descriptor <http://www.semanticweb.org/matheus19/ontologies/2019/1/untitled-ontology-14#sub_has_the> ?subject."+
				"?inhibitor <http://www.semanticweb.org/matheus19/ontologies/2019/1/untitled-ontology-14#has_the> ?chemical_descriptor."+
				"FILTER (?lipinski = false)}";
		
				Query query = QueryFactory.create(queryString);
				QueryExecution qexec = QueryExecutionFactory.create(query,model);
				
				try{
					ResultSet results = qexec.execSelect();
					//ResultSetFormatter.out(System.out, results, query) ;
					while(results.hasNext()){
						QuerySolution soln = results.nextSolution();
						
						//pega somente o inibidor
						RDFNode name = soln.getResource("inhibitor");
						Node a = name.asNode();
						String inibidor = a.toString();
						int aux=0;
						
						//faz um for pra imprimir somente o inibidor e não toda a URI
						for (int i = 0; i < inibidor.length(); i++) {
							if(inibidor.charAt(i)=='#')break;
							aux++;
						}
						
						aux++;
						
						for (int i = aux; i < inibidor.length(); i++) {
							System.out.print(inibidor.charAt(i));
						}
						System.out.println();		
					}
				}finally{
					qexec.close();
				}
		
}


}
