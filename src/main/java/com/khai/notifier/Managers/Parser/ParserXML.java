package com.khai.notifier.Managers.Parser;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

/**
 * Created by inokentii on 23.12.2016.
 */
public class ParserXML {

    public static Document loadXMLFromString(String xml) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    public static String getXMLParamValueWithParamName(Document xmlDoc, String paramName) {

        return xmlDoc.getElementsByTagName(paramName).item(0).getTextContent();
    }
}
