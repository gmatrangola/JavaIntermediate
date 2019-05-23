package com.matrangola.school.dao.inmemory;

import com.matrangola.school.dao.BaseDAO;
import com.matrangola.school.domain.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDAO<T extends Item> implements BaseDAO<T> {
    private static int nextId = 0;
    protected Map<Integer, T> items = new HashMap<>();

    public void update(T updateObject) {
        if(items.containsKey(updateObject.getId())) {
            items.put(updateObject.getId(), updateObject);
        }
    }

    public void delete(T item) {
        items.remove(item.getId());
    }

    public T create(T newObject) {
        //Create a new Id
        int newId = nextId++;
        newObject.setId(newId);
        items.put(newId, newObject);

        return newObject;
    }

    public T get(int id) {
        return items.get(id);
    }

    public List<T> getAll() {
        return new ArrayList<T>(items.values());
    }

    public void deleteStore() {
        items = null;
    }

    public void createStore() {
        items = new HashMap<Integer, T>();
    }
}
