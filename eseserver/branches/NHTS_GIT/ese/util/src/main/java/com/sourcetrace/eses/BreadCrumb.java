
package com.sourcetrace.eses;


import java.util.ArrayList;
import java.util.List;

import com.sourcetrace.eses.entity.Menu;

public class BreadCrumb {

    public static final String BREADCRUMB = "breadCrumb";
    public static final String BREADCRUMBRS = "breadCrumbRS";
    public static final String DEFAULT_FOLDER_ICON_CLASS = "fa-folder-open";

    public static List<Menu> getBreadCrumb(String breadCrumb) {
        List<Menu> menus = null;
        try {
            if (breadCrumb != null && !("").equalsIgnoreCase(breadCrumb.trim())) {
                String[] breadCrumbArray = breadCrumb.split(",");
                if (breadCrumbArray.length > 0) {
                    for (String breadCrumbSingle : breadCrumbArray) {
                        if (breadCrumbSingle != null && !("").equalsIgnoreCase(breadCrumbSingle.trim())) {
                            String[] bdCrumb = breadCrumbSingle.trim().split("~");
                            Menu menu = null;
                            if (bdCrumb.length == 2) {
                                menu = new Menu();
                                menu.setLabel((("").equalsIgnoreCase(bdCrumb[0].trim())) ? "Label" : bdCrumb[0].trim());
                                menu.setUrl((("").equalsIgnoreCase(bdCrumb[1].trim())) ? "#" : bdCrumb[1].trim());
                            } else if (bdCrumb.length == 1) {
                                menu = new Menu();
                                menu.setLabel((("").equalsIgnoreCase(bdCrumb[0].trim())) ? "Label" : bdCrumb[0].trim());
                                menu.setUrl("#");
                            }
                            if (menu != null) {
                                if (menus == null) {
                                    menus = new ArrayList<Menu>();
                                }
                                menus.add(menu);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            menus = null;
        }
        return menus;
    }

}