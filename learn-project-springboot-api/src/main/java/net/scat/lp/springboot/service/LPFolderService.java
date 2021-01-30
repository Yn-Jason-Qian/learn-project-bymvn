package net.scat.lp.springboot.service;

import net.scat.lp.springboot.po.LPFolder;

import java.util.List;

public interface LPFolderService extends BaseService<LPFolder>{
    void delete(List<Integer> ids);
}
