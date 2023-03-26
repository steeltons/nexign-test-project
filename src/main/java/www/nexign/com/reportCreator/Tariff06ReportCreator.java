package www.nexign.com.reportCreator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import www.nexign.com.entity.CallInformation;
import www.nexign.com.entity.PhoneReport;

public class Tariff06ReportCreator implements ReportCreator{

	private final double MINUTE_COST = 1.0;
	
	Tariff06ReportCreator() {}
	
	@Override
	public void createReportFile(List<CallInformation> callInfoList) throws IOException {
		long phoneNumber = callInfoList.get(0).getPhoneNumber();
		File reportDir = new File("reports/");
		if(!reportDir.exists()) {
			reportDir.mkdir();
		}
		FileWriter writer = new FileWriter("reports/"+phoneNumber+"_report.txt");
		writer.write("Tariff index: 06\n");
		writer.write("---------------------------------------------------------------------------\n");
		writer.write(String.format("Report for phone number %d:\n", phoneNumber));
		writer.write("----------------------------------------------------------------------------\n");
		writer.write("| Call Type |   Start Time        |     End Time        | Duration | Cost  |\n");
		writer.write("----------------------------------------------------------------------------\n");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		double totalPrice = 0;
		DecimalFormat decimalFormat = new DecimalFormat("000.00");
		for(PhoneReport report : createPhoneReports(callInfoList)) {
			long seconds = report.getDuration().getSeconds();
			totalPrice += report.getCost();
			String duration = String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60);
			String outputLine = String.format("|     %02d    | %s | %s | %s | %s |\n",
											 report.getCallType(), formatter.format(report.getStartCallingTime()),
											 formatter.format(report.getEndCallingTime()), duration,
											 decimalFormat.format(report.getCost()));
			writer.write(outputLine);
		}
		decimalFormat = new DecimalFormat("000000.00");
		totalPrice += 100;
		writer.write("----------------------------------------------------------------------------\n");
		writer.write(String.format("|                                           Total Cost: | %s rubles |\n", 
								   decimalFormat.format(totalPrice)));
		writer.write("----------------------------------------------------------------------------");
		writer.flush();
		writer.close();
	}
	
	public List<PhoneReport> createPhoneReports(List<CallInformation> callInfoList) {
		Duration callLimitBuffer = Duration.ofMinutes(300);
		List<PhoneReport> phoneReportList = new ArrayList<>();
		for(CallInformation callInfo : callInfoList) {
			double price = 0;
			long callSecondsDuration = callInfo.getCallDurationInSeconds();
			if(callLimitBuffer.minusSeconds(callSecondsDuration).isNegative()) {
				callSecondsDuration -= callLimitBuffer.toSeconds();
				if(!callLimitBuffer.isZero()) { 
					callLimitBuffer = Duration.ZERO;
				}
				price = Math.ceil(callSecondsDuration / 60.0) * MINUTE_COST;
			} else {
				callLimitBuffer = callLimitBuffer.minusSeconds(callSecondsDuration);
			}
			phoneReportList.add(new PhoneReport(callInfo.getCallType(), callInfo.getStartCallingTime(),
												callInfo.getEndCallingTime(), price));
						
		}
		return phoneReportList.stream()
				  .sorted(PHONE_REPORT_COMPARATOR)
				  .collect(Collectors.toList());
	}

}
