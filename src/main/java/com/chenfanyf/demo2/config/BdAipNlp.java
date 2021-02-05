package com.chenfanyf.demo2.config;

import com.baidu.aip.nlp.AipNlp;

public class BdAipNlp {

    public static final String APP_ID = "23564540";
    public static final String API_KEY = "fNOV7DrsM8jSlfVDGk9cmGEK";
    public static final String SECRET_KEY = "bDCOgKRCNlzBTeQaELSDht502A0G0vZG";

    private static BdAipNlp bdAipNlp = new BdAipNlp();
    private static AipNlp client;

    private BdAipNlp(){

        client =new AipNlp(APP_ID,API_KEY,SECRET_KEY);

    }

    public static BdAipNlp getInstance(){
        return bdAipNlp;
    }

    public AipNlp getAipNlp(){


        return client;


    }
}
