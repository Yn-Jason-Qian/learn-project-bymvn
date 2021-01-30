package net.scat.lp.springboot.po;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lp_article")
public class LPArticle {
    @Id
    private Integer id;

    private String title;

    private Integer folderId;

    private String content;

    private String labels;

    private Integer isDel;

    private Date createTime;

    private Date updateTime;
}
