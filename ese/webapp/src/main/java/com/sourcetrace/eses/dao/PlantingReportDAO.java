package com.sourcetrace.eses.dao;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.FarmCrops;

import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;

public class PlantingReportDAO extends ReportDAO{
	protected void addExampleFiltering(Criteria criteria, Map params) {
		Planting entity = (Planting) params.get(FILTER);
		criteria.createAlias("farmCrops", "fc", Criteria.LEFT_JOIN).createAlias("fc.farm", "f", Criteria.LEFT_JOIN).createAlias("f.farmer","fa",Criteria.LEFT_JOIN);
		 criteria.add(Restrictions.eq("status", 1));
		 HttpSession httpSession = ReflectUtil.getCurrentHttpSession();
		 Long role = (Long) httpSession.getAttribute("dealerId");
		 if (role != null && role > 0) {
			 criteria.add(Restrictions.eq("fc.exporter.id", role));
		 }
		 if (!ObjectUtil.isEmpty(entity.getFarmerId()) && !StringUtil.isEmpty(entity.getFarmerId()) && entity.getFarmerId()>0){
             criteria.add(Restrictions.eq("fa.id", entity.getFarmerId()));
	            }
	    }
	 
}