package Utils;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;

public  class User {
	
	private String firstName = "";
	private String lastName = "";
	private long chatId = 0;
	private long userId = 0;
	private String messageText = "";
	long message_id = 0;
    long chat_id = 0;
    private String inlineKeyboardData = "";
	
	
	public User(Update update) {
		if(update.hasMessage()) {
			firstName = update.getMessage().getChat().getFirstName();
			lastName =  update.getMessage().getChat().getLastName();
			chatId = update.getMessage().getChatId();
			userId = update.getMessage().getChat().getId();
			messageText = update.getMessage().getText();
		}else
			if(update.hasCallbackQuery()) {
				firstName = update.getCallbackQuery().getMessage().getChat().getFirstName();
				lastName =  update.getCallbackQuery().getMessage().getChat().getLastName();
				chatId = update.getCallbackQuery().getMessage().getChatId();
				userId = update.getCallbackQuery().getMessage().getChat().getId();
				messageText = update.getCallbackQuery().getMessage().getText();
				
				message_id = update.getCallbackQuery().getMessage().getMessageId();
				chat_id = update.getCallbackQuery().getMessage().getChatId();
				inlineKeyboardData = update.getCallbackQuery().getData();
			}
		
	}

	public long getMessage_id() {
		return message_id;
	}

	public void setMessage_id(long message_id) {
		this.message_id = message_id;
	}

	public long getChat_id() {
		return chat_id;
	}

	public void setChat_id(long chat_id) {
		this.chat_id = chat_id;
	}

	public SendMessage composeMessage( String text) {
		return new SendMessage() 
        .setChatId(this.getChatId())
        .setText(text);
	}
	
	public EditMessageText composeTextToSendBack(String text) {
		 return new EditMessageText()
                 .setChatId(this.getChatId())
                 .setMessageId( (int) (this.getMessage_id()))
                 .setText(text);
	}
	
	

	
	

	public String getInlineKeyboardData() {
		return inlineKeyboardData;
	}

	public void setInlineKeyboardData(String inlineKeyboardData) {
		this.inlineKeyboardData = inlineKeyboardData;
	}

	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public long getChatId() {
		return chatId;
	}


	public void setChatId(long chatId) {
		this.chatId = chatId;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public String getMessageText() {
		return messageText;
	}


	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	


}
