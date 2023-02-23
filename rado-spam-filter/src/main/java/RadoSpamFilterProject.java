import spamfilter.SpamFilter;

import java.util.List;

public class RadoSpamFilterProject {
    public static void main(String[] args){
        List<String> spamLinkDomains = List.of();
        String context = "https://moiming.page.link/exam?_imcp=1, https://moiming.page.link/exam?_imcp=2";
        boolean result = new SpamFilter().isSpam(context, spamLinkDomains, 1);
        System.out.println(result);

    }
}
