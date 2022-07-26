package food.init;

import edu.fudan.common.entity.Food;
import food.entity.StationFoodStore;
import food.service.StationFoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class InitData implements CommandLineRunner{

    @Autowired
    StationFoodService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(InitData.class);


    @Override
    public void run(String... args) throws Exception {

        BufferedReader br1 = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/foodstores.txt")));
        try{
            String line = br1.readLine();
            while( line != null ){
                if( !line.trim().equals("") ){
                    StationFoodStore fs = new StationFoodStore();
                    fs.setId(UUID.randomUUID().toString());
                    String[] lineTemp = line.trim().split("=");
                    fs.setStationName(lineTemp[1]);

                    lineTemp = br1.readLine().trim().split("=");
                    fs.setStoreName(lineTemp[1]);

                    lineTemp = br1.readLine().trim().split("=");
                    fs.setTelephone(lineTemp[1]);

                    lineTemp = br1.readLine().trim().split("=");
                    fs.setBusinessTime(lineTemp[1]);

                    lineTemp = br1.readLine().trim().split("=");
                    fs.setDeliveryFee( Double.parseDouble(lineTemp[1]) );

                    lineTemp = br1.readLine().trim().split("=");

                    fs.setFoodList(toFoodList(lineTemp[1]));
                    service.createFoodStore(fs,null);
                }
                line = br1.readLine();
            }

        } catch(Exception e){
            InitData.LOGGER.info("the foodstores.txt has format error!");
            InitData.LOGGER.error(e.getMessage());
            System.exit(1);
        }

//        BufferedReader br2 = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/trainfood.txt")));
//        try{
//            String line2 = br2.readLine();
//            while( line2 != null ){
//                if( !line2.trim().equals("") ){
//                    TrainFood tf = new TrainFood();
//                    tf.setId(UUID.randomUUID());
//                    String[] lineTemp = line2.trim().split("=");
//                    tf.setTripId(lineTemp[1]);
//                    lineTemp = br2.readLine().trim().split("=");
//                    tf.setFoodList(toFoodList(lineTemp[1]));
//                    service.createTrainFood(tf,null);
//                }
//                line2 = br2.readLine();
//            }
//
//        } catch(Exception e){
//            InitData.LOGGER.info("the trainfood.txt has format error!");
//            InitData.LOGGER.error(e.getMessage());
//            System.exit(1);
//        }
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
