package it.shape.hibernate;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class EntityTypeTest
{
    protected static EntityManagerFactory emf;

    @BeforeClass
    public static void init()
    {
        emf = Persistence.createEntityManagerFactory("HHH-10910");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Cat c1 = new Cat("titti", "red");
        em.persist(c1);

        Cat c2 = new Cat("ciccio", "gray");
        em.persist(c2);

        Dog d1 = new Dog("penny", "white");
        em.persist(d1);

        Dog d2 = new Dog("gaia", "black");
        em.persist(d2);

        Dog d3 = new Dog("amy", "black");
        em.persist(d3);

        transaction.commit();
    }

    @Test
    public void testJPQL()
    {
        List<?> resultList = emf.createEntityManager()
            .createQuery("select type(x), count(x) from Pet x group by type(x)", Object[].class)
            .getResultList();

        Assert.assertEquals(2, resultList.size());
    }

    @Test
    public void testJPQL2()
    {
        List<Result> resultList = emf.createEntityManager()
            .createQuery("select new it.shape.hibernate.Result(type(x), count(x)) from Pet x group by type(x)", Result.class)
            .getResultList();

        Assert.assertEquals(2, resultList.size());
    }

    @Test
    public void testCriteria()
    {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<?> query = builder.createQuery();
        Root<Pet> root = query.from(Pet.class);

        Expression<Class<? extends Pet>> type = root.type();
        Expression<Long> count = builder.count(root);

        query.multiselect(type, count);

        query.groupBy(type);

        List<?> resultList = em.createQuery(query).getResultList();

        Assert.assertEquals(2, resultList.size());
    }

    @Test
    public void testCriteria2()
    {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<Result> query = builder.createQuery(Result.class);
        Root<Pet> root = query.from(Pet.class);

        Expression<Class<? extends Pet>> type = root.type();
        Expression<Long> count = builder.count(root);

        query.multiselect(type, count);

        query.groupBy(type);

        List<Result> resultList = em.createQuery(query).getResultList();

        Assert.assertEquals(2, resultList.size());
    }

    @Test
    public void testCriteria3()
    {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<Result> query = builder.createQuery(Result.class);
        Root<Pet> root = query.from(Pet.class);

        Expression<Class<? extends Pet>> type = root.type();
        Expression<Long> count = builder.count(root);

        query.select(builder.construct(Result.class, type, count));

        query.groupBy(type);

        List<Result> resultList = em.createQuery(query).getResultList();

        Assert.assertEquals(2, resultList.size());
    }

    @AfterClass
    public static void cleanup()
    {
        if(emf.isOpen())
        {
            emf.close();
        }
    }
}
