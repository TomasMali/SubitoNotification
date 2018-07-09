package bot_telegram.botTelegram;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	// Jsoup_HTML.getCitta();

		ApiContextInitializer.init();

		// Instantiate Telegram Bots API
		TelegramBotsApi botsApi = new TelegramBotsApi();

		// Register our bot
		try {
			botsApi.registerBot(new MyAmazingBot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
    }
}
