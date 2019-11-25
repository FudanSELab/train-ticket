package other.entitytest;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import other.entity.TrainType;

import static org.junit.jupiter.api.Assertions.*;

class TrainTypeTest {

    @Test
    void getNameByCode() {

        String test1 = TrainType.getNameByCode(0);
        String test2 = TrainType.getNameByCode(1);
        String test3 = TrainType.getNameByCode(2);
        String test4 = TrainType.getNameByCode(3);
        String test5 = TrainType.getNameByCode(4);
        String test6 = TrainType.getNameByCode(5);
        String test7 = TrainType.getNameByCode(6);
        String test8 = TrainType.getNameByCode(7);
        String test9 = TrainType.getNameByCode(8);
        String test10 = TrainType.getNameByCode(9);

        Assert.assertEquals(test1,"G");
        Assert.assertEquals(test2,"D");
        Assert.assertEquals(test3,"C");
        Assert.assertEquals(test4,"Z");
        Assert.assertEquals(test5,"T");
        Assert.assertEquals(test6,"K");
        Assert.assertEquals(test7,"L");
        Assert.assertEquals(test8,"Y");
        Assert.assertEquals(test9,"S");
        Assert.assertEquals(test10,"");



    }
}