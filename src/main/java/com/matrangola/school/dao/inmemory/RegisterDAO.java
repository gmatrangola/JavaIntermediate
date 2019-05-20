package com.matrangola.school.dao.inmemory;

import com.matrangola.school.dao.BaseDAO;
import com.matrangola.school.dao.DaoException;
import com.matrangola.school.domain.RegistrationItemInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterDAO<T extends RegistrationItemInterface> implements BaseDAO<T> {
    Map<Integer, T> itemMap = new HashMap<>();
    int nextId = 0;

    @Override
    public synchronized void load(T updateObject) throws DaoException {
        if(!itemMap.containsKey(updateObject.getId())) {
            itemMap.put(updateObject.getId(), updateObject);
        }
    }

    @Override
    public void delete(T section) {
        itemMap.remove(section.getId());
    }

    @Override
    public synchronized T create(T newObject) {
        newObject.setId(++nextId);
        itemMap.put(newObject.getId(), newObject);
        return newObject;
    }

    @Override
    public T get(int id) {
        return itemMap.get(id);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(itemMap.values());
    }
}
