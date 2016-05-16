import java.util.ArrayList;

public class StringOperations 
{
	public ArrayList<String> parseMusic(String s)
	{
		int lastIndex = 0;
		int firstIndex = 0;
		ArrayList<String> lines = new ArrayList<String>();
		
		while(true)
		{	
			char c = s.charAt(lastIndex);
			
			if (c == '\n')
			{
				lines.add(s.substring(firstIndex, lastIndex));
				firstIndex = lastIndex+1;
			}
			
			if (c == '*') break;
			
			lastIndex++;
		}
		
		ArrayList<String> datas = new ArrayList<String>();
		datas = parseMore(lines);
		ArrayList<String> finalLines = new ArrayList<String>();
		finalLines = makeLine(datas);
		
		return finalLines;
	}
	
	private ArrayList<String> makeLine(ArrayList<String> eachLine) 
	{
		ArrayList<String> last = new ArrayList<String>();
		
		for(int j = 0; j<eachLine.size()/5; j++)
		{
			String finalLine;
			int nameLength, priceLength, locLength, dateLength, genreLength;
			int k = j*5;
			
			finalLine = eachLine.get(k);
			nameLength = 30 - eachLine.get(k).length();
			for(int i = 0; i<nameLength; i++)
			{
				finalLine = finalLine + " ";
			}
		
			finalLine = finalLine + eachLine.get(k+1);
			priceLength = 10 - eachLine.get(k+1).length();
			for(int i = 0; i<priceLength; i++)
			{
				finalLine = finalLine + " ";
			}
		
			finalLine = finalLine + eachLine.get(k+2);
			locLength = 27 - eachLine.get(k+2).length();
			for(int i = 0; i<locLength; i++)
			{
				finalLine = finalLine + " ";
			}
		
			finalLine = finalLine + eachLine.get(k+3);
			dateLength = 27 - eachLine.get(k+3).length();
			for(int i = 0; i<dateLength; i++)
			{
				finalLine = finalLine + " ";
			}
		
			finalLine = finalLine + eachLine.get(k+4);
			genreLength = 20 - eachLine.get(k+4).length();
			for(int i = 0; i<genreLength; i++)
			{
				finalLine = finalLine + " ";
			}
			finalLine = finalLine + "\n";
			
			last.add(finalLine);
		}
		
		return last;
	}
	
	private ArrayList<String> parseMore(ArrayList<String> eachLine)
	{
		ArrayList<String> eachData = new ArrayList<String>();
		
		for(int i = 0; i < eachLine.size(); i++)
		{
			int lastIndex = 0;
			int firstIndex = 0;
			String line = eachLine.get(i);
			
			while(true)
			{
				char c = line.charAt(lastIndex);
				
				if (c == '1' || c == '2'|| c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9')
				{
					eachData.add(line.substring(0, lastIndex));
					firstIndex = lastIndex;
					break;
				}
				lastIndex++;
			}
			lastIndex++;
			
			while(true)
			{
				char c = line.charAt(lastIndex);
				
				if (c != '0' && c != '1' && c != '2' && c != '3' && c != '4' && c != '5' && c != '6' && c != '7' && c != '8' && c != '9')
				{
					eachData.add(line.substring(firstIndex, lastIndex));
					firstIndex = lastIndex;
					break;
				}	
				lastIndex++;
			}
			lastIndex++;
			
			while(true)
			{
				char c = line.charAt(lastIndex);
				String s = "" + c;
				
				if (s.contains("1") || s.contains("2") || s.contains("3") || s.contains("4") || s.contains("5") 
						|| s.contains("6") || s.contains("7") || s.contains("8") || s.contains("9"))
				{
					eachData.add(line.substring(firstIndex+1, lastIndex));
					firstIndex = lastIndex;
					break;
				}
				lastIndex++;
			}
			lastIndex++;
			
			while(true)
			{
				char c = line.charAt(lastIndex);
				
				if (c == '.')
				{
					eachData.add(line.substring(firstIndex, lastIndex));
					firstIndex = lastIndex;
					break;
				}	
				lastIndex++;
			}
			lastIndex = lastIndex + 3;
			
			while(true)
			{
				char c = line.charAt(lastIndex);
				
				if (c == ' ')
				{
					eachData.add(line.substring(firstIndex+3, lastIndex));
					firstIndex = lastIndex;
					break;
				}	
				lastIndex++;
			}
		}
		
		return eachData;
	}
}
