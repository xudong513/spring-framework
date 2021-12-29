/*
 * Copyright 2002-2020 the original author or authors.
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

package org.springframework.core;

/**
 * AliasRegistry是用于管理别名的通用接口,定义了别名的简单增删等操作;
 * <p>
 * BeanDefinitionRegistry: AliasRegistry的子接口(扩展接口),使BeanDefinition的注册接口具有别名管理的功能;
 * SimpleAliasRegistry: AliasRegistry的简单实现;
 * SimpleBeanDefinitionRegistry: BeanDefinitionRegistry的简单实现;
 *
 * @see SimpleAliasRegistry
 * @see org.springframework.beans.factory.support.BeanDefinitionRegistry
 * @see org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry
 * @since 2.5.2
 */
public interface AliasRegistry {

	/**
	 * 为指定名称注册一个别名
	 *
	 * @param name  原名称
	 * @param alias 要注册的别名
	 * @throws IllegalStateException 如果别名已在被使用,并且不允许别名覆盖;
	 */
	void registerAlias(String name, String alias);

	/**
	 * 从注册中心中移除指定别名
	 *
	 * @param alias 待移除的别名
	 * @throws IllegalStateException 如果未找到指定别名
	 */
	void removeAlias(String alias);

	/**
	 * 判断给定的名称是否是别名
	 *
	 * @param name 待校验的名称
	 * @return 给定的名称是否是别名
	 */
	boolean isAlias(String name);

	/**
	 * 获取给定name的所有别名信息
	 *
	 * @param name 给定名称
	 * @return 返回别名集, 如果没有别名则返回空数组
	 */
	String[] getAliases(String name);

}
