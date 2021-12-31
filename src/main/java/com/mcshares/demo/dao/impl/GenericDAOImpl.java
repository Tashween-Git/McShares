package com.mcshares.demo.dao.impl;

import com.mcshares.demo.dao.IGenericDAO;
import com.mcshares.demo.dao.SessionDAOHelper;
import com.mcshares.demo.model.GenericObject;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class GenericDAOImpl<T> extends SessionDAOHelper implements IGenericDAO<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericDAOImpl.class);

    private Class<T> type;

    public GenericDAOImpl() {
        super();
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = null;
        if (t instanceof ParameterizedType) {
            pt = (ParameterizedType) t;
        }
        if (pt == null) {
            // another level of abstraction
            t = getClass().getSuperclass().getGenericSuperclass();
            pt = (ParameterizedType) t;
        }

        type = (Class) pt.getActualTypeArguments()[0];
    }


    @Override
    public T create(Object t) {
        this.sessionFactory.getCurrentSession().save(t);
        return (T) t;
    }


    @Override
    /**
     * hibernate query.list() method is returning empty list NOT a null value
     */
    public List<T> findAll() {
        final StringBuilder queryString = new StringBuilder("SELECT o from ");

        queryString.append(type.getSimpleName()).append(" o ");

        final Query query = getFilteredCurrentSession().createQuery(queryString.toString());

        return query.list();
    }


    /**
     * check entity existence with current filter session (supprime)
     *
     * @param id
     * @return entity with given id, can be null
     */
    @Override
    public T find(final Serializable id) {
        this.sessionFactory.getCurrentSession().get(type, id);
        if (getEntityWithSessionFilter(type, id) != null) {
            return (T) getFilteredCurrentSession().load(type, id);
        }
        return null;
    }


    @Override
    public T update(final Object o) {
        if (o instanceof GenericObject) {
            GenericObject obj = (GenericObject) o;
            obj.onUpdate();
        }
        return (T) this.sessionFactory.getCurrentSession().merge(o);
    }


    /**
     * create query with condition on SUPPRIME=FALSE and primaryKey=@param id
     * SUPPRIME=FALSE is on getFilteredCurrentSession
     *
     * @param marsClass - entity
     * @param id        - primary key
     * @return entity from DB
     */
    protected Object getEntityWithSessionFilter(final Class marsClass, final Serializable id) {
        Object result = null;
        if (id != null) {
            // verify if marsClass inherits GenericMarsClass - has supprime filter
            if (extendsGenericObjectClass(marsClass)) {
                String primaryKey = null;
                try {
                    Constructor<?> cons = marsClass.getConstructor(String.class);
                    Object object = cons.newInstance();
                    primaryKey = ((GenericObject) object).getPrimaryKey();
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    LOGGER.error("Exception occurred: ", e);
                }

                if (primaryKey != null) {
                    StringBuilder queryString = new StringBuilder().append(" SELECT ").append(primaryKey)
                            .append(" FROM ").append(marsClass.getName()).append(" WHERE ").append(primaryKey)
                            .append(" = :id ");
                    Query query = getFilteredCurrentSession().createQuery(queryString.toString());

                    query.setParameter("id", id);

                    result = query.uniqueResult();
                } else {
                    LOGGER.error("getEntityWithSessionFilter: This entity [" + marsClass.getName()
                            + "] doesn't have a primary key!! ");
                }

            } else {
                LOGGER.info(
                        "getEntityWithSessionFilter: no point verifying filter because current class doesn't have a filter");
                result = id;
            }
        }
        return result;
    }

    /**
     * verify if class inherits GenericObjectClass - has supprime filter
     *
     * @param genericClass
     * @return
     */
    private boolean extendsGenericObjectClass(Class<?> genericClass) {
        boolean result = false;
        Class<?> superClass = genericClass.getSuperclass();
        while (superClass != null) {
            if (superClass.equals(GenericObject.class)) {
                result = true;
                break;
            }
            superClass = superClass.getSuperclass();
        }

        return result;
    }


    public Class<T> getType() {
        return type;
    }

    @Override
    public int delete(Serializable id) {


        if (StringUtils.isEmpty(id)) {
            return -1;
        }

        String queryString = "UPDATE " + type.getSimpleName() + " obj " + "SET obj.supprime = true,"
                + "obj.timestampSupprime = :today " + "WHERE obj.id = :idObj ";

        final Query query = getFilteredCurrentSession().createQuery(queryString);
        query.setParameter("idObj", id);
        query.setParameter("today", new Date());
        return query.executeUpdate();

    }

}
