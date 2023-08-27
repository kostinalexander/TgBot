package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegrambot.model.NotificationTask;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationTask,Long> {




    @Query("SELECT nt from NotificationTask nt " +
    "WHERE YEAR(nt.data) = ?1 " +
    "AND MONTH(nt.data) = ?2 " +
    "AND DAY(nt.data) = ?3 " +
    "AND HOUR(nt.data) = ?4 " +
    "AND MINUTE(nt.data) = ?5")
    List<NotificationTask> findByDataYearMonthDayHourMinute(int year, int month, int day, int hour, int minute);
}
