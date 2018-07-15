public class Test {
	public static void main(String[] args) {
		Notify notify = Notify.parseFromFile("data.xml");
		if (notify == null) {
			return;
		}
		System.out.println(notify);
	}
}
