package www.nexign.com.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.text.DateFormatter;

public class CallInformation {

	private byte callType;
	private long phoneNumber;
	private Date startCallingTime;
	private Date endCallingTime;
	private byte tariff;
	
	public CallInformation() {}
	
	public CallInformation(byte callType, long phoneNumber, Date start, Date end, byte tariff) {
		this.callType = callType;
		this.phoneNumber = phoneNumber;
		this.startCallingTime = start;
		this.endCallingTime = end;
		this.tariff = tariff;
	}
	
	
	public long getCallDurationInSeconds() {
		return (long) (endCallingTime.getTime() - startCallingTime.getTime()) / 1000;
	}
	
	// Getters, Setters, HashCode and Equals
	
	@Override
	public int hashCode() {
		int res = 1;
		res = res * 31 + callType;
		res = (int) (res * 31 + phoneNumber);
		res = res * 31 + startCallingTime.hashCode();
		res = res * 31 + endCallingTime.hashCode();
		res = res * 31 + tariff;
		return res;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(o == null || !(o instanceof CallInformation)) {
			return false;
		}
		CallInformation v = (CallInformation) o;
		return this.callType == v.callType && this.phoneNumber == v.phoneNumber && this.tariff == v.tariff &&
			   this.startCallingTime.equals(v.startCallingTime) && this.endCallingTime.equals(v.endCallingTime);
	}
	
	public void setCallType(byte callType) {
		this.callType = callType;
	}
	
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setStartCallingTime(Date startCallingTime) {
		this.startCallingTime = startCallingTime;
	}
	
	public void setEndCallingTime(Date endCallingTime) {
		this.endCallingTime = endCallingTime;
	}
	
	public void setTariff(byte tariff) {
		this.tariff = tariff;
	}
	
	public byte getCallType() {
		return callType;
	}
	
	public long getPhoneNumber() {
		return phoneNumber;
	}
	
	public Date getStartCallingTime() {
		return startCallingTime;
	}
	
	public Date getEndCallingTime() {
		return endCallingTime;
	}
	
	public byte getTariff() {
		return tariff;
	}
}
