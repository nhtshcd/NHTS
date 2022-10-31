/*
 * JSONArrayTag.java
 * Copyright (c) 2008, Source Trace Systems
 * ALL RIGHTS RESERVED
 */
package com.sourcetrace.eses.tags;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


// TODO: Auto-generated Javadoc
/**
 * The Class JSONArrayTag.
 * @author $Author: aravind $
 * @version $Rev: 19 $, $Date: 2008-10-16 17:29:43 +0530 (Thu, 16 Oct 2008) $
 */
public class JSONArrayTag extends TagSupport {

    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        List<JSONObject> records = (List<JSONObject>) pageContext.getRequest().getAttribute("list");

        JSONArray array = new JSONArray();
        for(JSONObject record : records) {
            array.add(record);
        }

        JSONObject list = new JSONObject();
        list.put("list", array);

        JspWriter out = pageContext.getOut();
        try {
            out.println("<script type=\"text/javascript\">");
            out.println("var Data = " + list.toString() + ";");
            out.println("</script>");
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        return (EVAL_PAGE);
    }
}
