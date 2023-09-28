package com.lzb.component.operation.context;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.lzb.component.operation.annotation.Property;
import com.lzb.component.operation.diff.ArrayDiffer;
import de.danielbechler.diff.ObjectDiffer;
import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.comparison.ComparisonService;
import de.danielbechler.diff.identity.IdentityService;
import de.danielbechler.diff.node.DiffNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;

import org.springframework.expression.spel.support.StandardEvaluationContext;

@Slf4j
public class OperationLogContext {

	private static final ThreadLocal<StandardEvaluationContext> CONTEXT_THREAD_LOCAL = new TransmittableThreadLocal<>();

	// 多个注解叠加的话，默认生成diffId,用于CONTEXT_THREAD_LOCAL.set()方法的时候
	private static AtomicInteger diffIdAutoAdd = new AtomicInteger(0);
	// 多个注解叠加的话，默认生成diffId,用于CONTEXT_THREAD_LOCAL.get()方法的时候
	private static AtomicInteger diffIdAutoGet = new AtomicInteger(0);

	private static final ThreadLocal<Map<Integer,String>> DIFF_THREAD_LOCAL = new TransmittableThreadLocal<>();

	public static final OperationLogContext INSTANCE = new OperationLogContext();

	public static StandardEvaluationContext getContext() {
		return CONTEXT_THREAD_LOCAL.get() == null ? new StandardEvaluationContext() : CONTEXT_THREAD_LOCAL.get();
	}

	public static void put(String key, Object value) {
		StandardEvaluationContext context = getContext();
		context.setVariable(key, value);
		CONTEXT_THREAD_LOCAL.set(context);
	}

	/**
	 * 用于增加diffId
	 * @return
	 */
	public static int getAndIncrementDiffIdAuto(){
		return diffIdAutoAdd.getAndIncrement();
	}
	/**
	 * 用于获取diffId
	 * @return
	 */
	public static int getDiffIdAutoGet(){
		return diffIdAutoGet.getAndIncrement();
	}
	public static void clearDiffId(){
		diffIdAutoAdd.set(0);
		diffIdAutoGet.set(0);
	}

	public OperationLogContext puts(String key, Object value) {
		StandardEvaluationContext context = getContext();
		context.setVariable(key, value);
		CONTEXT_THREAD_LOCAL.set(context);
		return INSTANCE;
	}

	public static void clearContext() {
		CONTEXT_THREAD_LOCAL.remove();
	}

	public static String getDiffDTOList() {
		Map<Integer, String> map = DIFF_THREAD_LOCAL.get();
		if (MapUtils.isEmpty(map)) return "";
		return map.getOrDefault(OperationLogContext.getDiffIdAutoGet(), "");
	}

	public static String getDiffDTOList(Integer diffId) {
		Map<Integer, String> map = DIFF_THREAD_LOCAL.get();
		if (MapUtils.isEmpty(map)) return "";
		return map.getOrDefault(diffId, "");
	}


	public static void clearDiff() {
		DIFF_THREAD_LOCAL.remove();
	}

	public static String diff(Object oldObject, Object newObject) {
		try {
			return diff(oldObject, newObject, true, OperationLogContext.getAndIncrementDiffIdAuto());
		}
		catch (Exception e) {
			// 吃掉异常不影响正常业务执行
			log.error("diff error ", e);
		}
		return "";
	}

	public static String diff(Object oldObject, Object newObject,Integer diffId) {
		try {
			return diff(oldObject, newObject, true,diffId);
		}
		catch (Exception e) {
			// 吃掉异常不影响正常业务执行
			log.error("diff error ", e);
		}
		return "";
	}

	/**
	 * diff 两个 对象
	 * @param oldObject 旧对象
	 * @param newObject 新对象
	 */
	public static String diff(Object oldObject, Object newObject, boolean context,Integer diffId) {

		ObjectDiffer objectDiffer = createObjectDifferHelper();
		DiffNode diff = objectDiffer.compare(oldObject, newObject);
		StringBuilder sb = new StringBuilder();
		diff.visit((node, visit) -> {
			final Object baseValue = node.canonicalGet(oldObject);
			final Object workingValue = node.canonicalGet(newObject);
			// 自定义对象直接进入到对象中 diff
			if (Objects.nonNull(node.getValueTypeInfo()) || Objects.equals(node.getParentNode().getValueType(), ArrayList.class)
					|| node.getParentNode().getValueType().isArray()) {
				return;
			}
			DiffNode parentNode = node.getParentNode();

			while (Objects.nonNull(parentNode)) {
				if (Objects.equals(parentNode.getValueType(), ArrayList.class)) {
					return;
				}
				parentNode = parentNode.getParentNode();
			}
			Property fieldAnnotation = node.getFieldAnnotation(Property.class);

			String alias = null;
			if (Objects.nonNull(fieldAnnotation)) {
				alias = fieldAnnotation.value();

			}
			String toString = Objects.toString(alias, node.getPropertyName());
			if (node.getParentNode() != null) {
				//获取对象的定语：比如：创建人的ID
				toString = getParentFieldName(node) + toString;
			}
			String baseValueStr = null;
			if (Objects.nonNull(baseValue)) {
				baseValueStr = baseValue.toString();
				if (baseValue.getClass().isArray()) baseValueStr = List.of((Object[]) baseValue).toString();
			}
			String workingValueStr = null;
			if (Objects.nonNull(workingValue)) {
				workingValueStr = workingValue.toString();
				if (workingValue.getClass().isArray()) workingValueStr = List.of((Object[]) workingValue).toString();
			}
			final String message = toString + " 由: " + baseValueStr + " 更新为: " + workingValueStr;

			sb.append(message).append("\n");
		});
		if (!context) {
			return sb.toString();
		}
		if (Objects.isNull(DIFF_THREAD_LOCAL.get())) {
			DIFF_THREAD_LOCAL.set(new HashMap<Integer, String>() {{
				put(diffId, sb.toString());
			}});
		} else {
			DIFF_THREAD_LOCAL.get().put(diffId, sb.toString());
		}
		return sb.toString();
	}

	private static String getParentFieldName(DiffNode node) {
		DiffNode parent = node.getParentNode();
		String fieldNamePrefix = "";
		while (parent != null) {
			Property diffLogFieldAnnotation = parent.getFieldAnnotation(Property.class);
			if (diffLogFieldAnnotation == null) {
				//父节点没有配置名称，不拼接
				parent = parent.getParentNode();
				continue;
			}
			fieldNamePrefix = diffLogFieldAnnotation.value().concat("-").concat(fieldNamePrefix);
			parent = parent.getParentNode();
		}
		return fieldNamePrefix;
	}

	public static ObjectDiffer createObjectDifferHelper() {
		final ObjectDifferBuilder objectDifferBuilder = ObjectDifferBuilder.startBuilding().comparison()
				.ofType(LocalDateTime.class).toUseEqualsMethod().and();

		return objectDifferBuilder
//				.differs().register((differDispatcher, nodeQueryService) -> new ByteArrayDiffer())
				.differs().register((differDispatcher, nodeQueryService) -> new ArrayDiffer(differDispatcher, new ComparisonService(objectDifferBuilder), new IdentityService(objectDifferBuilder)))
				.build();
	}
}
