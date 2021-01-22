package net.scat.lp.springboot.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Table(name = "lp_article")
public class LPArticle {
    @Id
    private Integer id;

    private String title;

    private Integer folderIdd;

    private String content;

    private String labels;

    private Integer isDel;

    private Date createTime;

    private Date updateTime;
}
