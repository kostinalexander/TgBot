package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.ResponseParameters;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import okhttp3.ResponseBody;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final NotificationRepository repository;

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    public TelegramBotUpdatesListener(NotificationRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            for (int i = 0; i < updates.size() ; i++) {
                if(update.message().text().equals("/start")){
                    SendMessage message = new SendMessage(update.message().chat().id(), "Здравствуйте");
                    SendResponse response = telegramBot.execute(message);
                }


                // 01.04.2023 21:00 привет

                // ([0-9\.\:\s]{16}) - 01.04.2023 21:00 - 16 - group 1


                // (\s) - " "              - group 2
                // ([\W+]+)  - привет        - group 3
                Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
                CharSequence string= update.message().text();
                Matcher matcher = pattern.matcher(string);
                Long chatId = update.message().chat().id();
                if(matcher.matches()){

                    String dateTimeString = matcher.group(1);
                    String textMessage = matcher.group(3);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                    LocalDateTime date = LocalDateTime.parse(dateTimeString, formatter);

                    NotificationTask notificationTask = new NotificationTask(date,chatId,textMessage);

                    repository.save(notificationTask);
//
                }


            }

            // Process your updates here

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;

    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void retrieveRowsWithCurrentDate() {

        System.out.println("retrieveRowsWithCurrentDate работает");

        LocalDateTime currentDateTime = LocalDateTime.now();

        int year = currentDateTime.getYear();
        int month = currentDateTime.getMonthValue();
        int day = currentDateTime.getDayOfMonth();
        int hour = currentDateTime.getHour();
        int minute = currentDateTime.getMinute();
        List<NotificationTask> tasks = repository.findByDataYearMonthDayHourMinute(year, month, day, hour, minute);

        for (NotificationTask task : tasks) {
            System.out.println(task.getTextMessage());
            SendMessage message = new SendMessage(task.getChat_id(), task.getTextMessage());
            SendResponse response = telegramBot.execute(message);
            System.out.println(response.errorCode());

        }

         }



    }







