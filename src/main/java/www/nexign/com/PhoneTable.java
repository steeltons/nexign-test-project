package www.nexign.com;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import www.nexign.com.entity.CallInformation;
import www.nexign.com.reportCreator.NoSuchTariffException;
import www.nexign.com.reportCreator.ReportCreator;
import www.nexign.com.reportCreator.ReportCreatorBuilder;

/**
 * <h2>Storage for phone calls</h2>
 * This class aggregates HashMap to store
 * phone calls information, that stored by
 * phone number.
 * 
 * @author Lakmilak1
 * @version 1.0.0-alpha
 * @since 2023-03-26
 */
public class PhoneTable {

	private Map<Long, List<CallInformation>> phoneTable;
	
	public PhoneTable() {
		this.phoneTable = new HashMap<>();
	}
	
	/**
	 * <h2>Add to storage method</h2>
	 * This method puts call information into
	 * list of calls (if number contains). Otherwise
	 * create list by phone number and put info in it.
	 * @param callInfo - phone call info. 
	 * <b>callInfo - phone call info. CallInfo must be not null and CallInfo.phoneNumber must be between 70000000000 
	 * and 79999999999.</b>
	 */
	public void add(CallInformation callInfo) {
		if(callInfo == null || callInfo.getPhoneNumber() < 70000000000l || callInfo.getPhoneNumber() >= 79999999999l) {
			throw new IllegalArgumentException("callInfo is null or phone number not between 70000000000 "
												+ "and 79999999999");
		}
		if(!phoneTable.containsKey(callInfo.getPhoneNumber())) {
			phoneTable.put(callInfo.getPhoneNumber(), new ArrayList<>());
		}
		phoneTable.get(callInfo.getPhoneNumber()).add(callInfo);
	}
	
	/**
	 * <h2>Phone number report creator</h2>
	 * This method create report with name \"${number}_report.txt\" in reports directory for concrete phone number.
	 * @param phoneNumber - phone number. Must be between 70000000000 and 79999999999.
	 */
	public void createReportForPhoneNumber(long phoneNumber) {
		if(phoneNumber < 70000000000l || phoneNumber >= 79999999999l) {
			throw new IllegalArgumentException("Phone number is not between 70000000000 and 79999999999");
		}
		List<CallInformation> callList = phoneTable.get(phoneNumber);
		callList = callList.stream().sorted((c1, c2) -> c1.getStartCallingTime()
														  .compareTo(c2.getStartCallingTime()))
									.collect(Collectors.toList());
		try {
			ReportCreator creator = new ReportCreatorBuilder().build(callList.get(0).getTariff());
			creator.createReportFile(callList);
		} catch (NoSuchTariffException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <h2>Phone number reports creator</h2>
	 * This method create reports for all phone numbers, that contains in storage.
	 */
	public void createReportForAllPhoneNumbers() {
		for(Long phoneNumber : phoneTable.keySet()) {
			createReportForPhoneNumber(phoneNumber);
		}
	}
}
