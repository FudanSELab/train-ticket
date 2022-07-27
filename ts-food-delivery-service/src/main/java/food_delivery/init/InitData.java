package food_delivery.init;

import food_delivery.service.FoodDeliveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import edu.fudan.common.entity.Food;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    FoodDeliveryService foodDeliveryService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InitData.class);


    @Override
    public void run(String... args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/food_delivery_orders.txt")));
        try {  // 有限制，不能随意init初始化数据，因此先注释掉。
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] strs = line.trim().split("\\|");
//                String[] foods = strs[1].trim().split(",");
//                List<Food> foodList = new ArrayList<>();
//                for (String food : foods) {
//                    String[] food_info = food.trim().split("_");
//                    foodList.add(new Food(food_info[0], Double.parseDouble(food_info[1])));
//                }
//                FoodDeliveryOrder foodDeliveryOrder = new FoodDeliveryOrder(
//                        UUID.randomUUID().toString(),       // id
//                        strs[0],                            // stationFoodStoreId
//                        foodList,                           // foodList
//                        strs[2],                            // tripId
//                        Integer.parseInt(strs[3]),          // seatNo
//                        strs[4],                            // createdTime
//                        strs[5],                            // deliveryTime
//                        Double.parseDouble(strs[6])         // deliveryFee
//                );
//                foodDeliveryService.createFoodDeliveryOrder(foodDeliveryOrder, null);
//            }
        } catch(Exception e) {
            InitData.LOGGER.info("food_delivery_orders.txt has format error!");
            InitData.LOGGER.error(e.getMessage());
            System.exit(1);
        }
    }
}
