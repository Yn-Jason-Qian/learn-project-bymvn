package net.scat.lp.springboot.service;

import net.scat.lp.springboot.dao.LPResourceMapper;
import net.scat.lp.springboot.po.LPResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LPResourceServiceImpl implements LPResourceService{
    @Autowired
    private LPResourceMapper lpResourceMapper;
    @Override
    public List<LPResource> getList(int page, int pageSize) {
        int start = (page - 1) * pageSize;
        return lpResourceMapper.selectList(start, pageSize);
    }

    @Override
    public void add(LPResource resource) {
        lpResourceMapper.insert(resource);
    }

    @Override
    public void update(LPResource resource) {
        lpResourceMapper.update(resource);
    }

    @Override
    public void delete(int id) {
        lpResourceMapper.delete(id);
    }
}
