package solrtest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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



public class 遍历文件夹检索 {
	
	static ArrayList<File> List_file = new ArrayList<File>(); 
public static void main(String[] args) throws IOException, SolrServerException {

		System.out.println("开始");
		String filename =  "/home/kingsun/git.pdf";

		String ppt_path = "/home/kingsun/桌面/文档资料/HTTP权威指南（中文版）.pdf";
		String  file_path = "/home/kingsun/下载/为了测试用";
		File file1=new File(file_path);// 实例化File类的对象
		ArrayList<File> list_file_name = print_file(file1);
        for(int i = 0;i < list_file_name.size(); i ++){
//            System.out.println(list_file_name.get(i));
            File file_name = list_file_name.get(i);
            System.out.println( file_name.toString());

//            String filena = file1.getName();
            String solrId = file_name.getName();
            System.out.println(file_name.getName());
//    		System.out.println(filena);
            Path path = Paths.get(file_name.toString()); 
            String contentT = Files.probeContentType(path);
            System.out.println(contentT);
           indexFilesSolrCell(file_name.toString(), solrId.toString(),contentT.toString());
            
        }

	
//		String filena = file1.getName();
//		System.out.println(filena);
//		Path path = Paths.get(filename);  
		

//		try {
////			String contentT = Files.probeContentType(path); 
////			System.out.println(contentT);
////			indexFilesSolrCell(filename, solrId,contentT);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
	}

public static void indexFilesSolrCell( String fileName, String solrId,String contentT)  throws IOException, SolrServerException  {
	String urlString="http://localhost:8080/solr";
	SolrServer server = new HttpSolrServer(urlString);
	ContentStreamUpdateRequest req = new ContentStreamUpdateRequest("/update/extract");
	req.addFile(new File(fileName), contentT);
	req.setParam("literal.id", solrId);  
//	req.setParam("fmap.content", "attr_content");
//	req.setParam("uprefix","attr_");
	req.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true); 
	server.request(req);
//	NamedList<Object> result = server.request(req);
//	System.out.println("RESULT:" + result);
	System.out.println("OK");
}
	
// 文件夹遍历,查找文件,需要先实例化file类

public  static   ArrayList<File> print_file(File file)
{
	if(file!=null)
	{
		if(file.isDirectory())
			{
					File f[]=file.listFiles();// 列出全部的文件 
					if(f!=null)
					{  
						for(int i=0;i<f.length;i++)
							{  
									print_file(f[i]);//递归调用自身
							}
					}			
			}
		else {
//			System.out.println(file);
// 使用arraylist保存文件						
			List_file.add(file);
//			System.out.println(List_file);
		}

	}
	return List_file;

}
	
	
}
