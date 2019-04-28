package com.zy.graduation1.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zy.graduation1.entity.SuperEntity;

import com.baomidou.mybatisplus.annotation.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zy
 * @since 2019-04-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("dictionary_data")
public class DictionaryData extends SuperEntity<DictionaryData> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("dict_value")
    private String dictValue;
    @TableField("dictdata_name")
    private String dictdataName;
    @TableField("dictdata_value")
    private Integer dictdataValue;
    /**
     *  0不固定，1固定；固定就不能再去修改了
     */
    @TableField("is_fixed")
    private String isFixed;
    /**
     * 取消标识
     */
    @TableField("is_cancel")
    private String isCancel;
    /**
     * 父节点，作为简单的树形结构
     */
    @TableField("parent_id")
    private Integer parentId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
