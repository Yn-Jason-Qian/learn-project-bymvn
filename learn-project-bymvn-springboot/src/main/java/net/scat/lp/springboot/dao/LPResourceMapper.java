package net.scat.lp.springboot.dao;

import net.scat.lp.springboot.po.LPResource;

import java.util.List;

public interface LPResourceMapper {

    List<LPResource> selectList(int start, int limit);

    void insert(LPResource resource);

    void update(LPResource resource);

    void delete(int id);
}
