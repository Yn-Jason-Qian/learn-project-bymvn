package net.scat.lp.springboot.dao;

import net.scat.lp.springboot.po.LPResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LPResourceMapperImpl implements LPResourceMapper{

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<LPResource> selectList(int start, int limit) {
        return jdbcTemplate.query("select * from lp_resource limit ?,?", new Object[]{start, limit}, new BeanPropertyRowMapper<>(LPResource.class));
    }

    @Override
    public void insert(LPResource resource) {
        jdbcTemplate.update("insert into lp_resource(title, description, author, url, folder_id,labels) values(?,?,?,?,?,?)", new Object[]{
                resource.getTitle(),
                resource.getDescription(),
                resource.getAuthor(),
                resource.getUrl(),
                resource.getFolderId(),
                resource.getLabels()
        });
    }

    @Override
    public void update(LPResource resource) {
        jdbcTemplate.update("update lp_resource set title=?,description=?,author=?,url=?,folderId=?,labels=? where id =?", new Object[]{
                resource.getTitle(),
                resource.getDescription(),
                resource.getAuthor(),
                resource.getUrl(),
                resource.getFolderId(),
                resource.getLabels(),
                resource.getId()
        });
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("update lp_resouce set is_del = 1 where id = ?", id);
    }
}
