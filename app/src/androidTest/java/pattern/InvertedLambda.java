package pattern;

import java.util.ArrayList;
import java.util.List;


public class InvertedLambda implements Patterns {
    List<Boolean> pattern = new ArrayList<Boolean>(){{
        add(false);
        add(false);
        add(false);
        add(true);
        add(false);
        add(false);
        add(true);
        add(false);
        add(false);
        add(true);
        add(false);
        add(false);
        add(true);
        add(false);
        add(true);
        add(false);
        add(false);
        add(true);
        add(false);
        add(false);
        add(true);
        add(false);
        add(true);
        add(false);
        add(false);
        add(false);
        add(false);
        add(true);
    }};


    @Override
    public List<Boolean> setPattern() {

        return pattern;
    }
}
