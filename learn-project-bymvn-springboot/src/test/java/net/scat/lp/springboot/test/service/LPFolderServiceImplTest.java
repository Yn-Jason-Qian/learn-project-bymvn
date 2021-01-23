package net.scat.lp.springboot.test.service;

import lombok.extern.slf4j.Slf4j;
import net.scat.lp.springboot.po.LPFolder;
import net.scat.lp.springboot.service.LPFolderService;
import net.scat.lp.springboot.util.JSONUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class LPFolderServiceImplTest {
    @Autowired
    private LPFolderService lpFolderService;

    @Test
    public void getFolderList() {
        log.info(JSONUtils.toJSON(lpFolderService.getFolderList(1, 10)));
    }

    @Test
    public void add() {
        lpFolderService.add(LPFolder.builder()
                .folderName("java")
                .parentFolderId(0)
                .labels("java")
                .build());
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void testDelete() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        lpFolderService.delete(ids);
    }
}