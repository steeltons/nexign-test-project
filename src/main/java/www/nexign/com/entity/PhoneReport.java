package www.nexign.com.entity;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class PhoneReport {

	private byte callType;
	private Date startCallingTime;
	private Date endCallingTime;
	private Duration duration;
	private double cost;
	
	public PhoneReport() {}
	
	public PhoneReport(byte callType, Date start, Date end, double cost) {
		this.callType = callType;
		this.startCallingTime = start;
		this.endCallingTime = end;
		this.cost = cost;
		this.duration = Duration.ofMillis(end.getTime() - start.getTime());
	}
	
	public byte getCallType() {
		return callType;
	}
	
	public Date getStartCallingTime() {
		return startCallingTime;
	}
	
	public Date getEndCallingTime() {
		return endCallingTime;
	}
	
	public double getCost() {
		return cost;
	}
	
	public Duration getDuration() {
		return duration;
	}
	
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		long seconds = duration.getSeconds();
		return callType + " " + formatter.format(startCallingTime) + " " + formatter.format(endCallingTime) + " " +
			   String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60) + " " + cost;	
	}
}
