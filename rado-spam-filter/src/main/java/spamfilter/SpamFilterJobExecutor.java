package spamfilter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Logger;

public class SpamFilterJobExecutor {
    private final static Logger LOG = Logger.getGlobal();

    SpamLinkSearchDoneEventListener listener;

    public void addDoneListener(SpamLinkSearchDoneEventListener listener){
        this.listener = listener;
    }

    public void searchSpamUrlFromUrl(String url, int dept,  List<String> spamUrls,int maxDepth ){
        LOG.log(Level.INFO,"searchSpamUrlFromUrl ("+url+", "+dept+") Started in ["+Thread.currentThread().getName()+"]");
        List<String> foundUrl =  parseUrlsFromText(getContentFromUrl(url));

        if(false) {// TODO Check IF FOUNDED
            this.listener.spamLinkFound();
        }
        this.listener.spamLinkNotFound(foundUrl, dept, spamUrls,  maxDepth);
    }
    String getContentFromUrl(String urlString){
//        LOG.log(Level.INFO,"getContentFromUrl("+urlString+") Started in ["+Thread.currentThread().getName()+"]");
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) body.append(line);
//                System.out.println(line);
            return body.toString();
        }catch (Exception e){
//            e.printStackTrace();
            return null;
        }
    }

    List<String> parseUrlsFromText(String text){
        Set<String> urlLinks = new HashSet<>();
        String urlRegex = "https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)";
        Pattern urlPattern = Pattern.compile(urlRegex);
        Matcher matcher = urlPattern.matcher(text);
        while(matcher.find()) urlLinks.add(matcher.group());
        return new ArrayList<>(urlLinks);
    }

}
