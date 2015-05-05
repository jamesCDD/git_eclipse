package solrtest;

import java.io.File;
import java.util.ArrayList;

public class 文件目录遍历 {

	static ArrayList<File> List_file = new ArrayList<File>(); 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("开始");
		String  file_path = "/home/kingsun/桌面/文档资料";
		File f=new File(file_path);// 实例化File类的对象
		ArrayList<File> tte = print_file(f);
//		System.out.println(tte.size());		
        for(int i = 0;i < tte.size(); i ++){
            System.out.println(tte.get(i));
        }
//		System.out.println(tte);
		System.out.println("结束");
	}

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
