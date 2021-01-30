package net.scat.lp.springboot.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T>{
    @Autowired
    private Mapper<T> mapper;

    @Override
    public void add(T t) {
        mapper.insertSelective(t);
    }

    @Override
    public void update(T t) {
        mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public void delete(int id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<T> getList(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectAll();
    }
}
