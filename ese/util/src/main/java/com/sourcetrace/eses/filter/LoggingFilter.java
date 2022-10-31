package com.sourcetrace.eses.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.appengine.repackaged.com.google.gson.Gson;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.Transaction;
import com.sourcetrace.eses.entity.TransactionLog;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.txn.schema.JsonRequest;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.StringUtil;

public class LoggingFilter implements Filter {

	protected static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
	private static final String REQUEST_PREFIX = "Request: ";
	private static final String RESPONSE_PREFIX = "Response: ";
	private AtomicLong id = new AtomicLong(1);
	@Autowired
	private IUtilService utilDAO;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (utilDAO == null) {
			ServletContext servletContext = httpRequest.getSession().getServletContext();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils
					.getWebApplicationContext(servletContext);
			utilDAO = webApplicationContext.getBean(IUtilService.class);

		}

		long requestId = id.incrementAndGet();
		RequestWrapper wrappedRequest = new RequestWrapper(requestId, (HttpServletRequest) request);
		ResponseWrapper wres = new ResponseWrapper((HttpServletResponse) response);
		Gson gs = new Gson();
		JsonRequest js = gs.fromJson(wrappedRequest.getBody(), JsonRequest.class);
	
		wrappedRequest.setAttribute("reqObj", js);
		wrappedRequest.setAttribute("payload", wrappedRequest.getBody());
		TransactionLog tx = logRequest(js.getHead(),wrappedRequest.getBody());
		js.setTxnLogId(tx.getId());
		wrappedRequest.setAttribute("txnLogId", tx.getId());
		try {
			chain.doFilter(wrappedRequest, wres);
			wres.flushBuffer();
		} finally {

			logRequest(wrappedRequest);
		 logResponse(wres,requestId);

		}

	}

	private void logRequest(final HttpServletRequest request) {
		StringBuilder msg = new StringBuilder();
		msg.append("\n");
		msg.append(REQUEST_PREFIX);
		msg.append("\n");
		if (request instanceof RequestWrapper) {
			msg.append("ID:").append(((RequestWrapper) request).getId()).append("\n");
		}
		HttpSession session = request.getSession(false);
		if (session != null) {
			msg.append("Session id=").append(session.getId()).append("\n");
		}
		if (request.getMethod() != null) {
			msg.append("Method=").append(request.getMethod()).append("\n");
		}
		if (request.getContentType() != null) {
			msg.append("Content type=").append(request.getContentType()).append("\n");
		}
		msg.append("Url=").append(request.getRequestURI());
		if (request.getQueryString() != null) {
			msg.append('?').append(request.getQueryString());
		}

		if (request instanceof RequestWrapper && !isMultipart(request) && !isBinaryContent(request)) {
			RequestWrapper requestWrapper = (RequestWrapper) request;

			String charEncoding = requestWrapper.getCharacterEncoding() != null ? requestWrapper.getCharacterEncoding()
					: "UTF-8";
			msg.append("\n payload=").append(requestWrapper.getBody());
			msg.append("\n");
		}
		logger.info(msg.toString());
	}

	private boolean isBinaryContent(final HttpServletRequest request) {
		if (request.getContentType() == null) {
			return false;
		}
		return request.getContentType().startsWith("image") || request.getContentType().startsWith("video")
				|| request.getContentType().startsWith("audio");
	}

	private boolean isMultipart(final HttpServletRequest request) {
		return request.getContentType() != null && request.getContentType().startsWith("multipart/form-data");
	}

	private void logResponse(ResponseWrapper wres, long requestId) throws UnsupportedEncodingException {
		byte[] copy = wres.getCopy();
		StringBuilder msg = new StringBuilder();
		msg.append("\n");
		msg.append(RESPONSE_PREFIX);
		msg.append("\n");
		msg.append("ID:").append(requestId);
		msg.append("\n");
		msg.append("Payload=").append(new String(copy, wres.getCharacterEncoding()));

		logger.info(msg.toString());
	}

	
	private OutputStream loggingOutputStream() {
		return System.out;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	private TransactionLog logRequest(Head head, String payload) {
		Date date = DateUtil.getTransactionDate(head.getTxnTime());
		TransactionLog transactionLog = new TransactionLog();
		transactionLog.setRequestLog(payload);
		transactionLog.setTxnType(head.getTxnType());
		transactionLog.setSerialNo(head.getSerialNo());
		transactionLog.setMsgNo(head.getMsgNo());
		transactionLog.setResentCount(head.getResentCount());
		transactionLog.setAgentId(head.getAgentId());
		transactionLog.setOperationType(head.getOperType());
		transactionLog.setMode(head.getMode());
		transactionLog.setVersion(head.getVersionNo());
		if(head.getBranchId()==null || StringUtil.isEmpty(head.getBranchId())){
			head.setBranchId(utilDAO.findPrefernceByName(ESESystem.DEF_BRANCH));
		}
		transactionLog.setBranchId(head.getBranchId());
		try {

			transactionLog.setTxnTime(DateUtil.convertStringToDate(head.getTxnTime(), DateUtil.TXN_TIME_FORMAT));
		} catch (Exception e) {
			e.printStackTrace();
		}
		transactionLog.setStatusCode(Transaction.PENDING);
		transactionLog.setStatusMsg(Transaction.PENDING_MSG);
		transactionLog.setStatus(Transaction.Status.PENDING.ordinal());
		transactionLog.setCreateDt(new Date());

		utilDAO.save(transactionLog);
		return transactionLog;

	}
}
