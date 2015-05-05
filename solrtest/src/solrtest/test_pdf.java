package solrtest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.NamedList;

/**
判断文件类型使用了probeContentTpye;也可以使用jMimeMagic开源类
http://sourceforge.net/projects/jmimemagic/
*/



public class test_pdf {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename =  "/home/kingsun/下载/推荐系统实践.pdf";
		String solrId = "推荐系统实践.pdf";
//		String ppt_path = "/home/kingsun/桌面/文档资料/HTTP权威指南（中文版）.pdf";
//		File file1 = new File(ppt_path);
//		String filena = file1.getName();
//		System.out.println(filena);
		Path path = Paths.get(filename);  
		

		try {
			String contentT = Files.probeContentType(path); 
			System.out.println(contentT);
			indexFilesSolrCell(filename, solrId,contentT);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

public static void indexFilesSolrCell( String fileName, String solrId,String contentT)  throws IOException, SolrServerException  {
	String urlString="http://localhost:8080/solr";
	SolrServer server = new HttpSolrServer(urlString);
	ContentStreamUpdateRequest req = new ContentStreamUpdateRequest("/update/extract");
	System.out.println(contentT);
//	String contentType = "application/pdf";
	req.addFile(new File(fileName), contentT);

	req.setParam("literal.id", solrId);  
	req.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true); 
NamedList<Object> result = server.request(req);
System.out.println("RESULT:" + result);
}
	
	
	
	
}
