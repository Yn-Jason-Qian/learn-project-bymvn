package net.scat.lp.springboot.service;

import net.scat.lp.springboot.po.LPFolder;

import java.util.List;

public interface LPFolderService {

    List<LPFolder> getFolderList(int page, int pageSize);

    void add(LPFolder folder);

    void update(LPFolder folder);

    void delete(int id);

    void delete(List<Integer> ids);
}
