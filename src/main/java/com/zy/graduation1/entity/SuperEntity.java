package com.zy.graduation1.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
public abstract class SuperEntity<T extends Model> extends Model<Test>  {

    @TableField(value = "ctime", fill = FieldFill.INSERT)
    private Long ctime;

    @TableField(value = "utime", fill = FieldFill.INSERT_UPDATE)
    private Long utime;
}
