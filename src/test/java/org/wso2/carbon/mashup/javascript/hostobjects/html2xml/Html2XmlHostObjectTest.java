/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.mashup.javascript.hostobjects.html2xml;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

/**
 * @author Frederick Haebin Na
 */
public class Html2XmlHostObjectTest {

    @Test
    public void testGetClassName() {
	Html2XmlHostObject cleaner = new Html2XmlHostObject();
	assertThat(cleaner.getClassName(), is("Html2Xml"));
    }

    @Test
    public void testTransform() throws IOException {
	assertThat(
		Html2XmlHostObject.transform("<html><body><b>1</b><br>2<table><tr><td>3</td></tr></table></body></html>"),
		is("<html><head /><body><b>1</b><br />2<table><tbody><tr><td>3</td></tr></tbody></table></body></html>"));
    }
}
