package solrtest;

public class test_string {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str1="  he  l  o  +lj   jjQ@( 纵 观 各 国) ";
		String str2= str1.replaceAll(" +", "");
		System.out.println(str2);
	}

}
