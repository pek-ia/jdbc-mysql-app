
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.Scanner;

public class SqlApp {
    public static void main(String[] args) {

        BasicDataSource dataSource;
        dataSource = new BasicDataSource();

        String url = "jdbc:mysql://localhost:3306/dealership";

        dataSource.setUsername(args[0]);
        dataSource.setPassword(args[1]);
        dataSource.setUrl(url);

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        try (Connection connection = dataSource.getConnection()) {


            PreparedStatement updateStatement = connection.prepareStatement("UPDATE vehicles SET make = ? WHERE make = 'Porsche'");
            PreparedStatement statement = connection.prepareStatement("select * from vehicles where make = ?;");
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO vehicles VALUES ( ? );");
            while (running) {
                System.out.print("Enter the make you are looking to insert: ");
                String makeToSearchFor = scanner.nextLine();

                if (makeToSearchFor.equalsIgnoreCase("quit")){
                    break;
                }

                insertStatement.setString(1, makeToSearchFor);
                insertStatement.execute();

                updateStatement.setString(1, makeToSearchFor);
                updateStatement.execute();

                statement.setString(1, makeToSearchFor);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();

                while (resultSet.next()) {
                    String make = resultSet.getString("MAKE");
                    System.out.println(make);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            dataSource.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
