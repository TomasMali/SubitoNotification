package Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Jsoup_HTML {

	public static List<String> getCitta() {
		List<String> list = new ArrayList<String>();
		try {

			Document doc = Jsoup.connect("https://www.subito.it/annunci-veneto/vendita/usato/").userAgent(
					"Mozilla/17.0").get();

			int i = 0;
			Elements temp = doc.select("select#searcharea option");
			list.add("Tutta la provincia");
			for (Element uno : temp) {
				if (i > 3) {
					System.out.println(uno.text());
					list.add(uno.text());
				}
				i++;
			}

		} catch (

		IOException e) {
			e.printStackTrace();
		}
		return list;
		// str.substring(0, str.length() - 1)
	}

	public String getOggi(String path, float price) throws IOException {
		String query = "";

		Document doc = Jsoup.connect(path).userAgent("Mozilla/17.0").get();
		Elements temp = doc.select("ul.items_listing li");

		int i = 0;
		for (Element uno : temp.select("div.item_list_section.item_description")) {
			String html_price = uno.select("span.item_price").text();
			String original_price = html_price;
			html_price = html_price.replace(".", "");
			html_price = html_price.isEmpty() ? "000" : html_price;

			String time = uno.select("span.item_info").select("time").text();
			i++;
			System.out.println(uno.select("span.item_info").select("time").text());
			if (Float.parseFloat(html_price.substring(0, html_price.length() - 2)) <= price && !original_price.isEmpty()
					&& (time.contains("Oggi") || time.contains("Ieri"))) {
				String x = String.valueOf(i);
				query += x + "\n" + "Titolo: " + uno.select(" h2 a").text() + "\n" + "Prezzo: " + uno.select(
						"span.item_price").text() + "\n" + "Time: " + time + "\n";
			}
		}
		return query;

	}

}
