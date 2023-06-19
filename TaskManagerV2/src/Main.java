import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/task_manager_db";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        menu();
        int command = scanner.nextInt();
        while (command != 4) {
            chose(command, scanner, connection);
            System.out.println();
            command = scanner.nextInt();
        }


    }

    public static void chose(int command, Scanner scanner, Connection connection) throws SQLException {
        switch (command) {
            case 1:
                Statement statement = connection.createStatement();
                String SQL_SELECT_TASK = "select * from task order by id desc";
                ResultSet result = statement.executeQuery(SQL_SELECT_TASK);
                while (result.next()) {
                    System.out.println(result.getInt("id") + " "
                            + result.getString("name") + " "
                            + result.getString("state"));
                }
                menu();
                break;
            case 2:
                String sql = "update public.task set state = 'DONE' where id = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                System.out.println("Введите индентификатор задачи: ");
                int taskId = scanner.nextInt();
                preparedStatement.setInt(1, taskId);
                preparedStatement.executeUpdate();
                menu();
                break;
            case 3:
                String sql2 = "insert into public.task (name,state) values (?, 'IN_PROCESS');";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                System.out.println("Введите название задачи: ");
                scanner.nextLine();
                String taskName = scanner.nextLine();
                preparedStatement2.setString(1, taskName);
                preparedStatement2.executeUpdate();
                menu();
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.err.println("Команда не распознана ");
        }
    }

    public static void menu() {
        System.out.println();
        System.out.println("1. Показать список всех задач");
        System.out.println("2. Выполнит задачу");
        System.out.println("3. Создать задачу");
        System.out.println("4. Выход");
        System.out.println();
    }

}
