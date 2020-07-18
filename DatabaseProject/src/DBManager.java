import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/* Here we will learn to connect to Oracle DB using JDBC Driver. */
public class DBManager {

	static Connection connection = null;

	private static DBManager shared = new DBManager();

	public static DBManager getInstance() {
		if (shared == null)
			shared = new DBManager();
		return shared;
	}

	DBManager() {
		initConnection();
	}

	boolean addImage() {

		Statement stmt1;
		try {
			stmt1 = connection.createStatement();
			// OrdImage imgProxy = (OrdImage)rset.getORAData("photo",
			// OrdImage.getORADataFactory());
			String insertSQL = "insert into photos(photo) values" + " (ordsys.ordimage.init())";
			stmt1.executeUpdate(insertSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	void initConnection() {
		String dbName = "orclpdb";
		String userName = "xirava00";
		String connectionPassword = "u2jn69fh";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@//gort.fit.vutbr.cz:1521/" + dbName, userName, connectionPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (connection != null)
			System.out.println("Successfullly connected to Oracle DB");
		else
			System.out.println("Failed to connect to Oracle DB");

	}

	boolean addCategory(String name) {
		try {
			String query = " insert into categories (name)" + " values (?)";

			PreparedStatement preparedStmt = connection.prepareStatement(query);

			preparedStmt.setString(1, name);

			if (!preparedStmt.execute()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	boolean addProduct(String name, double price, double amount, int categoryID) {
		try {
			String query = " insert into products (categoryID, name, price, amount)" + " values (?, ?, ?, ?)";

			PreparedStatement preparedStmt = connection.prepareStatement(query);

			preparedStmt.setInt(1, categoryID);
			preparedStmt.setString(2, name);
			preparedStmt.setDouble(3, price);
			preparedStmt.setDouble(4, amount);

			if (!preparedStmt.execute()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	boolean loginWith(String username, String password) {
		try {
			String query = " select * from users where login = " + " ? and password = ?";

			PreparedStatement preparedStmt = connection.prepareStatement(query);

			preparedStmt.setString(1, username);
			preparedStmt.setString(2, password);

			ResultSet resultSet = preparedStmt.executeQuery();

			while (resultSet.next()) {

				int id = resultSet.getInt("ID");
				String name = resultSet.getString("NAME");
				String surname = resultSet.getString("surname");
				String passwordResult = resultSet.getString("password");
				String email = resultSet.getString("email");
				String phone = resultSet.getString("phone");
				String login = resultSet.getString("login");

				User user = new User(id, name, surname, passwordResult, phone, login, email);

				User.setCurrentUser(user);

				System.out.println(user);
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	ArrayList<Category> getAllCategories() {
		try {
			String query = " select * from categories";

			PreparedStatement preparedStmt = connection.prepareStatement(query);
			ResultSet resultSet = preparedStmt.executeQuery();

			ArrayList<Category> categories = new ArrayList<>();

			while (resultSet.next()) {

				int id = resultSet.getInt("ID");
				String name = resultSet.getString("NAME");
				Category c = new Category(id, name);
				categories.add(c);
			}

			return categories;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	User getUserWithLogin(String login) {
		try {
			String query = " select * from users where login = ?";

			PreparedStatement preparedStmt = connection.prepareStatement(query);

			preparedStmt.setString(1, login);

			ResultSet resultSet = preparedStmt.executeQuery();

			while (resultSet.next()) {

				int id = resultSet.getInt("ID");
				String name = resultSet.getString("NAME");
				String surname = resultSet.getString("surname");
				String passwordResult = resultSet.getString("password");
				String email = resultSet.getString("email");
				String phone = resultSet.getString("phone");

				User user = new User(id, name, surname, passwordResult, phone, login, email);

				System.out.println(user);

				return user;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	boolean createUser(String name, String surname, String password, String email, String phone, String login) {

		try {
			String query = " insert into users (name, surname, password, email, phone, login)" + " values (?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = connection.prepareStatement(query);

			preparedStmt.setString(1, name);
			preparedStmt.setString(2, surname);
			preparedStmt.setString(3, password);
			preparedStmt.setString(4, email);
			preparedStmt.setString(5, phone);
			preparedStmt.setString(6, login);

			if (!preparedStmt.execute()) {
				return true;
			}

			// connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}