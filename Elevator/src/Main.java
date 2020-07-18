public class Main {

	public static void main(String[] args) throws InterruptedException {

		int numberOfFloors = 10;
		int numberOfElevators = 2;

		Building sapphirePlaza = new Building("Sapphire Plaza", numberOfFloors, numberOfElevators);
		Controller controller = new Controller("Main Controller", sapphirePlaza);

		sapphirePlaza.setController(controller);

		System.out.println(sapphirePlaza);

		Person Arsalan = new Person("Arsalan", sapphirePlaza, 9, 1);
		Person Farxad = new Person("Farxad", sapphirePlaza, 6, 4);
		Person Naza = new Person("Naza", sapphirePlaza, 6);
		Person Sveta = new Person("Sveta", sapphirePlaza);

		Thread.sleep(100);
		new Thread(Arsalan).start();

		Thread.sleep(100);
		new Thread(Farxad).start();

		Thread.sleep(100);
		new Thread(Naza).start();

		Thread.sleep(100);
		new Thread(Sveta).start();

	}
}
