package de.telran.lesson3.domain_layer.database;

import java.sql.Connection;
import java.sql.DriverManager;

import static de.telran.lesson3.constans.Constans.*;

public class MySqlConnector {
    //Класс создающий соединие между JDK and DB
    //метод для создания коннекта с БД
    public static Connection getConnection(){// ИНтерфейс Connecrion наследуется от Autoclosable что позволяет не заморачиваться с закрытием потока.
        try{
            Class.forName(DRIVER_PATH);
            //"jdbc:mysql://localhost:3306/shop?user=root&password=21111980
            String dbUrl= String.format("%s%s?user=%s&password=%s", DB_ADDRESS,DB_NAME,USER_NAME,DB_PASSWORD);//сктрока коннекта
            return DriverManager.getConnection(dbUrl);//запускаем коннект
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
