package solrtest;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;

public class test {

	public test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws SolrServerException, IOException{
		String urlString = "http://localhost:8080/solr";
		SolrServer solr = new HttpSolrServer(urlString);
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "1987");
		document.addField("title", "solr test");
		document.addField("content", "if you can see this ,it means working");
		solr.add(document);
		// Remember to commit your changes!
		solr.commit();
		SolrQuery query = new SolrQuery();
        query.setQuery("*");
        query.addSort(new SortClause("id", ORDER.desc));
        QueryResponse response = solr.query(query);
        System.out.println(response) ;
	}

}
