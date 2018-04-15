package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.FeedResponse;
import data.Message;
import data.NewsAgentResponse;
import services.FeedService;
import services.NewsAgentService;

import java.util.UUID;

public class MessageActor extends UntypedActor {
    private final ActorRef out;

    //self-reference the actor
    public MessageActor(ActorRef out) {
        this.out = out;
    }

    //props
    public static Props props(ActorRef out) {
        return Props.create(MessageActor.class, out);
    }

    public FeedService feedService = new FeedService();
    public NewsAgentService newsAgentService = new NewsAgentService();
    //object of feed service
    //object of newsAgentservice
    //define another actor reference
        public NewsAgentResponse newsAgentResponse=new NewsAgentResponse();
        private FeedResponse feedResponse=new FeedResponse();
    @Override
    public void onReceive(Object message) throws Throwable {
        //send back the response
        ObjectMapper objectMapper = new ObjectMapper();
        if (message instanceof String) {
            Message messageObject = new Message();
                messageObject.text=(String)message;
                messageObject.sender=Message.Sender.USER;
            out.tell(objectMapper.writeValueAsString(messageObject), self());
            String query = newsAgentService.getNewsAgentResponse("Find " + message, UUID.randomUUID()).query;
            feedResponse=feedService.getFeedByQuery(query);
            messageObject.text = (feedResponse.title == null) ? "No results found" : "Showing results for: " +query;
            messageObject.feedResponse=feedResponse;
            messageObject.sender=Message.Sender.BOT;
            out.tell(objectMapper.writeValueAsString(messageObject), self());
        }
    }
}