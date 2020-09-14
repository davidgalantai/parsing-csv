package parseCSV;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class parseCSV {

	public static String COMMA_DELIMITER = ",";

	public static void main(String[] args) {

		List<String> nameColumn = new ArrayList<String>();// använder det här i flera metoder
		List<List<String>> records = new ArrayList<>();
		try (Scanner scanner = new Scanner(new File("sample.csv"));) {
			while (scanner.hasNextLine()) {
				records.add(getRecordFromLine(scanner.nextLine()));

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		for (List<String> row : records) {
			nameColumn.add(row.get(1)); // lägger till namn from column1 och 2 till listan
			nameColumn.add(row.get(2));

		}
		findDuplicateDates(records, nameColumn); // kallar på mina metoder
		Android(records);
		LetterA(nameColumn);

	}

	private static void findDuplicateDates(List<List<String>> records, List<String> nameColumn) {
		Map<Integer, String> duplicateDates = new HashMap<>();
		Map<Integer, String> uniqueDates = new HashMap<>();

		List<String> dateCol = new ArrayList<String>();

		int x = 0;
		for (List<String> row : records) {
			dateCol.add(row.get(0)); // lägger till column 0
			if (uniqueDates.containsValue(dateCol.get(x)) && !dateCol.get(x).equals("")) {
				duplicateDates.put(x, dateCol.get(x));
			} else {
				uniqueDates.put(x, dateCol.get(x));
			}
			x++;
		}//två hashmaps, kontrollera när du lägger till först värdet om det redan finns så  lägger den till det i andra (duplicateDates)

		for (Map.Entry<Integer, String> entry : duplicateDates.entrySet()) {
			for (int i = 1; i < dateCol.size(); i++) {
				if (duplicateDates.get(entry.getKey()).equals(dateCol.get(i)) && !dateCol.get(i).equals("")
						&& !nameColumn.get(entry.getKey()).equals(nameColumn.get(i))) {
					System.out.println(nameColumn.get(entry.getKey() * 2) + " and "
							+ nameColumn.get(entry.getKey() * 2 + 1) + " have the same timestamp with "
							+ nameColumn.get(i * 2) + " and " + nameColumn.get(i * 2 + 1));
				}
				// kollar vart datumen passar in i datum columnen och printar ut namnerna som
				// har samma instämpling
			}
		}
	}

	private static void Android(List<List<String>> records) {
		List<String> androidCol = new ArrayList<String>();
		int count = 0;
		for (List<String> row : records) {
			androidCol.add(row.get(6)); // lägger till column 6 till listan
		}
		for (String android : androidCol) {
			if (android.equals("Android App")) { // om raden har android app plussa på med 1
				count++;
			}
		}
		System.out.println(count * 2 + " persons will work with Android"); // printa ut summan gånger 2 för dfet finns 2
																			// rader med namn
	}

	private static void LetterA(List<String> nameColumn) {
		int count = 0;

		for (int i = 2; i < nameColumn.size(); i++) { // börjar på 2 så inte namn och namn räknas med i det hela
			String firstNameCol = nameColumn.get(i).toLowerCase(); // läser in a i lowercase

			if (firstNameCol.contains("a")) { // om namnet inehåller a plussa på
				count++;

			}

		}
		System.out.println(count + " names has letter a"); // printa ut hur många namn har bokstav a i sig
	}

	private static List<String> getRecordFromLine(String line) {
		List<String> values = new ArrayList<String>();
		try (Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(COMMA_DELIMITER);
			while (rowScanner.hasNext()) {
				values.add(rowScanner.next());
			}
		}

		return values;
	}

}