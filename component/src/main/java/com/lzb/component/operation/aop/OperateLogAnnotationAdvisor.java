package com.lzb.component.operation.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

public class OperateLogAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {

	private final Advice advice;

	private final Pointcut pointcut;

	private final Set<Class<? extends Annotation>> annotations;

	public OperateLogAnnotationAdvisor( MethodInterceptor advice,
			@NonNull Set<Class<? extends Annotation>> annotations) {
		this.advice = advice;
		this.annotations = annotations;
		this.pointcut = buildPointcut();
	}

	@Override
	public Pointcut getPointcut() {
		return this.pointcut;
	}

	@Override
	public Advice getAdvice() {
		return this.advice;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		if (this.advice instanceof BeanFactoryAware) {
			((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
		}
	}

	private Pointcut buildPointcut() {
		ComposablePointcut result = null;
		for (Class<? extends Annotation> annotation : this.annotations) {
			Pointcut cpc = new AnnotationMatchingPointcut(annotation, true);
			Pointcut mpc = new AnnotationMethodPoint(annotation);
			if (result == null) {
				result = new ComposablePointcut(cpc).union(mpc);
			} else {
				result.union(cpc).union(mpc);
			}
		}
		return result;
	}

	/**
	 * In order to be compatible with the spring lower than 5.0
	 */
	private static class AnnotationMethodPoint implements Pointcut {

		private final Class<? extends Annotation> annotationType;

		public AnnotationMethodPoint(Class<? extends Annotation> annotationType) {
			Assert.notNull(annotationType, "Annotation type must not be null");
			this.annotationType = annotationType;
		}

		@Override
		public ClassFilter getClassFilter() {
			return ClassFilter.TRUE;
		}

		@Override
		public MethodMatcher getMethodMatcher() {
			return new AnnotationMethodMatcher(annotationType);
		}

		private static class AnnotationMethodMatcher extends StaticMethodMatcher {
			private final Class<? extends Annotation> annotationType;

			public AnnotationMethodMatcher(Class<? extends Annotation> annotationType) {
				this.annotationType = annotationType;
			}

			@Override
			public boolean matches(Method method, Class<?> targetClass) {
				if (matchesMethod(method)) {
					return true;
				}
				// Proxy classes never have annotations on their redeclared methods.
				if (Proxy.isProxyClass(targetClass)) {
					return false;
				}
				// The method may be on an interface, so let's check on the target class as well.
				Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
				return (specificMethod != method && matchesMethod(specificMethod));
			}

			private boolean matchesMethod(Method method) {
				return AnnotatedElementUtils.hasAnnotation(method, this.annotationType);
			}
		}
	}
}
