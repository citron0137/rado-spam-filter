package spamfilter;


import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpamFilter {
    public boolean isSpam(String context, String[] spamLinkDomains, int redirectionDepth){
        // 1. find url link from the context
        Set<String> urlLinks = new HashSet<>();
        String urlRegex = "https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)";
        Pattern urlPattern = Pattern.compile(urlRegex);
        Matcher matcher = urlPattern.matcher(context);
        while(matcher.find()) urlLinks.add(matcher.group());
        // 2. for 10 times parse urls or redirect urls (BFS)
        // 3. if any of the urls contains string in spamLinkDomains return true
        // else return false

        return false;
    }


}
