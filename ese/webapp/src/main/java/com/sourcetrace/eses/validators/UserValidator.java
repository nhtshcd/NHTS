package com.sourcetrace.eses.validators;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.validator.IValidator;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.dao.IUtilDAO;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.PasswordHistory;
import com.sourcetrace.eses.entity.User;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.util.ICryptoUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.util.ValidationUtil;

public class UserValidator implements IValidator {

    private static final Logger logger = Logger.getLogger(UserValidator.class);
   
    @Autowired
    private IUtilDAO utilDAO;
    @Autowired
	private ICryptoUtil cryptoUtil;

   
    public Map<String, String> validate(Object user) {

        HttpServletRequest httpRequest = ReflectUtil.getCurrentHttpRequest();
        String branchId_F = httpRequest.getParameter("branchId_F");
        String currentBrnch=(String) httpRequest.getSession().getAttribute(ISecurityFilter.CURRENT_BRANCH);

        User aUser = (User) user;
        if(aUser!=null && aUser.getId()!=null){
        User findUser = utilDAO.findUser(aUser.getId());
        if(findUser.getAgroChDealer()!=null){
        	aUser.setAgroChDealer(findUser.getAgroChDealer());
        }
        }
        Map<String, String> errorCodes = new LinkedHashMap<String, String>();
        if (logger.isInfoEnabled()) {
            logger.info("validate(Object) " + user.toString());
        }
        //String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{%%min%%,10000000}$";
        String PASSWORD_PATTERN = "^(?=.*).{%%min%%,10000000}$";
        ESESystem es =  utilDAO.findPrefernceByOrganisationId(currentBrnch);
			PASSWORD_PATTERN = PASSWORD_PATTERN.replace("%%min%%",
					es.getPreferences().get(ESESystem.PASSWORD_MIN_LENGTH).toString());

			Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

        if (aUser.isChangePassword()) {

            if (StringUtil.isEmpty(aUser.getPassword())// if password and confirm password is not
                                                       // given
                    || StringUtil.isEmpty(aUser.getConfirmPassword())) {
                errorCodes.put("user.password", "password.missing");
            } else if (!aUser.getPassword().equals(aUser.getConfirmPassword())) {// if password and
                                                                                 // confirm password
                                                                                 // is not matched
                errorCodes.put("user.password", "password.missmatch");
            }

           /* else if (aUser.getPassword().length() < Integer.valueOf(es.getPreferences().get(ESESystem.PASSWORD_MIN_LENGTH).toString())) {
            	String pww =utilDAO.findLocaleProperty("length.password", aUser.getLanguage()==null || StringUtil.isEmpty(aUser.getLanguage()) ? "en" : aUser.getLanguage());
            	pww=pww.replace("[min]", es.getPreferences().get(ESESystem.PASSWORD_MIN_LENGTH).toString());
                 errorCodes.put("user.password", pww);
            }*/

       /*     else if (aUser.getPassword().length() > Integer.valueOf(es.getPreferences().get(ESESystem.PASSWORD_MAX_LENGTH).toString())) {
            	String pww =utilDAO.findLocaleProperty("length.password", aUser.getLanguage());
            	pww=pww.replace("[min]", es.getPreferences().get(ESESystem.PASSWORD_MIN_LENGTH).toString());
            	pww=pww.replace("[max]", es.getPreferences().get(ESESystem.PASSWORD_MAX_LENGTH).toString());
                errorCodes.put("user.password", pww);
            }*/
            if (aUser.getId() != null) {
            	  List<String> pwList=utilDAO.listPasswordsByTypeAndRefId(String.valueOf(PasswordHistory.Type.WEB_USER.ordinal()),aUser.getId());	if (pwList != null) {
					pwList.stream().forEach(uu -> {
						String plainText = cryptoUtil.decrypt(uu);
						if (!StringUtil.isEmpty(plainText)) {
							plainText = plainText.trim();
							plainText = plainText.substring(aUser.getUsername().length(), plainText.length()).trim();
							if (plainText.equals(aUser.getPassword())) {
								errorCodes.put("repeated.password", "repeated.password");
							}
						}
					});

				}
			}
            Matcher matcher = pattern.matcher(aUser.getPassword());
			if (!matcher.matches()) {
				String mss = utilDAO.findLocaleProperty("agent.mishmatchbyregex","en");
				mss = mss.replace("[min]", String.valueOf(es.getPreferences().get(ESESystem.PASSWORD_MIN_LENGTH).toString()));
            	errorCodes.put(mss,mss);
             }else   if (aUser.getPassword()!=null && !StringUtil.isEmpty(aUser.getPassword())) {
            	
            	if(aUser.getPersInfo()!=null && aUser.getPersInfo().getFirstName() !=null && !StringUtil.isEmpty(aUser.getPersInfo().getFirstName()) &&  aUser.getPassword().toLowerCase().contains(aUser.getPersInfo().getFirstName().toLowerCase())){
            		errorCodes.put("agent.namecontains", "agent.namecontains");
            	}
            	
            	if(aUser.getPersInfo()!=null && aUser.getPersInfo().getLastName() !=null && !StringUtil.isEmpty(aUser.getPersInfo().getLastName()) &&  aUser.getPassword().toLowerCase().contains(aUser.getPersInfo().getLastName().toLowerCase())){
            		errorCodes.put("agent.namecontains", "agent.namecontains");
            	}
            	if(aUser.getUsername()!=null && aUser.getUsername() !=null && aUser.getPassword().toLowerCase().contains(aUser.getUsername().toLowerCase())){
            		errorCodes.put("agent.namecontains", "agent.namecontains");
            	}
            }
      

        }
        if (!StringUtil.isEmpty(branchId_F)) {
            if (branchId_F.equals("-1")) {
                errorCodes.put("empty.branchId", "empty.branchId");
            }
        }
        if (!StringUtil.isEmpty(aUser.getUsername())) {
            if (aUser.getIsMultiBranch().equalsIgnoreCase("1")) {
                User existingUser = utilDAO.findUserByUserNameExcludeBranch(aUser.getUsername());
                if (existingUser!=null && existingUser.getId() != aUser.getId()) {
                    errorCodes.put("unique.userName", "unique.userName");
                }
            }else{
                User existingUser = utilDAO.findUserByNameAndBranchId(aUser.getUsername(), aUser.getParentBranchId());
                if (existingUser!=null && !existingUser.getId().equals(aUser.getId())) {
                    errorCodes.put("unique.userName", "unique.userName");
                } 
            }
           
        }
            if(StringUtil.isEmpty(aUser.getUsername())){
            	 errorCodes.put("user.username.empty", "user.username.empty");
            }

        if (!StringUtil.isEmpty(aUser.getPersInfo().getFirstName())) {
            if (!ValidationUtil.isPatternMaches(aUser.getPersInfo().getFirstName(),
                    ValidationUtil.ALPHANUMERIC_PATTERN)) {
                errorCodes.put("pattern.firstName", "pattern.firstName");
            }
        } else {
            errorCodes.put("empty.firstName", "empty.firstName");
        }
       /* if (!StringUtil.isEmpty(aUser.getPersInfo().getFirstName())) {
            if (!ValidationUtil.isPatternMaches(aUser.getPersInfo().getFirstName(),
                    ValidationUtil.ALPHANUMERIC_PATTERN)) {
                errorCodes.put("pattern.lastName", "pattern.lastName");
            }
        }
        if (!StringUtil.isEmpty(aUser.getContInfo().getMobileNumbere())) {
            if (!ValidationUtil.isPatternMaches(aUser.getContInfo().getMobileNumbere(),
                    ValidationUtil.NUMBER_PATTERN)) {
                errorCodes.put("pattern.mobile", "pattern.mobile");
            }
        }*/
        
        if (StringUtil.isEmpty(aUser.getLanguage())) {
            errorCodes.put("empty.language", "empty.language");
        }
        
        if (!StringUtil.isEmpty(aUser.getContInfo().getEmail())) {
            if (!ValidationUtil.isPatternMaches(aUser.getContInfo().getEmail(),
                    ValidationUtil.EMAIL_PATTERN)) {
                errorCodes.put("pattern.email", "pattern.email");
            }else{
                User user1 = utilDAO.findUserByEmailId(aUser.getContInfo().getEmail());
                
                if( user1!=null && !user1.getId().equals(aUser.getId()) && user1.getBranchId().equals(aUser.getParentBranchId()) ){
                    errorCodes.put("unique.email", "unique.email");
                }
            }
        } else {
            errorCodes.put("empty.email", "empty.email");
        }

        if (aUser.getRole()==null || StringUtil.isEmpty(aUser.getRole().getId()) || aUser.getRole().getId() == 0) {
            errorCodes.put("empty.role.id", "empty.role");
        }else if(aUser.getRole()!=null && Arrays.asList(1,2).contains(aUser.getRole().getType()) && aUser.getAgroChDealer()==null){
        	 errorCodes.put("empty.exporter", "empty.exporter");
        }


        return errorCodes;
    }

}
