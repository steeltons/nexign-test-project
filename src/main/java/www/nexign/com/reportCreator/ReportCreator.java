package www.nexign.com.reportCreator;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import www.nexign.com.entity.CallInformation;
import www.nexign.com.entity.PhoneReport;

/**
 * <h2>Report creator</h2>
 * This interface specialized to create report files for specific tariff.
 * @author Lakmilak1
 *
 */
public interface ReportCreator {
	
	public final Comparator<PhoneReport> PHONE_REPORT_COMPARATOR = (pr1, pr2) -> {
		if(pr1.getCallType() != pr2.getCallType()) {
			return pr1.getCallType() - pr2.getCallType();
		}
		return pr1.getStartCallingTime().compareTo(pr2.getEndCallingTime());
	};
	
	/**
	 * <h2>Create report file.</h2>
	 * This method create report file for specific tariff.
	 * @param callInfoList - list of phone calls.
	 * @throws IOException throws if directory <b>reports</b> cannot be found and created or if error while 
	 * writing file.
	 */
	public void createReportFile(List<CallInformation> callInfoList) throws IOException; 
}
