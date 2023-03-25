package www.nexign.com.reportCreator;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import www.nexign.com.entity.CallInformation;
import www.nexign.com.entity.PhoneReport;

public interface ReportCreator {
	
	public final Comparator<PhoneReport> PHONE_REPORT_COMPARATOR = (pr1, pr2) -> {
		if(pr1.getCallType() != pr2.getCallType()) {
			return pr1.getCallType() - pr2.getCallType();
		}
		return pr1.getStartCallingTime().compareTo(pr2.getEndCallingTime());
	};
	
	public void createReportFile(List<CallInformation> vendorList) throws IOException; 
}
