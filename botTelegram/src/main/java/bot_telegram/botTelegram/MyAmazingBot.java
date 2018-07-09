package bot_telegram.botTelegram;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import Utils.Consts;
import Utils.InlineKeyboardsBuilder;
import Utils.Jsoup_HTML;
import Utils.User;
import Utils.UserList;
import Utils.Utils;

public class MyAmazingBot extends TelegramLongPollingBot {
	public UserList userListObject = UserList.getInstanceUser();
	public List<User> user_List = userListObject.getUserList();
	User current_user = null;
	Jsoup_HTML Jsoup_HTML = new Jsoup_HTML();

	public String citta = "";
	public String cerca = "";
	public String path = "https://www.subito.it/annunci-veneto/vendita/usato/" + citta + cerca;
	public float price;
	public String sms = "";

	public String getBotUsername() {
		return "tomasmalibot";
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return "502596920:AAGXj1omTxPldCElns1Wiw965LqslMSKBHw";
	}

	public void onUpdateReceived(Update update) {
		// method that insert a user in the list if this one doesn't exsist
		current_user = new User(update);
		userListObject.addUser(current_user);
		// All the text message
		if (update.hasMessage())
			execHasMessage(update);
		else if (update.hasCallbackQuery())
			execHasCallbackQuery(update);
	}

	public void execHasMessage(Update update) {
		if (update.getMessage().hasText()) {
			switch (update.getMessage().getText()) {
			case Consts.Subito:
				getCitta(update);
				break;
			case Consts.Timer:
				giveMeNotification(update);
				break;

			default:
				sms = update.getMessage().getText();
				checkIfSearch(update.getMessage().getText());
				break;
			}

		}
	}

	public void execHasCallbackQuery(Update update) {

		switch (current_user.getInlineKeyboardData()) {
		case Consts.TUTTAPROVINCIA:
			citta = "";
			break;
		case Consts.BELLUNO:
			citta = Consts.BELLUNO;
			break;
		case Consts.PADOVA:
			citta = Consts.PADOVA;
			break;
		case Consts.ROVIGO:
			citta = Consts.ROVIGO;
			break;
		case Consts.TREVISO:
			citta = Consts.TREVISO;
			break;
		case Consts.VENEZIA:
			citta = Consts.VENEZIA;
			break;
		case Consts.VERONA:
			citta = Consts.VERONA;
			break;
		case Consts.VICENZA:
			citta = Consts.VICENZA;
			break;

		default:

			break;
		}

		EditMessageText new_message = current_user.composeTextToSendBack(
				"Scrivi '#' poi subito l'articolo che desideri cercare e premi invio");

		// new_message.setReplyMarkup(new InlineKeyboardsBuilder().composeInlineKeyboard_Prova2());

		try {
			execute(new_message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}

	public void getCitta(Update update) {

		SendMessage message = current_user.composeMessage("Scegli Città");

		message.setReplyMarkup(new InlineKeyboardsBuilder().composeInlineKeyboard_Citta());
		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	public void checkIfSearch(String aux) {
		if (aux.contains("#")) {
			path = "https://www.subito.it/annunci-veneto/vendita/usato/" + citta + "?q=" + aux.substring(1) + "&";
			try {
				execute(current_user.composeMessage("Scrivi il prezzo massimo da cercare"));
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
		if (Utils.isNumeric(aux)) {
			price = Float.parseFloat(aux);

			try {
				String x = Jsoup_HTML.getOggi(path, price);
				System.out.println(x);
				SendMessage message = current_user.composeMessage(x);
				execute(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void giveMeNotification(Update update) {

		try {
			execute(current_user.composeMessage("Il sistema di notificazione giornaliera è attivato"));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		Runnable helloRunnable = new Runnable() {
			public void run() {
				checkIfSearch(sms);
				System.out.println("Hello world");
			}
		};

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(helloRunnable, 0, 5, TimeUnit.MINUTES);
	}

}
