package com.algar.demoalar.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable {

    private Integer id;

    private  String name;

    private List<Product> products = new ArrayList<>();

}
