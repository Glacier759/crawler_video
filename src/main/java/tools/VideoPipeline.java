package tools;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.util.Map;

/**
 * Created by only. on 2015/3/3.
 */
public class VideoPipeline extends FilePersistentBase implements Pipeline {
    private String directory = null;
    /**
     * create a PutFiles with default path"/data/webmagic/"
     */
    public VideoPipeline() {
        setPath("/data/video/");
    }

    public VideoPipeline(String path) {
        setPath(path);
    }

    public void process(ResultItems resultItems, Task task) {
        directory = resultItems.getRequest().getExtra("videoName").toString();
        Download download=null;
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if (entry.getValue() instanceof Iterable) {
                Iterable value = (Iterable) entry.getValue();
                for (Object o : value) {
                    try {
                        download=  new Download((Request)o,getPath() + directory,entry.getKey());
                        download.start();
                        download.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

