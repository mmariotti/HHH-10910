package it.shape.hibernate;

public class Result
{
    private Class<?> resultClass;

    private long count;

    public Result()
    {
        super();
    }

    public Result(Class<?> resultClass, long count)
    {
        super();
        this.resultClass = resultClass;
        this.count = count;
    }

    public Class<?> getResultClass()
    {
        return resultClass;
    }

    public void setResultClass(Class<?> resultClass)
    {
        this.resultClass = resultClass;
    }

    public long getCount()
    {
        return count;
    }

    public void setCount(long count)
    {
        this.count = count;
    }
}
