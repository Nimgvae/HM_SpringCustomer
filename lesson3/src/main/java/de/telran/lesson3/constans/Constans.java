package de.telran.lesson3.constans;

public interface Constans {
    //тут будут ГЛОБАЛЬНЫЕ переменные для взаимодействия между средой разработки и БД.
    String DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
    String DB_ADDRESS = "jdbc:mysql://localhost:3306/";//дефолтный адрес СЕРВЕРА mysql если он поднят на компе
    String DB_NAME = "shop";//name databae
    String USER_NAME = "root";
    String DB_PASSWORD ="21111980";
}
