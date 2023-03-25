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

public class PhoneTable {

	private Map<Long, List<CallInformation>> phoneTable;
	
	public PhoneTable() {
		this.phoneTable = new HashMap<>();
	}
	
	public void add(CallInformation callInfo) {
		if(!phoneTable.containsKey(callInfo.getPhoneNumber())) {
			phoneTable.put(callInfo.getPhoneNumber(), new ArrayList<>());
		}
		phoneTable.get(callInfo.getPhoneNumber()).add(callInfo);
	}
	
	public void createReportForPhoneNumber(long phoneNumber) {
		if(!phoneTable.containsKey(phoneNumber)) {
			return;
		}
		List<CallInformation> callList = phoneTable.get(phoneNumber);
		callList = callList.stream().sorted((c1, c2) -> c1.getStartCallingTime()
														  .compareTo(c2.getEndCallingTime()))
									.collect(Collectors.toList());
		try {
			ReportCreator creator = new ReportCreatorBuilder().build(callList.get(0).getTariff());
			creator.createReportFile(callList);
		} catch (NoSuchTariffException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createReportForAllPhoneNumbers() {
		for(Long phoneNumber : phoneTable.keySet()) {
			List<CallInformation> callList = phoneTable.get(phoneNumber);
			callList = callList.stream().sorted((c1, c2) -> c1.getStartCallingTime()
															  .compareTo(c2.getEndCallingTime()))
										.collect(Collectors.toList());
			try {
				ReportCreator creator = new ReportCreatorBuilder().build(callList.get(0).getTariff());
				creator.createReportFile(callList);
			} catch (NoSuchTariffException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
