package spamfilter;

import java.util.List;

interface SpamLinkSearchDoneEventListener {
    void spamLinkNotFound(List<String> containedUrls,int finishedDepth, List<String> spamUrls, int maxDepth);
    void spamLinkFound();
}
