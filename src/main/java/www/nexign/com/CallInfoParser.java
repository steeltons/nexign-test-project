package www.nexign.com;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import www.nexign.com.entity.CallInformation;

/**
 * <h2>Call Info parser</h2>
 * This class created to parse input file with phone calls information.
 * @author Lakmilak
 *
 */
public class CallInfoParser {

	/**
	 * <h2>Parse phone calls file</h2>
	 * This static method is converting phone call information line
	 * from file to CallInformation object. Write in console line number
	 * in file, if line cannot be parsed.
	 * @param callInfoFile - file with information of calls.
	 * @return List<CallInformation> - list of calls information.
	 * @throws FileNotFoundException throws if file was not found.
	 */
	public static List<CallInformation> parseCallInfosFromFile(File callInfoFile) throws FileNotFoundException {
		Scanner reader = new Scanner(callInfoFile);
		List<CallInformation> callInfoList = new ArrayList<>();
		int lineCount = 1;
		while(reader.hasNext()) {
			String line = reader.nextLine();
			if(line.isBlank()) {
				continue;
			}
			String[] params = line.replace(" ", "").split(",");
			try {
				CallInformation callInfo = new CallInformation();
				callInfo.setCallType(Byte.parseByte(params[0]));
				callInfo.setPhoneNumber(Long.parseLong(params[1]));
				callInfo.setStartCallingTime(parseDate(params[2]));
				callInfo.setEndCallingTime(parseDate(params[3]));
				callInfo.setTariff(Byte.parseByte(params[4]));
				callInfoList.add(callInfo);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.err.println(String.format("Error on line %d. Call information cannot be parsed.", lineCount));
			}
			lineCount++;
		}
		return callInfoList;
	}
	
	/**
	 * <h2>Date converter</h2>
	 * This method convert date from String date.
	 * @param date - line of format YYYYMMDDHH24MMSS
	 * @return Date
	 */
	private static Date parseDate(String date) {
		int year = Integer.parseInt(date.substring(0, 4)) - 1900;
		int month = Integer.parseInt(date.substring(4, 6)) - 1;
		int day = Integer.parseInt(date.substring(6, 8));
		int hour = Integer.parseInt(date.substring(8, 10));
		int minute = Integer.parseInt(date.substring(10, 12));
		int second = Integer.parseInt(date.substring(12, 14));
		return new Date(year, month, day, hour, minute, second);
	}
}
