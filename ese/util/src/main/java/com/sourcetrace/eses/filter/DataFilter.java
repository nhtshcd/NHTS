package com.sourcetrace.eses.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.request.async.WebAsyncManager;
import org.springframework.web.context.request.async.WebAsyncUtils;

/**
 * DataFilter extends OpenSessionInViewFilter to enable data filtering in the
 * opened session for processing a request.
 *
 * @author $Author: aravind $
 * @version $Rev: 544 $ $Date: 2009-09-19 16:23:26 +0530 (Sat, 19 Sep 2009) $
 */
public class DataFilter extends OpenSessionInViewFilter implements Filter {

	private static final Logger logger = Logger.getLogger(DataFilter.class);
	private static final String BRANCH_FILTER = "filterByBranch";
	private static final String BRANCH_FILTER_PARAM = "branch";

	/**
	 * Do filter internal.
	 *
	 * @param req
	 *            the req
	 * @param res
	 *            the res
	 * @param chain
	 *            the chain
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @see org.springframework.orm.hibernate3.support.OpenSessionInViewFilter#doFilterInternal(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		SessionFactory sessionFactory = lookupSessionFactory(request);
		boolean participate = false;

		WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
		String key = getAlreadyFilteredAttributeName();

		if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
			// Do not modify the Session: just set the participate flag.
			participate = true;
		} else {
			boolean isFirstRequest = !isAsyncDispatch(request);
			if (isFirstRequest || !applySessionBindingInterceptor(asyncManager, key)) {
				logger.debug("Opening Hibernate Session in OpenSessionInViewFilter");
				Session session = getSession(request, sessionFactory);
				SessionHolder sessionHolder = new SessionHolder(session);
				TransactionSynchronizationManager.bindResource(sessionFactory, sessionHolder);

				// AsyncRequestInterceptor interceptor = new
				// AsyncRequestInterceptor(sessionFactory, sessionHolder);
				// asyncManager.registerCallableInterceptor(key, interceptor);
				// asyncManager.registerDeferredResultInterceptor(key,
				// interceptor);
			}
		}

		try {
			filterChain.doFilter(request, response);
		} finally {
			if (!participate) {
				SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
						.unbindResource(sessionFactory);
				if (!isAsyncStarted(request)) {
					logger.debug("Closing Hibernate Session in OpenSessionInViewFilter");
					SessionFactoryUtils.closeSession(sessionHolder.getSession());
				}
			}
		}

	}

	/**
	 * Gets the session.
	 *
	 * @param sessionFactory
	 *            the session factory
	 * @param req
	 *            the req
	 * @return the session
	 * @throws DataAccessResourceFailureException
	 *             the data access resource failure exception
	 * @see org.springframework.orm.hibernate3.support.OpenSessionInViewFilter#getSession(org.hibernate.SessionFactory)
	 */
	protected Session getSession(HttpServletRequest req, SessionFactory sessionFactory)
			throws DataAccessResourceFailureException {

		Session session = sessionFactory.openSession();
		session.setFlushMode(FlushMode.COMMIT);
		return session;
	}

	private boolean applySessionBindingInterceptor(WebAsyncManager asyncManager, String key) {
		if (asyncManager.getCallableInterceptor(key) == null) {
			return false;
		}

		// ((AsyncRequestInterceptor)
		// asyncManager.getCallableInterceptor(key)).bindSession();
		return true;
	}

}
