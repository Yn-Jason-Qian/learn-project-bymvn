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
@Table(name = "lp_folder")
public class LPFolder {
    @Id
    private Integer id;

    private String folderName;

    private Integer parentFolderId;

    private String labels;

    private Integer isDel;

    private Date createTime;

    private Date updateTime;
}
