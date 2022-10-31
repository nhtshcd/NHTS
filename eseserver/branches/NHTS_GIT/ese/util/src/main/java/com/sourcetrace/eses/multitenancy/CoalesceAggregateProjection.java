package com.sourcetrace.eses.multitenancy;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.AggregateProjection;
import org.hibernate.criterion.CriteriaQuery;

public class CoalesceAggregateProjection extends AggregateProjection {

private static final long serialVersionUID = 1L;

private String aggregate;
private Object defaultValue;

protected CoalesceAggregateProjection (String aggregate, String propertyName, Object defaultValue) {
    super(aggregate, propertyName);

    this.aggregate = aggregate;
    this.defaultValue = defaultValue;
}

@Override
public String toSqlString(Criteria criteria, int loc, CriteriaQuery criteriaQuery) throws HibernateException {
    return new StringBuffer()
    .append("coalesce(")
    .append(aggregate)
    .append("(")
    .append( criteriaQuery.getColumn(criteria, propertyName) )
    .append("),")
    .append(defaultValue.toString())
    .append(") as y")
    .append(loc)
    .append('_')
    .toString();
}

public static AggregateProjection sum (String propertyName, Object defaultValue) {
    return new CoalesceAggregateProjection("sum", propertyName, defaultValue);
}
}