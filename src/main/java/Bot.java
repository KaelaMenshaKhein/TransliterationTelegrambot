import com.ibm.icu.text.Transliterator;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Bot extends TelegramLongPollingBot {
    //создаем две константы, присваиваем им значения токена и имя бота соответсвтенно
    final private String BOT_TOKEN = "6151067801:AAHvytTg5VYamseNt14sv76NoCKYiP0FbJw";
    final private String BOT_NAME = "Transliter_bot";
    Storage storage;
    private String incoming;

    Bot() {
        storage = new Storage();
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                //Извлекаем из объекта сообщение пользователя
                Message inMess = update.getMessage();
                //Достаем из inMess id чата пользователя
                String chatId = inMess.getChatId().toString();
                //Получаем текст сообщения пользователя, отправляем в написанный нами обработчик
                String response = parseMessage(inMess.getText());
                //Создаем объект класса SendMessage - наш будущий ответ пользователю
                SendMessage outMess = new SendMessage();

                //Добавляем в наше сообщение id чата а также наш ответ
                outMess.setChatId(chatId);
                outMess.setText(response);

                //Отправка в чат
                execute(outMess);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String parseMessage(String textMsg) {
        String response = "Твой результат: ";

        // Сравниваем текст пользователя с нашими командами, на основе этого формируем ответ
        if (textMsg.equals("/start")) {
            response = "Hey Bob. Введи что хочешь транслитерировать с помощью команды /get. Например: /get Съешь ещё этих мягких французских булок, да выпей же чаю.";
        } else if (textMsg.startsWith("/get ")) {
            String userInput = textMsg.substring(5);
            Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");
            String out = toLatinTrans.transliterate(userInput);
            response = "Твой результат: " + out;
        }
        return response;
    }
}
