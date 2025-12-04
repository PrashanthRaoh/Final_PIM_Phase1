package common_functions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotepadManager {
//	private static final String FILE_PATH = "src/test/resources/OnHoldSystem.txt";

	private NotepadManager() {
	}

	public static void ReadWriteNotepad(String fileName, Map<String, Object> data) throws IOException {
		Path path = Paths.get("src/test/resources/" + fileName);
		System.out.println(path.getParent());
		Files.createDirectories(path.getParent());
		
		if (!Files.exists(path)) {
			Files.createFile(path);
			System.out.println("File created: " + path.toString());
		} else {
			String existingcontent = new String(Files.readAllBytes(path));
			System.out.println("Existing lines are " + existingcontent);
		}

		/*********************************
		 * Write to notepad
		 *********************************/
		StringBuilder content = new StringBuilder();
		for (Entry<String, Object> entry : data.entrySet()) {
			content.append(" \"").append(entry.getKey()).append("\" = ");
			Object value = entry.getValue();
			if (value instanceof List) {
				List<?> list = (List<?>) value;
				content.append("[");
				for (int i = 0; i < list.size(); i++) {
					content.append("\"").append(list.get(i)).append("\"");

					if (i < list.size() - 1) {
						content.append(",");
					}
				}
				content.append("]");
			} else {
				content.append("\"").append(value).append("\"");
			}
			content.append(System.lineSeparator());
		}
		content.append("---------------------------------------").append(System.lineSeparator());
		Files.write(path, content.toString().getBytes(), StandardOpenOption.APPEND);
		System.out.println("File updated with value");
	}
	
	/***********************************
	 * Overwrites the text in notepad
	***********************************/
	
	public static void Over_WriteNotepad(String fileName, Map<String, Object> data) throws IOException {
		Path path = Paths.get("src/test/resources/" + fileName);
		System.out.println(path.getParent());
		Files.createDirectories(path.getParent());
		
		if (!Files.exists(path)) {
			Files.createFile(path);
			System.out.println("File created: " + path.toString());
		} else {
			String existingcontent = new String(Files.readAllBytes(path));
			System.out.println("Existing lines are " + existingcontent);
		}
		
		/*********************************
		 * Write to notepad
		 *********************************/
		StringBuilder content = new StringBuilder();
		for (Entry<String, Object> entry : data.entrySet()) {
			content.append(" \"").append(entry.getKey()).append("\" = ");
			Object value = entry.getValue();
			if (value instanceof List) {
				List<?> list = (List<?>) value;
				content.append("[");
				for (int i = 0; i < list.size(); i++) {
					content.append("\"").append(list.get(i)).append("\"");
					
					if (i < list.size() - 1) {
						content.append(",");
					}
				}
				content.append("]");
			} else {
				content.append("\"").append(value).append("\"");
			}
			content.append(System.lineSeparator());
		}
		content.append("---------------------------------------").append(System.lineSeparator());
		Files.write(path, content.toString().getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
		System.out.println("File Overwritten with latest value");
	}

	/*********************************
	 * Get the output as a list of Material IDs
	 *********************************/
	public static List<String> GetMaterialIDs(String fileName) throws IOException {
		Path path = Paths.get("src/test/resources/" + fileName);
		if (!Files.exists(path)) {
			System.out.println("File not found: " + fileName);
			return Collections.emptyList();
		}

		List<String> lines = Files.readAllLines(path);
		Pattern pattern = Pattern.compile("\"Material ID\"\\s*=\\s*\"([^\"]+)\"");
		List<String> materialIds = new LinkedList<>();

		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				materialIds.add(matcher.group(1));
			}
		}
		return materialIds;
	}

	/*********************************
	 * Get the output as a list of Key
	 *********************************/
	public static List<String> getValuesByKey(String fileName, String key) throws IOException {
	    Path path = Paths.get("src/test/resources/" + fileName);
	    if (!Files.exists(path)) {
	        System.out.println("File not found: " + fileName);
	        return Collections.emptyList();
	    }

	    List<String> lines = Files.readAllLines(path);
	    List<String> values = new LinkedList<>();

	    for (String line : lines) {
	        // Match scalar value: "key" = "value"
	        Pattern scalarPattern = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*=\\s*\"([^\"]+)\"");
	        Matcher scalarMatcher = scalarPattern.matcher(line);
	        if (scalarMatcher.find()) {
	            values.add(scalarMatcher.group(1));
	            continue;
	        }

	        // Match list value: "key" = ["val1", "val2"]
	        Pattern listPattern = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*=\\s*\\[(.*?)\\]");
	        Matcher listMatcher = listPattern.matcher(line);
	        if (listMatcher.find()) {
	            String listContent = listMatcher.group(1);
	            String[] listItems = listContent.split(",");
	            for (String item : listItems) {
	                values.add(item.replaceAll("^\"|\"$", "").trim());
	            }
	        }
	    }

	    return values;
	}
	
	/***************************************************************************************************
	 * Compare the difference between Pre ETL and Post ETL values of sales org auto
	 ***************************************************************************************************/
	    public static <T> Map<String, List<T>> getListDifferences(List<T> preList, List<T> postList) {
	        Map<String, List<T>> differences = new HashMap<>();
	        List<T> addedInPost = new ArrayList<>();
	        for (T item : postList) {
	            if (!preList.contains(item)) {
	                addedInPost.add(item);
	            }
	        }

	        List<T> removedFromPre = new ArrayList<>();
	        for (T item : preList) {
	            if (!postList.contains(item)) {
	                removedFromPre.add(item);
	            }
	        }

	        differences.put("Added in Post List", addedInPost);
	        differences.put("Removed from Pre List", removedFromPre);

	        return differences;
	    }
	    
	    
	    
	    
	    
	    
	}