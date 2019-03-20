package com.matrangola.school.dao;

import java.util.List;

public interface BaseDAO<T> {

	void load(T updateObject) throws DaoException;

	void delete(T student);

	T create(T newObject);

	T get(int id);

	List<T> getAll();
}