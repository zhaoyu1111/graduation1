package com.zy.graduation1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.entity.AlumniAssociation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2019-03-18
 */
public interface AlumniAssociationService extends IService<AlumniAssociation> {

    /**
     * 分页查询校友会信息
     * @param associaName
     * @param address
     * @param currentPage
     * @return
     */
    IPage<AlumniAssociation> queryAlumniAssociation(String associaName, String address, Integer currentPage);

    /**
     * 新增或修改校友会信息
     * @param associaId
     * @param associaName
     * @param address
     * @param presidentId
     */
    void saveOrUpdateAssocia(Long associaId, String associaName, String address,
                             Long presidentId, String descrip, Integer deleted);

    /**
     * 删除校友会信息
     * @param associaId
     */
    void deleteAssocia(Long associaId);
}
