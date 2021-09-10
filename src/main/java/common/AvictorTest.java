package common;

import com.googlecode.aviator.AviatorEvaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvictorTest {

    public static void main(String[] args) {
        List<String> labels = new ArrayList<>();
        labels.add("1");
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("labels", labels);
        System.out.println(AviatorEvaluator.execute("(labels != nil)&&(count(labels)>0)", env));
    }

}
