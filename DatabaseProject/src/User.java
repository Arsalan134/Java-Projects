
public class User {

	private static User currentUser;

	private int id;
	private String name, surname, password, phone, login, email;

	public int getId() {
		return id;
	}

	// public User() {
	//
	// }

	public User(String name, String surname, String password, String phone, String login, String email) {
		super();
		this.id = -1;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.phone = phone;
		this.login = login;
		this.email = email;
	}

	public User(int id, String name, String surname, String password, String phone, String login, String email) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.phone = phone;
		this.login = login;
		this.email = email;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static void setCurrentUser(User currentUser) {
		User.currentUser = currentUser;
	}

	public static User getCurrentUser() {
		return currentUser;
	}

}
