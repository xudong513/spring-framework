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

package org.springframework.core.serializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Spring反序列化器接口：策略接口,用于将输入流InputStream中的数据转换为对象Object
 *
 * @param <T> 对象类型
 * @author Gary Russell
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @see Serializer
 * @since 3.0.5
 */
@FunctionalInterface
public interface Deserializer<T> {

	/**
	 * 从给定的输入流InputStream反序列化(读取)一个类型为 T 的对象
	 * <p>注意：该方法的实现不应关闭给定的 InputStream（或该 InputStream 的任何装饰器），而应将其留给调用者
	 *
	 * @param inputStream the input stream
	 * @return the deserialized object
	 * @throws IOException in case of errors reading from the stream
	 */
	T deserialize(InputStream inputStream) throws IOException;

	/**
	 * 从给定的字节数组中反序列化(读取)一个类型为 T 的对象
	 *
	 * @param serialized 字节数组
	 * @return 反序列化的对象
	 * @throws IOException 反序列化失败时抛出异常
	 * @since 5.2.7
	 */
	default T deserializeFromByteArray(byte[] serialized) throws IOException {
		return deserialize(new ByteArrayInputStream(serialized));
	}

}
