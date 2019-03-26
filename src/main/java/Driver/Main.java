package Driver;

import Mail.MailHandler;

public class Main {

    public static void main(String[] args){

        assert args.length==6;

        new MailHandler(args[0],args[1],args[2],args[3],args[4],args[5]);
    }
}



