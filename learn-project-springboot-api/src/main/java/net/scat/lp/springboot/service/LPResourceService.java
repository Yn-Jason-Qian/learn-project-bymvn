package net.scat.lp.springboot.service;


import net.scat.lp.springboot.po.LPResource;

import java.util.List;

public interface LPResourceService {
    List<LPResource> getList(int page, int pageSize);

    void add(LPResource resource);

    void update(LPResource resource);

    void delete(int id);
}
