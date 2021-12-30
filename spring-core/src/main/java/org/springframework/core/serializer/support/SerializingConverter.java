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

package org.springframework.core.serializer.support;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.DefaultSerializer;
import org.springframework.core.serializer.Serializer;
import org.springframework.util.Assert;

/**
 * 序列化转换器,将对象转换为字节数组
 * 该转换器{@link Converter}代理了一个序列化器{@link Serializer} ,转换方法的实现实际上是通过序列化器的序列化方法完成的.
 *
 * @author Gary Russell
 * @author Mark Fisher
 * @since 3.0.5
 */
public class SerializingConverter implements Converter<Object, byte[]> {

	/** 被代理的序列化器 */
	private final Serializer<Object> serializer;

	/** 创建一个序列化转换器{@code SerializingConverter}，默认使用标准的Java序列化 */
	public SerializingConverter() {
		this.serializer = new DefaultSerializer();
	}

	/** 创建一个序列化转换器{@code SerializingConverter},并指定其代理的序列化器 {@link Serializer}. */
	public SerializingConverter(Serializer<Object> serializer) {
		Assert.notNull(serializer, "Serializer must not be null");
		this.serializer = serializer;
	}

	/** 序列化源对象并返回对应的字节数组 */
	@Override
	public byte[] convert(Object source) {
		try {
			return this.serializer.serializeToByteArray(source);
		} catch (Throwable ex) {
			throw new SerializationFailedException("Failed to serialize object using " +
					this.serializer.getClass().getSimpleName(), ex);
		}
	}

}
