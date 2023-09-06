package de.telran.lesson3.repository_layer;

import de.telran.lesson3.domain_layer.entity.*;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import static de.telran.lesson3.domain_layer.database.MySqlConnector.getConnection;

public class MySqlCustomerRepository implements CustomerRepository{
    @Override
    public List<Customer> getAll() {
        try (Connection connection = getConnection()){
            String query = "SELECT * FROM customer as c left join customer_product as cp on c.id = cp.customer_id left join product as p on cp.product_id = p.id;";
            ResultSet resultSet = connection.createStatement().executeQuery(query);// создаем ЗАПРОС на вывод ВСЕХ продуктов из БД
            List<Customer> result = new ArrayList<>();

            CommonCustomer customer = new CommonCustomer();
            //луп для внесения в список ВСЕХ продуктов по строчно.
            while (resultSet.next()){

                int customerId = resultSet.getInt(1);
                String customerName = resultSet.getString(2);
                int productId = resultSet.getInt(6);
                String productName = resultSet.getString(7);
                double productPrice = resultSet.getDouble(8);
                CommonCart cart = new CommonCart();
                cart.addProduct(new CommonProduct(productId,productName,productPrice));
                result.add(new CommonCustomer(customerId,customerName,cart));
            }
            return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer getById(int id) {
        try (Connection connection = getConnection()) {
            String query = String.format("SELECT * FROM customer as c left join customer_product as cp on c.id = cp.customer_id left join product as p on cp.product_id = p.id where c.id = %d;", id);
            // Здесь ваш код
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            CommonCart cart = new CommonCart();
            CommonCustomer customer = new CommonCustomer();
            while (resultSet.next()){

                int customerId = resultSet.getInt(1);
                String customerName = resultSet.getString(2);
                int productId = resultSet.getInt(6);
                String productName = resultSet.getString(7);
                double productPrice = resultSet.getDouble(8);
                if(customer == null){
                    customer = new CommonCustomer(customerId,customerName,cart);
                }
                else{
                    cart.addProduct(new CommonProduct(productId,productName,productPrice));
                    customer = new CommonCustomer(customerId,customerName,cart);
                }
            }
            return customer;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(String name) {
        try (Connection connection = getConnection()) {
            String query = String.format("INSERT INTO `customer` (`name`) VALUES ('%s');", name);
            // Здесь ваш код
            connection.createStatement().execute(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = getConnection()) {
            String query = String.format("DELETE FROM `customer` WHERE (`id` = '%d');", id);
            // Здесь ваш код
            connection.createStatement().execute(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addToCartById(int customerId, int productId) {
        try (Connection connection = getConnection()) {
            String query = String.format("INSERT INTO `customer_product` (`customer_id`, `product_id`) VALUES ('%d', '%d');", customerId, productId);
            // Здесь ваш код
            connection.createStatement().execute(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFromCart(int customerId, int productId) {
        try (Connection connection = getConnection()) {
            String query = String.format("DELETE FROM `customer_product` WHERE (`customer_id` = '%d' and `product_id` = '%d');", customerId, productId);
            // Здесь ваш код
            connection.createStatement().execute(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearCart(int customerId) {
        try (Connection connection = getConnection()) {
            String query = String.format("DELETE FROM `customer_product` WHERE (`customer_id` = '%d');", customerId);
            // Здесь ваш код
            connection.createStatement().execute(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
