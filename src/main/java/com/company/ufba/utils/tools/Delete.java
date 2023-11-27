package com.company.ufba.utils.tools;


import org.springframework.cglib.core.Local;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.logging.Logger;

public class Delete extends Thread {
    final Logger log = Logger.getLogger("DELETE");
    final Path path = Path.of("PDF/");
    final int Twenty_Four_Hours = 86400000;
    public Delete(Path path) throws IOException {
        Files.deleteIfExists(path);
        log.info("DELETE:" + path.toUri());
    }

    public Delete() {
    }

    @Override
    public void run() {
        while (true) {
            log.info("Clean pdf of system. " + LocalDate.now(ZoneId.systemDefault()));
            try {
                Files.list(path).filter(file -> file.getFileName().toString().split("_")[1].equals(LocalDate.now(ZoneId.systemDefault()).toString()))
                        .forEach(file-> {
                            try {
                                 Files.deleteIfExists(file);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                sleep(Twenty_Four_Hours);
            } catch (InterruptedException e) {
                new Delete().start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ;
        }
    }
}
