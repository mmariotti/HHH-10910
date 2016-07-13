package it.shape.hibernate;

import javax.persistence.Entity;


@Entity
public class Cat extends Pet
{
    private static final long serialVersionUID = 1L;

    public Cat()
    {
        super();
    }

    public Cat(String name, String color)
    {
        super(name, color);
    }
}
