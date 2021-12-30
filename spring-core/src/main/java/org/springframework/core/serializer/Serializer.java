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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Spring序列化器接口：策略接口,用于将对象Object转化为输出流OutputStream
 *
 * @param <T> 对象的类型
 * @author Gary Russell
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @see Deserializer
 * @since 3.0.5
 */
@FunctionalInterface
public interface Serializer<T> {

	/**
	 * 对象序列化：将类型为 T 的对象写入给定的OutputStream
	 * <p>注意：该方法的实现不应关闭给定的 OutputStream（或该 OutputStream 的装饰器）,而应将其留给调用者
	 *
	 * @param object       要序列化的对象
	 * @param outputStream 待写入的OutputStream
	 * @throws IOException 流写入时出错
	 */
	void serialize(T object, OutputStream outputStream) throws IOException;

	/**
	 * 对象序列化为数组：将类型为 T 的对象转换为序列化的字节数组
	 *
	 * @param object 要序列化的对象
	 * @return 序列化的字节数组
	 * @throws IOException 序列化失败时抛出异常
	 * @since 5.2.7
	 */
	default byte[] serializeToByteArray(T object) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		serialize(object, out);
		return out.toByteArray();
	}

}
