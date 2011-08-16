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

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.SimpleXmlSerializer;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * Html2Xml host object for converting HTML to XML for WSO2 Mashup Server.
 * 
 * @author Frederick Haebin Na
 */
public class Html2XmlHostObject extends ScriptableObject {
    private static HtmlCleaner htmlCleaner = new HtmlCleaner();
    private static SimpleXmlSerializer xmlizer = new SimpleXmlSerializer(
	    htmlCleaner.getProperties());
    private static String encoding = "UTF-8";
    private static String xmlEndTag = "?>";
    private static String className = "Html2Xml";

    /**
     * Type to be used for this object inside the javascript.
     */
    public String getClassName() {
	return className;
    }

    /**
     * Constructor the user will be using inside javaScript, this doesn't need
     * any arguments to be passed
     */
    public static Scriptable jsConstructor(Context cx, Object[] args,
	    Function ctorObj, boolean inNewExpr) {
	Html2XmlHostObject html2xml = new Html2XmlHostObject();
	if (args.length != 0) {
	    throw new RuntimeException(
		    "Html2Xml constructor doesn't accept any arguments");
	}
	return html2xml;
    }
    
    public static Scriptable jsFunction_transform(Context cx,
	    Scriptable thisObj, Object[] args, Function funObj) {
	String page = "";
	// parse the passed arguments into this executeMethod()
	switch (args.length) {
	case 1:
	    if (args[0] instanceof String) {
		page = (String) args[0];
	    } else {
		throw new RuntimeException("Page text should be a String value");
	    }
	    break;
	default:
	    throw new RuntimeException("Need a page text as an argument.");
	}

	return cx.newObject(thisObj, "XML", new Object[] { transform(page) });
    }

    protected static String transform(String html) {
	try {
	    String body = xmlizer
		    .getXmlAsString(htmlCleaner.clean(html), encoding);
	    return body.substring(body.indexOf(xmlEndTag) + 2).trim();
	} catch (Exception e) {
	    throw new RuntimeException("Failed to clean html.", e);
	}
    }
}
