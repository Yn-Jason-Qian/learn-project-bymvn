package net.stealthcat.util.ctrip.pms.stock;

import java.sql.Timestamp;

public class VbkChannelStockUsage {

	private Long iD;

	private Integer cityID;

	private Integer vendorID;

	private Integer storeID;

	private Integer vehicleID;

	private Long carID;

	private Long childSeatStockUsage;

	private Timestamp beginTime;

	private Timestamp endTime;

	private Integer usageType;

	private String usageID;

	private Integer channelCode;

	private Integer saleChannelCode;

	private Integer status;

	private String createUser;

	private Timestamp createTime;

	private String lastUser;

	private Timestamp datachangeLasttime;

	private Integer originalVendorID;

	private Integer originalStoreID;

	public Long getID() {
		return iD;
	}

	public void setID(Long iD) {
		this.iD = iD;
	}

	public Integer getCityID() {
		return cityID;
	}

	public void setCityID(Integer cityID) {
		this.cityID = cityID;
	}

	public Integer getVendorID() {
		return vendorID;
	}

	public void setVendorID(Integer vendorID) {
		this.vendorID = vendorID;
	}

	public Integer getStoreID() {
		return storeID;
	}

	public void setStoreID(Integer storeID) {
		this.storeID = storeID;
	}

	public Integer getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(Integer vehicleID) {
		this.vehicleID = vehicleID;
	}

	public Long getCarID() {
		return carID;
	}

	public void setCarID(Long carID) {
		this.carID = carID;
	}

	public Long getChildSeatStockUsage() {
		return childSeatStockUsage;
	}

	public void setChildSeatStockUsage(Long childSeatStockUsage) {
		this.childSeatStockUsage = childSeatStockUsage;
	}

	public Timestamp getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Integer getUsageType() {
		return usageType;
	}

	public void setUsageType(Integer usageType) {
		this.usageType = usageType;
	}

	public String getUsageID() {
		return usageID;
	}

	public void setUsageID(String usageID) {
		this.usageID = usageID;
	}

	public Integer getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(Integer channelCode) {
		this.channelCode = channelCode;
	}

	public Integer getSaleChannelCode() {
		return saleChannelCode;
	}

	public void setSaleChannelCode(Integer saleChannelCode) {
		this.saleChannelCode = saleChannelCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getLastUser() {
		return lastUser;
	}

	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

	public Integer getOriginalVendorID() {
		return originalVendorID;
	}

	public void setOriginalVendorID(Integer originalVendorID) {
		this.originalVendorID = originalVendorID;
	}

	public Integer getOriginalStoreID() {
		return originalStoreID;
	}

	public void setOriginalStoreID(Integer originalStoreID) {
		this.originalStoreID = originalStoreID;
	}

}