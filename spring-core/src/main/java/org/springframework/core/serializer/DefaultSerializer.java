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

package org.springframework.core.serializer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * 序列化器接口{@link Serializer}的默认实现：使用Java(反)序列化技术将对象Object写入输出流OutputStream
 *
 * @author Gary Russell
 * @author Mark Fisher
 * @see ObjectOutputStream
 * @since 3.0.5
 */
public class DefaultSerializer implements Serializer<Object> {

	/**
	 * 使用 Java 序列化将源对象(sourceObject )写入输出流,源对象必须实现序列化接口{@link Serializable}
	 *
	 * @see ObjectOutputStream#writeObject(Object)
	 */
	@Override
	public void serialize(Object object, OutputStream outputStream) throws IOException {
		if (!(object instanceof Serializable)) {
			throw new IllegalArgumentException(getClass().getSimpleName() + " requires a Serializable payload " +
					"but received an object of type [" + object.getClass().getName() + "]");
		}
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		objectOutputStream.writeObject(object);
		objectOutputStream.flush();
	}

}
