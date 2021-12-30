/*
 * Copyright 2002-2017 the original author or authors.
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

package org.springframework.core.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import org.springframework.core.ConfigurableObjectInputStream;
import org.springframework.core.NestedIOException;
import org.springframework.lang.Nullable;

/**
 * 反序列化器接口{@link Deserializer}的默认实现：使用Java(反)序列化技术将输入流InputStream中的数据转换(读取)为对象Object
 *
 * @author Gary Russell
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @see ObjectInputStream
 * @since 3.0.5
 */
public class DefaultDeserializer implements Deserializer<Object> {

	@Nullable
	private final ClassLoader classLoader;


	/**
	 * 创建默认的反序列化器{@code DefaultDeserializer}
	 * Create a {@code DefaultDeserializer} with default {@link ObjectInputStream} configuration, using the "latest user-defined ClassLoader".
	 */
	public DefaultDeserializer() {
		this.classLoader = null;
	}

	/**
	 * 创建默认的反序列化器{@code DefaultDeserializer}，并为其配置指定的类加载器{@code ClassLoader}
	 *
	 * @see ConfigurableObjectInputStream#ConfigurableObjectInputStream(InputStream, ClassLoader)
	 * @since 4.2.1
	 */
	public DefaultDeserializer(@Nullable ClassLoader classLoader) {
		this.classLoader = classLoader;
	}


	/**
	 * 从提供的输入流InputStream中读取并将内容反序列化为一个对象
	 *
	 * @see ObjectInputStream#readObject()
	 */
	@Override
	@SuppressWarnings("resource")
	public Object deserialize(InputStream inputStream) throws IOException {
		ObjectInputStream objectInputStream = new ConfigurableObjectInputStream(inputStream, this.classLoader);
		try {
			return objectInputStream.readObject();
		} catch (ClassNotFoundException ex) {
			throw new NestedIOException("Failed to deserialize object type", ex);
		}
	}

}
