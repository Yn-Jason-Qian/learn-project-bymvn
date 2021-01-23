package net.scat.lp.springboot.service;

import net.scat.lp.springboot.dao.LPFolderMapper;
import net.scat.lp.springboot.po.LPFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LPFolderServiceImpl implements LPFolderService{
    @Autowired
    private LPFolderMapper lpFolderMapper;

    @Override
    public List<LPFolder> getFolderList(int page, int pageSize) {
        int start = (page - 1) * pageSize;
        return lpFolderMapper.selectList(start, pageSize);
    }

    @Override
    public void add(LPFolder folder) {
        lpFolderMapper.insert(folder);
    }

    @Override
    public void update(LPFolder folder) {
        lpFolderMapper.update(folder);
    }

    @Override
    public void delete(int id) {
        lpFolderMapper.delete(id);
    }

    @Override
    public void delete(List<Integer> ids) {
        lpFolderMapper.deleteByIds(ids);
    }
}
