package net.scat.lp.springboot.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Table(name = "lp_label")
public class LPLabel {
    @Id
    private Integer id;

    private String labelName;

    private String labelLink;

    private String labelColor;

    private Integer isDel;

    private Date createTime;

    private Date updateTime;
}
