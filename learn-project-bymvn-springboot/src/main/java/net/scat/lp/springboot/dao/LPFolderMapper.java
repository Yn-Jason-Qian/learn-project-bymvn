package net.scat.lp.springboot.dao;

import net.scat.lp.springboot.po.LPFolder;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LPFolderMapper {
    @Select("select * from lp_folder limit #{start},#{end}")
    @Results(id = "lp_folder_map", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "folder_name", property = "folderName"),
            @Result(column = "parent_folder_id", property = "parentFolderId"),
            @Result(column = "labels", property = "labels"),
            @Result(column = "is_del", property = "isDel"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<LPFolder> selectList(@Param("start") int start,@Param("end") int end);

    @Insert("insert into lp_folder(folder_name, parent_folder_id, labels) values(#{folderName}, #{parentFolderId}, #{labels})")
    void insert(LPFolder folder);

    @Update("update lp_folder set folder_name = #{folderName}, parent_folder_id = #{parentFolderId}, labels = #{labels} where id = #{id}")
    void update(LPFolder folder);

    @Update("update lp_folder set is_del = 1 where id = #{id}")
    void delete(@Param("id") int id);

    @UpdateProvider(type = DeleteSqlBuilder.class, method = "deleteByIds")
    void deleteByIds(@Param("ids") List<Integer> ids);

    class DeleteSqlBuilder {
        public String deleteByIds(@Param("ids") List<Integer> list) {
            StringBuilder builder = new StringBuilder("update lp_folder set is_del = 1 where id in (");
            for (Integer id : list) {
                builder.append(id.toString()).append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append(")");
            return builder.toString();
        }
    }
}
