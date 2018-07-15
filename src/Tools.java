import java.util.regex.Pattern;

public class Tools {
	public static boolean isNumbric(String str) {
		if(str == null || str == "") {
			return false;
		}
		
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		
		return pattern.matcher(str).matches();
	}
	
	public static void main(String[] args) {
		System.out.println(Tools.isNumbric("3"));
		System.out.println(Tools.isNumbric("3.14"));
		System.out.println(Tools.isNumbric("ab3"));
		System.out.println(Tools.isNumbric("3a"));
		System.out.println(Tools.isNumbric("3.1530934840123481032800398q0er8q0ewr8"));
		System.out.println(Tools.isNumbric("31830481348103480132"));
		System.out.println(Tools.isNumbric("3.1415926535"));
	}
}
