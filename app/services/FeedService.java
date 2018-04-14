package services;

import data.FeedResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import play.libs.XML;
import play.libs.ws.WS;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class FeedService {
    public FeedResponse getFeedByQuery(String query){
        FeedResponse feedResponseObject =new FeedResponse();
        try
        {
           WSRequest feedRequest=WS.url("https://news.google.com/newss");
            CompletionStage<WSResponse> responsePromise=feedRequest
                    .setQueryParameter("q",query)
                    .setQueryParameter("output","rss")
                    .get();
            Document feedresponse=responsePromise.thenApply(WSResponse::asXml).toCompletableFuture().get();
            Node item=feedresponse.getFirstChild().getChildNodes().item(10);
            feedResponseObject.title=item.getChildNodes().item(0).getNodeValue();
            feedResponseObject.pubDate=item.getChildNodes().item(3).getNodeValue();
            feedResponseObject.description=item.getChildNodes().item(4).getNodeValue();



        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return feedResponseObject;
    }

}
