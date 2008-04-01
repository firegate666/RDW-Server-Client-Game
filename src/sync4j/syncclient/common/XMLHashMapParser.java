/**
 * Copyright (C) 2003-2005 Funambol
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package sync4j.syncclient.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * It supplies the methods for the marshall and unmarshall of a HashMap in xml.
 * <p>The xml begins with &#60;RECORD&#62; and ends with &#60;/RECORD&#62;
 * <p>Every object of the table comes represented with formed following
 * <p>&#60;FIELD&#62;<br>
 * &nbsp;&nbsp;&#60;NAME&#62;name of the field&#60;/NAME&#62;<br>
 * &nbsp;&nbsp;&#60;VALUE&#62;value of the field&#60;/VALUE&#62;<br>
 * &#60;/FIELD&#62;<br>
 *
 * <p>Example of HashMap represented in xml:
 * <p>&#60;RECORD&#62;<br>
 * &nbsp;&nbsp;&#60;FIELD&#62;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&#60;NAME&#62;name&#60;/NAME&#62;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&#60;VALUE&#62;john&#60;/VALUE&#62;<br>
 * &nbsp;&nbsp;&#60;/FIELD&#62;<br>
 * &nbsp;&nbsp;&#60;FIELD&#62;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&#60;NAME&#62;birthDate&#60;/NAME&#62;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&#60;VALUE&#62;100128313321&#60;/VALUE&#62;<br>
 * &nbsp;&nbsp;&#60;/FIELD&#62;<br>
 * &#60;/RECORD&#62;
 *
 * <p>The null values are represent by <code>NULL_VALUE</code>
 *
 * @version  $Id: XMLHashMapParser.java,v 1.3 2008-04-01 22:41:04 firegate666 Exp $
 * @author Stefano Nichele
 */

public class XMLHashMapParser {

    // ----------------------------------------------------------------Constants
    /** Representation of a null object */
    private static final String NULL_VALUE = "NULL_VALUE";


    // -----------------------------------------------------------Public methods
    /**
     * Converts a HashMap in xml
     * @param values the HasMap to convert
     * @return the representation of the given HasMap in xml
     */
    public static String toXML(Map values) {
        StringBuffer sbXml = new StringBuffer("<RECORD>");

        Iterator keys = values.keySet().iterator();
        String name = null;
        String value = null;
        Object objectValue = null;
        while (keys.hasNext()) {
            name = (String)keys.next();
            objectValue = values.get(name);
            if (objectValue == null) {
                value = NULL_VALUE;
            } else {
                value = objectValue.toString();
            }

            sbXml.append("\n<FIELD>\n<NAME>");
            sbXml.append(name);
            sbXml.append("</NAME>\n<VALUE>");
            sbXml.append(value);
            sbXml.append("</VALUE>\n</FIELD>");

        }

        sbXml.append("\n</RECORD>");

        return sbXml.toString();
    }

    /**
     * Converts a xml in HashMap
     * @param xml the xml to convert
     * @return the HashMap correspondent to the given xml
     * @throws IllegalStateException if an error occurs during the conversion
     */
    public static Map toMap(String xml) throws IllegalStateException {
        Map <String, String> values = new HashMap<String, String>();

        String record = getTagContent(xml, "RECORD");

        if (record == null) {
            throw new IllegalStateException("Bad xml rappresentation");
        }

        Vector fields = getListTagContent(record, "FIELD");
        String field = null;
        String name = null;
        String value = null;

        int numFields = fields.size();
        for (int i=0; i<numFields; i++) {
            field = (String)fields.elementAt(i);
            name = getTagContent(field, "NAME");
            value = getTagContent(field, "VALUE");

            values.put(name, value);
        }

        return values;
    }

    // ----------------------------------------------------------Private methods
    /**
     * Given a string (xml), return a Vector with the contents
     * of all the occurrences of a given tag
     * @param xml string in which searching the tag
     * @param tag searched tag
     * @return a vector that contains all the occurrences of a given tag
     */
    private static Vector getListTagContent(String xml, String tag) {
        Vector <String> tagsContent = new Vector<String>();

        String startTag = "<" + tag + ">";
        String endTag = "</" + tag + ">";

        int indexStartTag = 0;
        int indexEndTag = 0;

        String tagContent = null;

        while ( (indexStartTag = xml.indexOf(startTag, indexEndTag)) != -1 ) {
            indexEndTag = xml.indexOf(endTag, indexStartTag);

            if (indexEndTag == -1) {
                // found the start of the tag, but not the end
                break;
            }

            if (indexStartTag == -1 || indexEndTag == -1) {
                return tagsContent;
            }

            tagContent = xml.substring(indexStartTag, indexEndTag);
            tagsContent.addElement(tagContent);

        }
        return tagsContent;
    }

    /**
     * Given a string (xml), return the first occurrence of the tag
     * @param xml string in which searching the tag
     * @param tag searched tag
     * @return the first occurrence of the given tag
     */
    private static String getTagContent(String xml, String tag) {
        String startTag = "<" + tag + ">";
        String endTag = "</" + tag + ">";
        int indexStartTag = xml.indexOf(startTag) + startTag.length();
        int indexEndTag = xml.indexOf(endTag);

        if (indexStartTag == -1 || indexEndTag == -1) {
            return null;
        }

        return xml.substring(indexStartTag, indexEndTag);

    }


}