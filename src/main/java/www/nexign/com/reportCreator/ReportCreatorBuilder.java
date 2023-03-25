package www.nexign.com.reportCreator;

public class ReportCreatorBuilder {

	public ReportCreator build(byte tariff) throws NoSuchTariffException {
		switch(tariff) {
			case 3  : return new Tariff03ReportCreator();
			case 6  : return new Tariff06ReportCreator();
			case 11 : return new Tariff11ReportCreator();
			default : throw new NoSuchTariffException(String.format("Tariff %d is not exist.", tariff));
		}
	}
}
