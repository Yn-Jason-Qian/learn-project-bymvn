package net.scat.lp.springboot.po;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lp_resource")
public class LPResource {
    @Id
    private Integer id;

    private String title;

    private String description;

    private String author;

    private String url;

    private Integer folderId;

    private String labels;

    private Integer isDel;

    private Date createTime;

    private Date updateTime;
}
