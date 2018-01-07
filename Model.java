package it.polito.tdp.ufo.bean;

import java.util.*;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.ufo.db.UfoDAO;

public class Model {

	UfoDAO dao ; 

	private List<String> listShape;
	private List<Sighting> ListSighting;	
	private List<FasciaOraria> listFasceOrarie;
	private DefaultDirectedWeightedGraph<Sighting, DefaultWeightedEdge> graph;

	public Model() {
		
		dao = new UfoDAO();
		if(listFasceOrarie==null) listFasceOrarie= new ArrayList<FasciaOraria>();
		listFasceOrarie.add(new FasciaOraria (0,5));
		listFasceOrarie.add(new FasciaOraria (6,11));
		listFasceOrarie.add(new FasciaOraria (12,17));
		listFasceOrarie.add(new FasciaOraria (18,23));
	
		if(listShape==null) listShape= dao.getAllShape();	//Si trattino il valore "unknown" ed il valore mancante/vuoto come sinonimi.
	}
	public List<Sighting> getAllSighting(FasciaOraria fo,String shape){
		if(ListSighting==null){ 
				if(shape.equals("unknown"))
					ListSighting=dao.getAllSightingUnknown(fo,shape);
				else 
					ListSighting=dao.getAllSighting(fo,shape);	
		} 
			
		return ListSighting;
	}

	//lista shape without null 
	public List<String>getAllShape(){
		return listShape;
	}
	public List<FasciaOraria> getFasceOrarie() { 
		return listFasceOrarie;
	}
	
	public void  creaGrafo(FasciaOraria fo , String shape, double d) {
		String s="";
		if(graph==null)		
			this.graph = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungo vertici Sighting con shape e fascia oraria      
			
		Graphs.addAllVertices (graph, this.getAllSighting(fo, shape)); 
		
		
		System.out.println("Grafo creato: " + graph.vertexSet().size() +"  vertici "  );	//ok
		
		//creo archi
		for (Sighting a1 : graph.vertexSet()) {
			for (Sighting a2 : graph.vertexSet()) {		
				if (a1 != null && a2 != null && !a1.equals(a2)) {				
					//calcolo distanza con latlng	
					double dist = LatLngTool.distance(a1.getCoords(), a2.getCoords(), LengthUnit.KILOMETER);				
					//calcolo differenza giorni tra avvistamenti				
					Integer diffyear= a1.getDatetime().getYear()-a2.getDatetime().getYear();
					Integer diffday= a1.getDatetime().getDayOfYear()-a2.getDatetime().getDayOfYear();
					Integer diff= diffyear*365+diffday;		
					//oppure tutto in una operazione
					Integer peso= (a1.getDatetime().getYear()*365+a1.getDatetime().getDayOfYear())-(a2.getDatetime().getYear()*365+a2.getDatetime().getDayOfYear());
					//se distanza input < distanza calcolata aggiungo archi				
					if (d>=dist)
					{
						DefaultWeightedEdge e=graph.addEdge(a1, a2);
						if(e!=null)
							graph.setEdgeWeight(e, peso);
						//striga grafo 
							s += e+" "+peso+"\n";
						}		
//						Graphs.addEdgeWithVertices(graph, a1, a2, peso);
					
					
				}
			}
		 }
			//stampo
			System.out.println("Grafo creato: " + graph.vertexSet().size() + " nodi, " + graph.edgeSet().size() + " archi");
			System.out.println(s);
//			System.out.println(graph);
			
	}

	
/**	Una volta costruito il grafo, si determini:
		- quante sono le componenti connesse del grafo
		- quanti vertici ha la componente connessa più grande
		- quali sono le 5 coppie di avvistamenti, appartenenti alla componente connessa più grande, 
		  avvenuti con intervalli di tempo più ravvicinati (ossia trovare i 5 archi di peso minimo). 
*/	

	
/*  avvistamenti sono connessi??  */	
	public String isConnesso () {
		if(graph == null)	throw new RuntimeException("grafo non esiste");		
	    ConnectivityInspector<Sighting , DefaultWeightedEdge> conn = new ConnectivityInspector<>(this.graph);
	    if (conn.isGraphConnected()){ return "Il grafo � fortemente connesso+\n";	 }
		else return "il grafo non � fortemente connesso+\n";
	}
	
	
/* numero degli avvistamenti connessi */
	public int getNumberConn(){
		if(graph == null)	throw new RuntimeException("grafo non esiste");		
		ConnectivityInspector<Sighting ,DefaultWeightedEdge> ci =new ConnectivityInspector<>(graph);
		int i=ci.connectedSets().size();
		return i;
	}
		
/* lista degli avvistamenti connessi */
	public List<Set<Sighting>> getConn(){
			if(graph == null) throw new RuntimeException("grafo non esiste");		
			ConnectivityInspector<Sighting ,DefaultWeightedEdge> ci =new ConnectivityInspector<>(graph);
			return ci.connectedSets();
		}

/* trovo (max) degli avvistamenti connessi */
	public Set<Sighting> MAXconnesso(int id) {
		if(graph == null) throw new RuntimeException("grafo non esiste");		
		ConnectivityInspector<Sighting, DefaultWeightedEdge> ci = new ConnectivityInspector<>(graph);
		id= this.getNumberConn();
		return ci.connectedSetOf(ListSighting.get(id));
		
	}
/* trovo (max size) degli avvistamenti connessi */
	public int MAXconnessioni(int id) {
		if(graph == null) throw new RuntimeException("grafo non esiste");		
		ConnectivityInspector<Sighting, DefaultWeightedEdge> ci = new ConnectivityInspector<>(graph);
		id= this.getNumberConn();
		return ci.connectedSetOf(ListSighting.get(id)).size();
		
	}
		
/* trovo 5 achi con peso minimo di (max size) */		//????		
 
public String  get5ArchiMinWeight (){
	
      //SortedSet?
  	  String s="";
	  int id= this.getNumberConn();	  	  
	  Sighting a1 = ListSighting.get(id);	   
	  List <DefaultWeightedEdge> edge= new  ArrayList<>();
	  
	  for (Sighting a2: Graphs.neighborListOf(graph , a1)){
		  if (! a1.equals(a2)){  	
		   if( edge.size()<5){
			Integer peso= (a1.getDatetime().getYear()*365+a1.getDatetime().getDayOfYear())-(a2.getDatetime().getYear()*365+a2.getDatetime().getDayOfYear());
			   DefaultWeightedEdge e=graph.addEdge(a1, a2);
				if(e!=null)
					graph.setEdgeWeight(e, peso);
				edge.add( e);
				//striga grafo 
					s += a1+" "+a2+" "+peso+"\n";
		    }			
		   }	 
	  }	  
	  return s;	
    }


	
	public static void main(String[] args) {
		
		Model model = new Model();
		FasciaOraria fo= new FasciaOraria(0,5);
		FasciaOraria fo1= new FasciaOraria(6,11);
		FasciaOraria fo2= new FasciaOraria(12,17);
		FasciaOraria fo3= new FasciaOraria(18,23);
		Sighting si= new Sighting(45153, "cone");
		model.creaGrafo(fo1,"cone",400) ;
		
		System.out.println("\n");
		System.out.println("-------- n connessioni grafo ---------");
		System.out.println("\n");
		
		int n=model.getNumberConn();
		System.out.println(n);
		
		System.out.println("\n");
		System.out.println("------- lista componenti connesse ----------");
		System.out.println("\n");
		
		List<Set<Sighting>> l =model.getConn();
		System.out.println(l);	
		
		System.out.println("\n");
		System.out.println("------- Arco max connesso ----------");
		System.out.println("\n");
		
		Set<Sighting> s=model.MAXconnesso(n);
		System.out.println(s);
		
		System.out.println("\n");
		System.out.println("-------- n archi Arco max connesso ---------");
		System.out.println("\n");
		
		int nc=model.MAXconnessioni(n);
		System.out.println(nc);
		
		System.out.println("\n");
		System.out.println("------- 5 Archi di max connesso ----------");
		System.out.println("\n");

		String e= model.get5ArchiMinWeight(); 
		System.out.println(e);
		
	}

}
	
	
	


