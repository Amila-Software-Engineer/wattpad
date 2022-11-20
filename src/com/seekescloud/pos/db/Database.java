package com.seekescloud.pos.db;

import com.seekescloud.pos.model.Customer;
import com.seekescloud.pos.model.Order;
import com.seekescloud.pos.model.Product;
import com.seekescloud.pos.model.User;

import java.util.ArrayList;

public class Database {

    public  static ArrayList<User> usertable = new ArrayList<User>();
    public  static ArrayList<Customer> customertable = new ArrayList<Customer>();
    public  static ArrayList<Product> producttable = new ArrayList<Product>();

    public  static ArrayList<Order> ordertable = new ArrayList<Order>();

    static{
        customertable.add(new Customer("C-001","Kamal","Colombo",45000));
        customertable.add(new Customer("C-002","Sunil","Gampaha",5000));
        customertable.add(new Customer("C-003","Chamara","Panadure",57000));
        customertable.add(new Customer("C-004","Amila","Kandy",1000000));

        producttable.add(new Product("P-001","Apple",50.00,5000));
        producttable.add(new Product("P-002","Orange",60.00,5000));
        producttable.add(new Product("P-003","Pears",220.00,5000));
        producttable.add(new Product("P-004","Mango",550.00,5000));


    }
}
