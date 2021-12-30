/*
 * Copyright 2002-2015 the original author or authors.
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

import java.io.ByteArrayInputStream;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.DefaultDeserializer;
import org.springframework.core.serializer.Deserializer;
import org.springframework.util.Assert;

/**
 * 反序列化转换器,将字节数组中的数据转换为对象
 * 该转换器{@link Converter}代理了一个反序列化器{@link Deserializer} ,转换方法的实现实际上是通过反序列化器的反序列化方法完成的.
 *
 * @author Gary Russell
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @since 3.0.5
 */
public class DeserializingConverter implements Converter<byte[], Object> {

	/** 被代理的反序列化器 */
	private final Deserializer<Object> deserializer;


	/**
	 * 创建一个反序列化转换器{@code DeserializingConverter}，默认使用标准的Java反序列化
	 *
	 * @see DefaultDeserializer#DefaultDeserializer()
	 */
	public DeserializingConverter() {
		this.deserializer = new DefaultDeserializer();
	}

	/**
	 * 创建一个反序列化转换器{@code DeserializingConverter},默认使用DefaultDeserializer，并指定对应的类加载器
	 *
	 * @see DefaultDeserializer#DefaultDeserializer(ClassLoader)
	 * @since 4.2.1
	 */
	public DeserializingConverter(ClassLoader classLoader) {
		this.deserializer = new DefaultDeserializer(classLoader);
	}

	/** 创建一个反序列化转换器{@code DeserializingConverter},并指定其代理的反序列化器 {@link Deserializer} */
	public DeserializingConverter(Deserializer<Object> deserializer) {
		Assert.notNull(deserializer, "Deserializer must not be null");
		this.deserializer = deserializer;
	}


	/** 将字节数组中的数据转换为对象 */
	@Override
	public Object convert(byte[] source) {
		ByteArrayInputStream byteStream = new ByteArrayInputStream(source);
		try {
			return this.deserializer.deserialize(byteStream);
		} catch (Throwable ex) {
			throw new SerializationFailedException("Failed to deserialize payload. " +
					"Is the byte array a result of corresponding serialization for " +
					this.deserializer.getClass().getSimpleName() + "?", ex);
		}
	}

}
