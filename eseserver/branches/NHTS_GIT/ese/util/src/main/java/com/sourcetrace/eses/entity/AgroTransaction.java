package com.sourcetrace.eses.entity;
// Generated 24 Jun, 2020 6:21:14 PM by Hibernate Tools 5.0.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import com.sourcetrace.eses.util.ObjectUtil;

/**
 * TrxnAgro generated by hbm2java
 */
@Entity
 @FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))@Filters(@org.hibernate.annotations.Filter(name="branchFilter", condition="branch_id in ( :branchIdParam )"))
@Table(name = "trxn_agro")
public class AgroTransaction implements java.io.Serializable {

	 private static final long serialVersionUID = 1L;

	    public static final String NOT_APPLICABLE = "N/A";
	    public static final String CASH_PAYMENT = "CASH PAYMENT";
	    public static final String CASH_WITHDRAW = "CASH WITHDRAW";
	    public static final String CASH_RECEIVED = "CASH RECEIVED";
	    public static final String CASH_SETTLEMENT = "CASH SETTLEMENT";

	    public static final String EMPTY_PAGE_NO = "-";

	private Long id;
	private AgroTransaction trxnAgro;
	//private Warehouse warehouse;
	private String receiptNo;
	private String agentId;
	private String agentName;
	private String deviceId;
	private String deviceName;
	private String servicePointId;
	private String servicePointName;
	private String farmerId;
	private String farmerName;
	private String vendorId;
	private String vendorName;
	private String customrId;
	private String customerName;
	private String profType;
	private String txnType;
	private String modeOfPayment;
	private Double intBal;
	private Double txnAmt;
	private Double balAmt;
	private Integer operType;
	private Date txnTime;
	private String txnDesc;
	private ESEAccount eseAccount;
	private Date serverUpdateTime;
	private byte[] audioFile;
	private String masterType;
	private String masterTypeId;
	private Procurement procurement;
	private Packhouse samithi;
	private Double qty;
	private Double initialBalance;
	private Double debitAmount;
	private Double creditAmount;
	private String seasonCode;
	private String branchId;
	private String latitude;
	private String longitude;
	private Set<AgroTransaction> trxnAgros = new HashSet<AgroTransaction>(0);
	private String productStock;
	private double txnAmount;

	private double intBalance;

	private double balAmount;
	
	// transient variable
	@Transient
    private String transientId;
	@Transient
    private int balanceType;
	@Transient
    private AgroTransaction refAgroTransaction;
	@Transient
    private Packhouse packhouse;
	@Transient
    private long eseAccountId;
	@Transient
    private List<String> agentList;
	@Transient
    private List<String> txnTypeList;
	@Transient
    private Double balanceAmt;
	@Transient
    private String item;
	@Transient
    private String finalBalance;
	@Transient
    private List<ProcurementDetail>procurementDetailList;
	@Transient
    private Double paidAmount;
	@Transient
    private String txnTimeStr;
	@Transient
    private Map<String, String> filterData;
	@Transient
        private String tempBalAmt;
	@Transient
    private String temptxnAmount;
	@Transient
    private String tempIntBalance;
	@Transient
    private String startDate;
	@Transient
    private String endDate;
	@Transient
    private double vendorIntBalance;
	@Transient
    private double vendorTxnAmount;
	@Transient
    private double vendorBalAmount;  
	@Transient
    private long samithiId;
	@Transient
    private String proofNo;

    

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_TRXN_AGRO_ID")
	public AgroTransaction getTrxnAgro() {
		return this.trxnAgro;
	}

	public void setTrxnAgro(AgroTransaction trxnAgro) {
		this.trxnAgro = trxnAgro;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "SAMITHI_ID")
//	public Warehouse getWarehouse() {
//		return this.warehouse;
//	}
//
//	public void setWarehouse(Warehouse warehouse) {
//		this.warehouse = warehouse;
//	}

	@Column(name = "RECEIPT_NO", length = 45)
	public String getReceiptNo() {
		return this.receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	@Column(name = "AGENT_ID", length = 45)
	public String getAgentId() {
		return this.agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	@Column(name = "AGENT_NAME", length = 100)
	public String getAgentName() {
		return this.agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	@Column(name = "DEVICE_ID", length = 45)
	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "DEVICE_NAME", length = 45)
	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Column(name = "SERVICE_POINT_ID", length = 45)
	public String getServicePointId() {
		return this.servicePointId;
	}

	public void setServicePointId(String servicePointId) {
		this.servicePointId = servicePointId;
	}

	@Column(name = "SERVICE_POINT_NAME", length = 45)
	public String getServicePointName() {
		return this.servicePointName;
	}

	public void setServicePointName(String servicePointName) {
		this.servicePointName = servicePointName;
	}

	@Column(name = "FARMER_ID", length = 45)
	public String getFarmerId() {
		return this.farmerId;
	}

	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}

	@Column(name = "FARMER_NAME", length = 100)
	public String getFarmerName() {
		return this.farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	@Column(name = "VENDOR_ID", length = 20)
	public String getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	@Column(name = "VENDOR_NAME", length = 90)
	public String getVendorName() {
		return this.vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name = "CUSTOMR_ID", length = 20)
	public String getCustomrId() {
		return this.customrId;
	}

	public void setCustomrId(String customrId) {
		this.customrId = customrId;
	}

	@Column(name = "CUSTOMER_NAME", length = 90)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "PROF_TYPE", length = 45)
	public String getProfType() {
		return this.profType;
	}

	public void setProfType(String profType) {
		this.profType = profType;
	}

	@Column(name = "TXN_TYPE", length = 45)
	public String getTxnType() {
		return this.txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	@Column(name = "MODE_OF_PAYMENT", length = 10)
	public String getModeOfPayment() {
		return this.modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	@Column(name = "INT_BAL", precision = 20)
	public Double getIntBal() {
		return this.intBal;
	}

	public void setIntBal(Double intBal) {
		this.intBal = intBal;
	}

	@Column(name = "TXN_AMT", precision = 20)
	public Double getTxnAmt() {
		return this.txnAmt;
	}

	public void setTxnAmt(Double txnAmt) {
		this.txnAmt = txnAmt;
	}

	@Column(name = "BAL_AMT", precision = 20)
	public Double getBalAmt() {
		return this.balAmt;
	}

	public void setBalAmt(Double balAmt) {
		this.balAmt = balAmt;
	}

	@Column(name = "OPER_TYPE")
	public Integer getOperType() {
		return this.operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TXN_TIME", length = 19)
	public Date getTxnTime() {
		return this.txnTime;
	}

	public void setTxnTime(Date txnTime) {
		this.txnTime = txnTime;
	}

	@Column(name = "TXN_DESC", length = 550)
	public String getTxnDesc() {
		return this.txnDesc;
	}

	public void setTxnDesc(String txnDesc) {
		this.txnDesc = txnDesc;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ESE_ACCOUNT_ID")
	public ESEAccount getEseAccount() {
		return this.eseAccount;
	}

	public void setEseAccount(ESEAccount eseAccountId) {
		this.eseAccount = eseAccountId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SERVER_UPDATE_TIME",  length = 19)
	public Date getServerUpdateTime() {
		return this.serverUpdateTime;
	}

	public void setServerUpdateTime(Date serverUpdateTime) {
		this.serverUpdateTime = serverUpdateTime;
	}

	@Column(name = "AUDIO_FILE")
	public byte[] getAudioFile() {
		return this.audioFile;
	}

	public void setAudioFile(byte[] audioFile) {
		this.audioFile = audioFile;
	}

	@Column(name = "MASTER_TYPE", length = 10)
	public String getMasterType() {
		return this.masterType;
	}

	public void setMasterType(String masterType) {
		this.masterType = masterType;
	}

	@Column(name = "MASTER_TYPE_ID", length = 10)
	public String getMasterTypeId() {
		return this.masterTypeId;
	}

	public void setMasterTypeId(String masterTypeId) {
		this.masterTypeId = masterTypeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROCUREMENT_ID")
	public Procurement getProcurement() {
		return this.procurement;
	}
	public void setProcurement(Procurement procurementId) {
		this.procurement = procurementId;
	}

	@Column(name = "QTY", precision = 20, scale = 3)
	public Double getQty() {
		return this.qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	@Column(name = "INITIAL_BALANCE", precision = 20, scale = 3)
	public Double getInitialBalance() {
		return this.initialBalance;
	}

	public void setInitialBalance(Double initialBalance) {
		this.initialBalance = initialBalance;
	}

	@Column(name = "DEBIT_AMOUNT", precision = 20, scale = 3)
	public Double getDebitAmount() {
		return this.debitAmount;
	}

	public void setDebitAmount(Double debitAmount) {
		this.debitAmount = debitAmount;
	}

	@Column(name = "CREDIT_AMOUNT", precision = 20, scale = 3)
	public Double getCreditAmount() {
		return this.creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	@Column(name = "SEASON_CODE", length = 20)
	public String getSeasonCode() {
		return this.seasonCode;
	}

	public void setSeasonCode(String seasonCode) {
		this.seasonCode = seasonCode;
	}

	@Column(name = "BRANCH_ID", length = 45)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "LATITUDE")
	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Column(name = "LONGITUDE")
	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@OneToMany( mappedBy = "trxnAgro")
	public Set<AgroTransaction> getTrxnAgros() {
		return this.trxnAgros;
	}

	public void setTrxnAgros(Set<AgroTransaction> trxnAgros) {
		this.trxnAgros = trxnAgros;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SAMITHI_ID")

	public Packhouse getSamithi() {
		return samithi;
	}

	public void setSamithi(Packhouse samithi) {
		this.samithi = samithi;
	}

	public void calculateBalance(double initBalance, double txnAmount, boolean isCredit) {

		this.initialBalance = initBalance;
		this.txnAmount = txnAmount;
		this.balAmt = isCredit ? (this.initialBalance + this.txnAmount) : (this.initialBalance - this.txnAmount);
	}

	  public void calculateBalance(ESEAccount account, double txnAmount,
	            boolean isProcurementBalance, boolean isCredit) {

	        this.eseAccount = account;
	        if (!ObjectUtil.isEmpty(this.eseAccount)) {
	            if (isProcurementBalance) {
	                calculateBalance(this.eseAccount.getCashBalance(), txnAmount, isCredit);
	                this.eseAccount.setCashBalance(this.balAmount);
	            } else {
	                calculateBalance(this.eseAccount.getCashBalance(), txnAmount, isCredit);
	                this.eseAccount.setCashBalance(this.balAmount);
	            }
	        } else {
	            calculateBalance(this.intBalance, txnAmount, isCredit);
	        }
	    }
	    
	    
	    public void calculateCreditBalance(double initBalance, double txnAmount, boolean isCredit) {

	        this.intBalance = initBalance;
	        this.txnAmount = txnAmount;
	        this.balAmount = isCredit ? (this.intBalance + this.txnAmount)
	                : (this.intBalance - this.txnAmount);
	    }

	 
	    public void calculateCreditBalance(ESEAccount account, double txnAmount,
	            boolean isProcurementBalance, boolean isCredit) {

	        this.eseAccount = account;
	        if (!ObjectUtil.isEmpty(this.eseAccount)) {
	            if (!isProcurementBalance) {
	                calculateCreditBalance(this.eseAccount.getCreditBalance(), txnAmount, isCredit);
	                this.eseAccount.setCreditBalance(this.balAmount);
	            } else {
	                calculateCreditBalance(this.eseAccount.getCreditBalance(), txnAmount, isCredit);
	                this.eseAccount.setCreditBalance(this.balAmount);
	            }
	        } else {
	            calculateCreditBalance(this.intBalance, txnAmount, isCredit);
	        }
	    }

//		public String getProductStock() {
//			return productStock;
//		}
//
//		public void setProductStock(String productStock) {
//			this.productStock = productStock;
//		}
		
		
	public void calculateFarmerLoanBalance(ESEAccount account, double txnAmount) {

		this.eseAccount = account;
		if (!ObjectUtil.isEmpty(this.eseAccount)) {
			calculateLoanBalance(this.eseAccount.getOutstandingLoanAmount(), txnAmount);
			this.eseAccount.setOutstandingLoanAmount(this.balAmount);

		}
	}

	public void calculateLoanBalance(double outStandingLoanAmt, double txnAmount) {

		this.intBalance = outStandingLoanAmt;
		this.txnAmount = txnAmount;
		if (this.intBalance > this.txnAmount) {
			this.balAmount = this.intBalance - this.txnAmount;
		} else {
			this.balAmount = 0.0;
		}

	}

	public String getProductStock() {
		return productStock;
	}

	public void setProductStock(String productStock) {
		this.productStock = productStock;
	}

	public double getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(double txnAmount) {
		this.txnAmount = txnAmount;
	}

	public double getIntBalance() {
		return intBalance;
	}

	public void setIntBalance(double intBalance) {
		this.intBalance = intBalance;
	}

	public double getBalAmount() {
		return balAmount;
	}

	public void setBalAmount(double balAmount) {
		this.balAmount = balAmount;
	}
	@Transient
	public String getTransientId() {
		return transientId;
	}

	public void setTransientId(String transientId) {
		this.transientId = transientId;
	}
	@Transient
	public int getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(int balanceType) {
		this.balanceType = balanceType;
	}
	@Transient
	public AgroTransaction getRefAgroTransaction() {
		return refAgroTransaction;
	}

	public void setRefAgroTransaction(AgroTransaction refAgroTransaction) {
		this.refAgroTransaction = refAgroTransaction;
	}
	@Transient
	public Packhouse getPackhouse() {
		return packhouse;
	}

	public void setPackhouse(Packhouse packhouse) {
		this.packhouse = packhouse;
	}
	@Transient
	public long getEseAccountId() {
		return eseAccountId;
	}

	public void setEseAccountId(long eseAccountId) {
		this.eseAccountId = eseAccountId;
	}
	@Transient
	public List<String> getAgentList() {
		return agentList;
	}
	@Transient
	public void setAgentList(List<String> agentList) {
		this.agentList = agentList;
	}
	@Transient
	public List<String> getTxnTypeList() {
		return txnTypeList;
	}
	public void setTxnTypeList(List<String> txnTypeList) {
		this.txnTypeList = txnTypeList;
	}
	@Transient
	public Double getBalanceAmt() {
		return balanceAmt;
	}

	public void setBalanceAmt(Double balanceAmt) {
		this.balanceAmt = balanceAmt;
	}
	@Transient
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	@Transient
	public String getFinalBalance() {
		return finalBalance;
	}

	public void setFinalBalance(String finalBalance) {
		this.finalBalance = finalBalance;
	}
	
	@Transient
	public List<ProcurementDetail> getProcurementDetailList() {
		return procurementDetailList;
	}

	public void setProcurementDetailList(List<ProcurementDetail> procurementDetailList) {
		this.procurementDetailList = procurementDetailList;
	}
	@Transient
	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}
	@Transient
	public String getTxnTimeStr() {
		return txnTimeStr;
	}

	public void setTxnTimeStr(String txnTimeStr) {
		this.txnTimeStr = txnTimeStr;
	}

	
	@Transient
	public Map<String, String> getFilterData() {
		return filterData;
	}

	public void setFilterData(Map<String, String> filterData) {
		this.filterData = filterData;
	}
	@Transient
	public String getTempBalAmt() {
		return tempBalAmt;
	}

	public void setTempBalAmt(String tempBalAmt) {
		this.tempBalAmt = tempBalAmt;
	}
	@Transient
	public String getTemptxnAmount() {
		return temptxnAmount;
	}

	public void setTemptxnAmount(String temptxnAmount) {
		this.temptxnAmount = temptxnAmount;
	}
	@Transient
	public String getTempIntBalance() {
		return tempIntBalance;
	}

	public void setTempIntBalance(String tempIntBalance) {
		this.tempIntBalance = tempIntBalance;
	}
	@Transient
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	@Transient
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	@Transient
	public double getVendorIntBalance() {
		return vendorIntBalance;
	}

	public void setVendorIntBalance(double vendorIntBalance) {
		this.vendorIntBalance = vendorIntBalance;
	}
	@Transient
	public double getVendorTxnAmount() {
		return vendorTxnAmount;
	}

	public void setVendorTxnAmount(double vendorTxnAmount) {
		this.vendorTxnAmount = vendorTxnAmount;
	}
	@Transient
	public double getVendorBalAmount() {
		return vendorBalAmount;
	}

	public void setVendorBalAmount(double vendorBalAmount) {
		this.vendorBalAmount = vendorBalAmount;
	}
	@Transient
	public long getSamithiId() {
		return samithiId;
	}

	public void setSamithiId(long samithiId) {
		this.samithiId = samithiId;
	}
	@Transient
	public String getProofNo() {
		return proofNo;
	}

	public void setProofNo(String proofNo) {
		this.proofNo = proofNo;
	}
	
	
}
