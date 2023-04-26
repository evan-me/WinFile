package com.evan.winfile;

import com.evan.winfile.core.MainAppApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WinfileApplication extends MainAppApplication {

    public static void main(String[] args) {
        run(WinfileApplication.class, args);
    }

}
