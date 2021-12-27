/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.lang.Nullable;

/**
 * @see BeanFactory 是访问Bean容器的顶级接口,但是BeanFactory过于简单,并不适合实际的企业级应用的开发;
 * 因此,Spring提供了另外一套Ioc容器的体系-ApplicationContext体系.
 * <p>
 * Central interface to provide configuration for an application.
 * This is read-only while the application is running, but may be
 * reloaded if the implementation supports this.
 *
 * <p> 一个ApplicationContext提供:
 * @see EnvironmentCapable 用于提供当前系统环境的Environment组件
 * @see ListableBeanFactory BeanFactory的子接口,扩展BeanFactory使其支持迭代Ioc容器持有的Bean对象
 * @see HierarchicalBeanFactory BeanFactory的子接口,扩展BeanFactory使其支持层级结构
 * @see MessageSource 提供解析信息的能力,支持信息的国际化及参数替换
 * @see ApplicationEventPublisher 用于发布事件,能够将事件发布到注册的监听器
 * @see ResourcePatternResolver ResourceLoader的子接口,用于解析资源文件
 *
 * <p> Spring中Capable后缀的接口带有getXXX的含义,也就是实现了这个接口,就可以通过该接口的实例获取到XXX,和Aware接口很类似;
 * 如果ListableBeanFactory同时也是HierarchicalBeanFactory,那么大多数情况下,只迭代当前Ioc容器持有的Bean对象,不会向父级递归迭代;
 * ResourcePatternResolver是ResourceLoader接口的拓展接口,其特殊的地方在于--支持带有"*"这种通配符的资源路径的解析;
 * <p>
 * Inheritance from a parent context. Definitions in a descendant context
 * will always take priority. This means, for example, that a single parent
 * context can be used by an entire web application, while each servlet has
 * its own child context that is independent of that of any other servlet.
 *
 *
 * <p>In addition to standard {@link org.springframework.beans.factory.BeanFactory}
 * lifecycle capabilities, ApplicationContext implementations detect and invoke
 * {@link ApplicationContextAware} beans as well as {@link ResourceLoaderAware},
 * {@link ApplicationEventPublisherAware} and {@link MessageSourceAware} beans.
 */
public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, HierarchicalBeanFactory,
		MessageSource, ApplicationEventPublisher, ResourcePatternResolver {

	/**
	 * 返回此上下文的唯一ID
	 *
	 * @return 上下文的唯一ID, 如果没有则返回{@code null}
	 */
	@Nullable
	String getId();

	/**
	 * 返回此上下文所属的应用程序的名称
	 *
	 * @return 已部署应用程序的名称, 默认为空字符串
	 */
	String getApplicationName();

	/**
	 * 返回此上下文的显示名称
	 *
	 * @return 上下文的显示名称, 永不为{@code null}
	 */
	String getDisplayName();

	/**
	 * 返回首次加载此上下文时的时间戳
	 *
	 * @return 首次加载此上下文时的时间戳（毫秒）
	 */
	long getStartupDate();

	/**
	 * 返回父上下文,如果没有父上下文则返回 {@code null}
	 *
	 * @return 父上下文, 没有则返回{@code null}
	 */
	@Nullable
	ApplicationContext getParent();

	/**
	 * 本方法主要用作 ApplicationContext 接口上的一个方便的、特定的工具.
	 * <p>
	 * AutowireCapableBeanFactory是在BeanFactory的基础上实现对已存在实例的管理,可以使用这个接口集成其他框架,捆绑并填充并不由Spring管理生命周期并已存在的实例;
	 * ApplicationContext没有继承AutowireCapableBeanFactory接口,因为应用代码很少用到此功能,如果需要的话,可以调用getAutowireCapableBeanFactory方法;
	 * <p>
	 * 注意：
	 * 从 4.2 开始，此方法将在应用上下文关闭后始终抛出 IllegalStateException;
	 * 在当前的 Spring Framework 版本中，只有可刷新的应用上下文才会这样做;
	 * 从 4.2 开始，所有应用上下文实现都需要遵守;
	 *
	 * @return 上下文的AutowireCapableBeanFactory
	 * @throws IllegalStateException 1、上下文不支持AutowireCapableBeanFactory接口;
	 *                               2、上下文没有支持自动装配(autowire-capable)的bean工厂,例如从未执行refresh方法;
	 *                               3、上下文已经关闭;obtainFreshBeanFactory
	 * @see AbstractApplicationContext#refresh() 该方法中的obtainFreshBeanFactory用于刷新内部bean工厂
	 * @see ConfigurableApplicationContext#getBeanFactory()
	 * @see AbstractRefreshableApplicationContext#getBeanFactory()
	 */
	AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException;

}
