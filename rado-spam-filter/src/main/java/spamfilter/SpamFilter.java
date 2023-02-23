package spamfilter;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SpamFilter {
    private final static Logger LOG = Logger.getGlobal();

    public boolean isSpam(String context, List<String> spamLinkDomains, int redirectionDepth){
        // 1. find url link from the context
        List<String> strings = parseUrlsFromText(context);
        // 2. for 10 times parse urls or redirect urls
        SpamFilterJobManager spamFilterJobManager = new SpamFilterJobManager();
        return spamFilterJobManager.searchSpam(strings, spamLinkDomains, redirectionDepth);
        // 3. if any of the urls contains string in spamLinkDomains return true
        // else return false
    }

    List<String> parseUrlsFromText(String text){
        Set<String> urlLinks = new HashSet<>();
        String urlRegex = "https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)";
        Pattern urlPattern = Pattern.compile(urlRegex);
        Matcher matcher = urlPattern.matcher(text);
        while(matcher.find()) urlLinks.add(matcher.group());
        return new ArrayList<>(urlLinks);
    }
    String getContentFromUrl(String urlString){
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
            e.printStackTrace();
            return null;
        }
    }




}
