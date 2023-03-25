package www.nexign.com;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import www.nexign.com.entity.CallInformation;

public class MainApplication {

	private static final String INPUT_FILE_NAME = "report_example.txt";
	public static void main(String[] args) throws IOException, URISyntaxException{
		File inputFile = new File(MainApplication.class.getClassLoader().getResource(INPUT_FILE_NAME).toURI());
		List<CallInformation> vendors = CallInfoParser.parseCallInfosFromFile(inputFile);
		PhoneTable pt = new PhoneTable();
		vendors.stream().forEach((v) -> pt.add(v));
		pt.createReportForAllPhoneNumbers();
	}
}
