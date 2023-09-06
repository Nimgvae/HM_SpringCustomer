package de.telran.lesson3.repository_layer;

import de.telran.lesson3.domain_layer.entity.CommonProduct;
import de.telran.lesson3.domain_layer.entity.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
//импортируем СТАТИЧЕСКИ импорт метода вызова соединения с БД
import static de.telran.lesson3.domain_layer.database.MySqlConnector.getConnection;

public class MySqlProductRepository implements ProductRepository{
    @Override
    public List<Product> getAll() {
        try (Connection connection = getConnection()){
            String query = "select * from product;";
            ResultSet resultSet = connection.createStatement().executeQuery(query);// создаем ЗАПРОС на вывод ВСЕХ продуктов из БД
            List<Product> result = new ArrayList<>();
            //луп для внесения в список ВСЕХ продуктов по строчно.
            while (resultSet.next()){
                //                       можно по имени колонки к прримеру id
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                double price = resultSet.getDouble(3);
                result.add(new CommonProduct(id,name,price));
            }
            return result;
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public Product getById(int id) {
        try (Connection connection = getConnection()){ //тут мы создаем коннект для каждого метода.
            String query = String.format("select * from product where id = %d;", id);
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            resultSet.next();
            String name = resultSet.getString(2);
            double price = resultSet.getDouble(3);
            return  new CommonProduct(id,name,price);

        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public void add(String name, double price) {
        try (Connection connection = getConnection()) {
            String query = String.format(Locale.US, "INSERT INTO `shop`.`product` (`name`, `price`) " +
                    "VALUES ('%s', '%.2f');", name, price);
            connection.createStatement().execute(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = getConnection()) {
            String query = String.format("DELETE FROM `product` WHERE (`id` = '%d');", id);
            connection.createStatement().execute(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
