package com.lzb.component.mybatis.extend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.ibatis.logging.LogFactory;

import org.springframework.transaction.annotation.Transactional;

/**
 * 扩展mybatis-plus service
 *
 * @author mac
 * @date 2022/09/21
 */
@Slf4j
public class MyBaseService<T extends Serializable, M extends MyBaseMapper<T>> extends ServiceImpl<M, T> implements IService<T> {

	/**
	 * 更新实体所有字段，add_time/update_time属于数据库维护的字段，没有业务含义，不映射到Do
	 *
	 * @param entity 实体对象
	 * @return boolean
	 * @author mac
	 * @date 2022/11/01
	 */
	public boolean updateAllFieldsById(T entity) {
		return getBaseMapper().updateAllFieldsById(entity);
	}

	/**
	 * 更新实体所有字段，add_time/update_time属于数据库维护的字段，没有业务含义，不映射到Do
	 *
	 * @param entities 实体对象
	 * @author mac
	 * @date 2022/11/01
	 */
	public void batchUpdateAllFieldsById(List<T> entities) {
		SqlHelper.executeBatch(entityClass, LogFactory.getLog(this.getClass().toString()), sqlSession -> {
			entities.forEach(this::updateAllFieldsById);
		});
	}


	/**
	 * 保存或者更新
	 *
	 * @param entityDo 实体do
	 * @param idColumn id列
	 * @return boolean
	 * @author mac
	 * @date 2022/11/23
	 */
	public <ID> boolean saveOrUpdateAllFieldsById(@NonNull T entityDo, SFunction<T, ID> idColumn) {
		ID id = idColumn.apply(entityDo);
		if (Objects.isNull(id) || !lambdaQuery().eq(idColumn, id).exists()) {
			return super.save(entityDo);
		}
		return updateAllFieldsById(entityDo);
	}

	/**
	 * 新增、更新do集合
	 * @param saveList
	 * @param updateList
	 * @param <T>
	 */
	@Transactional(rollbackFor = Exception.class)
	public void batchSaveAndUpdateAllFieldsById(@NonNull List<T> saveList, @NonNull List<T> updateList) {
		saveBatch(saveList);
		batchUpdateAllFieldsById(updateList);
	}
}
