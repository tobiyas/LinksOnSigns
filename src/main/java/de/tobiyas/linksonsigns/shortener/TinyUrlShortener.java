package de.tobiyas.linksonsigns.shortener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class TinyUrlShortener
{

	public static String shortenURL(String LongURL)
	{
		String _result = "";
		
		if (LongURL.startsWith("http://"))
			LongURL = LongURL.replace("http://", "");
		if (LongURL.startsWith("https://"))
			LongURL = LongURL.replace("https://", "");
		if (LongURL.startsWith("ftp://"))
			LongURL = LongURL.replace("ftp://", "");
		try
		{
			URL DataURL = new URL("http://tinyurl.com/create.php?url=" + LongURL);
			URLConnection openURL = DataURL.openConnection();
			openURL.addRequestProperty("User-Agent", "Mozilla/4.76");
			BufferedReader in = new BufferedReader(new InputStreamReader(openURL.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				if (inputLine.contains("<small>["))
				{
					int smallStart = inputLine.indexOf("<small>");
					int smallEnd = inputLine.indexOf("</small>");
					_result = inputLine.substring(smallStart, smallEnd + 8);
					int hrefStart = _result.indexOf("href=\"");
					int hrefEnd = _result.indexOf("\"", hrefStart + 6);
					_result = _result.substring(hrefStart + 6, hrefEnd);
				}
			}
			in.close();
		}
		catch (IOException e)
		{
			_result = "Check internet connection";
		}
		catch (Exception e)
		{
			_result = e.getMessage();
		}

		//remove preview stuff
		_result = _result.replace("preview.", "");

		return _result;
	}
}