package spamfilter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

class SpamFilterJobManager implements SpamLinkSearchDoneEventListener {
    private final static Logger LOG = Logger.getGlobal();

    boolean exist = false;
    SpamFilterJobExecutor spamFilterJobExecutor = new SpamFilterJobExecutor();

    ExecutorService threadPool = Executors.newCachedThreadPool();

    public boolean searchSpam(List<String> urls, List<String> spamUrls, int maxDepth){
        LOG.log(Level.INFO,"searchSpam Started in ["+Thread.currentThread().getName()+"]");
        this.spamFilterJobExecutor.addDoneListener(this);
        try {
            for (String url : urls)
                threadPool.submit(() ->this.spamFilterJobExecutor.searchSpamUrlFromUrl(url,1,spamUrls, maxDepth));
            boolean awaitSuccess = threadPool.awaitTermination(60, TimeUnit.SECONDS);
            LOG.log(Level.INFO,"searchSpam await returned :"+awaitSuccess);
            return exist;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void spamLinkNotFound(List<String> containedUrls, int finishedDepth, List<String> spamUrls, int maxDepth) {
        LOG.log(Level.INFO,"spamLinkNotFound Started in ["+Thread.currentThread().getName()+"]");
        if (finishedDepth >= maxDepth) return;
//        LOG.log(Level.INFO,"spamLinkNotFound Check1 in ["+Thread.currentThread().getName()+"]");
        for (String containedUrl : containedUrls)
            threadPool.submit(() -> this.spamFilterJobExecutor.searchSpamUrlFromUrl(containedUrl,finishedDepth+1, spamUrls,  maxDepth));
        LOG.log(Level.INFO,"spamLinkNotFound Finished in ["+Thread.currentThread().getName()+"]");
    }
    @Override
    public void spamLinkFound() {
        LOG.log(Level.INFO,"spamLinkFound Started in ["+Thread.currentThread().getName()+"]");
        this.exist = true;
        threadPool.shutdownNow();
    }
}
