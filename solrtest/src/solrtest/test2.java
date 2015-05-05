package solrtest;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import org.apache.solr.client.solrj.SolrQuery; 
import org.apache.solr.client.solrj.SolrServer;   
import org.apache.solr.client.solrj.SolrServerException;   
import org.apache.solr.client.solrj.impl.HttpSolrServer;   
import org.apache.solr.client.solrj.response.QueryResponse;   
import org.apache.solr.common.SolrDocument;   
import org.apache.solr.common.SolrDocumentList; 


public class test2 {
	/**
	 * SOLR   访问地址/
	 */
	
	private  static  final  String  SOLR_URL ="http://localhost:8080/solr";   

	//通过http获取solrserver 对象
    public  SolrServer getSolrServer()  throws MalformedURLException {   
           
        return new HttpSolrServer(SOLR_URL);   
    }   
	
 // 查询方法     
    public void search(String key) throws MalformedURLException{     
        SolrServer solrServer = getSolrServer();   
        // 查询对象   
        SolrQuery query = new SolrQuery(key);         
       
        query.setQuery(key);    
             
        try {      
               
            query.setHighlight(true)   
                //设置开头   
                .setHighlightSimplePre("<font color=\"red\">")   
                .setHighlightSimplePost("</font>") //设置结尾   
                .setStart(0)    
                .setRows(1000);//设置行数   
            
            //设置高亮的哪些区域   
            query.setParam("hl.fl", "content");   
            QueryResponse response=solrServer.query(query);;   
            SolrDocumentList list=response.getResults();   
               
            System.out.println("高亮显示：");   
            for(SolrDocument sd:list){   
                String id=(String) sd.getFieldValue("id");   
                if(response.getHighlighting().get(id)!=null){   
                    System.out.println(response.getHighlighting()   
                        .get(id).get("content"));   
       
                }   
            }   
               
            System.out.println("——————————————-");   
               
            SolrDocumentList docs = response.getResults();     
            
            System.out.println("文档个数：" + docs.getNumFound());     
            System.out.println("查询时间：" + response.getQTime());   
            System.out.println("——————————————–");   
               
            for (SolrDocument doc : docs) {                     
                // 获取查询返回结果   
                String id = doc.getFieldValue("id").toString();     
                String title = doc.getFirstValue("title").toString();     
                String content = doc.getFirstValue("content").toString();   
                   
                // 打印查询结果   
                System.out.println("编号："+id);     
                System.out.println("标题："+title);    
                System.out.println("内容: "+content);    
           
                System.out.println("—————————————-");   
                   
            }     
                
        } catch (SolrServerException e) {     
             e.printStackTrace();     
        }      
    }     
       
	
	public static void main(String[] args)  throws MalformedURLException {
		//  TODO 测试solr
		 // 创建一个SolrJSearcheDemo对象   
        test2  sj = new test2();    
        // 查询条件   
        String Query ="图灵";   
        // 调用查询方法   
        sj.search(Query);     
	}

}
