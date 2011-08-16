/*
 * @(#)HtmlCleanerHostObjectTest.java $version 2011. 8. 12.
 *
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
	Html2XmlHostObject cleaner = new Html2XmlHostObject();
	assertThat(
		cleaner.transform("<html><body><b>1</b><br>2<table><tr><td>3</td></tr></table></body></html>"),
		is("<html><head /><body><b>1</b><br />2<table><tbody><tr><td>3</td></tr></tbody></table></body></html>"));
    }
}
