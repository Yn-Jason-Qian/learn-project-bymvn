package net.scat.lp.springboot.service;

import java.util.List;

public interface BaseService<T> {

    void add(T t);

    void update(T t);

    void delete(int id);

    List<T> getList(int page, int pageSize);
}
