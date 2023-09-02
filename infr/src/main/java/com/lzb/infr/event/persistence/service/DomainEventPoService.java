package com.lzb.infr.event.persistence.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.infr.event.persistence.DomainEventPo;
import com.lzb.infr.event.persistence.mapper.DomainEventPoMapper;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizebin
 * @since 2023-09-02
 */
@Service
public class DomainEventPoService extends ServiceImpl<DomainEventPoMapper, DomainEventPo> implements IService<DomainEventPo> {

}