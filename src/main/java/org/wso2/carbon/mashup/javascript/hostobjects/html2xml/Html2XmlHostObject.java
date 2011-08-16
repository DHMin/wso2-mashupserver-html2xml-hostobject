/*
 * @(#)HtmlCleanerHostObject.java $version 2011. 8. 11.
 *
 * Copyright 2007 NHN Corp. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.wso2.carbon.mashup.javascript.hostobjects.html2xml;

import java.io.IOException;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.SimpleXmlSerializer;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.wso2.carbon.CarbonException;

/**
 * Html2Xml converter for WSO2 Mashup Server.
 * 
 * @author Frederick Haebin Na
 */
public class Html2XmlHostObject extends ScriptableObject {
    private static HtmlCleaner htmlCleaner = new HtmlCleaner();
    private static SimpleXmlSerializer xmlizer = new SimpleXmlSerializer(
	    Html2XmlHostObject.htmlCleaner.getProperties());
    private static String encoding = "UTF-8";
    private static String xmlEndTag = "?>";

    /**
     * Type to be used for this object inside the javascript.
     */
    public String getClassName() {
	return "Html2Xml";
    }

    /**
     * Constructor the user will be using inside javaScript, this doesn't need
     * any arguments to be passed
     */
    public static Scriptable jsConstructor(Context cx, Object[] args,
	    Function ctorObj, boolean inNewExpr) throws CarbonException {
	Html2XmlHostObject html2xml = new Html2XmlHostObject();
	if (args.length != 0) {
	    throw new CarbonException(
		    "Html2Xml constructor doesn't accept any arguments");
	}
	return html2xml;
    }

    public static Scriptable jsFunction_transform(Context cx,
	    Scriptable thisObj, Object[] args, Function funObj)
	    throws CarbonException, IOException {
	Html2XmlHostObject html2xml = (Html2XmlHostObject) thisObj;
	String page = "";
	// parse the passed arguments into this executeMethod()
	switch (args.length) {
	case 1:
	    if (args[0] instanceof String) {
		page = (String) args[0];
	    } else {
		throw new CarbonException("Page text should be a String value");
	    }
	    break;
	default:
	    throw new CarbonException("Need a page text as an argument.");
	}
	return cx.newObject(html2xml, "XML",
		new Object[] { html2xml.transform(page) });
    }

    protected String transform(String html) throws IOException {
	String body = xmlizer.getXmlAsString(htmlCleaner.clean(html), encoding);
	return body.substring(body.indexOf(xmlEndTag) + 2).trim();
    }
}
