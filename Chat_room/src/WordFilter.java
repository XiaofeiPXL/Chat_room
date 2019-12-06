import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class WordFilter 
{
    // prepare to filter content
	private String word = "";

	// get content after filter
	public String getWord() {
		return word;
	}

	// set filter content
	public void setWord(String word) 
	{
		this.word = word;
	}

	public WordFilter(String word) 
	{
		super();
		this.word = word;
	}

	// set filter class
	public WordFilter() 
	{
		// TODO 
	}

	public void process() 
	{
		String badWord = this.getFile("badword.txt");
		String badWordList[] = badWord.split(",");
		for (int i = 0; i < badWordList.length; i++) {
			if (word.indexOf(badWordList[i]) != -1) {
				word = "Forbid contend,not show!";
			}
		}

	}

	// get content from bedword file
	public String getFile(String file) {
		String fileString = "";
		try {
			File files = new File(file);
			FileReader fileReader = new FileReader(files);
			BufferedReader read = new BufferedReader(fileReader);
			while (true) {
				String line = read.readLine();
				if (line == null) {
					break;
				}
				fileString += (line);
			}
			read.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileString;
	}

	// filter illegal contend and return other content
	public static String filter(String word) {
		WordFilter wf = new WordFilter(word);
		wf.process();

		return wf.getWord();

	}

}
