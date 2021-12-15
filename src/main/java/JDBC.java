import java.sql.*;
import java.util.Calendar;

public class JDBC {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // პაროლი ჩემთან არის password123
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_network_otar_mamatsashvili", "root", "password123");
            return connection;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void read() {
        Connection connection = getConnection();

        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from social_network_otar_mamatsashvili.user");
                printResultSet(resultSet);
                if (connection != null) connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void write() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into social_network_otar_mamatsashvili.user(full_name, birth_date, friends_count) values(?,?,?)");
                preparedStatement.setString(1, "Memphis Depay");
                int year = 1994;
                int month = 2;
                int day = 13;
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month - 1); // months start at 0
                cal.set(Calendar.DAY_OF_MONTH, day);
                preparedStatement.setDate(2, new java.sql.Date(cal.getTimeInMillis()));
                preparedStatement.setInt(3, 347);
                int rowsEffected = preparedStatement.executeUpdate();

                if (rowsEffected < 1) {
                    System.out.println("Problem while inserting data");
                } else {
                    System.out.println("Success while inserting data");
                }
                preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void updateFriendsCount() {
        Connection connection = getConnection();

        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("update social_network_otar_mamatsashvili.user set friends_count=200 where friends_count>20");
                int rowsEffected = preparedStatement.executeUpdate();

                if (rowsEffected < 1) {
                    System.out.println("Problem while updating data");
                } else {
                    System.out.println("Success while updating data");
                }
                preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void delete() {
        Connection connection = getConnection();

        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("delete from social_network_otar_mamatsashvili.user where id=?");
                preparedStatement.setInt(1, 3);
                int rowsEffected = preparedStatement.executeUpdate();
                if (rowsEffected < 1) {
                    System.out.println("Problem while deleting data");
                } else {
                    System.out.println("Success while deleting data");
                }
                preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void printResultSet(ResultSet resultSet) throws SQLException {
        System.out.println("Users:");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String fullName = resultSet.getString("full_name");
            Date birthDate = resultSet.getDate("birth_date");
            int friendsCount = resultSet.getInt("friends_count");
            System.out.println("User id: " + id);
            System.out.println("User name: " + fullName);
            System.out.println("User birth date: " + birthDate);
            System.out.println("Number of user's friends: " + friendsCount);
        }
    }
}
