package com.sourcetrace.eses.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public interface IExporter {
	
	public static final String EXPORT_MANUAL = "0";
    public static final String EXPORT_DAILY = "1";
    public static final String EXPORT_CONSOLIDATED = "2";
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Gets the export data stream.
     * @param manual the manual
     * @return the export data stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public InputStream getExportDataStream(String exportType) throws IOException;

}
