package it.shape.hibernate;

import javax.persistence.Entity;


@Entity
public class Dog extends Pet
{
    private static final long serialVersionUID = 1L;

    public Dog()
    {
        super();
    }

    public Dog(String name, String color)
    {
        super(name, color);
    }
}
