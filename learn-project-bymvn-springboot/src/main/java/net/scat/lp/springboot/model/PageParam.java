package net.scat.lp.springboot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageParam {
    private int page;

    private int rows;

    private String sidx;

    private String sord;

}
