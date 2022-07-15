package trainFood.init;

import edu.fudan.common.entity.Food;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import trainFood.entity.TrainFood;
import trainFood.service.TrainFoodService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class InitData implements CommandLineRunner{

    @Autowired
    TrainFoodService trainFoodService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InitData.class);


    @Override
    public void run(String... args) throws Exception {
        BufferedReader br2 = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/trainfood.txt")));
        try{
            String line2 = br2.readLine();
            while( line2 != null ){
                if( !line2.trim().equals("") ){
                    TrainFood tf = new TrainFood();
                    tf.setId(UUID.randomUUID().toString());
                    String[] lineTemp = line2.trim().split("=");
                    tf.setTripId(lineTemp[1]);
                    lineTemp = br2.readLine().trim().split("=");
                    tf.setFoodList(toFoodList(lineTemp[1]));
                    trainFoodService.createTrainFood(tf,null);
                }
                line2 = br2.readLine();
            }

        } catch(Exception e){
            InitData.LOGGER.info("the trainfood.txt has format error!");
            InitData.LOGGER.error(e.getMessage());
            System.exit(1);
        }
    }

    private List<Food> toFoodList(String s){
        InitData.LOGGER.info("s= {}", s);
        String[] foodstring = s.split("_");
        List<Food> foodList = new ArrayList<>();
        for(int i = 0; i< foodstring.length; i++){
            String[] foodTemp = foodstring[i].split(",");
            Food food = new Food();
            food.setFoodName(foodTemp[0]);

            food.setPrice(Double.parseDouble(foodTemp[1]));

            foodList.add(food);
        }
        return foodList;
    }
}
