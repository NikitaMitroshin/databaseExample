import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import spark.Route;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        staticFileLocation("/");

        get("/getAllGoods", getAllGoodsRoute());
        get("/getAllStudentFromDataBase", getAllStudentFromDataBase());
    }

    private static Route getAllGoodsRoute() {
        Route getRoute = (request, response) -> {
            return OBJECT_MAPPER.writeValueAsString(getItemsFromJson());
        };
        return getRoute;
    }

    private static Route getAllStudentFromDataBase() {
        Route getRoute = (request, response) -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/students", "root", "root");
                Statement stmt = con.createStatement();
                LOGGER.info("ready for query");
                ResultSet rs = stmt.executeQuery("select * from Student");
                 List<Student> students = new ArrayList<>();
                while (rs.next()) {
                    LOGGER.info("creating student");
                    Student student = new Student(rs.getInt(1), rs.getString(2), rs.getString(3));
                    students.add(student);
                }
                return students;
            } catch (Exception e) {
                LOGGER.error("ощибка ", e);
            }
            return new ArrayList<>();
        };
        return getRoute;
    }

    private static List<Item> getItemsFromJson() {
        List<Item> items = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream("items.json");
            items = OBJECT_MAPPER.readValue(inputStream, new TypeReference<List<Item>>() {
            });
        } catch (FileNotFoundException e) {
            LOGGER.error("файл items.json не найден", e);
        } catch (IOException e) {
            LOGGER.error("ошибка при чтении items.json", e);
        }
        return items;
    }

}
