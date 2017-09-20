/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  See the NOTICE file distributed with this work for additional
 *  information regarding copyright ownership.
 */

package org.spinrdf.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;


/**
 * <p>IOUtil class.</p>
 *

 * @version $Id: $Id
 */
public class IOUtil {

	/**
	 * <p>loadString.</p>
	 *
	 * @param reader a {@link java.io.Reader} object.
	 * @return a {@link java.lang.StringBuffer} object.
	 * @throws java.io.IOException if any.
	 */
	public static StringBuffer loadString(Reader reader) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader bis = new BufferedReader(reader);
		for (;;) {
			int c = bis.read();
			if (c < 0) {
				break;
			}
			sb.append((char)c);
		}
		bis.close();
		return sb;
	}

	/**
	 * <p>loadStringUTF8.</p>
	 *
	 * @param in a {@link java.io.InputStream} object.
	 * @return a {@link java.lang.StringBuffer} object.
	 * @throws java.io.IOException if any.
	 */
	public static StringBuffer loadStringUTF8(
			InputStream in) throws IOException {
		try {
			return loadString(new InputStreamReader(in,"utf-8"));
		}
		catch (UnsupportedEncodingException e) {
			throw new Error(e);
		}
	}

}
