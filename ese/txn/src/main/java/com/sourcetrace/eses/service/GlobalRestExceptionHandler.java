package com.sourcetrace.eses.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sourcetrace.eses.entity.Transaction;
import com.sourcetrace.eses.entity.TransactionLog;
import com.sourcetrace.eses.txn.exception.TxnFault;
import com.sourcetrace.eses.txn.schema.Response;
import com.sourcetrace.eses.txn.schema.Status;

@ControllerAdvice
public class GlobalRestExceptionHandler {
	@Autowired
	private IUtilService utilService;

	@ExceptionHandler(TxnFault.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Response handleSecurityException(TxnFault ex) {
		Response rs = new Response();
		rs.setStatus(Status.builder().code(ex.getCode()).message(ex.getMessage()).build());
		rs.setBody(new HashMap());
		TransactionLog transactionLog = utilService.findTransactionLogById(ex.getTxnLogId());
		if (transactionLog != null) {
			transactionLog.setStatus(Transaction.Status.FAILED.ordinal());
			transactionLog.setStatusCode(ex.getCode());
			transactionLog.setStatusMsg(ex.getMessage());
			utilService.update(transactionLog);
		}
		return rs;
	}

}