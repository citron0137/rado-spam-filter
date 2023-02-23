package spamfilter;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpamFilter {
    public boolean isSpam(String context, String[] spamLinkDomains, int redirectionDepth){
        // 1. find url link from the context
        List<String> strings = parseUrlsFromText(context);
        // 2. for 10 times parse urls or redirect urls (BFS)
        String contentFromUrl = getContentFromUrl(strings.get(0));
        List<String> urls2 = parseUrlsFromText(contentFromUrl);
        for (String s : urls2) {System.out.println(s);}
        // 3. if any of the urls contains string in spamLinkDomains return true
        // else return false
        return false;
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
