package com.devteam.module.base;

import com.devteam.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
@Setter
abstract  public class BaseEntity<T extends Serializable> {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "created_by")
    private String createdBy;

    @JsonFormat(pattern = DateUtil.COMPACT_DATETIME_FORMAT)
    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "modified_by")
    private String modifiedBy;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = DateUtil.COMPACT_DATETIME_FORMAT)
    @Column(name = "modified_time")
    private Date  modifiedTime;

}
