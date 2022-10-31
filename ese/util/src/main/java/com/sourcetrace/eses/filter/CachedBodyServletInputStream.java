package com.sourcetrace.eses.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;

public class CachedBodyServletInputStream extends ServletInputStream {
	 private InputStream cachedBodyInputStream;

	    public CachedBodyServletInputStream(byte[] cachedBody) {
	        this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
	    }

	   
	    @Override
	    public int read() throws IOException {
	        return cachedBodyInputStream.read();
	    }
}