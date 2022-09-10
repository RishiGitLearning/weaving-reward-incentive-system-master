package com.technoride.RewardIncentiveSystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.*;

@SpringBootApplication
public class RewardIncentiveSystemApplication implements CommandLineRunner {
	public static Logger logger;
	public static void main(String[] args) {
		SpringApplication.run(RewardIncentiveSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createlogger();
		logger= Logger.getGlobal();
	}

	public static void createlogger() throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy-HHmmss");
		Calendar cal = Calendar.getInstance();
		for(Handler hh: Logger.getGlobal().getHandlers()){
			Logger.getGlobal().removeHandler(hh);
		}
		Logger.getGlobal().setLevel(Level.INFO);
		Handler fileHandler = new FileHandler( "log/"+sdf.format(Calendar.getInstance().getTime()) + ".log");
		fileHandler.setFormatter(new Formatter() {
			@Override
			public String format(LogRecord record) {
				StringBuffer buffer = new StringBuffer();
				SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy HH:mm:ss.SSS");
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(record.getMillis());
				buffer.append(sdf.format(cal.getTime()));
				buffer.append(":");
				buffer.append(record.getLevel().getName());
				buffer.append("::");
				buffer.append(record.getMessage());
				buffer.append("\n");
				return buffer.toString();
			}
		});
		Logger.getGlobal().addHandler(fileHandler);
	}

}
