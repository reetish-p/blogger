package com.example.blogger.tags;

import com.example.blogger.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;


@Entity(name ="tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity extends BaseEntity {
    private String name;
}
