package com.sourcetrace.eses.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.entity.ESEAccount;
import com.sourcetrace.eses.entity.Vendor;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

public class VendorAction extends SwitchValidatorAction 
{

    private static final long serialVersionUID = 584542518364324369L;

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(VendorAction.class);
    
    protected static final String CREATE = "create";
    protected static final String DETAIL = "detail";
    protected static final String UPDATE = "update";
    protected static final String MAPPING = "mapping";
    protected static final String DELETE = "delete";
    protected static final String LIST = "list";
    protected static final String TITLE_PREFIX = "title.";
    protected static final String HEADING = "heading";
    
    private IUniqueIDGenerator idGenerator;
    
    @Autowired
	private IUtilService utilService;


    
    private Vendor vendor;
    private Vendor filter;
    private String id;
    

    
    /**
     * Data.
     * @return the string
     * @throws Exception the exception
     * @see com.sourcetrace.esesw.view.SwitchAction#data()
     */
    @SuppressWarnings("unchecked")
    public String data() throws Exception {

        Map<String, String> searchRecord = getDataTableJQGridRequestParam(); // get the search parameter with

        Vendor filter = new Vendor();
        
        if (!StringUtil.isEmpty(searchRecord.get("branchId"))) {
            if (!getIsMultiBranch().equalsIgnoreCase("1")) {
                List<String> branchList = new ArrayList<>();
                branchList.add(searchRecord.get("branchId").trim());
                filter.setBranchesList(branchList);
            } else {
                List<String> branchList = new ArrayList<>();
                List<BranchMaster> branches = utilService
                        .listChildBranchIds(searchRecord.get("branchId").trim());
                branchList.add(searchRecord.get("branchId").trim());
                branches.stream().filter(branch -> !StringUtil.isEmpty(branch)).forEach(branch -> {
                    branchList.add(branch.getBranchId());
                });
                filter.setBranchesList(branchList);
            }
        }
        
        if (!StringUtil.isEmpty(searchRecord.get("subBranchId"))) {
            filter.setBranchId(searchRecord.get("subBranchId").trim());
        }
        if (!StringUtil.isEmpty(searchRecord.get("vendorId"))) 
        {
            filter.setVendorId(searchRecord.get("vendorId").trim());
        }

        if (!StringUtil.isEmpty(searchRecord.get("vendorName")))
        {
            filter.setVendorName(searchRecord.get("vendorName").trim());
        }
        if (!StringUtil.isEmpty(searchRecord.get("personName"))) {
            filter.setPersonName(searchRecord.get("personName").trim());
        }
        if (!StringUtil.isEmpty(searchRecord.get("emailId")))
        {
            filter.setEmailId(searchRecord.get("emailId").trim());
        }
        if (!StringUtil.isEmpty(searchRecord.get("vendorAddress")))
        {
            filter.setVendorAddress(searchRecord.get("vendorAddress").trim());
        }
        
        if (!StringUtil.isEmpty(searchRecord.get("mobileNo")))
        {
            filter.setMobileNo(searchRecord.get("mobileNo").trim());
        }
        
        Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(),
                getResults(), filter, getPage());

        return sendDataTableJSONResponse(data);
    }
    
    
    /**
     * Detail.
     * @return the string
     * @throws Exception the exception
     */
    public String detail() throws Exception 
    {
        String view = "";
        if (id != null && !id.equals(""))
        {
            vendor = utilService.findVendor(Long.valueOf(id));
            if (vendor == null) 
            {
                addActionError(NO_RECORD);
                return REDIRECT;
            }
            setCurrentPage(getCurrentPage());
            command = UPDATE;
            view = DETAIL;
            request.setAttribute(HEADING, getText("vendordetail"));
        }
        else 
        {
            request.setAttribute(HEADING, getText("vendorlist"));
            return LIST;
        }
        return view;
    }
    
    
    /**
     * Creates the.
     * @return the string
     * @throws Exception the exception
     */
    public String create() throws Exception
    {

        if (vendor == null)
        {
            command = "create";
            request.setAttribute(HEADING, getText("vendorcreate"));        
            return INPUT;
        }
        else 
        {            
            String vendorIdSeq = idGenerator.createVendorId();
            vendor.setVendorId(vendorIdSeq);
            vendor.setBranchId(getBranchId());
            utilService.addVendor(vendor);
            String accountNo=idGenerator.createVendorAccountNoSequence(vendorIdSeq);
            utilService.createAccount(vendorIdSeq, accountNo, new Date(), ESEAccount.VENDOR_ACCOUNT,vendor.getBranchId());   
            return REDIRECT;
        }
    }
    
    /**
     * Update.
     * @return the string
     * @throws Exception the exception
     */
    public String update() throws Exception
    {

        if (id != null && !id.equals(""))
        {
            vendor = utilService.findVendor(Long.valueOf(id));
            if (vendor == null)
            {
                addActionError(NO_RECORD);
                return REDIRECT;
            }
            setCurrentPage(getCurrentPage());
            id = null;
            command = UPDATE;
            request.setAttribute(HEADING, getText("vendorupdate"));
        }
        else
        {
            if (vendor != null)
            {
                Vendor tempVendor = utilService.findVendor(Long.valueOf(vendor.getId()));
                if (tempVendor == null) 
                {
                    addActionError(NO_RECORD);
                    return REDIRECT;
                }
                setCurrentPage(getCurrentPage());
                tempVendor.setVendorName(vendor.getVendorName());
                tempVendor.setVendorAddress(vendor.getVendorAddress());        
                tempVendor.setEmailId(vendor.getEmailId());
                tempVendor.setPersonName(vendor.getPersonName());
              
                tempVendor.setMobileNo(vendor.getMobileNo());
                utilService.editVendor(tempVendor);
            }
            request.setAttribute(HEADING, getText("vendorlist"));
            return LIST;
        }
        
        return super.execute();
    }
    private String deleteErrorMsg;
    
    /**
     * Delete.
     * @return the string
     * @throws Exception the exception
     */
    public String delete() throws Exception 
    {
        String result=null;
        if (this.getId() != null && !(this.getId().equals(EMPTY)))
        {
            vendor = utilService.findVendor(Long.valueOf(getId()));
             if (vendor == null)
            {
                addActionError(NO_RECORD);
                return null;
            }
            if (!ObjectUtil.isEmpty(vendor))
            {
                ESEAccount account=utilService.findAccountByProfileIdAndProfileType(vendor.getVendorId(),ESEAccount.VENDOR_ACCOUNT);
                /*//WarehousePayment warehousePayment=utilService.findVendorId(vendor.getId());
                if(!ObjectUtil.isEmpty(warehousePayment))
                {
                    addActionError(getText("cannotDeleteVendorHasTxn"));
                    request.setAttribute(HEADING, getText("vendordetail"));
                    result=DETAIL;
                    
                }
                else
                {*/
                    utilService.removeVendor(vendor);
                    if(!ObjectUtil.isEmpty(account))
                    {
                        utilService.removeAccountByProfileIdAndProfileType(account.getProfileId(), account.getType());
                    }
                    result=LIST;
                //}
                
            }
            
        }
        
        request.setAttribute(HEADING, getText("vendorlist"));
        return result;

    }
    
    
    /**
     * To json.
     * @param record the record
     * @return the JSON object
     * @see com.sourcetrace.esesw.view.SwitchAction#toJSON(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public JSONObject toJSON(Object record) 
    {

        JSONObject jsonObject = null;
        jsonObject = new JSONObject();
        if (record instanceof Vendor)
        {
            Vendor vendor = (Vendor) record;
           // JSONArray rows = new JSONArray();
            JSONObject objDt = new JSONObject();
    	  	/*if ((getIsMultiBranch().equalsIgnoreCase("1") && (getIsParentBranch().equals("1")||StringUtil.isEmpty(branchIdValue)))) {

    	          if (StringUtil.isEmpty(branchIdValue)) {
    	              rows.add(!StringUtil.isEmpty(
    	                      getBranchesMap().get(getParentBranchMap().get(vendor.getBranchId())))
    	                              ? getBranchesMap()
    	                                      .get(getParentBranchMap().get(vendor.getBranchId()))
    	                              : getBranchesMap().get(vendor.getBranchId()));
    	          }
    	          rows.add(getBranchesMap().get(vendor.getBranchId()));

    	      } else {
    	          if (StringUtil.isEmpty(branchIdValue)) {
    	              rows.add(branchesMap.get(vendor.getBranchId()));
    	          }
    	      }*/
            String linkField = "<a href=vendor_detail.action?id="+vendor.getId()+" title='Go To Detail' target=_blank>"+vendor.getVendorName()+"</a>";
            objDt.put("vendorId","<font color=\"#0000FF\" style=\"cursor:pointer;\">"
                    + vendor.getVendorId() + "</font>");

            objDt.put("vendorName",linkField);     
            objDt.put("personName",vendor.getPersonName());
            objDt.put("emailId",vendor.getEmailId());
            objDt.put("vendorAddress",vendor.getVendorAddress());
            objDt.put("mobileNo",vendor.getMobileNo());
            jsonObject.put("DT_RowId", vendor.getId());
    		jsonObject.put("cell", objDt);
    		return jsonObject;
        }
        return jsonObject;
    }
    
    @Override
    public Object getData() 
    {
        // TODO Auto-generated method stub
        return vendor;
    }

   
    public void setIdGenerator(IUniqueIDGenerator idGenerator) {

        this.idGenerator = idGenerator;
    }

    public IUniqueIDGenerator getIdGenerator() {

        return idGenerator;
    }

    public void setVendor(Vendor vendor) {

        this.vendor = vendor;
    }

    public Vendor getVendor() {

        return vendor;
    }

    public void setFilter(Vendor filter) {

        this.filter = filter;
    }

    public Vendor getFilter() {

        return filter;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getId() {

        return id;
    }
       public void setDeleteErrorMsg(String deleteErrorMsg) {

        this.deleteErrorMsg = deleteErrorMsg;
    }


    public String getDeleteErrorMsg() {

        return deleteErrorMsg;
    }

   
}
