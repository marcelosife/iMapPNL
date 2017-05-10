package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class RequestVISL {
	private ArrayList<String> requestResponse = new ArrayList<String>();
	
	public RequestVISL(String text){
		try {
			String html = getWebRequestPost("http://beta.visl.sdu.dk/visl/pt/parsing/automatic/parse.php?inputlang=pt&parser=dep-eb&visual=niceline&symbol=default&structure=flat&text="+text.replace(" ", "%20"));
			String[] lines = html.substring(html.indexOf("<dl>"), html.indexOf("</dl>")).split("<dt>");
			for(int i=1;i<lines.length;i++)
				requestResponse.add(lines[i].replaceAll("\\<.*?>","").replaceAll("\\&.?t;", "").replaceAll("\t", " ").trim());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private String getWebRequestPost(String url) throws IOException {
		java.net.HttpURLConnection httpConnect = (java.net.HttpURLConnection) new URL(url).openConnection();
		httpConnect.setRequestMethod("POST");
		httpConnect.setDoOutput(true);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnect.getInputStream()));
		final StringBuffer stringBuffer = new StringBuffer();
		String line;
		while ((line = reader.readLine()) != null)
			stringBuffer.append(line);
		reader.close();
		return stringBuffer.toString();
	}

	public ArrayList<String> getRequestResponse() {
		return requestResponse;
	}

}