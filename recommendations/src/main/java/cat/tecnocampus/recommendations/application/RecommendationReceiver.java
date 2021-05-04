package cat.tecnocampus.recommendations.application;

import cat.tecnocampus.recommendations.configuration.RecommendationReceiverChannel;
import cat.tecnocampus.recommendations.domain.Recommendation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(RecommendationReceiverChannel.class)
public class RecommendationReceiver {

    private static Logger logger = LoggerFactory.getLogger(RecommendationReceiver.class);
    private ApplicationController applicationController;

    public RecommendationReceiver(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @StreamListener(RecommendationReceiverChannel.RECOMMENDATION_CHANNEL)
    public void recommendationReceiver(Event payload) {
        System.out.println("----Recommendation Service");
        System.out.println(payload.toString());
        //logger.info(payload.toString());

        ObjectMapper mapper = new ObjectMapper();
        Recommendation recommendation = mapper.convertValue(payload.getData(), Recommendation.class);

        System.out.println("\tid" + recommendation.getId());
        System.out.println("\tproductId" + recommendation.getProductId());
        System.out.println("\tauthor" + recommendation.getAuthor());
        System.out.println("\trate" + recommendation.getRate());
        System.out.println("\tcontent" + recommendation.getContent());

        switch(payload.getEventType()){
            case CREATE:
                applicationController.createRecommendation(recommendation);
                break;
            case DELETE:
                applicationController.deleteRecommendation(recommendation.getProductId());
                break;
            default:
                System.out.println("Event received is neither CREATE nor DELETE");
                break;
        }
    }
}